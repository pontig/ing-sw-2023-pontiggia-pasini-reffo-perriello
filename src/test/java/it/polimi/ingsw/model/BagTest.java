package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BagTest {

    @Test
    void setItems() {
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        Bag testBag = new Bag();
        Map<Item, Integer> items = new HashMap<>();
        for(Type type : types){
            items.put(new Item(type,0),7);
            items.put(new Item(type,1),7);
            items.put(new Item(type,2),8);
        }
        testBag.setItems(items);
        assertEquals(items,testBag.getItems());
    }

    @Test
    void getItems() {
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        Bag testBag = new Bag();
        Map<Item, Integer> itemsInBag = new HashMap<>();
        for(Type type : types){
            itemsInBag.put(new Item(type,0),7);
            itemsInBag.put(new Item(type,1),7);
            itemsInBag.put(new Item(type,2),8);
        }
        testBag.setItems(itemsInBag);
        assertEquals(itemsInBag,testBag.getItems());
    }

    @Test
    void draw() {
        Random rand = new Random();
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        Set<Item> items = new HashSet<>();
        for(Type type : types){
            items.add(new Item(type,0));
            items.add(new Item(type,1));
            items.add(new Item(type,2));
        }
        Bag testBag = new Bag();
        Map<Item, Integer> itemsInBag = new HashMap<>();
        for(Type type : types){
            itemsInBag.put(new Item(type,0),7);
            itemsInBag.put(new Item(type,1),7);
            itemsInBag.put(new Item(type,2),8);
        }
        testBag.setItems(itemsInBag);
        Item randomItem = testBag.draw();
        if(randomItem.getVariant() == 0 || randomItem.getVariant() == 1){
            assertEquals(6, itemsInBag.get(randomItem));   //CHECK!
        }
        else{
            assertEquals(7, itemsInBag.get(randomItem)-1);   //CHECK!
        }
        for(Item item : items){
            if(randomItem == item){
                assertEquals(item, randomItem);
            }
        }
    }
}