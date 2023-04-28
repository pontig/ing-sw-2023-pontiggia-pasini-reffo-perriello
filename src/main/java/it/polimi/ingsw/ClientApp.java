package it.polimi.ingsw;

import it.polimi.ingsw.network.client.ClientImpl;
import it.polimi.ingsw.network.client.ServerStub;
import it.polimi.ingsw.network.server.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static it.polimi.ingsw.view.CLI.*;

public class ClientApp {
    public static void main( String[] args ) throws RemoteException, NotBoundException {
        Scanner terminal = new Scanner(System.in);
        int networkClient;
        int typeView;
        int port;

        do{
            System.out.println("Please choose if you want RMI or Socket client (0 is RMI / 1 is Socket):");
            networkClient = terminal.nextInt();
        } while(networkClient != 0 && networkClient != 1);

        do{
            System.out.println("\nPlease choose if you wanna play from CLI or GUI (0 is CLI / 1 is GUI):");
            typeView = terminal.nextInt();
        } while(typeView != 0 && typeView != 1);

        System.out.println("\nPlease enter Server port number:");
        port = terminal.nextInt();

        if(networkClient == 0){
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            ServerAbst server = (ServerAbst) registry.lookup("server");
            ClientImpl client = new ClientImpl(server.connect(), typeView);
            client.run();
        }
 /*       else if(networkClient == 1){
            ServerStub serverStub = new ServerStub(URL, port);              //da modifiare per ermettere di metterlo in rete
            ClientImpl client = new ClientImpl(serverStub, typeView);
            new Thread(() -> {
                while(true) {
                    try {
                        serverStub.receive(client);
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from server. Stopping...");
                        try {
                            serverStub.close();
                        } catch (RemoteException ex) {
                            System.err.println("Cannot close connection with server. Halting...");
                        }
                        System.exit(1);
                    }
                }
            }).start();
            client.run();
        }
*/
    }
}