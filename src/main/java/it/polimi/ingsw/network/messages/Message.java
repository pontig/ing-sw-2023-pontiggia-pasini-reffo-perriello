package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;
import it.polimi.ingsw.tuples.Pair;

import java.io.Serializable;

public interface Message extends Serializable {
    State getInfo();
    String getNickname();
    int getNumRowAction();
    int getColumnPos();
    boolean getConfirm();
    void printMsg();

    String getBoard();
    String getFirstCommon();
    String getSecondCommon();
    String getPersonal();
    String getSelected();
    String getShelf();
    String getOrdered();
    String getColumns();
}
