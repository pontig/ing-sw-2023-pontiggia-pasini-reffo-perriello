package it.polimi.ingsw;

import it.polimi.ingsw.network.server.ClientSkeleton;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp extends UnicastRemoteObject implements ServerAbst {
    private static ServerApp instance = null;
    Server s = null;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    protected ServerApp() throws RemoteException {}
    public static ServerApp getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServerApp();
        }
        return instance;
    }

    public static void main( String[] args ) throws RemoteException {
        Scanner terminal = new Scanner(System.in);
        final int portRMI;
        final int portSocket;
        System.out.println("Insert Server RMI port number:");
        portRMI = terminal.nextInt();
        System.out.println("Insert Server Socket port number:");
        portSocket = terminal.nextInt();
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
            System.out.println("\nServer working");
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
    }

//RMI start
    private static void startRMI(int port) throws RemoteException {
        System.out.println("RMI started");
        ServerApp instanceRMI = getInstance();
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
                        Server server = new ServerImpl();
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
    public Server connect() throws RemoteException {
        if(s == null)
            s = new ServerImpl();

        return s;
    }
}

