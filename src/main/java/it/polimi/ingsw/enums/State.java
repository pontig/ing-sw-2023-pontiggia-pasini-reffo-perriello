package it.polimi.ingsw.enums;

/**
 * Purposes of the messages exchanged between client and server and vice versa
 */
public enum State {
    /* =================================================================================================================
                                           ---CLIENT TO SERVER---
    ================================================================================================================= */
    SET_NICKNAME,           // The user has entered a username
    SET_NUMPLAYERS,         // The user has chosen a number of players for the game
    GAME_READY,             // Make the server check if the game is ready to start
    SELECT_ITEM,            // The user has chosen an item on the board
    CONFIRM_ITEMS,          // The user has confirmed the items selected
    ORDER_ITEM,             // The user has modified the order of the items to put in the shelf
    SELECT_COLUMN,          // The user has chosen a column
    CONFIRM_INSERTION,      // The user has confirmed the insertion of the ordered items in a column

    /* =================================================================================================================
                                           ---SERVER TO CLIENT---
    ================================================================================================================= */
    ACK_NICKNAME,           // The username chosen is confirmed for a specific client
    ASK_NUMPLAYERS,         // The server wants to know the number of players for the next game
    SAME_NICKNAME,          // A client has inserted a username already taken by another client
    NACK_NICKNAME,          // A client tried to connect while another game was going
    ACK_NUMPLAYERS,         // The server accepted the number of players for the game
    NACK_NUMPLAYERS,        // The user has chosen a number of players but another player selected it earlier
    OUT_BOUND_NUMPLAYERS,   // The user has inserted an out of bound number of players
    SEND_MODEL,             // Sends to the view the whole model
    SELECTED,               // The item chosen is selectable and it is selected
    ORDER_n_COLUMN,         // Lets the user chose a column and order the items to insert
    ACK_ORDER_n_COLUMN,     // The column chosen to insert into is eligible and it will be shown
    NACK_COLUMN,            // The number of the column inserted isn't valid
    NACK_ORDER,             // The player has inserted an invalid ordering sequence
    INSERTION_DONE,         // The turn has ended correctly
    FIRSTCOMMONGOAL_TAKEN,  // One player has obtained the first common goal
    SECONDCOMMONGOAL_TAKEN, // One player has obtained the second common goal
    TOKEN_END_GAME,         // One player has completed the shelf before the others
    RESULTS,                // Sends the result of the game
    SEND_OTHER_SHELF,       // Shelves of the other players
    RECONNECTED,            // A player has been reconnected from a previous game crashed
    NOT_IN_PREV_GAME,       // A player wants to join but the server is waiting for the players in the crashed game
    TOKENS_TAKEN,           // Another player obtained a token
    PLAYER_LIST,            // The player list for the select box in the GUI chat
    CHAT_MESSAGE,           // New message in the chat
    IN_GAME, // TODO: don t know
}
