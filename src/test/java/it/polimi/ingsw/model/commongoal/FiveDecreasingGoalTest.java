package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiveDecreasingGoalTest {

    @Test
    void specificGoal() {

        CommonGoalAbstract goal = new FiveDecreasingGoal(4);

        Shelf playerShelf = new Shelf();                                          //Example from the rulebook
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

        Shelf playerShelf2 = new Shelf();                                                   // case 2 (check FiveDecreasingGoal)
        playerShelf2.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf2.setItem(0, 2, new Item(Type.BOOK, 0));
        playerShelf2.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(0, 5, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(1, 2, new Item(Type.CAT, 0));
        playerShelf2.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf2.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(1, 5, new Item(Type.GAME, 0));
        playerShelf2.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf2.setItem(3, 4, new Item(Type.CAT, 0));
        playerShelf2.setItem(3, 5, new Item(Type.BOOK, 0));
        playerShelf2.setItem(4, 5, new Item(Type.FRAME, 0));

        assertTrue(goal.specificGoal(playerShelf2));

        Shelf playerShelf3 = new Shelf();                                                   // case 3 (check FiveDecreasingGoal)
        playerShelf3.setItem(0, 4, new Item(Type.PLANTS, 0));
        playerShelf3.setItem(0, 5, new Item(Type.BOOK, 0));
        playerShelf3.setItem(1, 3, new Item(Type.GAME, 0));
        playerShelf3.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf3.setItem(1, 5, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(2, 2, new Item(Type.PLANTS, 0));
        playerShelf3.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf3.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf3.setItem(3, 2, new Item(Type.CAT, 0));
        playerShelf3.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(3, 5, new Item(Type.TROPHY, 0));
        playerShelf3.setItem(4, 0, new Item(Type.BOOK, 0));
        playerShelf3.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf3.setItem(4, 2, new Item(Type.CAT, 0));
        playerShelf3.setItem(4, 3, new Item(Type.FRAME, 0));
        playerShelf3.setItem(4, 4, new Item(Type.GAME, 0));
        playerShelf3.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertTrue(goal.specificGoal(playerShelf3));

        Shelf playerShelf4 = new Shelf();                                                   // case 1 (check FiveDecreasingGoal)
        playerShelf4.setItem(0, 5, new Item(Type.BOOK, 0));
        playerShelf4.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf4.setItem(1, 5, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf4.setItem(3, 2, new Item(Type.CAT, 0));
        playerShelf4.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(3, 5, new Item(Type.TROPHY, 0));
        playerShelf4.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf4.setItem(4, 2, new Item(Type.CAT, 0));
        playerShelf4.setItem(4, 3, new Item(Type.FRAME, 0));
        playerShelf4.setItem(4, 4, new Item(Type.GAME, 0));
        playerShelf4.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertTrue(goal.specificGoal(playerShelf4));

        Shelf playerShelf5 = new Shelf();                                                   // case 4 (check FiveDecreasingGoal)
        playerShelf5.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf5.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf5.setItem(0, 2, new Item(Type.CAT, 0));
        playerShelf5.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf5.setItem(0, 4, new Item(Type.GAME, 0));
        playerShelf5.setItem(0, 5, new Item(Type.TROPHY, 0));
        playerShelf5.setItem(1, 1, new Item(Type.BOOK, 0));
        playerShelf5.setItem(1, 2, new Item(Type.CAT, 0));
        playerShelf5.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf5.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf5.setItem(1, 5, new Item(Type.GAME, 0));
        playerShelf5.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf5.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf5.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf5.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf5.setItem(3, 3, new Item(Type.BOOK, 0));
        playerShelf5.setItem(3, 4, new Item(Type.CAT, 0));
        playerShelf5.setItem(3, 5, new Item(Type.BOOK, 0));
        playerShelf5.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf5.setItem(4, 5, new Item(Type.FRAME, 0));

        assertTrue(goal.specificGoal(playerShelf5));

        Shelf playerShelf6 = new Shelf();                                             // case 1 with (x=0,y=5) null
        playerShelf6.setItem(1, 5, new Item(Type.CAT, 0));
        playerShelf6.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf6.setItem(2, 3, new Item(Type.GAME, 0));
        playerShelf6.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf6.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf6.setItem(3, 2, new Item(Type.PLANTS, 0));
        playerShelf6.setItem(3, 3, new Item(Type.CAT, 0));
        playerShelf6.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf6.setItem(3, 5, new Item(Type.BOOK, 0));
        playerShelf6.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf6.setItem(4, 2, new Item(Type.GAME, 0));
        playerShelf6.setItem(4, 3, new Item(Type.PLANTS, 0));
        playerShelf6.setItem(4, 4, new Item(Type.FRAME, 0));
        playerShelf6.setItem(4, 5, new Item(Type.FRAME, 0));

        assertFalse(goal.specificGoal(playerShelf6));

        Shelf playerShelf7 = new Shelf();                                             // case 1 with (x=1,y=4) null
        playerShelf7.setItem(0, 5, new Item(Type.GAME, 0));
        playerShelf7.setItem(1, 5, new Item(Type.CAT, 0));
        playerShelf7.setItem(2, 3, new Item(Type.GAME, 0));
        playerShelf7.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf7.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf7.setItem(3, 2, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(3, 3, new Item(Type.CAT, 0));
        playerShelf7.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf7.setItem(3, 5, new Item(Type.BOOK, 0));
        playerShelf7.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf7.setItem(4, 2, new Item(Type.GAME, 0));
        playerShelf7.setItem(4, 3, new Item(Type.PLANTS, 0));
        playerShelf7.setItem(4, 4, new Item(Type.FRAME, 0));
        playerShelf7.setItem(4, 5, new Item(Type.FRAME, 0));

        assertFalse(goal.specificGoal(playerShelf7));

        Shelf playerShelf8 = new Shelf();                                             // case 1 with (x=2,y=3) null
        playerShelf8.setItem(0, 5, new Item(Type.GAME, 0));
        playerShelf6.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf8.setItem(1, 5, new Item(Type.CAT, 0));
        playerShelf8.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf8.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf8.setItem(3, 2, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(3, 3, new Item(Type.CAT, 0));
        playerShelf8.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf8.setItem(3, 5, new Item(Type.BOOK, 0));
        playerShelf8.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf8.setItem(4, 2, new Item(Type.GAME, 0));
        playerShelf8.setItem(4, 3, new Item(Type.PLANTS, 0));
        playerShelf8.setItem(4, 4, new Item(Type.FRAME, 0));
        playerShelf8.setItem(4, 5, new Item(Type.FRAME, 0));

        assertFalse(goal.specificGoal(playerShelf8));

        Shelf playerShelf9 = new Shelf();                                                   // case 1 with the other part of the shelf not empty
        playerShelf9.setItem(0, 4, new Item(Type.CAT, 0));
        playerShelf9.setItem(0, 5, new Item(Type.GAME, 0));
        playerShelf9.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf9.setItem(1, 3, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf9.setItem(1, 5, new Item(Type.PLANTS, 0));
        playerShelf9.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf9.setItem(3, 2, new Item(Type.CAT, 0));
        playerShelf9.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(3, 4, new Item(Type.GAME, 0));
        playerShelf9.setItem(3, 5, new Item(Type.TROPHY, 0));
        playerShelf9.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf9.setItem(4, 2, new Item(Type.CAT, 0));
        playerShelf9.setItem(4, 3, new Item(Type.FRAME, 0));
        playerShelf9.setItem(4, 4, new Item(Type.GAME, 0));
        playerShelf9.setItem(4, 5, new Item(Type.PLANTS, 0));


        assertFalse(goal.specificGoal(playerShelf9));

        Shelf playerShelf10 = new Shelf();                                                   // case 2 with the other part of the shelf not empty
        playerShelf10.setItem(0, 1, new Item(Type.GAME, 0));
        playerShelf10.setItem(0, 2, new Item(Type.BOOK, 0));
        playerShelf10.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(0, 4, new Item(Type.PLANTS, 0));
        playerShelf10.setItem(0, 5, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(1, 2, new Item(Type.CAT, 0));
        playerShelf10.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf10.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(1, 5, new Item(Type.GAME, 0));
        playerShelf10.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf10.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf10.setItem(3, 3, new Item(Type.GAME, 0));
        playerShelf10.setItem(3, 4, new Item(Type.CAT, 0));
        playerShelf10.setItem(3, 5, new Item(Type.BOOK, 0));
        playerShelf10.setItem(4, 2, new Item(Type.FRAME, 0));
        playerShelf10.setItem(4, 3, new Item(Type.PLANTS, 0));
        playerShelf10.setItem(4, 4, new Item(Type.GAME, 0));
        playerShelf10.setItem(4, 5, new Item(Type.FRAME, 0));

        assertFalse(goal.specificGoal(playerShelf10));

        Shelf playerShelf11 = new Shelf();                                                   // case 2 with incomplete diagonals
        playerShelf11.setItem(0, 1, new Item(Type.GAME, 0));
        playerShelf11.setItem(0, 2, new Item(Type.BOOK, 0));
        playerShelf11.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(0, 4, new Item(Type.PLANTS, 0));
        playerShelf11.setItem(0, 5, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(1, 2, new Item(Type.CAT, 0));
        playerShelf11.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf11.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(1, 5, new Item(Type.GAME, 0));
        playerShelf11.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf11.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf11.setItem(3, 5, new Item(Type.BOOK, 0));

        assertFalse(goal.specificGoal(playerShelf11));

        Shelf playerShelf12 = new Shelf();                                                   // case 3 with (x=0,y=4) null
        playerShelf12.setItem(0, 5, new Item(Type.BOOK, 0));
        playerShelf12.setItem(1, 3, new Item(Type.GAME, 0));
        playerShelf12.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf12.setItem(1, 5, new Item(Type.TROPHY, 0));
        playerShelf12.setItem(2, 2, new Item(Type.PLANTS, 0));
        playerShelf12.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf12.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf12.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf12.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf12.setItem(3, 2, new Item(Type.CAT, 0));
        playerShelf12.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf12.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf12.setItem(3, 5, new Item(Type.TROPHY, 0));
        playerShelf12.setItem(4, 0, new Item(Type.BOOK, 0));
        playerShelf12.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf12.setItem(4, 2, new Item(Type.CAT, 0));
        playerShelf12.setItem(4, 3, new Item(Type.FRAME, 0));
        playerShelf12.setItem(4, 4, new Item(Type.GAME, 0));
        playerShelf12.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf12));

        Shelf playerShelf13 = new Shelf();                                                   // case 3 with (x=1,y=3) null
        playerShelf13.setItem(0, 4, new Item(Type.PLANTS, 0));
        playerShelf13.setItem(0, 5, new Item(Type.BOOK, 0));
        playerShelf13.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf13.setItem(1, 5, new Item(Type.TROPHY, 0));
        playerShelf13.setItem(2, 2, new Item(Type.PLANTS, 0));
        playerShelf13.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf13.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf13.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf13.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf13.setItem(3, 2, new Item(Type.CAT, 0));
        playerShelf13.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf13.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf13.setItem(3, 5, new Item(Type.TROPHY, 0));
        playerShelf13.setItem(4, 0, new Item(Type.BOOK, 0));
        playerShelf13.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf13.setItem(4, 2, new Item(Type.CAT, 0));
        playerShelf13.setItem(4, 3, new Item(Type.FRAME, 0));
        playerShelf13.setItem(4, 4, new Item(Type.GAME, 0));
        playerShelf13.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf13));

        Shelf playerShelf14 = new Shelf();                                                   // case 3 with the other part of the shelf not empty
        playerShelf14.setItem(0, 4, new Item(Type.PLANTS, 0));
        playerShelf14.setItem(0, 5, new Item(Type.BOOK, 0));
        playerShelf14.setItem(1, 1, new Item(Type.GAME, 0));
        playerShelf14.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf14.setItem(1, 3, new Item(Type.GAME, 0));
        playerShelf14.setItem(1, 4, new Item(Type.BOOK, 0));
        playerShelf14.setItem(1, 5, new Item(Type.TROPHY, 0));
        playerShelf14.setItem(2, 2, new Item(Type.PLANTS, 0));
        playerShelf14.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf14.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf14.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf14.setItem(3, 1, new Item(Type.BOOK, 0));
        playerShelf14.setItem(3, 2, new Item(Type.CAT, 0));
        playerShelf14.setItem(3, 3, new Item(Type.TROPHY, 0));
        playerShelf14.setItem(3, 4, new Item(Type.TROPHY, 0));
        playerShelf14.setItem(3, 5, new Item(Type.TROPHY, 0));
        playerShelf14.setItem(4, 0, new Item(Type.BOOK, 0));
        playerShelf14.setItem(4, 1, new Item(Type.CAT, 0));
        playerShelf14.setItem(4, 2, new Item(Type.CAT, 0));
        playerShelf14.setItem(4, 3, new Item(Type.FRAME, 0));
        playerShelf14.setItem(4, 4, new Item(Type.GAME, 0));
        playerShelf14.setItem(4, 5, new Item(Type.PLANTS, 0));

        assertFalse(goal.specificGoal(playerShelf14));

        Shelf playerShelf15 = new Shelf();                                                   // case 4 with incomplete diagonals
        playerShelf15.setItem(0, 0, new Item(Type.PLANTS, 0));
        playerShelf15.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf15.setItem(0, 2, new Item(Type.CAT, 0));
        playerShelf15.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf15.setItem(0, 4, new Item(Type.GAME, 0));
        playerShelf15.setItem(0, 5, new Item(Type.TROPHY, 0));
        playerShelf15.setItem(1, 2, new Item(Type.CAT, 0));
        playerShelf15.setItem(1, 3, new Item(Type.CAT, 0));
        playerShelf15.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf15.setItem(1, 5, new Item(Type.GAME, 0));
        playerShelf15.setItem(2, 2, new Item(Type.BOOK, 0));
        playerShelf15.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf15.setItem(2, 4, new Item(Type.TROPHY, 0));
        playerShelf15.setItem(2, 5, new Item(Type.CAT, 0));
        playerShelf15.setItem(3, 3, new Item(Type.BOOK, 0));
        playerShelf15.setItem(3, 4, new Item(Type.CAT, 0));
        playerShelf15.setItem(3, 5, new Item(Type.BOOK, 0));
        playerShelf15.setItem(4, 4, new Item(Type.BOOK, 0));
        playerShelf15.setItem(4, 5, new Item(Type.FRAME, 0));

        assertFalse(goal.specificGoal(playerShelf15));
    }
}
