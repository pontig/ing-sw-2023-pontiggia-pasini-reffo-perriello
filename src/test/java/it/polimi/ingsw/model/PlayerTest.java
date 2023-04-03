package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import it.polimi.ingsw.tuples.Triplet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private PersonalGoal personalGoal;

    @BeforeEach
    void setUp() {
        // We use the same example of the rulebook

        Set personalGoalSet = new HashSet<>(
                Set.of(
                        new Triplet<>(4, 1, Type.CAT),
                        new Triplet<>(1, 3, Type.GAME),
                        new Triplet<>(3, 2, Type.BOOK),
                        new Triplet<>(2, 0, Type.FRAME),
                        new Triplet<>(0, 0, Type.PLANTS),
                        new Triplet<>(2, 5, Type.TROPHY)
                ));
        this.personalGoal = new PersonalGoal(personalGoalSet);

        this.player = new Player("testPlayer", personalGoal);

        Shelf s = this.player.getShelf();
        s.setItem(0, 0, new Item(Type.PLANTS, 0));
        s.setItem(1, 0, new Item(Type.PLANTS, 0));
        s.setItem(2, 0, new Item(Type.PLANTS, 0));
        s.setItem(0, 1, new Item(Type.BOOK, 0));
        s.setItem(1, 1, new Item(Type.PLANTS, 0));
        s.setItem(2, 1, new Item(Type.PLANTS, 0));
        s.setItem(3, 1, new Item(Type.CAT, 0));
        s.setItem(0, 2, new Item(Type.FRAME, 0));
        s.setItem(1, 2, new Item(Type.BOOK, 0));
        s.setItem(2, 2, new Item(Type.FRAME, 0));
        s.setItem(3, 2, new Item(Type.BOOK, 0));
        s.setItem(0, 3, new Item(Type.TROPHY, 0));
        s.setItem(1, 3, new Item(Type.GAME, 0));
        s.setItem(2, 3, new Item(Type.TROPHY, 0));
        s.setItem(3, 3, new Item(Type.GAME, 0));
        s.setItem(0, 4, new Item(Type.TROPHY, 0));
        s.setItem(1, 4, new Item(Type.TROPHY, 0));
        s.setItem(2, 4, new Item(Type.CAT, 0));
        s.setItem(3, 4, new Item(Type.CAT, 0));
        s.setItem(4, 4, new Item(Type.CAT, 0));
        s.setItem(0, 5, new Item(Type.TROPHY, 0));
        s.setItem(1, 5, new Item(Type.TROPHY, 0));
        s.setItem(2, 5, new Item(Type.TROPHY, 0));
        s.setItem(3, 5, new Item(Type.CAT, 0));
        s.setItem(4, 5, new Item(Type.CAT, 0));

        this.player.setFirstCommonScore(4);
        this.player.setSecondCommonScore(8);
        this.player.setEndGameToken(1);

    }

    @Test
    void getNickname() {
        assertEquals("testPlayer", this.player.getNickname());
    }

    @Test
    void getPersonalGoal() {
        assertEquals(this.personalGoal, this.player.getPersonalGoal());
    }

    @Test
    void getShelf() {
    }

    @Test
    void computeFinalScore() {
        assertEquals(37, this.player.computeFinalScore());
    }

    @Test
    void getFirstCommonScore() {
        assertEquals(4, this.player.getFirstCommonScore());
    }

    @Test
    void setFirstCommonScore() {
        this.player.setFirstCommonScore(5);
        assertEquals(5, this.player.getFirstCommonScore());
    }

    @Test
    void getSecondCommonScore() {
        assertEquals(8, this.player.getSecondCommonScore());
    }

    @Test
    void setSecondCommonScore() {
        this.player.setSecondCommonScore(3);
        assertEquals(3, this.player.getSecondCommonScore());
    }

    @Test
    void getEndGameToken() {
        assertEquals(1, this.player.getEndGameToken());
    }

    @Test
    void setEndGameToken() {
        this.player.setEndGameToken(2);
        assertEquals(2, this.player.getEndGameToken());
    }
}