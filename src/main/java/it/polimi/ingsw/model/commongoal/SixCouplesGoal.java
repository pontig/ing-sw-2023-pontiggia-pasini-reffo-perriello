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
                    if(playerShelf[j][i] != null && playerShelf[j][i].getType().equals(color)){
                        playerShelf[j][i] = null;
                        if(j < 4 && playerShelf[j+1][i] != null && playerShelf[j+1][i].getType().equals(color)){
                            playerShelf[j+1][i] = null;
                            rowFirst++;
                            continue;
                        }
                        else if(i < 5 && playerShelf[j][i+1] != null && playerShelf[j][i+1].getType().equals(color)){
                            playerShelf[j][i+1] = null;
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
                    if(playerShelf[j][i] != null && playerShelf[j][i].getType().equals(color)){
                        playerShelf[j][i] = null;
                        if(i < 5 && playerShelf[j][i+1] != null && playerShelf[j][i+1].getType().equals(color)){
                            playerShelf[j][i+1] = null;
                            columnFirst++;
                            continue;
                        }
                        else if(j < 4 && playerShelf[j+1][i] != null && playerShelf[j+1][i].getType().equals(color)){
                            playerShelf[j+1][i] = null;
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
*/
