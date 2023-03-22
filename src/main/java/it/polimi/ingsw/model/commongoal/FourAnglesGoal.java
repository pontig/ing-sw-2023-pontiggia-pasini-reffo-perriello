package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;

public class FourAnglesGoal extends CommonGoalAbstract {

    //4 tiles of the same type in the four corners of the shelf
    public FourAnglesGoal(int numberPlayers) {
        super(numberPlayers);
    }
    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {

        return ((shelf.item[0][0].equals(shelf.item[4][0])) && (shelf.item[0][0].equals(shelf.item[4][5])) && (shelf.item[0][0].equals(shelf.item[0][5])));
    }
}