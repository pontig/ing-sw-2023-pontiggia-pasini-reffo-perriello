package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
        Shelf shelf = new Shelf();
        Random generator = new Random();
        int column = generator.nextInt(4);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                shelf.setItem(i, j, null);
            }
        }
        List<Item> insertableItems = new ArrayList<>();
        int insertableItemsNumber = generator.nextInt(5);
        for (int i = 0; i < insertableItemsNumber; i++) {
            insertableItems.add(new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
        }
        shelf.insertItems(insertableItems, column);
        int temp = 0;
        for(int i = 5; i > 5 - insertableItemsNumber; i--) {
            assertEquals(shelf.getItem(column, i),insertableItems.get(temp));
            temp++;
        }





    }

    @Test
    void testInsertItems2() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        int column = generator.nextInt(4);
        for(int i = 0; i < 5; i++) {                                                   //fill Shelf halfway
            for(int j = 5 ; j >= 3; j--) {
                shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
            }
        }
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 3; j++) {
                shelf.setItem(i, j, null);
            }
        }
        Item item = new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3));
        shelf.insertItem(item, column);
        for(int i = 0; i < 5; i++) {
            if(shelf.getItem(column, i) != null) {
                assertEquals(shelf.getItem(column, i), item);
                assertEquals(2, i);
            }
            break;
        }
    }

    @Test
    void setInsertableColumns() {
     Shelf shelf = new Shelf();
     Random generator = new Random();
     for(int i = 0; i < 5; i = i+2) {                  //fill alternate rows with a specific number of items
         for(int j = 5; j >= 3; j--) {
             shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
         }
     }
     for(int i = 0; i < 5; i = i+2) {
         for(int j = 0; j < 3; j++) {
             shelf.setItem(i, j, null);
         }
     }
     for(int i = 1; i < 4; i = i+2) {           //fill alternate rows the entire column;
         for(int j = 5; j >= 0; j--) {
             shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
         }
     }
     int numItems = 3;
     shelf.setInsertableColumns(numItems);
     int count = 0;
     for(int i = 0; i < 5; i++) {
         for(int j = 0; j < 6; j++) {
             if(shelf.getItem(i, j) == null) {
                 count++;
             }
         }
         if(count >= numItems) {
             assertTrue(shelf.getInsertableColumns().contains(i));
         }
         count = 0;
       }
     }

    @Test
    void getInsertableColumns() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        List<Integer> insertableColumns = new ArrayList<>();
        for(int i = 0; i < 5; i++) {                                //create Random Shelf
            int columnLength = generator.nextInt(5);
            for(int j = 5; j >= columnLength; j--) {
                shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
            }
            for(int k = columnLength - 1; k >= 0; k--) {
                shelf.setItem(i, k, null);
            }
          }
        int numItems = 3;
        shelf.setInsertableColumns(numItems);
        insertableColumns = shelf.getInsertableColumns();
        int counter = 0;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                if(shelf.getItem(i, j) == null) {
                    counter++;
                }
            }
            if(counter >= numItems) {
                assertTrue(insertableColumns.contains(i));
            }
        }
    }




    @Test
    void getMaxFreeSpace() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        int MaxFreeSpace = 0;
        for(int i = 0; i < 5; i++) {
            int maxcolumn = generator.nextInt(5);
            for(int j = 5; j >= maxcolumn; j--) {
                shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
            }
            for(int k = maxcolumn - 1; k >= 0; k--) {
                shelf.setItem(i, k, null);
            }
            if(maxcolumn > MaxFreeSpace) {
                MaxFreeSpace = maxcolumn;
            }
        }
        assertEquals(MaxFreeSpace, shelf.getMaxFreeSpace());
    }

    @Test
    void setItem() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        int column = generator.nextInt(4);
        int row = generator.nextInt(5);
        Item item = new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3));
        shelf.setItem(column, row, item);
        assertEquals(shelf.getItem(column, row), item);
    }

    @Test
    void getItem() {
        Shelf shelf = new Shelf();
        Random generator = new Random();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                shelf.setItem(i, j, new Item(Type.values()[generator.nextInt(Type.values().length)], generator.nextInt(3)));
            }
        }
        int column = generator.nextInt(4);
        int row = generator.nextInt(5);
        assertEquals(shelf.getItem(column, row), shelf.getItems()[column][row]);

    }
}

