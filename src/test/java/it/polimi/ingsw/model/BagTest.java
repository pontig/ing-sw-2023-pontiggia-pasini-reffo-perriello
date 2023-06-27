package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.tuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        List<Pair<Item, Integer>> items = new ArrayList<>();
        for(Type type : types){
            items.add(new Pair<>(new Item(type,0),7));
            items.add(new Pair<>(new Item(type,1),7));
            items.add(new Pair<>(new Item(type,2),8));
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
        List<Pair<Item, Integer>> items = new ArrayList<>();
        for(Type type : types){
            items.add(new Pair<>(new Item(type,0),7));
            items.add(new Pair<>(new Item(type,1),7));
            items.add(new Pair<>(new Item(type,2),8));
        }
        testBag.setItems(items);
        assertEquals(items,testBag.getItems());
    }

    @Test
    void draw() {
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        Bag testBag = new Bag();
        List<Pair<Item, Integer>> itemsInBag = new ArrayList<>();
        for(Type type : types){
            itemsInBag.add(new Pair<>(new Item(type,0),7));
            itemsInBag.add(new Pair<>(new Item(type,1),7));
            itemsInBag.add(new Pair<>(new Item(type,2),8));
        }
        testBag.setItems(itemsInBag);
        Item randomItem = testBag.draw();
        assertNotNull(randomItem);
    }
}