package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class NicknameAk extends Message {
    int numPlayers = 0;
    public NicknameAk(State info, int numPlayers){
        super(info);
        this.numPlayers = numPlayers;
    }
    @Override
    public void printMsg() {
        System.out.println(getInfo());
    }
    public int getNumPlayers() {
        return this.numPlayers;
    }
    @Override
    public String getNickname() {
        return null;
    }
}
