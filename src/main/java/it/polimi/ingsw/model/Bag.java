package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import jdk.jshell.spi.ExecutionControl;

import java.util.Map;

public class Bag {

    private Map<Type, Integer> items;

    public Bag(Map<Type, Integer> items){
        this.items = items;
    }

    public void setItems(Map<Type, Integer> items) {
        this.items = items;
    }

    public Map<Type, Integer> getItems(){return this.items;}

    public Item draw() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
}
