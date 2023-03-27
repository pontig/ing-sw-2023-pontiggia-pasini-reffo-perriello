package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;

    public class EightSameTypeGoal extends CommonGoalAbstract {
        //Eight tiles of the same type. There are no restrictions on the locations of these tiles
        int count = 0;
        boolean check = false;

        public EightSameTypeGoal(int numberPlayers) {
            super(numberPlayers);
        }

        public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    Item itemtest = shelf.getItem(i,j);
                    for (int k = 0; k < 6; k++) {
                        for (int h = 0; h < 5; h++) {
                            if (shelf.getItem(k,h).equals(itemtest) && k != i && h != j) {
                                count++;
                            }
                        }
                        boolean check = (count == 8) ? true : false;
                    }
                }
            }
            return check;
        }
    }

