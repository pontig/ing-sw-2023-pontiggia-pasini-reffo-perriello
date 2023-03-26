package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Circumstance;
import jdk.jshell.spi.ExecutionControl;

public class Cell {
    private Item content;
    private Circumstance circumstance;

    public Cell(Item content, Circumstance circumstance){
        this.content = content;
        this.circumstance = circumstance;
    }

    public void setContent(Item content) {
        this.content = content;
    }
    public Item getContent(){return this.content;}
    public void setCircumstance(Circumstance circumstance) {
        this.circumstance = circumstance;
    }
    public Circumstance getCircumstance(){return this.circumstance;}
}

