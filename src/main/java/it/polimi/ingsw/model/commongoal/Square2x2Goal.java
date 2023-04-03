package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class Square2x2Goal extends CommonGoalAbstract{
    List<Type> types;
    public Square2x2Goal(int numberPlayers){
        super(numberPlayers);
        types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
    }

    /*
         0 1 2 3 4
       0 x x x a a
       1 c x x t t
       2 c c x t t
       3 c t t b b
       4 a t t b b
       5 a a b b b
    */

    public boolean specificGoal(Shelf shelf){
        int counter = 0;
        Shelf shelfClone;
        Item[][] playerShelf;

        for(Type item: types) {
            shelfClone = shelf.clone();
            playerShelf = shelfClone.getItems();
            for(int r = 5; r > 0; r--) {
                for(int c = 0; c < 4; c++) {
                    if(playerShelf[r][c] == null || playerShelf[r][c+1] == null || playerShelf[r-1][c] == null || playerShelf[r-1][c+1] == null)
                        continue;

                    if(item.equals(playerShelf[r][c].getType()) && item.equals(playerShelf[r][c+1].getType())
                            && item.equals(playerShelf[r-1][c].getType()) && item.equals(playerShelf[r-1][c+1].getType())) {
                        counter++;
                        playerShelf[r][c] = null;
                        playerShelf[r][c+1] = null;
                        playerShelf[r-1][c] = null;
                        playerShelf[r-1][c+1] = null;
                        if(counter == 2)
                            return true;
                    }
                }
            }
            counter = 0;
        }
        return false;
    }
}
