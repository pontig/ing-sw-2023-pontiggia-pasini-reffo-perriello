package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.enums.Type.*;
import static org.junit.jupiter.api.Assertions.*;

class FiveDiagonalGoalTest {
    CommonGoalAbstract twoPlayers = new FiveDiagonalGoal(2);
    CommonGoalAbstract threePlayers = new FiveDiagonalGoal(3);
    CommonGoalAbstract fourPlayers = new FiveDiagonalGoal(4);
    Shelf playerShelf = new Shelf();

    @Test
    void specificGoal() {
        //empty shelf
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*b diagonal left to right full
          0 1 2 3 4 5
        0 b x x x x x
        1 x b x x x x
        2 x x b x x x
        3 x x x b x x
        4 x x x x b x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 0, new Item(BOOK, 1));
        playerShelf.setItem(1, 1, new Item(BOOK, 2));
        playerShelf.setItem(2, 2, new Item(BOOK, 3));
        playerShelf.setItem(3, 3, new Item(BOOK, 1));
        playerShelf.setItem(4, 4, new Item(BOOK, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
        //position 0,0 null
        playerShelf.setItem(0, 0, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //position 4,4 null
        playerShelf.setItem(0, 0, new Item(BOOK, 1));
        playerShelf.setItem(4, 4, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //different items
        playerShelf.setItem(0, 0, new Item(BOOK, 1));
        playerShelf.setItem(1, 1, new Item(BOOK, 2));
        playerShelf.setItem(2, 2, new Item(BOOK, 3));
        playerShelf.setItem(3, 3, new Item(CAT, 1));
        playerShelf.setItem(4, 4, new Item(BOOK, 1));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*a diagonal left to right full
          0 1 2 3 4 5
        0 x a x x x x
        1 x x a x x x
        2 x x x a x x
        3 x x x x a x
        4 x x x x x a
         */
        playerShelf.clear();
        playerShelf.setItem(0, 1, new Item(BOOK, 1));
        playerShelf.setItem(1, 2, new Item(BOOK, 2));
        playerShelf.setItem(2, 3, new Item(BOOK, 3));
        playerShelf.setItem(3, 4, new Item(BOOK, 1));
        playerShelf.setItem(4, 5, new Item(BOOK, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
        //position 0,1 null
        playerShelf.setItem(0, 1, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //position 4,5 null
        playerShelf.setItem(0, 1, new Item(BOOK, 1));
        playerShelf.setItem(4, 5, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //different items
        playerShelf.setItem(0, 1, new Item(CAT, 1));
        playerShelf.setItem(1, 2, new Item(BOOK, 2));
        playerShelf.setItem(2, 3, new Item(BOOK, 3));
        playerShelf.setItem(3, 4, new Item(BOOK, 1));
        playerShelf.setItem(4, 5, new Item(BOOK, 2));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*b diagonal right to left full
          0 1 2 3 4 5
        0 x x x x b x
        1 x x x b x x
        2 x x b x x x
        3 x b x x x x
        4 b x x x x x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 4, new Item(BOOK, 1));
        playerShelf.setItem(1, 3, new Item(BOOK, 2));
        playerShelf.setItem(2, 2, new Item(BOOK, 3));
        playerShelf.setItem(3, 1, new Item(BOOK, 1));
        playerShelf.setItem(4, 0, new Item(BOOK, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
        //position 0,4 null
        playerShelf.setItem(0, 4, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //position 4,0 null
        playerShelf.setItem(0, 4, new Item(BOOK, 1));
        playerShelf.setItem(4, 0, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //different items
        playerShelf.setItem(0, 4, new Item(BOOK, 1));
        playerShelf.setItem(1, 3, new Item(BOOK, 2));
        playerShelf.setItem(2, 2, new Item(CAT, 3));
        playerShelf.setItem(3, 1, new Item(BOOK, 1));
        playerShelf.setItem(4, 0, new Item(CAT, 2));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*a diagonal right to left full
          0 1 2 3 4 5
        0 x x x x x a
        1 x x x x a x
        2 x x x a x x
        3 x x a x x x
        4 x a x x x x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 5, new Item(BOOK, 1));
        playerShelf.setItem(1, 4, new Item(BOOK, 2));
        playerShelf.setItem(2, 3, new Item(BOOK, 3));
        playerShelf.setItem(3, 2, new Item(BOOK, 1));
        playerShelf.setItem(4, 1, new Item(BOOK, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
        //position 0,5 null
        playerShelf.setItem(0, 5, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //position 4,1 null
        playerShelf.setItem(0, 5, new Item(BOOK, 1));
        playerShelf.setItem(4, 1, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //different items
        playerShelf.setItem(0, 5, new Item(BOOK, 1));
        playerShelf.setItem(1, 4, new Item(CAT, 2));
        playerShelf.setItem(2, 3, new Item(BOOK, 3));
        playerShelf.setItem(3, 2, new Item(BOOK, 1));
        playerShelf.setItem(4, 1, new Item(CAT, 2));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*generic shelf false
          0 1 2 3 4 5
        0 b c x x x t
        1 x b c x t x
        2 x x x c x x
        3 x p t b c x
        4 p a x x x x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 0, new Item(BOOK, 0));
        playerShelf.setItem(0, 1, new Item(CAT, 0));
        playerShelf.setItem(0, 5, new Item(TROPHY, 2));
        playerShelf.setItem(1, 1, new Item(BOOK, 1));
        playerShelf.setItem(1, 2, new Item(CAT, 1));
        playerShelf.setItem(1, 4, new Item(TROPHY, 1));
        playerShelf.setItem(2, 3, new Item(CAT, 2));
        playerShelf.setItem(3, 1, new Item(PLANTS, 1));
        playerShelf.setItem(3, 2, new Item(TROPHY, 0));
        playerShelf.setItem(3, 3, new Item(BOOK, 2));
        playerShelf.setItem(3, 4, new Item(CAT, 1));
        playerShelf.setItem(4, 0, new Item(PLANTS, 0));
        playerShelf.setItem(4, 1, new Item(FRAME, 2));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));

        /*generic shelf true
          0 1 2 3 4 5
        0 b c x x x t
        1 x b c x t x
        2 x x x t x x
        3 x p t b c x
        4 p t x x x x
         */
        playerShelf.setItem(4, 1, new Item(TROPHY, 1));
        playerShelf.setItem(2, 3, new Item(TROPHY, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
    }
}