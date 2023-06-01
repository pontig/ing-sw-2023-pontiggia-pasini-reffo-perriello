package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.ObservableView;

public abstract class View extends ObservableView<Message> implements Runnable {
    public abstract void run();

    public abstract void update(Server o, Message arg);

    public void setNetwork(int networkClient) {
    }
}
