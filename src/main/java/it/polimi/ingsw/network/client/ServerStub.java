package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;


/**
 * Implementation of the Server interface that represents a stub for a remote server.
 */
public class ServerStub implements Server {
    private final int port;
    private final String ip;
    private static Game game;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    /**
     * Constructs a new ServerStub object with the specified IP address and port.
     *
     * @param ip   the IP address of the server.
     * @param port the port number of the server.
     */
    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    /**
     * Registers the client with the server.
     *
     * @param client the client to register.
     * @return true if the registration is successful, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public boolean register(Client client) throws RemoteException {
        //TODO - sistemare riconnessione con socket
        /*System.out.println("Match: ");
        if((game != null && (game.getNumberOfPlayers() > 0 || game.getNumberOfPlayers() <= game.getPlayerList().size()))){
            return false;
        } else if(game == null) {*/
            try {
                this.socket = new Socket(ip, port);
                try {
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
            System.out.println("Sono nel serverstub");
            return true;
      /*  } else {
            return false;
        }*/
    }

    /**
     * Updates the server's model with the specified client and message argument.
     *
     * @param client the client that initiated the update.
     * @param arg    the message argument to update the model.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public void updateModel(Client client, Message arg) throws RemoteException {
        try {
            objectOutputStream.writeObject(arg);
        } catch (IOException e) {
            throw new RemoteException("Unable to send message to server" + e.getMessage());
        }
    }

    /**
     * Receives a message from the server and updates the client's view.
     *
     * @param client the client to update the view.
     * @throws RemoteException if a remote communication error occurs.
     */
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
        //arg.printMsg();
        client.updateView(server, arg);
    }

    /**
     * Closes the socket connection.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    public void close() throws RemoteException{
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }

    /**
     * Pings the server.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public void ping() throws RemoteException { }
}
