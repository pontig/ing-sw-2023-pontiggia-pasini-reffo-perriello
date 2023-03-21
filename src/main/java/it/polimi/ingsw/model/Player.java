package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

public class Player {
    private final String nickname;
    private PersonalGoal personalGoal;
    private Shelf shelf;
    private int firstCommonScore;
    private int secondCommonScore;
    private int endGameToken;

    public Player(String nickname) {
        this.nickname = nickname;
        this.personalGoal = null;
        this.shelf = new Shelf();
        this.firstCommonScore = 0;
        this.secondCommonScore = 0;
        this.endGameToken = 0;
    }
    public String getNickname() {
        return this.nickname;
    }
    public PersonalGoal getPersonalGoal() {
        return this.personalGoal;
    }
    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
    }
    public Shelf getShelf() {
        return this.shelf;
    }
    public int getFirstCommonScore() {
        return this.firstCommonScore;
    }
    public void setFirstCommonScore(int firstCommonScore) {
        this.firstCommonScore = firstCommonScore;
    }
    public int getSecondCommonScore() {
        return this.secondCommonScore;
    }
    public void setSecondCommonScore(int secondCommonScore) {
        this.secondCommonScore = secondCommonScore;
    }
    public int getEndGameToken() {
        return this.endGameToken;
    }
    public void setEndGameToken(int endGameToken) {
        this.endGameToken = endGameToken;
    }

}
