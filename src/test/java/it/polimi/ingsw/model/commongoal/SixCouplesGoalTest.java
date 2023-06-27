package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Item;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.enums.Type.*;
import static org.junit.jupiter.api.Assertions.*;

class SixCouplesGoalTest {
    CommonGoalAbstract twoPlayers = new SixCouplesGoal(2);
    CommonGoalAbstract threePlayers = new SixCouplesGoal(3);
    CommonGoalAbstract fourPlayers = new SixCouplesGoal(4);
    Shelf playerShelf = new Shelf();
    @Test
    void specificGoal() {
        //empty shelf
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*full shelf one item type (TROPHY)
         0 1 2 3 4 5
       0 d d d d d d
       1 d d d d d d
       2 d d d d d d
       3 d d d d d d
       4 d d d d d d
         */
        playerShelf.clear();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                playerShelf.setItem(i, j, new Item(TROPHY, 1));
            }
        }
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));


        /*rowFirst less than columnFirst -> Column return true (BOOK)
         0 1 2 3 4 5    5 rowFirst        0 1 2 3 4 5    6 columnFirst
       0 b x x x x x                    0 b x x x x x
       1 b x b x x x                    1 b x b x x x
       2 x x b b b b                    2 x x b b b b
       3 b b x b x b                    3 b b x b x b
       4 x b x b x x                    4 x b x b x x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 0, new Item(BOOK, 1));
        playerShelf.setItem(1, 0, new Item(BOOK, 2));
        playerShelf.setItem(1, 2, new Item(BOOK, 3));
        playerShelf.setItem(2, 2, new Item(BOOK, 1));
        playerShelf.setItem(2, 3, new Item(BOOK, 2));
        playerShelf.setItem(2, 4, new Item(BOOK, 3));
        playerShelf.setItem(2, 5, new Item(BOOK, 1));
        playerShelf.setItem(3, 0, new Item(BOOK, 2));
        playerShelf.setItem(3, 1, new Item(BOOK, 3));
        playerShelf.setItem(3, 3, new Item(BOOK, 1));
        playerShelf.setItem(3, 5, new Item(BOOK, 2));
        playerShelf.setItem(4, 1, new Item(BOOK, 3));
        playerShelf.setItem(4, 3, new Item(BOOK, 1));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));


        /*rowFirst more than columnFirst -> Row return true (CAT)
         0 1 2 3 4 5    6 rowFirst        0 1 2 3 4 5    5 columnFirst
       0 x x d x x d                    0 x x d x x d
       1 x x d d d d                    1 x x d d d d
       2 x d x x d x                    2 x d x x d x
       3 d d d x d d                    3 d d d x d d
       4 d x x d x x                    4 d x x d x x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 2, new Item(CAT, 1));
        playerShelf.setItem(0, 5, new Item(CAT, 2));
        playerShelf.setItem(1, 2, new Item(CAT, 3));
        playerShelf.setItem(1, 3, new Item(CAT, 1));
        playerShelf.setItem(1, 4, new Item(CAT, 2));
        playerShelf.setItem(1, 5, new Item(CAT, 3));
        playerShelf.setItem(2, 1, new Item(CAT, 1));
        playerShelf.setItem(2, 4, new Item(CAT, 2));
        playerShelf.setItem(3, 0, new Item(CAT, 3));
        playerShelf.setItem(3, 1, new Item(CAT, 1));
        playerShelf.setItem(3, 2, new Item(CAT, 2));
        playerShelf.setItem(3, 4, new Item(CAT, 3));
        playerShelf.setItem(3, 5, new Item(CAT, 1));
        playerShelf.setItem(4, 0, new Item(CAT, 2));
        playerShelf.setItem(4, 3, new Item(CAT, 3));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));


        /* condition and path coverage (BOOK)
         0 1 2 3 4 5
       0 x B B C G B
       1 C P B B C C
       2 B T B T G B
       3 x B B P B B
       4 B B B P G B
         */
        playerShelf.clear();
        playerShelf.setItem(0, 1, new Item(BOOK, 1));
        playerShelf.setItem(0, 2, new Item(BOOK, 1));
        playerShelf.setItem(0, 3, new Item(CAT, 2));
        playerShelf.setItem(0, 4, new Item(GAME, 3));
        playerShelf.setItem(0, 5, new Item(BOOK, 1));
        playerShelf.setItem(1, 0, new Item(CAT, 1));
        playerShelf.setItem(1, 1, new Item(PLANTS, 1));
        playerShelf.setItem(1, 2, new Item(BOOK, 2));
        playerShelf.setItem(1, 3, new Item(BOOK, 3));
        playerShelf.setItem(1, 4, new Item(CAT, 1));
        playerShelf.setItem(1, 5, new Item(CAT, 2));
        playerShelf.setItem(2, 0, new Item(BOOK, 3));
        playerShelf.setItem(2, 1, new Item(TROPHY, 1));
        playerShelf.setItem(2, 2, new Item(BOOK, 2));
        playerShelf.setItem(2, 3, new Item(TROPHY, 2));
        playerShelf.setItem(2, 4, new Item(GAME, 2));
        playerShelf.setItem(2, 5, new Item(BOOK,1));
        playerShelf.setItem(3, 1, new Item(BOOK,2));
        playerShelf.setItem(3, 2, new Item(BOOK, 1));
        playerShelf.setItem(3, 3, new Item(PLANTS, 3));
        playerShelf.setItem(3, 4, new Item(BOOK, 2));
        playerShelf.setItem(3, 5, new Item(BOOK, 3));
        playerShelf.setItem(4, 0, new Item(BOOK,3));
        playerShelf.setItem(4, 1, new Item(BOOK, 1));
        playerShelf.setItem(4, 2, new Item(BOOK, 1));
        playerShelf.setItem(4, 3, new Item(PLANTS, 2));
        playerShelf.setItem(4, 4, new Item(GAME, 3));
        playerShelf.setItem(4, 5, new Item(BOOK, 1));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
    }
}