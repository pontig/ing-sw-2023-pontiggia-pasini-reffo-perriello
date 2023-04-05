package it.polimi.ingsw.model.commongoal;


import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.enums.Type;

import java.util.ArrayList;
import java.util.List;


public class FiveXGoal extends CommonGoalAbstract {

    /**
     * Constructor of FiveXGoal
     * @param numberPlayers the number of players
     */
    public FiveXGoal(int numberPlayers) {
        super(numberPlayers);
    }

    /**
     * Checks if the shelf has five tiles of the same type forming an X
     * @param shelf the shelf to check
     * @return if the shelf has five tiles of the same type forming an X
     */
    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                if (shelf.getItem(i, j) != null
                        && shelf.getItem(i + 1, j + 1) != null
                        && shelf.getItem(i + 1, j - 1) != null
                        && shelf.getItem(i - 1, j + 1) != null
                        && shelf.getItem(i - 1, j - 1) != null
                        && shelf.getItem(i, j).getType() == shelf.getItem(i + 1, j + 1).getType()
                        && shelf.getItem(i, j).getType() == shelf.getItem(i + 1, j - 1).getType()
                        && shelf.getItem(i, j).getType() == shelf.getItem(i - 1, j + 1).getType()
                        && shelf.getItem(i, j).getType() == shelf.getItem(i - 1, j - 1).getType()
                ) return true;
            }
        }
        return false;
    }
}


