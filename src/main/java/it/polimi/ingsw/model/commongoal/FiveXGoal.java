package it.polimi.ingsw.model.commongoal;



 import it.polimi.ingsw.model.Item;
 import it.polimi.ingsw.model.Shelf;
 import it.polimi.ingsw.model.enums.Type;

 import java.util.ArrayList;
 import java.util.List;


public class FiveXGoal extends CommonGoalAbstract {

    List<Type> types;


    public FiveXGoal(int numberPlayers) {
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
      0 x x x x x
      1 x x x x x
      2 x b x b x
      3 x x b x b
      4 x b x b b
      5 x x x x x
   */
    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {

        Item[][] playerShelf = shelf.getItems();
        for(Type item: types) {
            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 5; c++) {
                    if (item.equals(playerShelf[c][r].getType()) && item.equals(playerShelf[c+2][r].getType())
                            && item.equals(playerShelf[c+2][r-2].getType()) && item.equals(playerShelf[c][r-2].getType())
                            && item.equals(playerShelf[c + 1][r + 1].getType())) {
                        return true;
                    }
                }
            }
        }
         return false;
    }
}


