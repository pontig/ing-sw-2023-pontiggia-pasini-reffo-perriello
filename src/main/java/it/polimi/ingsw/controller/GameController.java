package it.polimi.ingsw.controller;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendChatMessage;

import java.util.List;

public class GameController {                                                                   //extends Observer
    private Game game;

    /**
     * Constructor of GameController
     *
     * @param model  game model
     * @param client client
     */
    public GameController(Game model, Client client) {
        this.game = model;
    }

    /**
     * Getter of the game
     *
     * @return game
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Setter of the game
     *
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Insert a new player in the game
     *
     * @param nickname nickname of the player
     * @see Game#insertPlayer(String)
     */
    public void setPlayer(String nickname) {
        getGame().insertPlayer(nickname);
    }

    /**
     * Set the number of players in the game
     *
     * @param nickname   nickname of the player
     * @param numPlayers number of players
     * @see Game#setNumberOfPlayers(String, int)
     */
    public void setNumPlayers(String nickname, int numPlayers) {
        getGame().setNumberOfPlayers(nickname, numPlayers);
    }

    /**
     * Starts the game
     *
     * @see Game#startGame()
     */
    public void gameReadyToStart() {
        getGame().startGame();
    }

    /**
     * Event handler for the selection of one item on the board
     *
     * @param x x coordinate of the item
     * @param y y coordinate of the item
     * @see Game#itemClick(int, int)
     */
    public void onItemClick(int x, int y) {
        getGame().itemClick(x, y);
    }

    /**
     * Event handler for the confirmation of the selected items
     *
     * @see Game#confirmItems()
     */
    public void onConfirmItems() {
        getGame().confirmItems();
    }

    /**
     * Event handler for the ordering of the selected items
     *
     * @param position position of the item
     * @param action   action to perform
     * @see Game#orderSelectedItem(int, int)
     */
    public void onOrderItem(int position, int action) {             //=> UML inserire action nel controller
        getGame().orderSelectedItem(position, action);
    }

    /**
     * Event handler for the selection of a column
     *
     * @param column column to select
     * @see Game#selectColumn(int)
     */
    public void onColumnSelection(int column) {
        getGame().selectColumn(column);
    }

    /**
     * Event handler for the confirmation of the insertion of the items in the shelf
     *
     * @see Game#confirmInsertion()
     * @see GameController#endTurnCheck()
     */
    public void onConfirmInsertion() {
        getGame().confirmInsertion();
        endTurnCheck();
    }

    /**
     * Checks if the game has to end and if not, passes the turn to the next player
     */
    private void endTurnCheck() {
        if (getGame().endTurnCheck())
            endGame();
        else
            nextPlayer();
    }

    /**
     * Passes the turn to the next player
     *
     * @see Game#nextPlayer()
     */
    private void nextPlayer() {
        getGame().nextPlayer();
    }

    /**
     * Ends the game
     */
    private void endGame() {
        getGame().endGame();
    }

    /**
     * Updates the observer
     *
     * @param o   the client that has to be updated
     * @param arg the message to send
     */
    public void update(Client o, Message arg) {
        play(arg);
    }

    /**
     * Event handler for the chat message
     *
     * @param from sender of the message
     * @param to   receiver of the message
     * @param text text of the message
     * @see Game#sendChat(String, String, String)
     */
    private void onChatMessage(String from, String to, String text) {
        getGame().sendChat(from, to, text);
    }

    /**
     * Handles the message received from the client
     *
     * @param arg the message received
     */
    private void play(Message arg) {
        State msg = arg.getInfo();
        switch (msg) {
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
            case CHAT_MESSAGE:
                arg.printMsg();
                onChatMessage(((SendChatMessage) arg).getFrom(), ((SendChatMessage) arg).getTo(), ((SendChatMessage) arg).getText());
                break;
            case CLIENT_DOWN:
                arg.printMsg();
                System.out.println("Client disconnected: " + arg.getNickname());
                break;
            case PING:
                arg.printMsg();
                break;

            default:
                System.err.println("Ignoring event from " + msg + ": " + arg);
                break;
        }
    }
}
