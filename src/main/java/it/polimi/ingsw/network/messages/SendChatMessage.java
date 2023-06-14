package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enums.State;

public class SendChatMessage implements Message {
    private State info = null;
    private String from = null;
    private String to = null;
    private String text = null;

    public SendChatMessage(State info, String from, String to, String text) {
        this.info = info;
        this.from = from;
        this.to = to; // null if the message is for everyone
        this.text = text;
    }

    @Override
    public State getInfo() {
        return this.info;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    @Override
    public void printMsg() {
        System.out.println("INFO Message: " + getInfo());
    }

    @Override
    public String getBoard() {
        return null;
    }

    @Override
    public String getFirstCommon() {
        return null;
    }

    @Override
    public String getSecondCommon() {
        return null;
    }

    @Override
    public String getPersonal() {
        return null;
    }

    @Override
    public String getSelected() {
        return null;
    }

    @Override
    public String getShelf() {
        return null;
    }

    @Override
    public String getOrderedRanking() {
        return null;
    }

    @Override
    public String getColumns() {
        return null;
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public int getNumRowAction(){
        return -1;
    }
    @Override
    public int getColumnPos(){
        return -1;
    }
    @Override
    public boolean getConfirm(){
        return false;
    }
}
