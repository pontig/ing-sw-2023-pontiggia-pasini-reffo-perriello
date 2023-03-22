package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;

import java.util.HashSet;
import java.util.Set;

public class PlayerTest {
    private final Player player;
    private Set<PG<Integer, Integer, Type>> personalGoalSet;
    public PlayerTest(){
        player = new Player("test");
        mockPlayer();
    }
    private void mockPlayer(){
        player.setFirstCommonScore(4);
        player.setSecondCommonScore(2);
        player.setEndGameToken(1);
        this.personalGoalSet = new HashSet<>(
                Set.of(
                        new PG<>(1, 1, Type.CAT),
                        new PG<>(4, 2, Type.BOOK),
                        new PG<>(3, 3, Type.GAME),
                        new PG<>(2, 4, Type.FRAME),
                        new PG<>(5, 5, Type.PLANTS),
                        new PG<>(0, 3, Type.TROPHY)
                )
        );
        PersonalGoal personalGoal = new PersonalGoal(personalGoalSet);
        player.setPersonalGoal(personalGoal);
    }
    public void testGetNickname(){
        assert player.getNickname().equals("test");
    }
    public void testGetPersonalGoal(){
        assert player.getPersonalGoal().equals(new PersonalGoal(personalGoalSet));
    }
    public void testGetShelf(){
        assert player.getShelf().equals(new Shelf());
    }
    public void testGetFirstCommonScore(){
        assert player.getFirstCommonScore() == 4;
    }
    public void testGetSecondCommonScore(){
        assert player.getSecondCommonScore() == 2;
    }
    public void testGetEndGameToken(){
        assert player.getEndGameToken() == 1;
    }
    public void testSetPersonalGoal(){
        PersonalGoal personalGoal = new PersonalGoal(personalGoalSet);
        player.setPersonalGoal(personalGoal);
        assert player.getPersonalGoal().equals(personalGoal);
    }
    public void testSetFirstCommonScore(){
        player.setFirstCommonScore(5);
        assert player.getFirstCommonScore() == 5;
    }
    public void testSetSecondCommonScore(){
        player.setSecondCommonScore(3);
        assert player.getSecondCommonScore() == 3;
    }
    public void testSetEndGameToken(){
        player.setEndGameToken(2);
        assert player.getEndGameToken() == 2;
    }
}
