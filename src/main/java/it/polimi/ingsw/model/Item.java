package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import jdk.jshell.spi.ExecutionControl;

public class Item {

    // https://www.geeksforgeeks.org/create-immutable-class-java/
    // questa classe qui sarà immutabile. segui le istruzioni di questo link per capire come funziona

    public Type type; //Read only
    public Item() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }

    public void setType(Type type) {
        this.type = type;
    }
    public Type getType(){return this.type;}
}
