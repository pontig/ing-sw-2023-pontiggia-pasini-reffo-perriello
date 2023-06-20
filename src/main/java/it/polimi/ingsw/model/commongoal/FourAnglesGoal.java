package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;

/**
 * Four tiles of the same type in the four corners of the bookshelf
 */

public class FourAnglesGoal extends CommonGoalAbstract {

    public FourAnglesGoal(int numberPlayers) {
        super(numberPlayers);
    }

    public boolean specificGoal(Shelf shelf) {

        if (shelf.getItem(0, 0) == null || shelf.getItem(4, 0) == null || shelf.getItem(4, 5) == null || shelf.getItem(0, 5) == null) {
            return false;
        } else {
            if ((shelf.getItem(0, 0).getType().equals(shelf.getItem(4, 0).getType())) && (shelf.getItem(0, 0).getType().equals(shelf.getItem(4, 5).getType())) && (shelf.getItem(0, 0).getType().equals(shelf.getItem(0, 5).getType()))) {
                return true;
            }
        }
        return false;
    }
}