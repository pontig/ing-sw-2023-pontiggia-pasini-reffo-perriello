package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Shelf {

    /* ============ SHELF: ============
    [ [0][0] [1][0] [2][0] [3][0] [4][0]
      [0][1] [1][1] [2][1] [3][1] [4][1]
      [0][2] [1][2] [2][2] [3][2] [4][2]
      [0][3] [1][3] [2][3] [3][3] [4][3]
      [0][4] [1][4] [2][4] [3][4] [4][4]
      [0][5] [1][5] [2][5] [3][5] [4][5] ]
    ======================================= */
    private Item[][] items;
    private List<Integer> insertableColumns;

    public Shelf() {
        this.items = new Item[5][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                items[i][j] = null;
            }
        }
        this.insertableColumns = new ArrayList<>();
    }

    /** clone creates a copy of the shelf
     * @return a clone of the shelf
     */

    public Shelf clone() {
        Shelf clone = new Shelf();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                clone.setItem(i, j, this.getItem(i, j));
            }
        }
        return clone;
    }

    /**
     * clear empties the shelf setting all the items to null
     */
    public void clear(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                setItem(i, j,null);
            }
        }
    }

    /**
     * getItems is used to get a group of items from the shelf
     * @return a matrix of items
     */

    public Item[][] getItems() {
        return this.items;
    }

    /**
     * setItems sets on the shelf a matrix of items
     * @param items : matrix of items to be set
     */

    public void setItems(Item[][] items) {
        // WARNING: Must be used only for testing purposes
        this.items = items;
    }

    /**
     * insertItems inserts a list of items in a specific column of the shelf
     * @param insertableItems : list of items to be inserted
     * @param Column : column where the items will be inserted
     */
    public void insertItems(List<Item> insertableItems, int Column) {
        for (int r = 5; r >= 0; r--) {
            if (items[Column][r] == null) {
                int index = 0;
                for (int j = r; j > r - insertableItems.size(); j--) {
                    items[Column][j] = insertableItems.get(index);
                    index++;
                }
                break;
            }
        }
        this.insertableColumns.clear();
    }


    /**
     * insertItem is the same as the previous one, but it inserts only one item(used for testing)
     * @param item : item to be inserted
     * @param column : column where the item will be inserted
     */
    public void insertItem(Item item, int column) {
        for (int r = 5; r >= 0; r--) {
            if (items[column][r] == null) {
                items[column][r] = item;
                break;
            }
        }
    }

    /**
     * setInsertableColumns sets a list called "insertableColumns", which contains the columns  where it is possible to insert
     * a certain number of items
     * @param numItems : number of items to be inserted
     */
    public void setInsertableColumns(int numItems) {
        int count = 0;
        this.insertableColumns.clear();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
               if(items[i][j] == null) {
                   count++;
               }
               if(count >= numItems) {
                   this.insertableColumns.add(i);
                   count = 0;
                   break;
               }
            }
            count = 0;
        }
    }

    /**
     * getInsertableColumns gives back the list created by the previous method( "setInsertableColumns" )
     * @return the list "insertableColumns", that contains the columns where it is possible to insert a certain number of items
     */
    public List<Integer> getInsertableColumns() {
        return this.insertableColumns;
    }

    /**
     * getMaxFreeSpace checks row by row, what is the maximum number of free spaces in a column of the shelf;
     * then it compares this number with 3
     * @return this number if it is less than 3, otherwise it returns 3
     */
    public int getMaxFreeSpace() {
        int countFreeSpace = 0;
        int maxFreeColumn = 0;
        for (int c = 0; c < 5; c++) {
            for (int r = 0; r < 6; r++) {
                if (items[c][r] == null) {
                    countFreeSpace++;
                }
            }
            if (countFreeSpace > maxFreeColumn) {
                maxFreeColumn = countFreeSpace;
            }
            countFreeSpace = 0;
        }
        return Math.min(maxFreeColumn, 3);
    }

    /**
     * setItem sets an item in a certain position (x, y) of the shelf
     * @param x : column
     * @param y : row
     * @param item : item to be inserted
     */
    public void setItem(int x, int y, Item item) {
        this.items[x][y] = item;
    }

    /**
     * getItem gives back the item in a certain position of the shelf
     * @param x : column
     * @param y : row
     * @return the item in the position (x, y) of the shelf
     */
    public Item getItem(int x, int y) {
        return this.items[x][y];
    }

    /**
     * getColumns creates a List of columns, where each column is a List of items
     * @return an ArrayList of ArrayLists of items, where each ArrayList contains the items of a column
     */
    public ArrayList<ArrayList<Item>> getColumns() {
        ArrayList<ArrayList<Item>> columns = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<Item> column = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                column.add(items[i][j]);
            }
            columns.add(column);
        }
        return columns;
    }

    /**
     * getRows creates a List of rows, where each row is a List of items
     * @return an ArrayList of ArrayLists of items, where each ArrayList contains the items of a row
     */
    public ArrayList<ArrayList<Item>> getRows() {
        ArrayList<ArrayList<Item>> rows = new ArrayList<>();
        for (int r = 0; r < 6; r++) {
            ArrayList<Item> row = new ArrayList<>();
            for (int c = 0; c < 5; c++) {
                row.add(items[c][r]);
            }
            rows.add(row);
        }
        return rows;
    }

    /**
     * adjacent analyzes the items of the shelf and counts how many items of a certain type are adjacent to each other
     * @param x : column
     * @param y : row
     * @param type : type of the items to be analyzed
     * @return the number of adjacent items of a certain type
     */
    public int adjacent(int x, int y, Type type) {
        if (x < 0 || x > 4 || y < 0 || y > 5 || this.getItem(x, y) == null ||
                !Objects.equals(this.getItem(x, y).getType(), type)) {
            return 0;
        } else {
            this.setItem(x, y, null);
            int up = adjacent(x, y - 1, type);
            int down = adjacent(x, y + 1, type);
            int left = adjacent(x - 1, y, type);
            int right = adjacent(x + 1, y, type);
            return 1 + up + down + left + right;
        }
    }

    public String toString(){
        StringBuilder shelf = new StringBuilder(" ");
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                if(getItems()[j][i] != null){
                    switch (getItems()[j][i].getType()) {
                        case BOOK:
                            shelf.append("W").append(getItems()[j][i].getVariant());
                            break;
                        case CAT:
                            shelf.append("G").append(getItems()[j][i].getVariant());
                            break;
                        case FRAME:
                            shelf.append("B").append(getItems()[j][i].getVariant());
                            break;
                        case GAME:
                            shelf.append("Y").append(getItems()[j][i].getVariant());
                            break;
                        case PLANTS:
                            shelf.append("P").append(getItems()[j][i].getVariant());
                            break;
                        case TROPHY:
                            shelf.append("L").append(getItems()[j][i].getVariant());
                            break;
                        default:
                            System.out.println("Error");
                            break;
                    }
                } else
                    shelf.append('■').append(" ");
            }
            if(i!=5) shelf.append("\n ");
            else shelf.append("\n");
        }
        return shelf.toString();
    }

    public String columnsToString(int choice){
        StringBuilder columns = new StringBuilder(" ");
        boolean equals = false;
        for(int i=0; i<5; i++){
            for(int c:getInsertableColumns()){
                if(c == i) {
                    if(c == choice) columns.append("#").append(" ");
                    else columns.append("▲").append(" ");
                    equals = true;
                    break;
                }
            }
            if(!equals) columns.append("  ");
            equals = false;
        }
        return columns.toString();
    }
}
