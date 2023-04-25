package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacentDifferentItemsGoalTest {

    @Test
    void specificGoalOne() {
        // Three columns each formed by 6 tiles of maximum three different types.One column can show the same or a
        // different combination of another column.

        CommonGoalAbstract goalOne = new AdjacentDifferentItemsGoal(4, 'v', 3, 3);
        Shelf shelf = new Shelf();

        shelf.setItem(0, 0, new Item(Type.TROPHY, 0));
        shelf.setItem(0, 1, new Item(Type.TROPHY, 0));
        shelf.setItem(0, 2, new Item(Type.TROPHY, 0));
        shelf.setItem(0, 3, new Item(Type.BOOK, 0));
        shelf.setItem(0, 4, new Item(Type.BOOK, 0));
        shelf.setItem(0, 5, new Item(Type.FRAME, 0));

        shelf.setItem(1, 3, new Item(Type.TROPHY, 0));
        shelf.setItem(1, 4, new Item(Type.CAT, 0));
        shelf.setItem(1, 5, new Item(Type.FRAME, 0));

        shelf.setItem(2, 0, new Item(Type.CAT, 0));
        shelf.setItem(2, 1, new Item(Type.BOOK, 0));
        shelf.setItem(2, 2, new Item(Type.CAT, 0));
        shelf.setItem(2, 3, new Item(Type.CAT, 0));
        shelf.setItem(2, 4, new Item(Type.BOOK, 0));
        shelf.setItem(2, 5, new Item(Type.BOOK, 0));

        shelf.setItem(3, 5, new Item(Type.TROPHY, 0));

        shelf.setItem(4, 0, new Item(Type.FRAME, 0));
        shelf.setItem(4, 1, new Item(Type.FRAME, 0));
        shelf.setItem(4, 2, new Item(Type.FRAME, 0));
        shelf.setItem(4, 3, new Item(Type.FRAME, 0));
        shelf.setItem(4, 4, new Item(Type.FRAME, 0));
        shelf.setItem(4, 5, new Item(Type.FRAME, 0));

        assertTrue(goalOne.specificGoal(shelf));

        shelf.setItem(0, 0, new Item(Type.CAT, 0));

        assertFalse(goalOne.specificGoal(shelf));

    }

    @Test
    void specificGoalTwo() {
        // Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a
        // different combination of another line.

        CommonGoalAbstract goalTwo = new AdjacentDifferentItemsGoal(4, 'h', 3, 4);
        Shelf shelf = new Shelf();

        shelf.setItem(0, 5, new Item(Type.TROPHY, 0));
        shelf.setItem(1, 5, new Item(Type.TROPHY, 0));
        shelf.setItem(2, 5, new Item(Type.TROPHY, 0));
        shelf.setItem(3, 5, new Item(Type.TROPHY, 0));
        shelf.setItem(4, 5, new Item(Type.TROPHY, 0));

        shelf.setItem(0, 4, new Item(Type.BOOK, 0));
        shelf.setItem(1, 4, new Item(Type.TROPHY, 0));
        shelf.setItem(2, 4, new Item(Type.CAT, 0));
        shelf.setItem(3, 4, new Item(Type.CAT, 0));
        shelf.setItem(4, 4, new Item(Type.BOOK, 0));

        shelf.setItem(0, 3, new Item(Type.FRAME, 0));
        shelf.setItem(1, 3, new Item(Type.CAT, 0));
        shelf.setItem(2, 3, new Item(Type.BOOK, 0));
        shelf.setItem(3, 3, new Item(Type.TROPHY, 0));
        shelf.setItem(4, 3, new Item(Type.FRAME, 0));

        shelf.setItem(0, 2, new Item(Type.CAT, 0));
        shelf.setItem(1, 2, new Item(Type.CAT, 0));
        shelf.setItem(2, 2, new Item(Type.CAT, 0));
        shelf.setItem(3, 2, new Item(Type.TROPHY, 0));
        shelf.setItem(4, 2, new Item(Type.TROPHY, 0));

        shelf.setItem(1, 1, new Item(Type.TROPHY, 0));

        shelf.setItem(1, 0, new Item(Type.FRAME, 0));

        assertFalse(goalTwo.specificGoal(shelf));

        shelf.setItem(1, 3, new Item(Type.FRAME, 0));

        assertTrue(goalTwo.specificGoal(shelf));
    }

    @Test
    void specificGoalThree() {
        // Two columns each formed by 6 different types of tiles.

        CommonGoalAbstract goalThree = new AdjacentDifferentItemsGoal(4, 'v', 6, 2);
        Shelf shelf = new Shelf();

        shelf.setItem(0, 0, new Item(Type.TROPHY, 0));
        shelf.setItem(0, 1, new Item(Type.BOOK, 0));
        shelf.setItem(0, 2, new Item(Type.PLANTS, 0));
        shelf.setItem(0, 3, new Item(Type.FRAME, 0));
        shelf.setItem(0, 4, new Item(Type.GAME, 0));
        shelf.setItem(0, 5, new Item(Type.CAT, 0));

        shelf.setItem(1, 3, new Item(Type.CAT, 0));
        shelf.setItem(1, 4, new Item(Type.GAME, 0));
        shelf.setItem(1, 5, new Item(Type.FRAME, 0));

        shelf.setItem(2, 0, new Item(Type.CAT, 0));
        shelf.setItem(2, 1, new Item(Type.BOOK, 0));
        shelf.setItem(2, 2, new Item(Type.TROPHY, 0));
        shelf.setItem(2, 3, new Item(Type.FRAME, 0));
        shelf.setItem(2, 4, new Item(Type.PLANTS, 0));
        shelf.setItem(2, 5, new Item(Type.GAME, 0));

        shelf.setItem(3, 3, new Item(Type.CAT, 0));
        shelf.setItem(3, 4, new Item(Type.GAME, 0));
        shelf.setItem(3, 5, new Item(Type.FRAME, 0));

        shelf.setItem(4, 0, new Item(Type.CAT, 0));
        shelf.setItem(4, 1, new Item(Type.BOOK, 0));
        shelf.setItem(4, 2, new Item(Type.TROPHY, 0));
        shelf.setItem(4, 3, new Item(Type.FRAME, 0));
        shelf.setItem(4, 4, new Item(Type.BOOK, 0));
        shelf.setItem(4, 5, new Item(Type.GAME, 0));

        assertTrue(goalThree.specificGoal(shelf));

        shelf.setItem(0, 0, new Item(Type.FRAME, 0));

        assertFalse(goalThree.specificGoal(shelf));

    }

    @Test
    void specificGoalFour() {
        // Two lines each formed by 5 different types of tiles.One line can show the same or a different combination of
        // the other line.

        CommonGoalAbstract goalFour = new AdjacentDifferentItemsGoal(4, 'h', 5, 2);
        Shelf shelf = new Shelf();

        shelf.setItem(0, 5, new Item(Type.TROPHY, 0));
        shelf.setItem(1, 5, new Item(Type.CAT, 0));
        shelf.setItem(2, 5, new Item(Type.BOOK, 0));
        shelf.setItem(3, 5, new Item(Type.FRAME, 0));
        shelf.setItem(4, 5, new Item(Type.PLANTS, 0));

        shelf.setItem(0, 4, new Item(Type.TROPHY, 0));
        shelf.setItem(1, 4, new Item(Type.FRAME, 0));
        shelf.setItem(2, 4, new Item(Type.BOOK, 0));
        shelf.setItem(3, 4, new Item(Type.CAT, 0));
        shelf.setItem(4, 4, new Item(Type.PLANTS, 0));

        shelf.setItem(0, 3, new Item(Type.TROPHY, 0));
        shelf.setItem(1, 3, new Item(Type.TROPHY, 0));
        shelf.setItem(2, 3, new Item(Type.TROPHY, 0));
        shelf.setItem(3, 3, new Item(Type.CAT, 0));

        shelf.setItem(2, 2, new Item(Type.TROPHY, 0));
        shelf.setItem(3, 2, new Item(Type.CAT, 0));

        shelf.setItem(2, 1, new Item(Type.TROPHY, 0));

        assertTrue(goalFour.specificGoal(shelf));

        shelf.setItem(0, 5, new Item(Type.CAT, 0));

        assertFalse(goalFour.specificGoal(shelf));

    }
}
