package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

public class Player {
    private String nickname;
    private PersonalGoal personalGoal;
    private int personalScore;
    private int firstCommonScore;
    private int secondCommonScore;
    private int adjacencyScore;
    private Collection<Item> chosenItems;
    private Shelf shelf;

    public Player() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    public String getNickname() {
        return this.nickname;
    }
    private int computePrivateScore() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public int computeFinalScore() {
        return this.firstCommonScore +
                this.secondCommonScore +
                this.adjacencyScore +
                this.computePrivateScore(); // let's keep this unhandled exception for now
    }
    public void checkCommonGoal() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public int checkFullShelfOnFreeSpace() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public Shelf getShelf() {
        return this.shelf;
    }
}
