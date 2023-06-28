package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Two groups each containing 4 tiles of the same type in a 2x2 square
 * The tiles of one square are the same from those of the other square.
 */
public class Square2x2Goal extends CommonGoalAbstract {
    List<Type> types;

    /**
     * Constructor of Square2x2Goal: initializes and fills a list of type
     * @param numberPlayers the number of players
     */
    public Square2x2Goal(int numberPlayers) {
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
      0 1 2 3 4 5   r
    0 x x x x x x
    1 x x x x x x
    2 x x x x x x
    3 x x x x x x
    4 x x x x x x

    c
    */

    /**
     * @param shelf the shelf to check
     * @return if the shelf has two groups each containing 4 tiles of the same type in a 2x2 square (The tiles of one square are the same from those of the other square.)
     */
    public boolean specificGoal(Shelf shelf) {
        int counter = 0;
        Shelf shelfClone;
        Item[][] playerShelf;

        for (Type item : types) {
            shelfClone = shelf.clone();
            playerShelf = shelfClone.getItems();
            for (int r = 5; r > 0; r--) {
                for (int c = 0; c < 4; c++) {
                    if (playerShelf[c][r] == null || playerShelf[c + 1][r] == null || playerShelf[c][r - 1] == null || playerShelf[c + 1][r - 1] == null)
                        continue;

                    if (item.equals(playerShelf[c][r].getType()) && item.equals(playerShelf[c + 1][r].getType())
                            && item.equals(playerShelf[c][r - 1].getType()) && item.equals(playerShelf[c + 1][r - 1].getType())) {
                        counter++;
                        playerShelf[c][r] = null;
                        playerShelf[c + 1][r] = null;
                        playerShelf[c][r - 1] = null;
                        playerShelf[c + 1][r - 1] = null;
                        if (counter == 2)
                            return true;
                    }
                }
            }
            counter = 0;
        }
        return false;
    }
}
