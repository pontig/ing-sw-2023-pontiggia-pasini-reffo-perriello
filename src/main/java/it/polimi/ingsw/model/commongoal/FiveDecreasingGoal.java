package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;

public class FiveDecreasingGoal extends CommonGoalAbstract{

    /*five columns of increasing or decreasing height: starting with the first column to the left or right,
     each subsequent column must consist of one more tile. Tiles can be of any type.*/

    public FiveDecreasingGoal(int numbPlayers){
        super(numbPlayers);
    }

    /* x x x x x
       p x x x p
       p p x p p
       p p d p p
       p d d d p
       d d d d d
     */



    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {

        int temp = 0;

        boolean check = true;

        for (int i = 5; i > 0; i--) {
            int j = temp;
            while (j < 5) {
                if (shelf.item[j][i].equals(null)) {
                    if ((i == 5) || (i == 4 && (j == 1 || j == 2 || j == 3)) || (i == 3 && j == 2)) {
                        return false;
                    } else {
                        check = false;
                    }
                } else {
                    j++;
                }
            }
            temp++;
        }

        temp = 5;

        if (check == false) {
            for (int i = 5; i > 0; i--) {
                int j = temp;
                while (j > -1) {
                    if (shelf.item[j][i].equals(null)) {
                        return false;
                    } else {
                        j++;
                    }
                }
                temp--;
            }
            check = true;
        }
        return check;
    }
}
