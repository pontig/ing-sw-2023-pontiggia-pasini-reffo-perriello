package it.polimi.ingsw.enums;

/**
 * Purposes of the messages exchanged between client and server and vice versa
 */
public enum State {
    /* =================================================================================================================
                                           ---CLIENT TO SERVER---
    ================================================================================================================= */
    /**
     * The user has entered a username
     */
    SET_NICKNAME,
    /**
     *  The user has chosen a number of players for the game
     */
    SET_NUMPLAYERS,
    /**
     * Make the server check if the game is ready to start
     */
    GAME_READY,
    /**
     * The user has chosen an item on the board
     */
    SELECT_ITEM,
    /**
     *  The user has confirmed the items selected
     */
    CONFIRM_ITEMS,
    /**
     *  The user has modified the order of the items to put in the shelf
     */
    ORDER_ITEM,
    /**
     *  The user has chosen a column
     */
    SELECT_COLUMN,
    /**
     * The user has confirmed the insertion of the ordered items in a column
     */
    CONFIRM_INSERTION,

    /* =================================================================================================================
                                           ---SERVER TO CLIENT---
    ================================================================================================================= */
    /**
     * The username chosen is confirmed for a specific client
     */
    ACK_NICKNAME,
    /**
     * The server wants to know the number of players for the next game
     */
    ASK_NUMPLAYERS,
    /**
     * A client has inserted a username already taken by another client
     */
    SAME_NICKNAME,
    /**
     * A client tried to connect while another game was going
     */
    NACK_NICKNAME,
    /**
     * The server accepted the number of players for the game
     */
    ACK_NUMPLAYERS,
    /**
     * The user has chosen a number of players but another player selected it earlier
     */
    NACK_NUMPLAYERS,
    /**
     * The user has inserted an out of bound number of players
     */
    OUT_BOUND_NUMPLAYERS,
    /**
     * Sends to the view the whole model
     */
    SEND_MODEL,
    /**
     * The item chosen is selectable and it is selected
     */
    SELECTED,
    /**
     * Lets the user chose a column and order the items to insert
     */
    ORDER_n_COLUMN,
    /**
     * The column chosen to insert into is eligible and it will be shown
     */
    ACK_ORDER_n_COLUMN,
    /**
     * The number of the column inserted isn't valid
     */
    NACK_COLUMN,
    /**
     * The player has inserted an invalid ordering sequence
     */
    NACK_ORDER,
    /**
     *  The turn has ended correctly
     */
    INSERTION_DONE,
    /**
     * One player has obtained the first common goal
     */
    FIRSTCOMMONGOAL_TAKEN,
    /**
     * One player has obtained the second common goal
     */
    SECONDCOMMONGOAL_TAKEN,
    /**
     * One player has completed the shelf before the others
     */
    TOKEN_END_GAME,
    /**
     * Sends the result of the game
     */
    RESULTS,
    /**
     *  Shelves of the other players
     */
    SEND_OTHER_SHELF,
    /**
     * A player has been reconnected from a previous game crashed
     */
    RECONNECTED,
    /**
     * A player wants to join but the server is waiting for the players in the crashed game
     */
    NOT_IN_PREV_GAME,
    /**
     * Another player obtained a token
     */
    TOKENS_TAKEN,
    /**
     * The player list for the select box in the GUI chat
     */
    PLAYER_LIST,
    /**
     * New message in the chat
     */
    CHAT_MESSAGE,
    /**
     * Send all the data about each player during the game
     */
    IN_GAME,
    /**
     * Notification that the client is disconnected
     */
    CLIENT_DOWN,
    /**
     *  Ping used to see if the server or the client is working in RMI
     */
    PING,
    /**
     * Close the connection if more client are in the game then the actual expected number
     */
    TOO_MANY,
}