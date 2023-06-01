package it.polimi.ingsw;

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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp extends UnicastRemoteObject implements ServerAbst {
    private static ServerApp instance = null;
    private static Server s = null;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private static String serverIP;
    private boolean fromScratch;

    protected ServerApp() throws RemoteException {
    }

    public static ServerApp getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServerApp();
        }
        return instance;
    }

    public Server getS() {
        return s;
    }

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
        //RMI
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
    private static void startRMI(int port) throws RemoteException {
        System.out.println("RMI started");
        ServerApp instanceRMI = getInstance();
        System.setProperty("java.rmi.server.hostname", serverIP);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("server", instanceRMI);
    }

    //SOCKET start
    public static void startSocket(int port) throws RemoteException {
        System.out.println("Socket started");
        ServerApp instanceSocket = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                instanceSocket.executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        Server server = getInstance().connect();
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
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
    @Override
    public Server connect() throws RemoteException {
        File f = new File("status.json");
        boolean fromScratch = !f.exists() && f.length() == 0;
        if (s == null)
            if (fromScratch)
                s = new ServerImpl(true);
            else s = new ServerImpl(false);

        return s;
    }
}

