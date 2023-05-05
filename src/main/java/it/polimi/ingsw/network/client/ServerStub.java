package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.ClientSkeleton;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server {
    private final int port;
    private final String localhost;
    private Socket socket;
    private java.io.ObjectOutputStream ObjectOutputStream;
    private ObjectInputStream ObjectInputStream;

    public ServerStub(String localhost, int port) {
        this.localhost = localhost;
        this.port = port;
    }

    public void receive(ClientImpl client) throws RemoteException {
         Server server;
        try {
            server = (Server) ObjectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot receive or cast event: " + e.getMessage());
        }
        Message arg;
        try {
            arg = (Message) ObjectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot receive or cast event: " + e.getMessage());
        }

        client.updateView(server, arg);

    }

    public void close() {
    }

    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(localhost, port);
            try{
            this.ObjectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create output stream" + e.getMessage());
            }
            try {
            this.ObjectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create input stream" + e.getMessage());
            }

        } catch (IOException e) {
            throw new RemoteException("Unable to connect to server" + e.getMessage());
        }
    }

    @Override
    public void updateModel(Client client, Message arg) throws RemoteException {
        try {
            ObjectOutputStream.writeObject(arg);
        } catch (IOException e) {
            throw new RemoteException("Unable to send message to server" + e.getMessage());
        }

    }

}
