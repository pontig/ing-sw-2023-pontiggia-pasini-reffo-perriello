package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.*;

public class Shelf {
    /* ============ SHELF SCHEMA: ============
    [ [0][0] [1][0] [2][0] [3][0] [4][0]
      [0][1] [1][1] [2][1] [3][1] [4][1]
      [0][2] [1][2] [2][2] [3][2] [4][2]
      [0][3] [1][3] [2][3] [3][3] [4][3]
      [0][4] [1][4] [2][4] [3][4] [4][4]
      [0][5] [1][5] [2][5] [3][5] [4][5] ]
    ======================================= */
    private Item[][] items;
    private List<Integer> insertableColumns;
    private List<Item> orderedItems;
    private int columnChosen;

    public  Shelf() {
        this.items= new Item[5][6];

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                items[i][j] = null;
            }
        }
        for(int k = 0; k < 6; k++) {
            this.insertableColumns = new ArrayList<>();
            this.insertableColumns.add(k);
        }
        this.orderedItems = null;
        this.columnChosen = -1;
    }
    public void printShelf() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void insertItem(Item item, int column) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void setInsertableColumns(int numItems) { this.numItems = numItems; }

    public List<Integer> getInsertableColumns() {
        return this.insertableColumns;
    }
    public int getMaxFreeSpace() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public List<Item> getOrderedItems() {return this.orderedItems; }
    public void setOrderItem(int posList) { this.orderedItems = posList; }


    public int getcolumnChosen() { return this.columnChosen; }

    public void setcolumnChosen(int columnChosen) { this.columnChosen = columnChosen; }

    public Item[][] getItems(int i, int j) { return this.items; }

    public void setItems(Item[][] items) { this.items = items; }
    public ArrayList<ArrayList<Item>> getColumns() {
        ArrayList<ArrayList<Item>> columns = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<Item> column = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                column.add(items[j][i]);
            }
            columns.add(column);
        }
        return columns;
    }
    public ArrayList<ArrayList<Item>> getRows() {
        ArrayList<ArrayList<Item>> rows = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ArrayList<Item> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                row.add(items[i][j]);
            }
            rows.add(row);
        }
        return rows;
    }
    public void setItem(int x, int y, Item item) {
        this.items[x][y] = item;
    }
    public Item getItem(int x, int y) {
        return this.items[x][y];
    }
}
