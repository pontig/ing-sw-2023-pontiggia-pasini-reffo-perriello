package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;
import it.polimi.ingsw.tuples.Pair;

import java.util.List;

public class SendDataToClient implements Message{
    State info = null;
    String nickname = null;
    String board = null;
    String personalGoal = null;
    String shelf = null;
    String c1 = null;
    String c2 = null;
    String selected = null;
    boolean confirm = false;
    String orderedRanking = null;
    String columns = null;

    public SendDataToClient(State info, String nickname, String board, String personalGoal, String shelf, String c1, String c2,
                            String selected, boolean confirm, String orderedRanking, String columns){
        this.info = info;
        this.nickname = nickname;
        this.board = board;
        this.personalGoal = personalGoal;
        this.shelf = shelf;
        this.c1 = c1;
        this.c2 = c2;
        this.selected = selected;
        this.confirm = confirm;
        this.orderedRanking = orderedRanking;
        this.columns = columns;
    }
    @Override
    public State getInfo() {
        return this.info;
    }
    @Override
    public void printMsg() {
        System.out.println("INFO Message: "+ getInfo());
    }

    @Override
    public String getBoard() {
        return board;
    }
    @Override
    public String getFirstCommon() {
        return c1;
    }
    @Override
    public String getSecondCommon() {
        return c2;
    }
    @Override
    public String getPersonal() { return personalGoal; }
    @Override
    public String getSelected() { return selected; }
    @Override
    public String getShelf() { return shelf; }
    @Override
    public String getOrderedRanking() { return orderedRanking; }
    @Override
    public String getColumns() { return columns; }

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

    @Override
    public String getNickname() {
        return nickname;
    }
    @Override
    public boolean getConfirm() {return confirm;}

    @Override
    public int getNumRowAction() {return 0;}
    @Override
    public int getColumnPos() {return 0;}
}
