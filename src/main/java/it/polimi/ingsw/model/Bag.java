package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Bag {

    private Map<Item, Integer> items;

    public Bag(){
        this.items = new HashMap<>();
        Type[] types = Type.values();
        for (Type type : types) {
            items.put(new Item(type, 0), 7);
            items.put(new Item(type, 1), 7);
            items.put(new Item(type, 2), 8);
        }
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public Map<Item, Integer> getItems(){return this.items;}

    public Item draw() {
        Random generator = new Random();
        Object[] values;
        values = items.keySet().toArray();
        Item rendomItem = (Item) values[generator.nextInt(values.length)];
        items.put(rendomItem, items.get(rendomItem) - 1);
        return rendomItem;
    }
}
