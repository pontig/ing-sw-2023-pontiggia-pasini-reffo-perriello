package it.polimi.ingsw.model;
import jdk.jshell.spi.ExecutionControl;


import it.polimi.ingsw.model.enums.Type;

import java.util.*;

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

    public Shelf clone() {
        Shelf clone = new Shelf();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                clone.setItem(i, j, this.getItem(i, j));
            }
        }
        return clone;
    }

    public Item[][] getItems() {
        return this.items;
    }

    public void setItems(Item[][] items) {
        // WARNING: Must be used only for testing purposes
        this.items = items;
    }

    public void insertItems(List<Item> insertableItems, int Column) {
        for (int r = 6; r > 0; r--) {
            if (items[Column][r] == null) {
                int index = 0;
                for (int j = r; j > r - insertableItems.size(); j--) {
                    items[Column][j] = insertableItems.get(index);
                    index++;
                }
            }
        }
        this.insertableColumns.clear();
    }

    public void insertItems(Item item, int column) {
        for (int r = 6; r > 0; r--) {
            if (items[column][r] == null) {
                items[column][r] = item;
                break;
            }
        }
    }

    public void setInsertableColumns(int numItems) {
        ArrayList<ArrayList<Item>> columns = this.getColumns();
        this.insertableColumns = columns.stream().mapToInt(column -> {
            int count = 0;
            for (Item item : column) {
                if (item == null) {
                    count++;
                }
            }
            return count;
        }).filter(count -> count >= numItems).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Integer> getInsertableColumns() {
        return this.insertableColumns;
    }

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
        }
        return Math.max(maxFreeColumn, 3);
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
                column.add(items[i][j]);
            }
            columns.add(column);
        }
        return columns;
    }

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

}