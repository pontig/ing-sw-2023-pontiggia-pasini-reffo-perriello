package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.StateTurn;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private List<Player> playerList = new ArrayList<Player>();
    private Player currentPlayer;
    private StateTurn playerState;
    private boolean endGame;
    private Board board;
    private CommonGoal fistCommonGoal;
    private CommonGoal secondCommonGoal;

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

    public void setPlayerList(List<Player> playerList){
        this.addAll(playerList);
    }
    public List<Player> getPlayers(){
        return playerList;
    }

    public void setCurrentPlayer(Player currentPlayer){

    }
    public List<Player> getPlayers(){
        return playerList;
    }




}
