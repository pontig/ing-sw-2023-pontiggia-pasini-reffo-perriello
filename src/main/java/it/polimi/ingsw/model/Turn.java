package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.StateTurn;
import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

public class Turn {
    private Player currentPlayer;
    private StateTurn currentState;
    private Collection<Item> pendingItems;

    public Turn() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        return;
    }
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    public void setCurrentState(StateTurn currentState) {
        this.currentState = currentState;
        return;
    }
    public StateTurn getCurrentState() {
        return this.currentState;
    }
    public void selectItemsFromBoard() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public int checkCommonGoal() throws ExecutionControl.NotImplementedException {
        // returns:
        // 0 if no common goal is reached
        // 1 if common goal 1 is reached
        // 2 if common goal 2 is reached
        // 3 if both common goals are reached
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public boolean checkFullShelf() {
        return this.currentPlayer.getShelf().isFull();
    }
    public boolean checkEmptyBoard(Board b) {
        return b.isEmpty();
    }
    public Item selectFromBoard(int x, int y) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void insertItemsInShelf(Collection<Item> items, int col) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }

}
