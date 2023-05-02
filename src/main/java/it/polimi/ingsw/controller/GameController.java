package it.polimi.ingsw.controller;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

public class GameController {                                                                   //extends Observer
    private Game game;
    private StateGame currentStateGame;
//Costruttore
    public GameController(Game model, Client client) {
        this.game = model;
    }
//getter
    public Game getGame(){ return this.game; }
    public StateGame getCurrentStateGame() { return this.currentStateGame; }
//setter
    public void setGame(Game game) { this.game = game; }
    public void setCurrentStateGame(StateGame currentStateGame) { this.currentStateGame = currentStateGame; }
//metodi, INGAME state in base al messaggio chiamerà una delle funzioni
    public void setPlayer(String nickname) {getGame().insertPlayer(nickname);}
    public void setNumPlayers(String nickname, int numPlayers){getGame().setNumberOfPlayers(nickname, numPlayers);}
    public void gameReadyToStart(){ getGame().startGame();}
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

    public void update(Client o, Message arg) {
        play(arg);
    }

    private void play(Message arg) {
        State msg = arg.getInfo();
        switch(msg){
            case SET_NICKNAME:
                arg.printMsg();
                setPlayer(arg.getNickname());
                break;
            case SET_NUMPLAYERS:
                arg.printMsg();
                setNumPlayers(arg.getNickname(), arg.getNumRowAction());
                break;
            case GAME_READY:
                arg.printMsg();
                gameReadyToStart();
                break;
            case SELECT_ITEM:
                arg.printMsg();
                onItemClick(arg.getColumnPos(), arg.getNumRowAction());
                break;
            case CONFIRM_ITEMS:
                arg.printMsg();
                onConfirmItems();
                break;
            case SELECT_COLUMN:
                arg.printMsg();
                onColumnSelection(arg.getColumnPos());
                break;
            case ORDER_ITEM:
                arg.printMsg();
                onOrderItem(arg.getColumnPos(), arg.getNumRowAction());
                break;
            case CONFIRM_INSERTION:
                arg.printMsg();
                onConfirmInsertion();
                break;
            default:
                System.err.println("Ignoring event from " + msg + ": " + arg);
                break;
        }
    }
}
