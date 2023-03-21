package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.NotNull;

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
    private List<items> orderedItems;
    private int columnChosen;

    public Shelf(items) {
        this.items = new int[5][6];

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                items[i][j] = null;
            }
        }
        for(int k = 0; k < 6; k++) {
            this.insertableColumns = new ArrayList<>();            //inizialmente posso inserire in tutte le colonne
            this.insertableColumns.add(k);
        }
        this.orderedItems = null;
        this.columnChosen = -1;
    }
    public void printShelf() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet"),
    }
    public void insertItem(Item item, int column) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public int[] setInsertableColumns(int numItems) { this.numItems = numItems; }

    public List<items> getInsertableColumns() {
        return insertableColumns;
    }
    public int getMaxFreeSpace() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void setOrderItem(int posList) { this.posList = posList; }
}
