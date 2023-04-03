package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CellTest {

    @Test
    void setContent() {
        Random rand = new Random();            //Random case
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        int randomIndex = rand.nextInt(types.size());
        int randomVariant = rand.nextInt(3);
        Item randomContent = new Item(types.get(randomIndex), randomVariant);
        int randomCircumstance = rand.nextInt(5);
        while(randomCircumstance == 1){
            randomCircumstance = rand.nextInt(5);
        }
        if (randomCircumstance == 0){
            Cell testCell = new Cell(randomCircumstance);
            testCell.setContent(null);
            assertEquals(null, testCell.getContent());
        }
        else{
            Cell testCell = new Cell(randomCircumstance);
            testCell.setContent(randomContent);
            assertEquals(randomContent, testCell.getContent());
        }
        for (Type type : types) {                               //All cases
            for (int variant = 0; variant < 3; variant++) {
                Item content = new Item(type, variant);
                for (int circumstance = 0; circumstance < 5; circumstance++) {
                    if (circumstance == 1) {
                        continue;
                    }
                    if(circumstance == 0){
                        Cell testCell = new Cell(circumstance);
                        testCell.setContent(null);
                        assertEquals(null, testCell.getContent());
                    }
                    else{
                        Cell testCell = new Cell(circumstance);
                        testCell.setContent(content);
                        assertEquals(content, testCell.getContent());
                    }
                }
            }
        }
    }



    @Test
    void getContent(){
        Random rand = new Random();            //Random case
        List<Type> types = new ArrayList<>();
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        int randomIndex = rand.nextInt(types.size());
        int randomVariant = rand.nextInt(3);
        Item randomContent = new Item(types.get(randomIndex), randomVariant);
        int randomCircumstance = rand.nextInt(5);
        while(randomCircumstance == 1){
            randomCircumstance = rand.nextInt(5);
        }
        if (randomCircumstance == 0){
            Cell testCell = new Cell(randomCircumstance);
            testCell.setContent(null);
            assertEquals(null, testCell.getContent());
        }
        else{
            Cell testCell = new Cell(randomCircumstance);
            testCell.setContent(randomContent);
            assertEquals(randomContent, testCell.getContent());
        }
        for (Type type : types) {                               //All cases
            for (int variant = 0; variant < 3; variant++) {
                Item content = new Item(type, variant);
                for (int circumstance = 0; circumstance < 5; circumstance++) {
                    if (circumstance == 1) {
                        continue;
                    }
                    if(circumstance == 0){
                        Cell testCell = new Cell(circumstance);
                        testCell.setContent(null);
                        assertEquals(null, testCell.getContent());
                    }
                    else{
                        Cell testCell = new Cell(circumstance);
                        testCell.setContent(content);
                        assertEquals(content, testCell.getContent());
                    }
                }
            }
        }
    }

    @Test
    void setCircumstance(){
        Random rand = new Random();                       //Random case
        int randomCircumstance = rand.nextInt(5);
        while(randomCircumstance == 1){
            randomCircumstance = rand.nextInt(5);
        }
        Cell testCell = new Cell(randomCircumstance);
        testCell.setCircumstance(randomCircumstance);
        assertEquals(randomCircumstance, testCell.getCircumstance());
        for (int circumstance = 0; circumstance < 5; circumstance++) {          //All cases
            if (circumstance == 1) {
                continue;
            }
            testCell.setCircumstance(circumstance);
            assertEquals(circumstance, testCell.getCircumstance());
        }
    }

    @Test
    void getCircumstance(){
        Random rand = new Random();                       //Random case
        int randomCircumstance = rand.nextInt(5);
        while(randomCircumstance == 1){
            randomCircumstance = rand.nextInt(5);
        }
        Cell testCell = new Cell(randomCircumstance);
        testCell.setCircumstance(randomCircumstance);
        assertEquals(randomCircumstance, testCell.getCircumstance());
        for (int circumstance = 0; circumstance < 5; circumstance++) {          //All cases
            if (circumstance == 1) {
                continue;
            }
            testCell.setCircumstance(circumstance);
            assertEquals(circumstance, testCell.getCircumstance());
        }
    }
}