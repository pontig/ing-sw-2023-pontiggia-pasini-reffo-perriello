package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

public class Item {

    public Type type; //Read only
    public Item() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }

    public void setType(Type type) {
        this.type = type;
    }
    public Type getType(){return this.type;}
}
