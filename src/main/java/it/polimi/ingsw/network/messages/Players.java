package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

public class Players extends Message{
    int numPlayers = 0;

    public Players (State info, int numPlayers){
        super(info);
        this.numPlayers = numPlayers;
    }
    @Override
    public void printMsg() {
        System.out.println("Number:"+ numPlayers);
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }
}
