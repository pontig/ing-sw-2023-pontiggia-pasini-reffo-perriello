package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;

import it.polimi.ingsw.model.Shelf;

/**
 * Five tiles of the same type forming a diagonal.
 */
public class FiveDiagonalGoal extends CommonGoalAbstract {
    public FiveDiagonalGoal(int numberPlayers){ super(numberPlayers); }

    /*
         0 1 2 3 4           0 1 2 3 4
       0 b x x x x         0 x x x x b
       1 a b x x x         1 x x x b a
       2 x a b x x         2 x x b a x
       3 x x a b x         3 x b a x x
       4 x x x a b         4 b a x x x
       5 x x x x a         5 a x x x x

    */
    @Override
    public boolean specificGoal(Shelf shelf) {
        int counter = 1;
        int j;
        Item[][] playerShelf = shelf.getItems();

        //b diagonal left to right
        for(int i = 1; i < 5; i++){
            if(playerShelf[i-1][i-1] == null || playerShelf[i][i] == null)
                break;

            if(playerShelf[i][i].getType().equals(playerShelf[i-1][i-1].getType()))
                counter++;
        }
        if(counter == 5)
            return true;

        counter = 1;
        //a diagonal from left to right
        for(int i = 1; i < 5; i++){
            if(playerShelf[i-1][i] == null || playerShelf[i][i+1] == null)
                break;

            if(playerShelf[i][i+1].getType().equals(playerShelf[i-1][i].getType()))
                counter++;
        }
        if(counter == 5)
            return true;

        counter = 1;
        j = 3;
        // b diagola right to left
        for(int i = 1; i < 5; i++){
            if(playerShelf[j+1][i-1] == null || playerShelf[j][i] == null)
                break;

            if(playerShelf[j][i].getType().equals(playerShelf[j+1][i-1].getType()))
                counter++;
            j--;
        }
        if(counter == 5)
            return true;

        counter = 1;
        j = 3;
        // a diagonal right to left
        for(int i = 1; i < 5; i++){
            if(playerShelf[j+1][i] == null || playerShelf[j][i+1] == null)
                break;

            if(playerShelf[j][i+1].getType().equals(playerShelf[j+1][i].getType()))
                counter++;
            j--;
        }
        if(counter == 5)
            return true;

        return false;
    }
}
