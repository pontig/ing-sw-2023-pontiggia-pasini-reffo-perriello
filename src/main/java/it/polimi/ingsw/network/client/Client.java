package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     *
     * @param arg   The causing event
     */
    void updateView(Server o, Message arg) throws RemoteException;    //Messaggio o => dati del model per la view

    //ModelChanged => Cosa è cambiato nel model
                                                                            //"o" ha getter, in base ad "arg" si chiama il getter corretto di "o"
}