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
    public boolean checkCommonGoal() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public boolean isShelfFull() {
        return this.currentPlayer.getShelf().isFull();
    }
}
