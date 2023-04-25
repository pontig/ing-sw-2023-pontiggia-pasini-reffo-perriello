package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerAbst extends Remote {
    Server connect() throws RemoteException;
}
