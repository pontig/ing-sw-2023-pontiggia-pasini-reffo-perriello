package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {

    private final ObjectOutputStream ObjectOutputStream;
    private static ObjectInputStream ObjectInputStream = null;

    public ClientSkeleton(Socket socket) throws RemoteException {
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

    }

    public static void receive(Server server) throws RemoteException {
        Message arg;
        try {
            arg = (Message) ObjectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot receive or cast event: " + e.getMessage());
        }

        server.updateModel(this, arg);

    }

    }

    @Override
    public void updateView(Server o, Message arg) throws RemoteException {
        try {
            ObjectOutputStream.writeObject(o);
            ObjectOutputStream.writeObject(arg);
        } catch (IOException e) {
            throw new RuntimeException("Cannot send event: " + e.getMessage());
        }

    }
}
