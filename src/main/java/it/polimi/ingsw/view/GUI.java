package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.ObservableView;

public class GUI extends ObservableView<Message> implements View, Runnable{
    public GUI(){}

    @Override
    public void run() {
        while(true){

        }
    }

}
