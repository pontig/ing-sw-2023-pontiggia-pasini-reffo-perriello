package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The ServerAbst interface defines the contract for a remote server.
 * It provides methods for connecting to the server.
 */
public interface ServerAbst extends Remote {
    Server connect() throws RemoteException;
}
