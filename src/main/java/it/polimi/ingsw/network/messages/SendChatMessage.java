package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

/**
 * The SendChatMessage class represents a message used for sending chat messages between clients
 */
public class SendChatMessage implements Message {
    private State info = null;
    private String from = null;
    private String to = null;
    private String text = null;


    /**
     * Constructs a SendChatMessage object with the specified information
     *
     * @param info the state information of the message
     * @param from the sender of the message
     * @param to   the recipient of the message (null if the message is for everyone)
     * @param text the text of the message
     */
    public SendChatMessage(State info, String from, String to, String text) {
        this.info = info;
        this.from = from;
        this.to = to; // null if the message is for everyone
        this.text = text;
    }

    /**
     * Returns the state information associated with this message
     *
     * @return the state information
     */
    @Override
    public State getInfo() {
        return this.info;
    }

    /**
     * Gets the sender of the message
     *
     * @return the sender of the message
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets the recipient of the message
     *
     * @return the recipient of the message
     */
    public String getTo() {
        return to;
    }

    /**
     * Gets the text of the message
     *
     * @return the text of the message
     */
    public String getText() {
        return text;
    }

    /**
     * Prints the message to the console
     */
    @Override
    public void printMsg() {
        System.out.println("INFO Message: " + getInfo());
    }

    /**
     * @return the board string (null for this implementation)
     */
    @Override
    public String getBoard() {
        return null;
    }

    /**
     * @return the first common string (null for this implementation)
     */
    @Override
    public String getFirstCommon() {
        return null;
    }

    /**
     * @return the second common string (null for this implementation)
     */
    @Override
    public String getSecondCommon() {
        return null;
    }

    /**
     * @return the personal string (null for this implementation)
     */
    @Override
    public String getPersonal() {
        return null;
    }

    /**
     * @return the selected string (null for this implementation)
     */
    @Override
    public String getSelected() {
        return null;
    }

    /**
     * @return the shelf string (null for this implementation)
     */
    @Override
    public String getShelf() {
        return null;
    }

    /**
     * @return the ordered ranking string (null for this implementation)
     */
    @Override
    public String getOrderedRanking() {
        return null;
    }

    /**
     * @return the columns string (null for this implementation)
     */
    @Override
    public String getColumns() {
        return null;
    }

    /**
     * @return the nickname string (null for this implementation)
     */
    @Override
    public String getNickname() {
        return null;
    }

    /**
     * @return the number of row action (-1 for this implementation)
     */
    @Override
    public int getNumRowAction(){
        return -1;
    }

    /**
     * @return the column position (-1 for this implementation)
     */
    @Override
    public int getColumnPos(){
        return -1;
    }

    /**
     * @return the confirmation flag (false for this implementation)
     */
    @Override
    public boolean getConfirm(){
        return false;
    }
}
