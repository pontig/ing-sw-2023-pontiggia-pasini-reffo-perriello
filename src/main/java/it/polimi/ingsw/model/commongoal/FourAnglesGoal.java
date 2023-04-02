package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.enums.Type;

import java.util.ArrayList;
import java.util.List;

//4 tiles of the same type in the four corners of the shelf

public class FourAnglesGoal extends CommonGoalAbstract {

    public FourAnglesGoal(int numberPlayers) {
        super(numberPlayers);
    }
    public boolean specificGoal(Shelf shelf){

        return(shelf.getItem(0, 0).getType().equals(shelf.getItem(4, 0).getType())) && (shelf.getItem(0, 0).getType().equals(shelf.getItem(4, 5).getType())) && (shelf.getItem(0, 0).getType().equals(shelf.getItem(0, 5).getType()));

    }
}