package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

public class PlayersAk extends Message{
    int numPlayers = 0;

    public PlayersAk (State info, int numPlayers){
        super(info);
        this.numPlayers = numPlayers;
    }
    @Override
    public void printMsg() {
        System.out.println("Number:"+ numPlayers + "Done game");
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
