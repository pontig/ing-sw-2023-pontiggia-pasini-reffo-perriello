package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Eight tiles of the same type. There are no restrictions on the locations of these tiles
 */
public class EightSameTypeGoal extends CommonGoalAbstract {

    List<Type> types;

    /**
     * Constructor EightSameTypeGoal: initializes and fills a list
     * @param numberPlayers the number of players
     */
    public EightSameTypeGoal(int numberPlayers) {
        /* creates a list of all the types of tiles
         * and calls the constructor of the superclass
         */
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


    /**
     * this method creates a clone of the shelf and a matrix of items
     * then it checks if there are 8 tiles of the same type
     * if there are it returns true
     * otherwise it returns false
     *
     * @param shelf shelf to check
     * @return if the shelf has eight tiles of the same type
     * @throws IllegalArgumentException if the shelf is null
     */
    @Override
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

