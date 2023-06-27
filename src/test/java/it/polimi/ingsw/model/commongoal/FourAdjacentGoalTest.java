package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class FourAdjacentGoalTest {

    @Test
    void specificGoalOne() {
        // Four groups each containing at least 4 tiles of the same type. The tiles of one group can be different
        // from those of another group.

        // In the shelf there are only the tiles subjected to the goal.

        CommonGoalAbstract goal = new FourAdjacentGoal(4);
        Shelf shelfOne = new Shelf();

        shelfOne.setItem(0,4, new Item(Type.TROPHY, 0));
        shelfOne.setItem(1,1, new Item(Type.TROPHY, 0));
        shelfOne.setItem(1,2, new Item(Type.TROPHY, 0));
        shelfOne.setItem(1,3, new Item(Type.TROPHY, 0));
        shelfOne.setItem(1,4, new Item(Type.TROPHY, 0));
        shelfOne.setItem(2,1, new Item(Type.TROPHY, 0));
        shelfOne.setItem(2,2, new Item(Type.TROPHY, 0));
        shelfOne.setItem(2,4, new Item(Type.TROPHY, 0));
        shelfOne.setItem(3,2, new Item(Type.TROPHY, 0));
        shelfOne.setItem(4,2, new Item(Type.TROPHY, 0));

        assertFalse(goal.specificGoal(shelfOne));

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

        Shelf shelfThree = new Shelf();

        shelfThree.setItem(0,0, new Item(Type.BOOK, 0));
        shelfThree.setItem(0,1, new Item(Type.BOOK, 0));
        shelfThree.setItem(0,2, new Item(Type.BOOK, 0));
        shelfThree.setItem(1,0, new Item(Type.FRAME, 0));

        assertFalse(goal.specificGoal(shelfThree));

        Shelf shelfFour = new Shelf();
        shelfFour.setItem(0,3, new Item(Type.GAME, 0));
        shelfFour.setItem(0,4, new Item(Type.GAME, 0));
        shelfFour.setItem(0,5, new Item(Type.GAME, 0));
        shelfFour.setItem(1,2, new Item(Type.FRAME, 0));
        shelfFour.setItem(1,3, new Item(Type.FRAME, 0));
        shelfFour.setItem(1,4, new Item(Type.FRAME, 0));
        shelfFour.setItem(1,5, new Item(Type.FRAME, 0));
        shelfFour.setItem(2,4, new Item(Type.BOOK, 0));
        shelfFour.setItem(2,5, new Item(Type.BOOK, 0));
        shelfFour.setItem(3,2, new Item(Type.CAT, 0));
        shelfFour.setItem(3,3, new Item(Type.CAT, 0));
        shelfFour.setItem(3,4, new Item(Type.CAT, 0));
        shelfFour.setItem(3,5, new Item(Type.CAT, 0));
        shelfFour.setItem(4,4, new Item(Type.PLANTS, 0));
        shelfFour.setItem(4,5, new Item(Type.TROPHY, 0));

        assertFalse(goal.specificGoal(shelfFour));

    }
}