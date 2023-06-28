package it.polimi.ingsw.model.commongoal;


import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Six groups each containing at least 2 tiles of the same type
 * The tiles of one group can be different from those of another group
 */

public class SixCouplesGoal extends CommonGoalAbstract {
    List<Type> types;

    /**
     * Constructor of SixCouplesGoal: initializes and fills a list
     * @param numberPlayers the number of players
     */
    public SixCouplesGoal(int numberPlayers) {
        super(numberPlayers);
        types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
    }

    /**
     * @param shelf the shelf to check
     * @return if the shelf has six groups each containing at least 2 tiles of the same type (The tiles of one group can be different from those of another group)
     */
    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
        Shelf shelfClone;
        Item[][] playerShelf;
        int rowFirst = 0;
        int columnFirst = 0;
        int couples = 0;


        for (Type color : types) {
            shelfClone = shelf.clone();
            playerShelf = shelfClone.getItems();
            //rowfirst
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playerShelf[j][i] != null && playerShelf[j][i].getType().equals(color)) {
                        playerShelf[j][i] = null;
                        if (j < 4 && playerShelf[j + 1][i] != null && playerShelf[j + 1][i].getType().equals(color)) {
                            playerShelf[j + 1][i] = null;
                            rowFirst++;
                        } else if (i < 5 && playerShelf[j][i + 1] != null && playerShelf[j][i + 1].getType().equals(color)) {
                            playerShelf[j][i + 1] = null;
                            rowFirst++;
                        }
                    }
                }
            }

            shelfClone = shelf.clone();
            playerShelf = shelfClone.getItems();
            //columnfirst
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playerShelf[j][i] != null && playerShelf[j][i].getType().equals(color)) {
                        playerShelf[j][i] = null;
                        if (i < 5 && playerShelf[j][i + 1] != null && playerShelf[j][i + 1].getType().equals(color)) {
                            playerShelf[j][i + 1] = null;
                            columnFirst++;
                        } else if (j < 4 && playerShelf[j + 1][i] != null && playerShelf[j + 1][i].getType().equals(color)) {
                            playerShelf[j + 1][i] = null;
                            columnFirst++;
                        }
                    }
                }
            }

            if(rowFirst > columnFirst)
                couples = couples + rowFirst;
            else
                couples = couples + columnFirst;

            rowFirst = 0;
            columnFirst = 0;
        }

        return couples >= 6;
    }
}

