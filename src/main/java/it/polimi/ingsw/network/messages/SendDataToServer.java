package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

/**
 * The SendDataToServer class represents a message sent from the client to the server
 * It implements the Message interface
 */
public class SendDataToServer implements Message{
    State info = null;
    String nickname = null;
    int players_row_action = 0;
    int column_position = 0;
    boolean confirm = false;

    /**
     * Constructs a SendDataToServer object with the specified information
     *
     * @param info              the state information of the message
     * @param nickname          the nickname associated with the message
     * @param players_row_action the number of row action
     * @param column_position   the column position
     * @param confirm           the confirmation flag
     */
    public SendDataToServer(State info, String nickname, int players_row_action, int column_position, boolean confirm){
        this.info = info;
        this.nickname = nickname;
        this.players_row_action = players_row_action;
        this.column_position = column_position;
        this.confirm = confirm;
    }

    /**
     * @return the state information associated with the message
     */
    @Override
    public State getInfo() {
        return this.info;
    }

    /**
     * @return the nickname associated with the message
     */
    @Override
    public String getNickname() {
        return this.nickname;
    }

    /**
     * @return the number of row action associated with the message
     */
    @Override
    public int getNumRowAction() {
        return this.players_row_action;
    }

    /**
     * @return the column position associated with the message
     */
    @Override
    public int getColumnPos() {
        return this.column_position;
    }

    /**
     * @return the confirmation flag associated with the message
     */
    @Override
    public boolean getConfirm() {
        return this.confirm;
    }

    /**
     * Prints the message information
     */
    @Override
    public void printMsg() {
        System.out.println("INFO Message: " + getInfo());
    }

    /**
     * @return the board information associated with the message
     */
    @Override
    public String getBoard() {
        return null;
    }

    /**
     * @return the first commonGoal information associated with the message
     */
    @Override
    public String getFirstCommon() {
        return null;
    }

    /**
     * @return the second commonGoal information associated with the message
     */
    @Override
    public String getSecondCommon() {
        return null;
    }

    /**
     * @return the personalGoal information associated with the message
     */
    @Override
    public String getPersonal() {return null;}

    /**
     * @return the selected information associated with the message
     */
    @Override
    public String getSelected() { return null; }

    /**
     * @return the shelf information associated with the message
     */
    @Override
    public String getShelf() { return null; }

    /**
     * @return the orderedRanking information associated with the message
     */
    @Override
    public String getOrderedRanking() { return null;}

    /**
     * @return the columns information associated with the message
     */
    @Override
    public String getColumns() { return null; }

    /**
     * Returns the "to" field of the message
     * This method always returns null for the SendDataToClient class
     *
     * @return the "to" field of the message
     */
    @Override
    public String getTo() {
        return null;
    }

    /**
     * Returns the "from" field of the message
     * This method always returns null for the SendDataToClient class
     *
     * @return the "from" field of the message
     */
    @Override
    public String getFrom() {
        return null;
    }

    /**
     * Returns the text content of the message
     * This method always returns null for the SendDataToClient class
     *
     * @return the text content of the message
     */
    @Override
    public String getText() {
        return null;
    }
}
