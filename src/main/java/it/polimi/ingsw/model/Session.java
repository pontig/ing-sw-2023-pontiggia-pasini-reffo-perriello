package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

public class Session {
    private Collection<Match> activeMatches;
    private Collection<Player> pendingPlayers;
    public Session() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    public void newMatch() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void PutPlayerInPending(Player p) {
        this.pendingPlayers.add(p);
        return;
    }
    public Match getMatchOfPlayer(String nickname) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
}
