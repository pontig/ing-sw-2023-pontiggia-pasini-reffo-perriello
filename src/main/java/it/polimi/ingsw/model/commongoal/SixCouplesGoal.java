package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class SixCouplesGoal extends CommonGoalAbstract {
    List<Type> types;
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

    /*
         0 1 2 3 4                   0 1 2 3 4
       0 B B x B x    5 rowFirst   0 B B x B x    6 columnFirst
       1 x x x B b                 1 x x x B b
       2 x B B x x                 2 x B B x x
       3 x x B B b                 3 x x B B B
       4 x x B x x                 4 x x B x x
       5 x x B b x                 5 x x B B x

         0 1 2 3 4                   0 1 2 3 4
       0 x x x d d    6 rowFirst   0 x x x d d    5 columnFirst
       1 x x d d x                 1 x x d d x
       2 d d x d x                 2 d d x d x
       3 x d x x d                 3 x d x x d
       4 x d d d x                 4 x d d d x
       5 d d x d x                 5 d d x d x

    */
    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
        Shelf shelfClone;
        Item[][] playerShelf;
        int rowFirst = 0;
        int columnFirst = 0;

        for(Type color: types){
            shelfClone = shelf.clone();
            playerShelf = shelfClone.getItems();
            //rowfirst
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 5; j++){
                    if(playerShelf[i][j] != null && playerShelf[i][j].getType().equals(color)){
                        playerShelf[i][j] = null;
                        if(j < 4 && playerShelf[i][j+1] != null && playerShelf[i][j+1].getType().equals(color)){
                            playerShelf[i][j+1] = null;
                            rowFirst++;
                            continue;
                        }
                        else if(i < 5 && playerShelf[i+1][j] != null && playerShelf[i+1][j].getType().equals(color)){
                            playerShelf[i+1][j] = null;
                            rowFirst++;
                            continue;
                        }
                    }
                }
            }

            if(rowFirst >= 6)
                return true;

            shelfClone = shelf.clone();
            playerShelf = shelfClone.getItems();
            //columnfirst
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 5; j++){
                    if(playerShelf[i][j] != null && playerShelf[i][j].getType().equals(color)){
                        playerShelf[i][j] = null;
                        if(i < 5 && playerShelf[i+1][j] != null && playerShelf[i+1][j].getType().equals(color)){
                            playerShelf[i+1][j] = null;
                            columnFirst++;
                            continue;
                        }
                        else if(j < 4 && playerShelf[i][j+1] != null && playerShelf[i][j+1].getType().equals(color)){
                            playerShelf[i][j+1] = null;
                            columnFirst++;
                            continue;
                        }
                    }
                }
            }

            if(columnFirst >= 6)
                return true;

            rowFirst = 0;
            columnFirst = 0;
        }
        return false;
    }
}
