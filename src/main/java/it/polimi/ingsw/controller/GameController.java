package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import jdk.jshell.spi.ExecutionControl;

public class GameController {                                                                   //extends Observer
    private Game game;
    private StateGame currentStateGame;
//Costruttore
    public GameController() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
//getter
    public Game getGame(){ return this.game; }
    public StateGame getCurrentStateGame() { return this.currentStateGame; }
//setter
    public void setGame(Game game) { this.game = game; }
    public void setCurrentStateGame(StateGame currentStateGame) { this.currentStateGame = currentStateGame; }
//metodi, INGAME state in base al messaggio chiamerà una delle funzioni
    public void onItemClick(int x, int y) {
        getGame().itemClick(x, y);
    }
    public void onConfirmItems() {
        getGame().confirmItems();
    }
    public void onOrderItem(int position, int action) {             //=> UML inserire action nel controller
        getGame().orderSelectedItem(position, action);
    }
    public void onColumnSelection(int column) {
        getGame().selectColumn(column);
    }
    public void onConfirmInsertion() {
        getGame().confirmInsertion();
        endTurnCheck();
    }
    private void endTurnCheck() {
        if(getGame().endTurnCheck())
            endGame();
        else
            nextPlayer();
    }
    private void nextPlayer() {
        getGame().nextPlayer();
    }
    private void endGame() {
        getGame().endGame();
    }
}
