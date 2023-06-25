package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.gui.JavaFXGui;
import javafx.application.Application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {
    //View view = null;
    View view = null;
    public ClientImpl(Server server, int typeView, int networkClient) throws RemoteException {
        super();
        if(typeView == 0) {
            view = new CLI();
            view.setNetwork(networkClient);
        } else if(typeView == 1)                                                    //da sistemare View e GUI
        {
            view = new GUI();
            //Application.launch(JavaFXGui.class);
        }

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

    public void ping(Server server) {
        try {
            server.updateModel(this, new SendDataToServer(State.PING, null, 0,0, false));           //arg è messaggio da view a controller - INIT per nome e num players
        } catch (RemoteException e) {
            System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
        }
    }
    public void run() {
        view.run();
    }
}
