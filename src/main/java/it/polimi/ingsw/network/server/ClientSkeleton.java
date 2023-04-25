package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {
    public ClientSkeleton(Socket socket) {
    }

    public void receive(Server server) {
    }

    @Override
    public void updateView(Server o, Message arg) throws RemoteException {

    }
}
