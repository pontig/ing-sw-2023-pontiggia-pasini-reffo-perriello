package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;
import it.polimi.ingsw.tuples.Pair;

import java.util.List;

public class SendDataToClient implements Message{
    State info = null;
    String board = null;
    String personalGoal = null;
    Item[][] shelf = null;
    String c1 = null;
    String c2 = null;
    List<Pair<Integer, Integer>> selected = null;
    boolean confirm = false;
    List<Item> ordered = null;
    List<Item> unordered = null;
    List<Integer> columns = null;

    public SendDataToClient(State info, String board, String personalGoal, Item[][] shelf, String c1, String c2,
                            List<Pair<Integer, Integer>> selected, boolean confirm, List<Item> ordered, List<Item> unordered, List<Integer> columns){
        this.info = info;
        this.board = board;
        this.personalGoal = personalGoal;
        this.shelf = shelf;
        this.c1 = c1;
        this.c2 = c2;
        this.selected = selected;
        this.confirm = confirm;
        this.ordered = ordered;
        this.unordered = unordered;
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
    public String getNickname() {
        return null;
    }
    @Override
    public int getNumRowAction() {return 0;}
    @Override
    public int getColumnPos() {return 0;}
    @Override
    public boolean getConfirm() {return false;}
}
