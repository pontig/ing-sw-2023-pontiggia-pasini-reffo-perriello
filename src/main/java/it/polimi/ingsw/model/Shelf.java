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

    private List<Item> insertableItems;


    public List<Integer> Shelf(Item[][] items, List<Integer> insertableColumns) {
        this.items = new Item[5][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                items[i][j] = null;
            }
        }

        for (int k = 0; k < 6; k++) {
            this.insertableColumns = new ArrayList<>();
            this.insertableColumns.add(k);
        }

        public Item[][] getItems () {
            return this.items;
        }

        public void setItems (Item[][]items){
            this.items = items;
        }

        public void insertItem (List < Item > insertableItems,int Column){
            for (int r = 6; r > 0; r--) {
                if (items[Column][r] == null) {
                    int index = 0;
                    for (int j = r; j > r - insertableItems.size(); j--) {
                        items[Column][j] = insertableItems.get(index);
                        index++;
                    }
                }
            }
        }
    }
        public void setInsertableColumns(int numItems) {
            int counter = 0;
            for (Integer i = 0; i < 5; i++) {
                for (Integer j = 0; j < 6; j++) {
                    if (items[i][j] == null) {
                        counter++;
                    }
                }
                if (counter == numItems) {
                    insertableColumns.add(i);
                }
            }


            public List<Integer> getInsertableColumns (List < Integer > items) {
                return this.insertableColumns;
            }

            public Integer getMaxFreeSpace () {
                Integer countFreeSpace = 0;
                Integer maxFreeColumn = 0;
                for (int c = 0; c < 5; c++) {
                    for (int r = 0; r < 6; r++) {
                        if (items[c][r] == null) {
                            countFreeSpace++;
                        }
                    }
                    if (countFreeSpace > maxFreeColumn) {
                        maxFreeColumn = countFreeSpace;
                    }
                }
                Integer maxFreeSpace = Math.max(Integer maxFreeColumn, Integer 3);
                return maxFreeSpace;
            }
    }

        public void setItem(int x, int y, Item item) {
            this.items[x][y] = item;
        }
        public Item getItem(int x, int y) {
            return this.items[x][y];
        }

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

}
