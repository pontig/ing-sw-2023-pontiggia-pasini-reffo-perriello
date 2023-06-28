package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

/**
 * Represents a data message sent to a client that implements the Message interface.
 * It contains information such as the state, nickname, board, personal goal, shelf, selected items, confirmation flag,
 * ordered ranking, and columns
 */
public class SendDataToClient implements Message{
    State info = null;
    String nickname = null;
    String board = null;
    String personalGoal = null;
    String shelf = null;
    String c1 = null;
    String c2 = null;
    String selected = null;
    boolean confirm = false;
    String orderedRanking = null;
    String columns = null;


    /**
     * Constructs a SendDataToClient object with the specified information
     *
     * @param info           the state information of the message
     * @param nickname       the nickname of the client
     * @param board          the board string
     * @param personalGoal   the personal goal string
     * @param shelf          the shelf string
     * @param c1             the first common string
     * @param c2             the second common string
     * @param selected       the selected string
     * @param confirm        the confirmation flag
     * @param orderedRanking the ordered ranking string
     * @param columns        the columns string
     */
    public SendDataToClient(State info, String nickname, String board, String personalGoal, String shelf, String c1, String c2,
                            String selected, boolean confirm, String orderedRanking, String columns){
        this.info = info;
        this.nickname = nickname;
        this.board = board;
        this.personalGoal = personalGoal;
        this.shelf = shelf;
        this.c1 = c1;
        this.c2 = c2;
        this.selected = selected;
        this.confirm = confirm;
        this.orderedRanking = orderedRanking;
        this.columns = columns;
    }

    /**
     * @return the state information associated with the message
     */
    @Override
    public State getInfo() {
        return this.info;
    }

    /**
     * Prints the message to the console
     */
    @Override
    public void printMsg() {
        System.out.println("INFO Message: "+ getInfo());
    }

    /**
     * @return the board string associated with the message
     */
    @Override
    public String getBoard() {
        return board;
    }

    /**
     * @return the first common string associated with the message
     */
    @Override
    public String getFirstCommon() {
        return c1;
    }

    /**
     * @return the second common string associated with the message
     */
    @Override
    public String getSecondCommon() {
        return c2;
    }

    /**
     * @return the personal goal string associated with the message
     */
    @Override
    public String getPersonal() { return personalGoal; }

    /**
     * @return the selected string associated with the message
     */
    @Override
    public String getSelected() { return selected; }

    /**
     * @return the shelf string associated with the message
     */
    @Override
    public String getShelf() { return shelf; }

    /**
     * @return the orderedRanking string associated with the message
     */
    @Override
    public String getOrderedRanking() { return orderedRanking; }

    /**
     * @return the columns string associated with the message
     */
    @Override
    public String getColumns() { return columns; }

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

    /**
     * @return the nickname string associated with the message
     */
    @Override
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the confirm string associated with the message
     */
    @Override
    public boolean getConfirm() {return confirm;}

    /**
     * @return the number of row action associated with the message
     */
    @Override
    public int getNumRowAction() {return 0;}

    /**
     * @return the column position associated with the message
     */
    @Override
    public int getColumnPos() {return 0;}
}
