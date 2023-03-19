package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;
import it.polimi.ingsw.model.enums.StateTurn;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private List<Player> playerList = new ArrayList<Player>();
    private Player currentPlayer;
    private StateTurn playerState;
    private boolean endGame;
    private Board board;
    private CommonGoalAbstract firstCommonGoal;
    private CommonGoalAbstract secondCommonGoal;

    public Match(){
        //imposta i player in gioco
        //definisce il player corrente
        //definisce lo stato del turno del player
        //imposto endGame a 0
        //fill della board in base al numero di giocatori
        //selezione dei personal goal e li assegno ai player
        //assegno ad ogni player una shelf vuota
        //selezione dei common goal e settaggio dei punti contenuti
    }

    public List<Player> getPlayers(){
        return playerList;
    }
    public Player getCurrentPlayer() { return currentPlayer; }
    public StateTurn getPlayerState() { return playerState;}
    public boolean getEndGame() { return endGame; }
    public Board getBoard() { return board;}
    public CommonGoalAbstract getFirstCommonGoal() { return firstCommonGoal; }
    public CommonGoalAbstract getSecondCommonGoal() { return secondCommonGoal; }
    public void setPlayerList(List<Player> playerList){
        this.playerList.addAll(playerList);
    }
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }
    public void setPlayerState(StateTurn playerState) {
        this.playerState = playerState;
    }
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
    public void setBoard() {
        board.fill(getPlayers().size());
    }
    // Per i common goal forse meglio fare una lista di due elementi CommonGoal
    public void setFirstCommonGoal(CommonGoalAbstract firstCommonGoal) {
        //Selezione randomica id uno dei common goal;
    }
    public void getSecondCommonGoal(CommonGoalAbstract secondCommonGoal) {
        // Selezione randomica di uno dei common goal diverso dal primo
    }



}
