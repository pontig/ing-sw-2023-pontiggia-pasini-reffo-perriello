package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Circumstance;

public class Cell {
    private Item content;
    private int circumstance;

    public Cell(int circumstance){
        this.content = null;
        this.circumstance = circumstance;
    }

    public void setContent(Item content) {
        this.content = content;
    }
    public Item getContent(){return this.content;}
    public void setCircumstance(int circumstance) {
        this.circumstance = circumstance;
    }
    public int getCircumstance(){return this.circumstance;}
}

