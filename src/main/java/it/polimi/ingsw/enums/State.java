package it.polimi.ingsw.enums;

public enum State {
    /*CLIENT to SERVER*/
    SET_NICKNAME,
    SET_NUMPLAYERS,
    GAME_READY,
    SELECT_ITEM,
    CONFIRM_ITEMS,
    ORDER_ITEM,
    SELECT_COLUMN,
    CONFIRM_INSERTION,


    /*SERVER to CLIENT*/
    ACK_NICKNAME,
    ASK_NUMPLAYERS,
    SAME_NICKNAME,
    NACK_NICKNAME,
    ACK_NUMPLAYERS,
    NACK_NUMPLAYERS,
    OUT_BOUND_NUMPLAYERS,
    SEND_MODEL,
    SELECTED,
    ORDER_n_COLUMN,
    ACK_ORDER_n_COLUMN,
    NACK_COLUMN,
    NACK_ORDER,
    INSERTION_DONE,
    RESULTS
}
