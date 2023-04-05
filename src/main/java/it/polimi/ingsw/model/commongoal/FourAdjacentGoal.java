package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;

public class FourAdjacentGoal extends CommonGoalAbstract {

    public FourAdjacentGoal(int numberPlayers) {
        super(numberPlayers);
    }

    /**
     * Checks if there are at least two groups of four adjacent items of the same type
     * @param shelf the shelf to check
     * @return if there are at least two groups of four adjacent items of the same type
     * @throws IllegalArgumentException if the shelf is null
     */
    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
        int count = 0; // Count of groups of four adjacent items

        Shelf copy = shelf.clone();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (copy.getItem(i,j) != null) {
                    int groups = copy.adjacent(i, j, copy.getItem(i, j).getType());
                    count += groups / 4;
                    if (count >= 2) return true;
                }
            }
        }
        return false;

    }
}
