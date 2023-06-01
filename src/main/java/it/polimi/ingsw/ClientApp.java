package it.polimi.ingsw;

import it.polimi.ingsw.network.client.ClientImpl;
import it.polimi.ingsw.network.client.ServerStub;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

public class ClientApp {
    public static void main( String[] args ) throws RemoteException, NotBoundException, SocketException, IOException {
        Scanner terminal = new Scanner(System.in);
        int networkClient = -1;
        int typeView = -1;
        String localIP = "";
        Integer port = 0;

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

                System.out.format("ni: %s, ia: %s\n", interface_, address);
                localIP = address.toString().substring(1);

                // stops at the first *working* solution
                break OUTER;
            }
        }

        System.out.print("Enter server IP address: ");
        String ip = terminal.next();
        System.out.println("Server IP address is: " + ip);

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
            System.setProperty("java.rmi.server.hostname", localIP);
            Registry registry = LocateRegistry.getRegistry(ip, port);
            ServerAbst server = (ServerAbst) registry.lookup("server");
            ClientImpl client = new ClientImpl(server.connect(), typeView);
            client.run();
        }
        else if(networkClient == 1){
            ServerStub serverStub = new ServerStub(ip, port);              //da modifiare per ermettere di metterlo in rete
            ClientImpl client = new ClientImpl(serverStub, typeView);
            new Thread(() -> {
                while(true) {
                    try {
                        serverStub.receive(client);
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from server. The match has been stopped :(");
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