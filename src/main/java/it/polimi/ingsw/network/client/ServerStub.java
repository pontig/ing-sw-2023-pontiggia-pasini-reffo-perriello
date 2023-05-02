package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;

import java.rmi.RemoteException;

public class ServerStub implements Server {
    public ServerStub(String localhost, int i) {
    }

    public void receive(ClientImpl client) {
    }

    public void close() {
    }

    @Override
    public void register(Client client) throws RemoteException {

    }

    @Override
    public void updateModel(Client client, Message arg) throws RemoteException {

    }

}
