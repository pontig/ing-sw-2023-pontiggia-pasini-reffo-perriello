package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

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

    public Shelf() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    public void printShelf() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void insertItem(Item item, int column) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public int[] getInsertableColumns(int numItems) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }

    public void insertItem(Item item, int column) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public int[] getInsertableColumns(int numItems) throws ExecutionControl.NotImplementedException {

        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
}
