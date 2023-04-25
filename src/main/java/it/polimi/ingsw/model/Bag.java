package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.Type;

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

    /** setItems is used to fill the bag with all the items at the start of the game
     * @param items: map of items that will be put in the bag (Integer indicates how many items of a certain variant will be contained in the bag)
     */
    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    /** getItems is used to get all the items contained in the bag
     * @return a Map of items
     */
    public Map<Item, Integer> getItems(){return this.items;}

    /** draw is used to draw a random element from the bag
     * @return a certain item drawn from the bag
     */
    public Item draw() {
        Random generator = new Random();
        Object[] values;
        values = items.keySet().toArray();
        Item randomItem = (Item) values[generator.nextInt(values.length)];
        items.put(randomItem, items.get(randomItem) - 1);
        return randomItem;
    }
}
