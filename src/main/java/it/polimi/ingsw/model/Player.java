package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

public class Player {
    private String nickname;
    private PersonalGoal personalGoal;
    private int firstCommonScore;
    private int secondCommonScore;
    private Collection<Item> chosenItems;
    private Shelf shelf;

    public Player() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    public String getNickname() {
        return this.nickname;
    }
    private int computePersonalScore() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    private int computeAdjacencyScore() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public int computeFinalScore() {
        return this.firstCommonScore +
                this.secondCommonScore +
                this.computeAdjacencyScore() + // let's keep this unhandled exception for now
                this.computePersonalScore(); // let's keep this unhandled exception for now
    }
    public boolean checkCommonGoal() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void orderedInsertion(Collection<Item> items, int column) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public boolean checkFullShelf() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public Shelf getShelf() {
        return this.shelf;
    }
}
