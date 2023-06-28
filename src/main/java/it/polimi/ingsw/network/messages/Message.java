package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

import java.io.Serializable;

/**
 * The Message interface represents a serializable message used for communication between clients and the server
 */
public interface Message extends Serializable {
    /**
     * Gets the state information from the message
     *
     * @return the state information
     */
    State getInfo();

    /**
     * Gets the nickname from the message
     *
     * @return the nickname
     */
    String getNickname();

    /**
     * Gets the number of row action from the message
     *
     * @return the number of row action
     */
    int getNumRowAction();

    /**
     * Gets the column position from the message
     *
     * @return the column position
     */
    int getColumnPos();

    /**
     * Gets the confirmation status from the message
     *
     * @return true if confirmed, false otherwise
     */
    boolean getConfirm();

    /**
     * Prints the message
     */
    void printMsg();


    /**
     * Gets the board information from the message
     *
     * @return the board information
     */
    String getBoard();

    /**
     * Gets the first common goal from the message
     *
     * @return the first common goal
     */
    String getFirstCommon();

    /**
     * Gets the second common goal from the message
     *
     * @return the second common goal
     */
    String getSecondCommon();

    /**
     * Gets the personal goal from the message
     *
     * @return the personal goal
     */
    String getPersonal();

    /**
     * Gets the selected tool card from the message
     *
     * @return the selected tool card
     */
    String getSelected();

    /**
     * Gets the shelf information from the message
     *
     * @return the shelf information
     */
    String getShelf();

    /**
     * Gets the ordered ranking information from the message
     *
     * @return the ordered ranking information
     */
    String getOrderedRanking();

    /**
     * Gets the columns information from the message
     *
     * @return the columns information
     */
    String getColumns();

    /**
     * Gets the recipient of the message
     *
     * @return the recipient of the message
     */
    String getTo();

    /**
     * Gets the sender of the message
     *
     * @return the sender of the message
     */
    String getFrom();


    /**
     * Gets the text of the message
     *
     * @return the text of the message
     */
    String getText();
}
