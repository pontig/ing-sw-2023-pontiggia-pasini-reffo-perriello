package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FourAnglesGoalTest {

    @Test
    void specificGoal() {

        CommonGoalAbstract goal = new FourAnglesGoal(4);

        Shelf playerShelf = new Shelf();                                     //Example from the rulebook
        playerShelf.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf.setItem(1, 0, new Item(Type.PLANTS, 0));
        playerShelf.setItem(2, 0, new Item(Type.PLANTS, 0));
        playerShelf.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf.setItem(1, 1, new Item(Type.PLANTS, 0));
        playerShelf.setItem(2, 1, new Item(Type.PLANTS, 0));
        playerShelf.setItem(3, 1, new Item(Type.CAT, 0));
        playerShelf.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf.setItem(2, 2, new Item(Type.FRAME, 0));
        playerShelf.setItem(3, 2, new Item(Type.BOOK, 0));
        playerShelf.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf.setItem(1, 3, new Item(Type.GAME, 0));
        playerShelf.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf.setItem(3, 3, new Item(Type.GAME, 0));
        playerShelf.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf.setItem(2, 4, new Item(Type.CAT, 0));
        playerShelf.setItem(3, 4, new Item(Type.CAT, 0));
        playerShelf.setItem(4, 4, new Item(Type.CAT, 0));
        playerShelf.setItem(0, 5, new Item(Type.TROPHY, 0));
        playerShelf.setItem(1, 5, new Item(Type.TROPHY, 0));
        playerShelf.setItem(2, 5, new Item(Type.TROPHY, 0));
        playerShelf.setItem(3, 5, new Item(Type.CAT, 0));
        playerShelf.setItem(4, 5, new Item(Type.CAT, 0));

        assertFalse(goal.specificGoal(playerShelf));

        Shelf playerShelf2 = new Shelf();                                        //Only first and last column full with matching angles
        playerShelf2.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf2.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf2.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf2.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(0, 5, new Item(Type.PLANTS, 0));
        playerShelf2.setItem(4, 0, new Item(Type.PLANTS, 0));
        playerShelf2.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf2.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf2.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf2.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf2.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertTrue(goal.specificGoal(playerShelf2));

        Shelf playerShelf3 = new Shelf();                                        //Top left (x=0, y=0) empty and other 3 matching
        playerShelf3.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf3.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf3.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(0, 5, new Item(Type.PLANTS, 0));
        playerShelf3.setItem(4, 0, new Item(Type.PLANTS, 0));
        playerShelf3.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf3.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf3.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf3.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf3.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf3));

        Shelf playerShelf4 = new Shelf();                                        //Top right (x=4, y=0) empty and other 3 matching
        playerShelf4.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf4.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf4.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf4.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(0, 5, new Item(Type.PLANTS, 0));
        playerShelf4.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf4.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf4.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf4.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf4.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf4));

        Shelf playerShelf5 = new Shelf();                                        //All shelf empty

        assertFalse(goal.specificGoal(playerShelf5));

        Shelf playerShelf6 = new Shelf();                                        //First and last column empty
        playerShelf6.setItem(1, 0, new Item(Type.PLANTS, 0));
        playerShelf6.setItem(1, 1, new Item(Type.BOOK, 0));
        playerShelf6.setItem(1, 2, new Item(Type.FRAME, 0));
        playerShelf6.setItem(1, 3, new Item(Type.TROPHY, 0));
        playerShelf6.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf6.setItem(1, 5, new Item(Type.PLANTS, 0));
        playerShelf6.setItem(2, 0, new Item(Type.CAT, 0));
        playerShelf6.setItem(2, 1, new Item(Type.CAT, 0));
        playerShelf6.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf6.setItem(2, 3, new Item(Type.CAT, 0));
        playerShelf6.setItem(2, 4, new Item(Type.BOOK, 0));
        playerShelf6.setItem(2, 5, new Item(Type.PLANTS, 0));
        playerShelf6.setItem(3, 0, new Item(Type.CAT, 0));
        playerShelf6.setItem(3, 1, new Item(Type.CAT, 0));
        playerShelf6.setItem(3, 2, new Item(Type.BOOK, 0));
        playerShelf6.setItem(3, 3, new Item(Type.CAT, 0));
        playerShelf6.setItem(3, 4, new Item(Type.BOOK, 0));
        playerShelf6.setItem(3, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf6));

        Shelf playerShelf7 = new Shelf();                                        //Full board with matching angles
        playerShelf7.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf7.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf7.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf7.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf7.setItem(0, 5, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(1, 0, new Item(Type.CAT, 0));
        playerShelf7.setItem(1, 1, new Item(Type.CAT, 0));
        playerShelf7.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf7.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf7.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf7.setItem(1, 5, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(2, 0, new Item(Type.CAT, 0));
        playerShelf7.setItem(2, 1, new Item(Type.CAT, 0));
        playerShelf7.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf7.setItem(2, 3, new Item(Type.CAT, 0));
        playerShelf7.setItem(2, 4, new Item(Type.BOOK, 0));
        playerShelf7.setItem(2, 5, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(3, 0, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf7.setItem(3, 2, new Item(Type.FRAME, 0));
        playerShelf7.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf7.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf7.setItem(3, 5, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(4, 0, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf7.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf7.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf7.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf7.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertTrue(goal.specificGoal(playerShelf7));

        Shelf playerShelf8 = new Shelf();                                        //Full board with (x=0, y=4) not matching
        playerShelf8.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf8.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf8.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf8.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf8.setItem(0, 5, new Item(Type.CAT, 0));
        playerShelf8.setItem(1, 0, new Item(Type.CAT, 0));
        playerShelf8.setItem(1, 1, new Item(Type.CAT, 0));
        playerShelf8.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf8.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf8.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf8.setItem(1, 5, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(2, 0, new Item(Type.CAT, 0));
        playerShelf8.setItem(2, 1, new Item(Type.CAT, 0));
        playerShelf8.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf8.setItem(2, 3, new Item(Type.CAT, 0));
        playerShelf8.setItem(2, 4, new Item(Type.BOOK, 0));
        playerShelf8.setItem(2, 5, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(3, 0, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf8.setItem(3, 2, new Item(Type.FRAME, 0));
        playerShelf8.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf8.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf8.setItem(3, 5, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(4, 0, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf8.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf8.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf8.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf8.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf8));

        Shelf playerShelf9 = new Shelf();                                        //Full board with (x=0, y=0) not matching
        playerShelf9.setItem(0, 0, new Item(Type.CAT, 0));
        playerShelf9.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf9.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf9.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(0, 5, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(1, 0, new Item(Type.CAT, 0));
        playerShelf9.setItem(1, 1, new Item(Type.CAT, 0));
        playerShelf9.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf9.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf9.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf9.setItem(1, 5, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(2, 0, new Item(Type.CAT, 0));
        playerShelf9.setItem(2, 1, new Item(Type.CAT, 0));
        playerShelf9.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf9.setItem(2, 3, new Item(Type.CAT, 0));
        playerShelf9.setItem(2, 4, new Item(Type.BOOK, 0));
        playerShelf9.setItem(2, 5, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(3, 0, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf9.setItem(3, 2, new Item(Type.FRAME, 0));
        playerShelf9.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(3, 5, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(4, 0, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf9.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf9.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf9.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf9.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf9));

        Shelf playerShelf10 = new Shelf();                                        //Full board with (x=4, y=0) not matching
        playerShelf10.setItem(0, 0, new Item(Type.CAT, 0));
        playerShelf10.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf10.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf10.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(0, 5, new Item(Type.CAT, 0));
        playerShelf10.setItem(1, 0, new Item(Type.CAT, 0));
        playerShelf10.setItem(1, 1, new Item(Type.CAT, 0));
        playerShelf10.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf10.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf10.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf10.setItem(1, 5, new Item(Type.PLANTS, 0));
        playerShelf10.setItem(2, 0, new Item(Type.CAT, 0));
        playerShelf10.setItem(2, 1, new Item(Type.CAT, 0));
        playerShelf10.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf10.setItem(2, 3, new Item(Type.CAT, 0));
        playerShelf10.setItem(2, 4, new Item(Type.BOOK, 0));
        playerShelf10.setItem(2, 5, new Item(Type.PLANTS, 0));
        playerShelf10.setItem(3, 0, new Item(Type.PLANTS, 0));
        playerShelf10.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf10.setItem(3, 2, new Item(Type.FRAME, 0));
        playerShelf10.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(3, 5, new Item(Type.PLANTS, 0));
        playerShelf10.setItem(4, 0, new Item(Type.PLANTS, 0));
        playerShelf10.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf10.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf10.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf10.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf10.setItem(4, 5, new Item(Type.CAT, 0));

        assertFalse(goal.specificGoal(playerShelf10));

        Shelf playerShelf11 = new Shelf();                                        //Full board with (x=4, y=5) not matching
        playerShelf11.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf11.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf11.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(0, 5, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(1, 0, new Item(Type.CAT, 0));
        playerShelf11.setItem(1, 1, new Item(Type.CAT, 0));
        playerShelf11.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf11.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf11.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf11.setItem(1, 5, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(2, 0, new Item(Type.CAT, 0));
        playerShelf11.setItem(2, 1, new Item(Type.CAT, 0));
        playerShelf11.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf11.setItem(2, 3, new Item(Type.CAT, 0));
        playerShelf11.setItem(2, 4, new Item(Type.BOOK, 0));
        playerShelf11.setItem(2, 5, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(3, 0, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf11.setItem(3, 2, new Item(Type.FRAME, 0));
        playerShelf11.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(3, 5, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(4, 0, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf11.setItem(4, 2, new Item(Type.BOOK, 0));
        playerShelf11.setItem(4, 3, new Item(Type.CAT, 0));
        playerShelf11.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf11.setItem(4, 5, new Item(Type.CAT, 0));

        assertFalse(goal.specificGoal(playerShelf11));
    }
}