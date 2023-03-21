package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;

    public class EightSameTypeGoal extends CommonGoalAbstract {
        //Eight tiles of the same type. There are no restrictions on the locations of these tiles
        int count = 0;
        Item itemtest;

        public EightSameTypeGoal(int numberPlayers) {
            super(numberPlayers);
        }
        public void checkeditem(Item i) {
            itemtest = i;
        }
        public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                                if (shelf.item[i][j].equals(shelf.itemtest)) {
                                    count++;
                                }
                            }
                        }
                Boolean test = (count == 8) ? true : false;
                return test;
                    }
                }

