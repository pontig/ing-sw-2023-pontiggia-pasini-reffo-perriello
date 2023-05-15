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
    private final String ip;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
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

        } catch (IOException e) {
            throw new RemoteException("Unable to connect to server" + e.getMessage());
        }
    }

    @Override
    public void updateModel(Client client, Message arg) throws RemoteException {
        try {
            objectOutputStream.writeObject(arg);
        } catch (IOException e) {
            throw new RemoteException("Unable to send message to server" + e.getMessage());
        }
    }

    public void receive(Client client) throws RemoteException {
        Server server;
        try {
            server = (Server) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot receive or cast event: " + e.getMessage());
        }
        Message arg;
        try {
            arg = (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot receive or cast event: " + e.getMessage());
        }
        client.updateView(server, arg);
    }

    public void close() throws RemoteException{
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }
}
