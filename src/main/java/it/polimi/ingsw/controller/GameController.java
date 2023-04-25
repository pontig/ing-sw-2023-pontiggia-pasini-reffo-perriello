package it.polimi.ingsw.controller;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

public class GameController {                                                                   //extends Observer
    private Game game;
    private Client currentClient; // forse da fare un lista di client su cui iterare e un current client

    private StateGame currentStateGame;
//Costruttore
    public GameController(Game model, Client client) {
        this.game = model;
        this.currentClient = client;
    }
//getter
    public Game getGame(){ return this.game; }
    public StateGame getCurrentStateGame() { return this.currentStateGame; }
//setter
    public void setGame(Game game) { this.game = game; }
    public void setCurrentStateGame(StateGame currentStateGame) { this.currentStateGame = currentStateGame; }
//metodi, INGAME state in base al messaggio chiamerà una delle funzioni
    public void setPlayer(String nickname) {getGame().insertPlayer(nickname);}
    public void setNumPlayers(int numPlayers){getGame().setNumberOfPlayers(numPlayers);}
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
        if (!o.equals(currentClient)) {
            System.err.println("Discarding notification from " + o);
            return;
        }
        play(arg);
    }

    private void play(Message arg) {
        State msg = arg.getInfo();
        switch(msg){
            case SET_NAME:
                arg.printMsg();
                setPlayer(arg.getNickname());
                break;
            case SET_NUMPLAYERS:
                arg.printMsg();
                setNumPlayers(arg.getNumPlayers());
                break;
            default:
                System.err.println("Ignoring event from " + msg + ": " + arg);
                break;
        }
    }
}
