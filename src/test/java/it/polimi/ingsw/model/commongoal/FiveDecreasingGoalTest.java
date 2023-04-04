package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.enums.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiveDecreasingGoalTest {

    @Test
    void specificGoal() {

        CommonGoalAbstract goal = new FiveDecreasingGoal(4);

        Shelf playerShelf = new Shelf();
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

        Shelf playerShelf2 = new Shelf();
        playerShelf2.setItem(0, 1, new Item(Type.BOOK, 0));
        playerShelf2.setItem(0, 2, new Item(Type.FRAME, 0));
        playerShelf2.setItem(1, 2, new Item(Type.BOOK, 0));
        playerShelf2.setItem(0, 3, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(1, 3, new Item(Type.GAME, 0));
        playerShelf2.setItem(2, 3, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(0, 4, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(1, 4, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(2, 4, new Item(Type.CAT, 0));
        playerShelf2.setItem(3, 4, new Item(Type.CAT, 0));
        playerShelf2.setItem(0, 5, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(1, 5, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(2, 5, new Item(Type.TROPHY, 0));
        playerShelf2.setItem(3, 5, new Item(Type.CAT, 0));
        playerShelf2.setItem(4, 5, new Item(Type.CAT, 0));

        assertTrue(goal.specificGoal(playerShelf2));
    }
}
