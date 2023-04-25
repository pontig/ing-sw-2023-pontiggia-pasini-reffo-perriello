package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

public class Nickname extends Message{
    String nickname = null;

    public Nickname(State info, String nickname){
        super(info);
        this.nickname = nickname;
    }
    public String getNickname(){
        return nickname;
    }

    @Override
    public int getNumPlayers() {
        return 0;
    }

    @Override
    public void printMsg() {
        System.out.println("Nickname:" + nickname);
    }
}
