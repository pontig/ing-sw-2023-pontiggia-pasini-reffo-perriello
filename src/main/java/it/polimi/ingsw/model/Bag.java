package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

public class Bag {

    private Map<Type, Integer> items;

    public Bag() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }

    public void setItems(Map<Type, Integer> items) {
        this.items = items;
    }

    public Map<Type, Integer> getItems(){return this.items;}

    public Item draw() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
}
