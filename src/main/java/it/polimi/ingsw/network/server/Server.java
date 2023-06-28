package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client the client to register
     */
    boolean register(Client client) throws RemoteException;

    /**
     * Notify the server that a client has made a choice
     * @param client  the client that generated the event
     * @param arg     the choice made by the client
     */
    void updateModel(Client client, Message arg) throws RemoteException;  //Choice => definisce i messaggi view controller

    void ping() throws RemoteException;
}
