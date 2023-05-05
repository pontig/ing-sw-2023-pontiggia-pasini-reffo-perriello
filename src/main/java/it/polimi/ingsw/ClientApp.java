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
        int networkClient = -1;
        int typeView = -1;
        Integer port = 0;

        System.out.print("Choose if you want a RMI or Socket client -- 0 is RMI and 1 is Socket: ");
        do{
            try{
                networkClient = Integer.parseInt(terminal.next());
            } catch (NumberFormatException e) {
                System.out.println("It is not a valid number!!");
            }
            if(networkClient != 0 && networkClient != 1)
                System.out.print("Enter 0 for RMI or 1 for Socket: ");

        } while(networkClient != 0 && networkClient != 1);

        System.out.print("\nChoose if you wanna play from CLI or GUI -- 0 is CLI and 1 is GUI: ");
        do{
            try{
                typeView = Integer.parseInt(terminal.next());
            } catch (NumberFormatException e) {
                System.out.println("It is not a valid number!!");
            }
            if(typeView != 0 && typeView != 1)
                System.out.print("Enter 0 for CLI or 1 for GUI: ");
        } while(typeView != 0 && typeView != 1);

        System.out.print("\nEnter Server port number - 4 digit number: ");
        do{
            try{
                port = Integer.parseInt(terminal.next());
            } catch (NumberFormatException e) {
                System.out.println("It is not a valid number!!");
            }
            if(port.toString().length() != 4)
                System.out.print("Enter a 4 digit number only: ");
        } while(port.toString().length() != 4);
        
        if(networkClient == 0){
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            ServerAbst server = (ServerAbst) registry.lookup("server");
            ClientImpl client = new ClientImpl(server.connect(), typeView);
            client.run();
        }
        else if(networkClient == 1){
            ServerStub serverStub = new ServerStub("localhost", port);              //da modifiare per ermettere di metterlo in rete
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
    }
}