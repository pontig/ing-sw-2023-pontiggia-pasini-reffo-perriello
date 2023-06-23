package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;
import it.polimi.ingsw.tuples.Pair;

public class SendDataToServer implements Message{
    State info = null;
    String nickname = null;
    int players_row_action = 0;
    int column_position = 0;
    boolean confirm = false;

    public SendDataToServer(State info, String nickname, int players_row_action, int column_position, boolean confirm){
        this.info = info;
        this.nickname = nickname;
        this.players_row_action = players_row_action;
        this.column_position = column_position;
        this.confirm = confirm;
    }

    @Override
    public State getInfo() {
        return this.info;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public int getNumRowAction() {
        return this.players_row_action;
    }

    @Override
    public int getColumnPos() {
        return this.column_position;
    }

    @Override
    public boolean getConfirm() {
        return this.confirm;
    }

    @Override
    public void printMsg() {
        System.out.println("INFO Message: " + getInfo());
    }

    @Override
    public String getBoard() {
        return null;
    }
    @Override
    public String getFirstCommon() {
        return null;
    }
    @Override
    public String getSecondCommon() {
        return null;
    }
    @Override
    public String getPersonal() {return null;}
    @Override
    public String getSelected() { return null; }
    @Override
    public String getShelf() { return null; }
    @Override
    public String getOrderedRanking() { return null;}
    @Override
    public String getColumns() { return null; }

    @Override
    public String getTo() {
        return null;
    }

    @Override
    public String getFrom() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }
}
