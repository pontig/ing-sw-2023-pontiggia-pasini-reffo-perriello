package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;

public class FiveDiagonalGoal extends CommonGoalAbstract {
    public FiveDiagonalGoal(int numberPlayers){ super(numberPlayers); }

    /*
         0 1 2 3 4
       0 b x x x x
       1 a b x x x
       2 x a b x x
       3 x x a b x
       4 x x x a b
       5 x x x x a

        [column, row]
       {[0,0], [1,1], [2,2], [3,3], [4,4]}
       {[0,1], [1,2], [2,3], [3,4], [4,5]}
    */

    @Override
    public boolean specificGoal(Shelf shelf) {
        Item[][] playerShelf = new Item[5][6];
        int counter = 1;

        playerShelf = shelf.getItems();
        for(int i = 1; i < 5; i++){
            if(playerShelf[i][i].equals(playerShelf[i-1][i-1]))
                counter++;
        }
        if(counter == 5)
            return true;

        counter = 1;

        for(int i = 1; i < 5; i++){
            if(playerShelf[i+1][i].equals(playerShelf[i][i-1]))
                counter++;
        }
        if(counter == 5)
            return true;

        return false;
    }
}
