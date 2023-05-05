package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public ClientSkeleton(Socket socket) throws RemoteException {
        try{
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create output stream" + e.getMessage());
        }
        try {
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create input stream" + e.getMessage());
        }
    }

    public void receive(Server server) throws RemoteException {
        Message arg;
        try {
            arg = (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot receive or cast event: " + e.getMessage());
        }
        server.updateModel(this, arg);
    }

    @Override
    public void updateView(Server o, Message arg) throws RemoteException {
        try {
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            throw new RuntimeException("Cannot send event: " + e.getMessage());
        }
        try {
            objectOutputStream.writeObject(arg);
        } catch (IOException e) {
            throw new RuntimeException("Cannot send event: " + e.getMessage());
        }
    }
}
