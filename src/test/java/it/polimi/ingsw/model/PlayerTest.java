package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import it.polimi.ingsw.tuples.Triplet;

import java.util.HashSet;
import java.util.Set;

public class PlayerTest {
    private final Player player;
    private final Set<Triplet<Integer, Integer, Type>> personalGoalSet;

    public PlayerTest() {
        this.personalGoalSet = new HashSet<>(
                Set.of(
                        new Triplet<>(4, 1, Type.CAT),
                        new Triplet<>(1, 3, Type.BOOK),
                        new Triplet<>(1, 3, Type.GAME),
                        new Triplet<>(2, 0, Type.FRAME),
                        new Triplet<>(0, 0, Type.PLANTS),
                        new Triplet<>(2, 5, Type.TROPHY)
                )
        );
        PersonalGoal personalGoal = new PersonalGoal(personalGoalSet);
        player = new Player("test", personalGoal);
        mockPlayer();
    }

    private void mockPlayer() {
        player.setFirstCommonScore(4);
        player.setSecondCommonScore(2);
        player.setEndGameToken(1);
        Shelf s = player.getShelf();
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

    }

    public void testGetNickname() {
        assert player.getNickname().equals("test");
    }

    public void testGetPersonalGoal() {
        assert player.getPersonalGoal().equals(new PersonalGoal(personalGoalSet));
    }

    public void testGetShelf() {
        assert player.getShelf().equals(new Shelf());
    }

    public void testGetFirstCommonScore() {
        assert player.getFirstCommonScore() == 4;
    }

    public void testGetSecondCommonScore() {
        assert player.getSecondCommonScore() == 2;
    }

    public void testGetEndGameToken() {
        assert player.getEndGameToken() == 1;
    }

    public void testSetPersonalGoal() {
        assert player.getPersonalGoal().equals(new PersonalGoal(personalGoalSet));
    }

    public void testSetFirstCommonScore() {
        player.setFirstCommonScore(4);
        assert player.getFirstCommonScore() == 4;
    }

    public void testSetSecondCommonScore() {
        player.setSecondCommonScore(8);
        assert player.getSecondCommonScore() == 8;
    }

    public void testSetEndGameToken() {
        player.setEndGameToken(0);
        assert player.getEndGameToken() == 0;
    }

    public void testComputeFinalScore() {
        assert player.computeFinalScore() == 36;
    }

}
