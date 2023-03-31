package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
class ShelfTest {

    @Test
    void testClone() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
            }
        }
        Shelf clone = shelf.clone();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(shelf.getItem(i, j), clone.getItem(i, j));
            }
        }
    }
    @Test
    void getItems() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
            }
        }
        Item[][] items = shelf.getItems();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals(shelf.getItem(i, j), items[i][j]);
            }
        }

    }

    @Test
    void setItems() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        Item[][] items = new Item[5][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                items[i][j] = new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3));
            }
        }
        shelf.setItems(items);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                assertEquals(shelf.getItem(i, j), items[i][j]);
            }
        }
    }

    @Test
    void insertItems() {
    }

    @Test
    void testInsertItems() {
    }

    @Test
    void setInsertableColumns() {
    }

    @Test
    void getInsertableColumns() {
    }

    @Test
    void getMaxFreeSpace() {
    }

    @Test
    void setItem() {
    }

    @Test
    void getItem() {
    }

    @Test
    void getColumns() {
    }

    @Test
    void getRows() {
    }

    @Test
    void adjacent() {
    }
}