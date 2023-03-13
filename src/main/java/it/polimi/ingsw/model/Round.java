package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

public class Round {
    private Collection<Player> playersLeft;
    private Turn currentTurn;
    private Boolean endGame;

    public Round() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    public Turn getCurrentTurn() {
        return this.currentTurn;
    }
    public void setEndGame(Boolean endGame) {
        this.endGame = endGame;
    }
    public boolean checkEmptyBoard(Board b) {
        return b.isEmpty();
    }
    public boolean checkNoPlayersLeft() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public boolean isGameEnded() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void assignEndGamePoint() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void nextTurn() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
}
