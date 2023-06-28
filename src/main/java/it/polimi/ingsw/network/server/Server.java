package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Server interface defines the operations that can be performed on the server-side.
 */
public interface Server extends Remote {
    /**
     * Register a client to the server
     *
     * @param client the client to register
     * @return true if the registration is successful, false otherwise
     * @throws RemoteException if an error occurs during the remote method invocation
     */
    boolean register(Client client) throws RemoteException;

    /**
     * Notify the server that a client has made a choice
     *
     * @param client  the client that generated the event
     * @param arg     the choice made by the client
     * @throws RemoteException if an error occurs during the remote method invocation
     */
    void updateModel(Client client, Message arg) throws RemoteException;  //Choice => definisce i messaggi view controller

    /**
     * Pings the server to check its availability.
     *
     * @throws RemoteException if an error occurs during the remote method invocation
     */
    void ping() throws RemoteException;
}
