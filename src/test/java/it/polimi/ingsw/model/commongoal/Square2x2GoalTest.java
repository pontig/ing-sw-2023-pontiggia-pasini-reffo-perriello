package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.enums.Type.*;
import static org.junit.jupiter.api.Assertions.*;

class Square2x2GoalTest {
    CommonGoalAbstract twoPlayers = new Square2x2Goal(2);
    CommonGoalAbstract threePlayers = new Square2x2Goal(3);
    CommonGoalAbstract fourPlayers = new Square2x2Goal(4);
    Shelf playerShelf = new Shelf();

    @Test
    void specificGoal() {
        //empty
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        //full
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++)
                playerShelf.setItem(i, j, new Item(BOOK, 1));
        }
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));


        /*all condition of null
          0 1 2 3 4 5
        0 a a x a x x
        1 a x x a x x
        2 x a x x a a
        3 a a x a x x
        4 x a x a a x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 0, new Item(BOOK,0));
        playerShelf.setItem(0, 1, new Item(BOOK,1));
        playerShelf.setItem(0, 3, new Item(BOOK,2));
        playerShelf.setItem(1, 0, new Item(BOOK,0));
        playerShelf.setItem(1, 3, new Item(BOOK,1));
        playerShelf.setItem(2, 1, new Item(BOOK,2));
        playerShelf.setItem(2, 4, new Item(BOOK,0));
        playerShelf.setItem(2, 5, new Item(BOOK,1));
        playerShelf.setItem(3, 0, new Item(BOOK,2));
        playerShelf.setItem(3, 1, new Item(BOOK,0));
        playerShelf.setItem(3, 3, new Item(BOOK,1));
        playerShelf.setItem(4, 1, new Item(BOOK,2));
        playerShelf.setItem(4, 3, new Item(BOOK,0));
        playerShelf.setItem(4, 4, new Item(BOOK,1));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*boundaries limit
          0 1 2 3 4 5
        0 a x x x x x
        1 a x x x x x
        2 a x x x x x
        3 a x x x x x
        4 a a a a a a
         */
        playerShelf.clear();
        playerShelf.setItem(0, 0, new Item(BOOK,0));
        playerShelf.setItem(1, 0, new Item(BOOK,1));
        playerShelf.setItem(2, 0, new Item(BOOK,2));
        playerShelf.setItem(3, 0, new Item(BOOK,0));
        playerShelf.setItem(4, 0, new Item(BOOK,1));
        playerShelf.setItem(4, 1, new Item(BOOK,1));
        playerShelf.setItem(4, 2, new Item(BOOK,1));
        playerShelf.setItem(4, 3, new Item(BOOK,1));
        playerShelf.setItem(4, 4, new Item(BOOK,1));
        playerShelf.setItem(4, 5, new Item(BOOK,1));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*two 2x2 square on boundaries angles
          0 1 2 3 4 5
        0 x x x x x x
        1 x x x x x x
        2 x x x x x x
        3 a a x x a a
        4 a a x x a a
         */
        playerShelf.clear();
        playerShelf.setItem(3, 0, new Item(BOOK,0));
        playerShelf.setItem(3, 1, new Item(BOOK,1));
        playerShelf.setItem(3, 4, new Item(BOOK,2));
        playerShelf.setItem(3, 5, new Item(BOOK,0));
        playerShelf.setItem(4, 0, new Item(BOOK,1));
        playerShelf.setItem(4, 1, new Item(BOOK,2));
        playerShelf.setItem(4, 4, new Item(BOOK,0));
        playerShelf.setItem(4, 5, new Item(BOOK,1));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));


        /*overlapping 2x2 square
          0 1 2 3 4 5
        0 x x x a a a
        1 x x x a a a
        2 x b b b a a
        3 x b b b x x
        4 x b b b x x
         */
        playerShelf.clear();
        playerShelf.setItem(0, 3, new Item(BOOK,0));
        playerShelf.setItem(0, 4, new Item(BOOK,1));
        playerShelf.setItem(0, 5, new Item(BOOK,2));
        playerShelf.setItem(1, 3, new Item(BOOK,0));
        playerShelf.setItem(1, 4, new Item(BOOK,1));
        playerShelf.setItem(1, 5, new Item(BOOK,2));
        playerShelf.setItem(2, 1, new Item(CAT,0));
        playerShelf.setItem(2, 2, new Item(CAT,1));
        playerShelf.setItem(2, 3, new Item(CAT,0));
        playerShelf.setItem(2, 4, new Item(BOOK,1));
        playerShelf.setItem(2, 5, new Item(BOOK,2));
        playerShelf.setItem(3, 1, new Item(CAT,2));
        playerShelf.setItem(3, 2, new Item(CAT,0));
        playerShelf.setItem(3, 3, new Item(CAT,1));
        playerShelf.setItem(4, 1, new Item(CAT,2));
        playerShelf.setItem(4, 2, new Item(CAT,0));
        playerShelf.setItem(4, 3, new Item(CAT,1));
        assertEquals(false, twoPlayers.specificGoal(playerShelf));
        assertEquals(false, threePlayers.specificGoal(playerShelf));
        assertEquals(false, fourPlayers.specificGoal(playerShelf));


        /*generic shelf two 2x2
          0 1 2 3 4 5
        0 x c c c a a
        1 x x c t t a
        2 x x x t t b
        3 a t t b b b
        4 a t t b b b
         */
        playerShelf.setItem(0, 1, new Item(CAT, 0));
        playerShelf.setItem(0, 2, new Item(CAT, 1));
        playerShelf.setItem(0, 3, new Item(CAT, 0));
        playerShelf.setItem(0, 4, new Item(BOOK, 0));
        playerShelf.setItem(0, 5, new Item(BOOK, 1));
        playerShelf.setItem(1, 2, new Item(CAT, 1));
        playerShelf.setItem(1, 3, new Item(TROPHY, 2));
        playerShelf.setItem(1, 4, new Item(TROPHY, 2));
        playerShelf.setItem(1, 5, new Item(BOOK, 0));
        playerShelf.setItem(2, 3, new Item(TROPHY, 0));
        playerShelf.setItem(2, 4, new Item(TROPHY, 1));
        playerShelf.setItem(2, 5, new Item(PLANTS, 2));
        playerShelf.setItem(3, 0, new Item(BOOK, 0));
        playerShelf.setItem(3, 1, new Item(TROPHY, 0));
        playerShelf.setItem(3, 2, new Item(TROPHY, 2));
        playerShelf.setItem(3, 3, new Item(PLANTS, 2));
        playerShelf.setItem(3, 4, new Item(PLANTS, 2));
        playerShelf.setItem(3, 5, new Item(PLANTS, 0));
        playerShelf.setItem(4, 0, new Item(BOOK, 0));
        playerShelf.setItem(4, 1, new Item(TROPHY, 0));
        playerShelf.setItem(4, 2, new Item(TROPHY, 1));
        playerShelf.setItem(4, 3, new Item(PLANTS, 0));
        playerShelf.setItem(4, 4, new Item(PLANTS, 0));
        playerShelf.setItem(4, 5, new Item(PLANTS, 0));
        assertEquals(true, twoPlayers.specificGoal(playerShelf));
        assertEquals(true, threePlayers.specificGoal(playerShelf));
        assertEquals(true, fourPlayers.specificGoal(playerShelf));
    }
}