package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.ClientSkeleton;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerImpl;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.enums.State.CLIENT_DOWN;

/** ServerApp class is the main class of the server side of the application. It contains the main method and the
 *  methods to start the server and to handle the connection with the clients.
 */
public class ServerApp extends UnicastRemoteObject implements ServerAbst {
    private static final long CHECK_INTERVAL = 60000; // Interval in milliseconds between connection checks
    private static ServerApp instance = null;
    private static Server s = null;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private static String serverIP;
    private boolean fromScratch;
    private List<ClientSkeleton> socketClients = new ArrayList<>();

    protected ServerApp() throws RemoteException {
    }

    public static ServerApp getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServerApp();
        }
        return instance;
    }

    /** the Getter of the Server Interface
     *
     * @return the Server Interface
     */
    public Server getS() {
        return s;
    }

    /** Main method of the ServerApp class. It starts the server and the RMI registry.
     * It's possible to choose if start the server with RMI or Socket connection.
     *
     * It also handles the connection with the clients.
     *
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        // iterate over the network interfaces known to java
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        OUTER:
        for (NetworkInterface interface_ : Collections.list(interfaces)) {
            // we shouldn't care about loopback addresses
            if (interface_.isLoopback())
                continue;

            // if you don't expect the interface to be up you can skip this
            // though it would question the usability of the rest of the code
            if (!interface_.isUp())
                continue;

            // iterate over the addresses associated with the interface
            Enumeration<InetAddress> addresses = interface_.getInetAddresses();
            for (InetAddress address : Collections.list(addresses)) {
                // look only for ipv4 addresses
                if (address instanceof Inet6Address)
                    continue;

                // use a timeout big enough for your needs
                if (!address.isReachable(3000))
                    continue;

                // java 7's try-with-resources statement, so that
                // we close the socket immediately after use
                //try (SocketChannel socket = SocketChannel.open()) {
                //    // again, use a big enough timeout
                //    socket.socket().setSoTimeout(3000);
                //
                //    // bind the socket to your local interface
                //    socket.bind(new InetSocketAddress(address, 8080));
                //
                //    // try to connect to *somewhere*
                //    socket.connect(new InetSocketAddress("google.com", 80));
                //} catch (IOException ex) {
                //    ex.printStackTrace();
                //    continue;
                //}

                System.out.format("ni: %s, ia: %s\n", interface_, address);
                serverIP = address.toString().substring(1);

                // stops at the first *working* solution
                break OUTER;
            }
        }

        Scanner terminal = new Scanner(System.in);
        Integer port = 0;
        final int portRMI;
        final int portSocket;
        System.out.print("\nInsert Server RMI port number - 4 digit only: ");
        do {
            try {
                port = Integer.parseInt(terminal.next());
            } catch (NumberFormatException e) {
                System.out.println("It is not a valid number!!");
            }
            if (port.toString().length() != 4)
                System.out.print("Enter a 4 digit number only: ");
        } while (port.toString().length() != 4);
        portRMI = port;

        port = 0;
        System.out.print("\nInsert Server Socket port number - 4 digit only: ");
        do {
            try {
                port = Integer.parseInt(terminal.next());
            } catch (NumberFormatException e) {
                System.out.println("It is not a valid number!!");
            }
            if (port.toString().length() != 4)
                System.out.print("Enter a 4 digit number only: ");
        } while (port.toString().length() != 4);
        portSocket = port;
        /* RMI */
        Thread rmiThread = new Thread(() -> {
            try {
                startRMI(portRMI);
            } catch (RemoteException e) {
                System.err.println("Cannot start RMI. This protocol will be disabled.");
            }
        });
        rmiThread.start();
        /* SOCKET */
        Thread socketThread = new Thread(() -> {
            try {
                startSocket(portSocket);
            } catch (RemoteException e) {
                System.err.println("Cannot start socket. This protocol will be disabled.");
            }
        });
        socketThread.start();
        //Connection RMI e Socket
        try {
            rmiThread.join();
            System.out.println("\nServer working on address " + serverIP);
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
    }

    //RMI start
    /**
     * Starts the RMI (Remote Method Invocation) server on the specified port.
     * This method creates a registry, binds the server instance to a name, and sets the RMI server hostname.
     *
     * @param port The port number on which to start the RMI server.
     * @throws RemoteException If a communication-related exception occurs during the RMI operation.
     */
    private static void startRMI(int port) throws RemoteException {
        System.out.println("RMI started");
        //Get the instance of the serverApp
        ServerApp instanceRMI = getInstance();
        System.setProperty("java.rmi.server.hostname", serverIP);
        //Create the registry on the specified port
        Registry registry = LocateRegistry.createRegistry(port);
        // Bind the server instance to the "server" name in the registry
        registry.rebind("server", instanceRMI);
    }

    //SOCKET start
    /**
     * This method starts the socket server on the specified port.
     * It creates a server socket, accepts incoming client connections, and handles client communication using separate threads.
     *
     * @param port The port number on which to start the socket server.
     * @throws RemoteException If a communication-related exception occurs during the socket operation.
     */
    public static void startSocket(int port) throws RemoteException {
        System.out.println("Socket started");
         //Get the instance of the serverApp
        ServerApp instanceSocket = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                instanceSocket.executorService.submit(() -> {
                    ClientSkeleton c = null;
                    boolean stopClient;
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        Server server = getInstance().connect();
                        stopClient = server.register(clientSkeleton);
                        if(stopClient){
                            c = clientSkeleton;
                            while (true) {
                                clientSkeleton.receive(server);
                            }
                        } else {
                            System.out.println("No more players are required in this game, we are sorry, you will be disconnected");
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client: " + c + "\nClosing the connection from server all the client will be disconnected");
                        System.exit(1);
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }

    //Crea un server implementato

    /**
     * This method connects to the Server and returns an instance of the Server interface.
     * If file status.json is empty or does not exist, it creates a new Server instance from scratch.
     * A separate thread is created to monitor the connection status by pinging the client periodically.
     * @return instance of the Server interface
     * @throws RemoteException If a communication-related exception occurs during the socket operation.
     */
    @Override
    public Server connect() throws RemoteException {
        File f = new File("status.json");
        boolean fromScratch = !f.exists() || f.length() == 0;
        if (s == null) {
            if (fromScratch)
                s = new ServerImpl(true);
            else s = new ServerImpl(false);

            // Create and start a separate thread for connection monitoring, it pings the client every minute to check the server status
            Thread connectionMonitorThread = new Thread(() -> {
                while (true) {
                    try {
                        // Perform a simple operation to check the connection
                        s.ping();
                        //System.out.println("Connection is active.");
                    } catch (RemoteException e) {
                        // Connection lost, handle the situation accordingly
                        System.err.println("Cannot receive from client \nClosing the connection from server all the client will be disconnected");
                        // Terminate the client
                        System.exit(1);
                    }

                    try {
                        Thread.sleep(CHECK_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            connectionMonitorThread.start();
        }

        return s;
    }
}

