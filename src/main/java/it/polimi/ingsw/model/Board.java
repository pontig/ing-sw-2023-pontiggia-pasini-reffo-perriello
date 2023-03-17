package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Set;

public class Board {

    private Cell[][] disposition;

    private Set<Cell> pending;

    public Board() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }

    public Cell[][] getBoard() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }

    public void fill(int numPlayer) throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }

    private boolean needToRefill() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }

    private Item showItem(int x, int y) throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }

    public void extractPending() {
        pending.forEach(c -> c.setItem(null)));
        pending.clear();
    }

    private boolean freeSide(Cell c) throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    private boolean isInLine(Cell c) throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Method not Implemented yet");
    }
    public Cell selectCell(int x, int y) throws NotSelectableException {
        Cell c = disposition[x][y];
        if (pending.contains(c)) {
            pending.remove(c);
            return null;
        }
        if (!freeSide(c) || !isInLine(c))
            throw new NotSelectableException();
        pending.add(c);
        return c;
    }
}
