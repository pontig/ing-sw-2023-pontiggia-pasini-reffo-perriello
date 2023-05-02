package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiveXGoalTest {

    @Test
    void specificGoal() {
        // Five tiles of the same type arranged in an X shape.
        CommonGoalAbstract goal = new FiveXGoal(4);
        Shelf shelfOne = new Shelf();

        shelfOne.setItem(3,4, new Item(Type.TROPHY, 0));
        shelfOne.setItem(4,5, new Item(Type.TROPHY, 0));
        shelfOne.setItem(2,3, new Item(Type.TROPHY, 0));
        shelfOne.setItem(2,5, new Item(Type.TROPHY, 0));
        shelfOne.setItem(4,3, new Item(Type.TROPHY, 0));

        assertTrue(goal.specificGoal(shelfOne));

        Shelf shelfTwo = new Shelf();
        shelfTwo.setItem(0,4, new Item(Type.BOOK, 0));
        shelfTwo.setItem(0,5, new Item(Type.BOOK, 0));
        shelfTwo.setItem(1,3, new Item(Type.BOOK, 0));
        shelfTwo.setItem(1,4, new Item(Type.BOOK, 0));
        shelfTwo.setItem(1,5, new Item(Type.BOOK, 0));
        shelfTwo.setItem(2,5, new Item(Type.BOOK, 0));
        shelfTwo.setItem(3,3, new Item(Type.GAME, 0));
        shelfTwo.setItem(3,4, new Item(Type.GAME, 0));
        shelfTwo.setItem(3,5, new Item(Type.GAME, 0));
        shelfTwo.setItem(4,3, new Item(Type.GAME, 0));

        assertFalse(goal.specificGoal(shelfTwo));
    }

}