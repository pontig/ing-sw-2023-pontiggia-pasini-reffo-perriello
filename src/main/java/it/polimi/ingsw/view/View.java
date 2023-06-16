package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.ObservableView;

public abstract class View extends ObservableView<Message> implements Runnable {
    /**
     * Overrides the Runnable run method
     * @see Runnable#run()
     */
    @Override
    public abstract void run();

    /**
     * Updates using the observer - observable paradigm
     * @param o the server that invoked the update
     * @param arg the information of the update (aka the model)
     * @see it.polimi.ingsw.observer
     * @see it.polimi.ingsw.enums.State for the meaning of the messages
     */
    public abstract void update(Server o, Message arg);

    // TODO: dont'know what to say in the javadocs
    public void setNetwork(int networkClient) {
    }
}
