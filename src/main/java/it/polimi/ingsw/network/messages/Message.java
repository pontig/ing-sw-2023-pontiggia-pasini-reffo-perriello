package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

import java.io.Serializable;

public abstract class Message implements Serializable {
    State info;
    public Message(State info){
        this.info = info;
    }
    public State getInfo(){
        return this.info;
    }
    public void setInfo(State info){
        this.info = info;
    }
    public abstract void printMsg();

    public abstract String getNickname();

    public abstract int getNumPlayers();
}
