package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.tuples.Pair;

import java.util.*;
import java.util.stream.Collectors;


public class Bag {

    private List<Pair<Item, Integer>> items;

    public Bag() {

        this.items = new ArrayList<>();
        Type[] types = Type.values();
        for (Type type : types) {
            items.add(new Pair<>(new Item(type, 0), 7));
            items.add(new Pair<>(new Item(type, 1), 7));
            items.add(new Pair<>(new Item(type, 2), 8));
        }
    }

    /**
     * setItems is used to fill the bag with all the items at the start of the game
     *
     * @param items: map of items that will be put in the bag (Integer indicates how many items of a certain variant will be contained in the bag)
     */
    public void setItems(List<Pair<Item, Integer>> items) {
        this.items = items;
    }

    @JsonProperty("items")
    public void restoreItems(List<Object> items) {
        this.items = items.stream()
                .map(e -> {
                    Integer second = ((LinkedHashMap<String, Integer>) e).get("y");
                    Type t = Type.valueOf((String) ((LinkedHashMap<String, String>) ((LinkedHashMap<String, Object>) e).get("x")).get("type"));
                    Integer v = ((LinkedHashMap<String, Integer>) ((LinkedHashMap<String, Object>) e).get("x")).get("variant");

                    return (new Pair<Item, Integer>(new Item(t,v), second));
                }).collect(Collectors.toList());
    }

    /**
     * getItems is used to get all the items contained in the bag
     *
     * @return a Map of items
     */
    public List<Pair<Item, Integer>> getItems() {
        return this.items;
    }

    /**
     * draw is used to draw a random element from the bag
     *
     * @return a certain item drawn from the bag
     */
    public Item draw() {
        Random generator = new Random();

        Pair<Item, Integer> randomItem = items.get(Math.abs(generator.nextInt() % items.size()));
        randomItem.setY(randomItem.getY() - 1);
        return randomItem.getX();
    }

}
