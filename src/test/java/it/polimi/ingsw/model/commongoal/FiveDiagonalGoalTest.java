package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.enums.Type.*;
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


        //b diagonal left to right full
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


        //a diagonal left to right full
        playerShelf.clear();
        playerShelf.setItem(1, 0, new Item(BOOK, 1));
        playerShelf.setItem(2, 1, new Item(BOOK, 2));
        playerShelf.setItem(3, 2, new Item(BOOK, 3));
        playerShelf.setItem(4, 3, new Item(BOOK, 1));
        playerShelf.setItem(5, 4, new Item(BOOK, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
        //position 1,0 null
        playerShelf.setItem(1, 0, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //position 5,4 null
        playerShelf.setItem(1, 0, new Item(BOOK, 1));
        playerShelf.setItem(5, 4, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //different items
        playerShelf.setItem(1, 0, new Item(CAT, 1));
        playerShelf.setItem(2, 1, new Item(BOOK, 2));
        playerShelf.setItem(3, 2, new Item(BOOK, 3));
        playerShelf.setItem(4, 3, new Item(BOOK, 1));
        playerShelf.setItem(5, 4, new Item(BOOK, 2));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        //b diagonal right to left full
        playerShelf.clear();
        playerShelf.setItem(0, 4, new Item(BOOK, 1));
        playerShelf.setItem(1, 3, new Item(BOOK, 2));
        playerShelf.setItem(2, 2, new Item(BOOK, 3));
        playerShelf.setItem(3, 1, new Item(BOOK, 1));
        playerShelf.setItem(4, 0, new Item(BOOK, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
        //position 0,0 null
        playerShelf.setItem(0, 4, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //position 4,4 null
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


        //a diagonal right to left full
        playerShelf.clear();
        playerShelf.setItem(1, 4, new Item(BOOK, 1));
        playerShelf.setItem(2, 3, new Item(BOOK, 2));
        playerShelf.setItem(3, 2, new Item(BOOK, 3));
        playerShelf.setItem(4, 1, new Item(BOOK, 1));
        playerShelf.setItem(5, 0, new Item(BOOK, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
        //position 0,0 null
        playerShelf.setItem(1, 4, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //position 4,4 null
        playerShelf.setItem(1, 4, new Item(BOOK, 1));
        playerShelf.setItem(5, 0, null);
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
        //different items
        playerShelf.setItem(1, 4, new Item(BOOK, 1));
        playerShelf.setItem(2, 3, new Item(CAT, 2));
        playerShelf.setItem(3, 2, new Item(BOOK, 3));
        playerShelf.setItem(4, 1, new Item(BOOK, 1));
        playerShelf.setItem(5, 0, new Item(CAT, 2));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*generic shelf false
          0 1 2 3 4
        0 b x x x p
        1 c b x p x
        2 x c x t x
        3 x x c b x
        4 x t x c x
        5 t x x x x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 0, new Item(BOOK, 0));
        playerShelf.setItem(0, 4, new Item(PLANTS, 0));
        playerShelf.setItem(1, 0, new Item(CAT, 0));
        playerShelf.setItem(1, 1, new Item(BOOK, 1));
        playerShelf.setItem(1, 3, new Item(PLANTS, 1));
        playerShelf.setItem(2, 1, new Item(CAT, 1));
        playerShelf.setItem(2, 3, new Item(TROPHY, 0));
        playerShelf.setItem(3, 2, new Item(CAT, 2));
        playerShelf.setItem(3, 3, new Item(BOOK, 2));
        playerShelf.setItem(4, 1, new Item(TROPHY, 1));
        playerShelf.setItem(4, 3, new Item(CAT, 1));
        playerShelf.setItem(5, 0, new Item(TROPHY, 2));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));

        /*generic shelf true
          0 1 2 3 4
        0 b x x x p
        1 c b x p t
        2 x c x t x
        3 x x t b x
        4 x t x c x
        5 t x x x x
         */
        playerShelf.setItem(1, 4, new Item(TROPHY, 1));
        playerShelf.setItem(3, 2, new Item(TROPHY, 2));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
    }
}