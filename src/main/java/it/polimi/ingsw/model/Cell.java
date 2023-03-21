package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Circumnstance;
import jdk.jshell.spi.ExecutionControl;

public class Cell {

    private Item content;

    private Circumstance circumstance;

    public Cell() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }

    public void setContent(Item content) {
        this.content = content;
    }

    public Item getContent() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }

    public void setCircumstance(Circumstance circumstance) {
        this.circumstance = circumstance;
    }

    public Circumstance getCircumstance(){return this.circumstance;}

    public void setItem(Type item) throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
}
