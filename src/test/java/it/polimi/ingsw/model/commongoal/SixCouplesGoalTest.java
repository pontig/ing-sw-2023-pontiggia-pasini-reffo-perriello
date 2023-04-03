package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Item;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.enums.Type.*;
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

        System.out.println("  ");
        //full shelf one item type (TROPHY)
        playerShelf.clear();
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                playerShelf.setItem(i, j, new Item(TROPHY, 1));
            }
        }
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));

        System.out.println("  ");
        //rowFirst less than columnFirst -> Column return true (BOOK)
        playerShelf.clear();
        playerShelf.setItem(0, 0, new Item(BOOK, 1));
        playerShelf.setItem(0, 1, new Item(BOOK, 2));
        playerShelf.setItem(0, 3, new Item(BOOK, 3));
        playerShelf.setItem(1, 3, new Item(BOOK, 1));
        playerShelf.setItem(1, 4, new Item(BOOK, 2));
        playerShelf.setItem(2, 1, new Item(BOOK, 3));
        playerShelf.setItem(2, 2, new Item(BOOK, 1));
        playerShelf.setItem(3, 2, new Item(BOOK, 2));
        playerShelf.setItem(3, 3, new Item(BOOK, 3));
        playerShelf.setItem(3, 4, new Item(BOOK, 1));
        playerShelf.setItem(4, 2, new Item(BOOK, 2));
        playerShelf.setItem(5, 2, new Item(BOOK, 3));
        playerShelf.setItem(5, 3, new Item(BOOK, 1));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));

        System.out.println("  ");
        //rowFirst more than columnFirst -> Row return true (CAT)
        playerShelf.clear();
        playerShelf.setItem(0, 3, new Item(CAT, 1));
        playerShelf.setItem(0, 4, new Item(CAT, 2));
        playerShelf.setItem(1, 2, new Item(CAT, 3));
        playerShelf.setItem(1, 3, new Item(CAT, 1));
        playerShelf.setItem(2, 0, new Item(CAT, 2));
        playerShelf.setItem(2, 1, new Item(CAT, 3));
        playerShelf.setItem(2, 3, new Item(CAT, 1));
        playerShelf.setItem(3, 1, new Item(CAT, 2));
        playerShelf.setItem(3, 4, new Item(CAT, 3));
        playerShelf.setItem(4, 1, new Item(CAT, 1));
        playerShelf.setItem(4, 2, new Item(CAT, 2));
        playerShelf.setItem(4, 3, new Item(CAT, 3));
        playerShelf.setItem(5, 0, new Item(CAT, 1));
        playerShelf.setItem(5, 1, new Item(CAT, 2));
        playerShelf.setItem(5, 3, new Item(CAT, 3));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));

        System.out.println("  ");
        //condition and path coverage (BOOK)
        playerShelf.clear();
        playerShelf.setItem(0, 1, new Item(CAT, 1));
        playerShelf.setItem(0, 2, new Item(BOOK, 1));
        playerShelf.setItem(0, 4, new Item(BOOK, 2));
        playerShelf.setItem(1, 0, new Item(BOOK, 3));
        playerShelf.setItem(1, 1, new Item(BOOK, 1));
        playerShelf.setItem(1, 2, new Item(PLANTS, 1));
        playerShelf.setItem(1, 3, new Item(BOOK, 1));
        playerShelf.setItem(1, 4, new Item(BOOK, 2));
        playerShelf.setItem(2, 0, new Item(BOOK, 3));
        playerShelf.setItem(2, 1, new Item(BOOK, 1));
        playerShelf.setItem(2, 2, new Item(BOOK, 2));
        playerShelf.setItem(2, 3, new Item(BOOK, 3));
        playerShelf.setItem(2, 4, new Item(BOOK, 1));
        playerShelf.setItem(3, 0, new Item(CAT, 2));
        playerShelf.setItem(3, 1, new Item(BOOK, 2));
        playerShelf.setItem(3, 2, new Item(PLANTS, 2));
        playerShelf.setItem(3, 3, new Item(FRAME,1));
        playerShelf.setItem(3, 4, new Item(FRAME,2));
        playerShelf.setItem(4, 0, new Item(GAME, 1));
        playerShelf.setItem(4, 1, new Item(CAT, 3));
        playerShelf.setItem(4, 2, new Item(GAME, 2));
        playerShelf.setItem(4, 3, new Item(BOOK, 3));
        playerShelf.setItem(4, 4, new Item(GAME,3));
        playerShelf.setItem(5, 0, new Item(BOOK, 1));
        playerShelf.setItem(5, 1, new Item(CAT, 1));
        playerShelf.setItem(5, 2, new Item(BOOK, 2));
        playerShelf.setItem(5, 3, new Item(BOOK, 3));
        playerShelf.setItem(5, 4, new Item(BOOK, 1));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));
    }
}