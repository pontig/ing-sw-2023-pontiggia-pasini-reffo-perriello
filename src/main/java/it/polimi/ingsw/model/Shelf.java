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
    private Collection<Item> items;
    private Collection<Integer> freeSpace;

    public Shelf() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    public Collection<Item> getShelf() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    private void decreaseFreeSpace() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public void putItems(Collection<Item> items, int column) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
}
