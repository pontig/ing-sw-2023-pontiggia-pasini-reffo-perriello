package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToClient;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.enums.State.NACK_NICKNAME;


/**
 * Implementation of the Client interface that represents a remote client in the application.
 * It extends UnicastRemoteObject and implements the Runnable interface for handling client operations.
 */
public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {
    private View view = null;

    /**
     * Constructs a new ClientImpl object with the specified server, view type, and network client.
     *
     * @param server        the server instance to connect to.
     * @param typeView      the type of view (0 for CLI, 1 for GUI).
     * @param networkClient the network client identifier.
     * @throws RemoteException if a remote communication error occurs.
     */
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

    /**
     * Initializes the client by registering with the server and setting up the observer for view updates.
     *
     * @param server the server instance to register with.
     * @throws RemoteException if a remote communication error occurs.
     */
    private void initialize(Server server) throws RemoteException{
        if(server.register(this)) {
            //System.out.println("True");
            this.view.addObserverView((o, arg) -> {
                try {
                    server.updateModel(this, arg);           //arg è messaggio da view a controller - INIT per nome e num players
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
            });
        } else {
            //System.out.println("False");
            this.updateView(server, new SendDataToClient(NACK_NICKNAME, null, null, null, null, null, null, null, false, null, null));
        }
    }

    /**
     * Updates the view with the provided server and message arguments.
     *
     * @param server the server instance.
     * @param arg    the message argument to update the view.
     */
    @Override
    public void updateView(Server server, Message arg) {     //message mantiene i dati, event definisce quali dati prendere
        view.update(server, arg);
    } //passo alla view il messaggio ricevuto dal server, con arg per le info da prendere -> se faccio un messaggio custom per ognuno si può togliere arg forse

    /**
     * Sends a ping message to the server.
     *
     * @param server the server instance.
     */
    public void ping(Server server) {
        try {
            server.updateModel(this, new SendDataToServer(State.PING, null, 0,0, false));           //arg è messaggio da view a controller - INIT per nome e num players
        } catch (RemoteException e) {
            System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
        }
    }

    /**
     * Runs the client's view.
     */
    public void run() {
        view.run();
    }
}
