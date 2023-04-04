package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class EightSameTypeGoal extends CommonGoalAbstract {
    //Eight tiles of the same type. There are no restrictions on the locations of these tiles
    List<Type> types;

    public EightSameTypeGoal(int numberPlayers) {
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
       0 x x x a x
       1 a x x x a
       2 x a x x x
       3 x x x a a
       4 a x x x x
       5 a x x x x
    */

    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
        int counter = 0;
        Shelf shelfClone;
        Item[][] playerShelf;

        for (Type item : types) {
            shelfClone = shelf.clone();
            playerShelf = shelfClone.getItems();
            for (int c = 0; c < 5; c++) {
                for (int r = 0; r < 6; r++) {
                    if (playerShelf[c][r] == null)
                        continue;
                    if (item.equals(playerShelf[c][r].getType())) {
                        counter++;
                        playerShelf[c][r] = null;
                        if (counter >= 8)
                            return true;
                    }
                }
            }
            counter = 0;
        }
           return false;
    }
}

