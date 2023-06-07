package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    void getType() {
        Random rand = new Random();                         //Random case
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        int randomVariant = rand.nextInt(3);
        int randomIndex = rand.nextInt(types.size());
        Type randomType = types.get(randomIndex);
        Item randomItem = new Item(randomType, randomVariant);
        assertEquals(randomType, randomItem.getType());
        for(Type type : types){                                   //All cases
            for(int variant = 0; variant < 3; variant++){
                Item testItem = new Item(type, variant);
                assertEquals(type, testItem.getType());
            }
        }
    }
    @Test
    void setType() {
        Random rand = new Random();
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        int randomVariant = rand.nextInt(3);
        int randomIndex = rand.nextInt(types.size());
        Type randomType = types.get(randomIndex);
        Item randomItem = new Item();
        randomItem.setType(randomType);
        assertEquals(randomType, randomItem.getType());
    }
    @Test
    void setVariant() {
        Random rand = new Random();
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        int randomVariant = rand.nextInt(3);
        int randomIndex = rand.nextInt(types.size());
        Item randomItem = new Item();
        randomItem.setVariant(randomVariant);
        assertEquals(randomVariant, randomItem.getVariant());
    }
    @Test
    void getVariant() {
        Random rand = new Random();                               //Random case
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        int randomVariant = rand.nextInt(3);
        int randomIndex = rand.nextInt(types.size());
        Type randomType = types.get(randomIndex);
        Item randomItem = new Item(randomType, randomVariant);
        assertEquals(randomVariant, randomItem.getVariant());
        for(Type type : types){                                   //All cases
            for(int variant = 0; variant < 3; variant++){
                Item testItem = new Item(type, variant);
                assertEquals(variant, testItem.getVariant());
            }
        }
    }
}