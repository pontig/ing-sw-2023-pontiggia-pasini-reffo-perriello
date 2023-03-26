package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;

/*five columns of increasing or decreasing height: starting with the first column to the left or right,
each subsequent column must consist of one more tile. Tiles can be of any type.*/

 /* case 1:    x x x x x   case 2: x x x x x   case 3: x x x x p   case 4: p x x x x
               x x x x p           p x x x x           x x x p p           p p x x x
               x x x p p           p p x x x           x x p p p           p p p x x
               x x p p p           p p p x x           x p p p p           p p p p x
               x p p p p           p p p p x           p p p p p           p p p p p
               p p p p p           p p p p p           p p p p p           p p p p p
*/

public class FiveDecreasingGoal extends CommonGoalAbstract{

    public FiveDecreasingGoal(int numbPlayers){
        super(numbPlayers);
    }

    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {

        int i;
        int j;
        boolean leftToRight = true;

        if (shelf.getItem(2, 2) == null){            //CASE 1
            i = 5;
            for(int count = 5; count > 0; count--){
                j = 5 - i;
                if(shelf.getItem(i, j) == null){
                    if(shelf.getItem(5,0) == null || shelf.getItem(4,1) == null || shelf.getItem(3,2) == null){
                        return false;
                    }
                    else{
                        leftToRight = false;
                    }
                }
                else{
                    i--;
                }
            }
            if(leftToRight){
                for(i = 0; i < 5; i++){
                    for(j = 4 - i; j > -1; j--){
                        if(shelf.getItem(i, j) != null){
                            return false;
                        }
                    }
                }
                return true;
            }
            else{                                          //CASE 2
                j = 0;
                i = 1;
                for(int count = 5; count > 0; count--){
                    if(shelf.getItem(i, j) == null){
                        return false;
                    }
                    else{
                        i++;
                        j++;
                    }
                }
                for(i = 0; i < 5; i++){
                    for(j = i; j < 5 ; j--){
                        if(shelf.getItem(i, j) != null){
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        else{
            i = 4;                                         //CASE 3
            for(int count = 5; count > 0; count--){
                j = 4 - i;
                if(shelf.getItem(i, j) == null){
                    if(shelf.getItem(4,0) == null || shelf.getItem(1,3) == null || shelf.getItem(2,2) == null){
                        return false;
                    }
                    else{
                        leftToRight = false;
                    }
                }
                else{
                    i--;
                }
            }
            if(leftToRight) {
                for (i = 0; i < 4; i++) {
                    for (j = 3 - i; j > -1; j--) {
                        if (shelf.getItem(i, j) != null) {
                            return false;
                        }
                    }
                }
                return true;
            }
            else{                                          //CASE 4
                i = 0;
                for(int count = 5; count > 0; count--){
                    j = i;
                    if(shelf.getItem(i, j) == null){
                        return false;
                    }
                    else{
                        i++;
                    }
                }
                for(i = 0; i < 4; i++){
                    for(j = i + 1; j < 5 ; j--){
                        if(shelf.getItem(i, j) != null){
                            return false;
                        }
                    }
                }
                return true;
            }
        }
    }
}
