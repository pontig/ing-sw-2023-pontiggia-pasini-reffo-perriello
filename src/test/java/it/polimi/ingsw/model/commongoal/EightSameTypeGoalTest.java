package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EightSameTypeGoalTest {
    CommonGoalAbstract twoPlayers = new EightSameTypeGoal(2);
    CommonGoalAbstract threePlayers = new EightSameTypeGoal(3);
    CommonGoalAbstract fourPlayers = new EightSameTypeGoal(4);

    Shelf PlayerShelf = new Shelf();
    @Test
    void specificGoal() {

        //empty
        assertFalse(twoPlayers.specificGoal(PlayerShelf));
        assertFalse( threePlayers.specificGoal(PlayerShelf));
        assertFalse(fourPlayers.specificGoal(PlayerShelf));




        //case true
        Shelf PlayerShelf = new Shelf();
        PlayerShelf.setItem(0, 0, new Item(Type.BOOK,0));
        PlayerShelf.setItem(0, 1, new Item(Type.BOOK,1));
        PlayerShelf.setItem(0, 2, new Item(Type.BOOK,2));
        PlayerShelf.setItem(0, 3, new Item(Type.BOOK,0));
        PlayerShelf.setItem(0, 4, new Item(Type.BOOK,1));
        PlayerShelf.setItem(1, 4, new Item(Type.BOOK,1));
        PlayerShelf.setItem(2, 4, new Item(Type.BOOK,1));
        PlayerShelf.setItem(3, 4, new Item(Type.BOOK,1));

       assertTrue(twoPlayers.specificGoal(PlayerShelf));
       assertTrue( threePlayers.specificGoal(PlayerShelf));
       assertTrue(fourPlayers.specificGoal(PlayerShelf));


       //case false
        Shelf PlayerShelf2 = new Shelf();
        PlayerShelf2.setItem(0, 0, new Item(Type.BOOK,0));
        PlayerShelf2.setItem(0, 3, new Item(Type.BOOK,1));
        PlayerShelf2.setItem(0, 2, new Item(Type.CAT,2));
        PlayerShelf2.setItem(1, 3, new Item(Type.BOOK,0));
        PlayerShelf2.setItem(0, 4, new Item(Type.BOOK,1));
        PlayerShelf2.setItem(1, 4, new Item(Type.BOOK,1));
        PlayerShelf2.setItem(2, 4, new Item(Type.FRAME,1));
        PlayerShelf2.setItem(2, 1, new Item(Type.BOOK,1));
        PlayerShelf2.setItem(4, 4, new Item(Type.BOOK,1));

        assertFalse(twoPlayers.specificGoal(PlayerShelf2));
        assertFalse( threePlayers.specificGoal(PlayerShelf2));
        assertFalse(fourPlayers.specificGoal(PlayerShelf2));

    }

}