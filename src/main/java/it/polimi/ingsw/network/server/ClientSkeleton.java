package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * The ClientSkeleton class implements the Client interface and represents the client-side skeleton
 * It is responsible for communication with the server
 */
public class ClientSkeleton implements Client {
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    /**
     * Constructs a ClientSkeleton object with the specified socket
     *
     * @param socket the socket for communication with the server
     * @throws RemoteException if an error occurs while creating the input/output streams
     */
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

    /**
     * Updates the view with the specified server and message
     *
     * @param o   the server object
     * @param arg the message object
     * @throws RemoteException if an error occurs while sending the event
     */
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

    /**
     * Receives the message from the server and updates the model accordingly
     *
     * @param server the server object
     * @throws RemoteException if an error occurs while receiving or casting the event
     */
    public void receive(Server server) throws RemoteException {
        Message arg;
        try {
            arg = (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot receive or cast event: " + e.getMessage());
        }
        server.updateModel(this, arg);
    }
}
