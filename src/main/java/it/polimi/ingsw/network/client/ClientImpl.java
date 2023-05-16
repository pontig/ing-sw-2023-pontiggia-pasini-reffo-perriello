package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {
    //View view = null;
    View view = null;
    public ClientImpl(Server server, int typeView) throws RemoteException {
        super();
        if(typeView == 0)
            view = new CLI();
        else if(typeView == 1)                                                    //da sistemare View e GUI
            view = new GUI();

        initialize(server);
    }

    private void initialize(Server server) throws RemoteException{
        server.register(this);
        this.view.addObserverView((o, arg) -> {
            try {
                server.updateModel(this, arg);           //arg è messaggio da view a controller - INIT per nome e num players
            } catch (RemoteException e) {
                System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
            }
        });
    }
    @Override
    public void updateView(Server server, Message arg) {     //message mantiene i dati, event definisce quali dati prendere
        view.update(server, arg);
    } //passo alla view il messaggio ricevuto dal server, con arg per le info da prendere -> se faccio un messaggio custom per ognuno si può togliere arg forse

    public void run() {
        view.run();
    }
}
