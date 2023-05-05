package it.polimi.ingsw;

import it.polimi.ingsw.network.client.ClientImpl;
import it.polimi.ingsw.network.client.ServerStub;

import java.rmi.RemoteException;

public class AppClientSocket {
    public static void main(String[] args) throws RemoteException {
            ServerStub serverStub = new ServerStub("localhost", 1234);
            ClientImpl client = new ClientImpl(serverStub);
            new Thread() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            serverStub.receive(client);
                        } catch (RemoteException e) {
                            System.err.println("Cannot receive from server. Stopping...");
                            try {
                                serverStub.close();
                            } catch (Exception ex) {
                                System.err.println("Cannot close server stub: " + ex.getMessage());
                            }
                            System.exit(1);
                        }
                    }
                }
            }.start();

            client.run();
        }
    }


