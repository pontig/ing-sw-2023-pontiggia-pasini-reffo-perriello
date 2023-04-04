package it.polimi.ingsw.model;

import it.polimi.ingsw.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Board {

    private Cell[][] disposition;

    private static Cell[][] emptyBoard = new Cell[9][9];
    private Set<Pair<Integer, Integer>> pendingCells;

    public Board(int[][] matrix) {
        // matrix represents the board: for every cell, the value represent the circumstance (0 for unusable, 2 for two
        // players etc.)
        emptyBoard = new Cell[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                emptyBoard[i][j] = new Cell(matrix[i][j]);
            }
        }
    }
    public Board() {
        // matrix represents the board: for every cell, the value represent the circumstance (0 for unusable, 2 for two
        // players etc.)
        disposition = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                disposition[i][j] = emptyBoard[i][j];
            }
        }
    }

    public void setDisposition(Cell[][] disposition) {
        this.disposition = disposition;
    }

    public Cell[][] getDisposition() {
        return this.disposition;
    }

    public void setPendingCells(Set<Pair<Integer, Integer>> pendingCells) {
        this.pendingCells = pendingCells;
    }

    public Set<Pair<Integer, Integer>> getPendingCells() {
        return this.pendingCells;
    }

    public int select(int x, int y) {
        boolean sameLineOrColumn = false;
        Pair<Integer, Integer> selectedPair = new Pair<>(x, y);
        int sizePending = pendingCells.size();
        if (adjacencyCheck(x, y) && sizePending < 3) {
            if (pendingCells.isEmpty()) {
                pendingCells.add(selectedPair);
            } else {
                for (Pair<Integer, Integer> pair : pendingCells) {
                    if (pair.getX() == x || pair.getY() == y) {
                        sameLineOrColumn = true;
                    } else {
                        sameLineOrColumn = false;
                    }
                }
                if (sameLineOrColumn) {
                    pendingCells.add(selectedPair);
                }
            }
        }
        return pendingCells.size();
    }

    public int deselect(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<>(x, y);
        if (pendingCells.contains(pair)) {
            pendingCells.remove(pair);
        }
        return pendingCells.size();
    }

    public void fill(int numPlayer, Bag bag) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (disposition[i][j].getContent() == null && numPlayer <= disposition[i][j].getCircumstance()) {
                    disposition[i][j].setContent(bag.draw());
                }
            }
        }
    }

    public List<Item> removePendingItems() {
        List<Item> offPending = new ArrayList<>();
        for (Pair<Integer, Integer> pair : pendingCells) {
            int x = pair.getX();
            int y = pair.getY();
            offPending.add(disposition[x][y].getContent());
            disposition[x][y].setContent(null);
            pendingCells.remove(pair);
        }
        return offPending;
    }

    public boolean needToRefill() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (disposition[i][j] != null) {
                    switch (i) {
                        case 0:
                            if (disposition[i + 1][j] == null && disposition[i][j - 1] == null && disposition[i][j + 1] == null) {
                                return true;
                            }
                        case 9:
                            if (disposition[i - 1][j] == null && disposition[i][j - 1] == null && disposition[i][j + 1] == null) {
                                return true;
                            }
                        default:
                            switch (j) {
                                case 0:
                                    if (disposition[i + 1][j] == null && disposition[i - 1][j] == null && disposition[i][j + 1] == null) {
                                        return true;
                                    }
                                case 9:
                                    if (disposition[i + 1][j] == null && disposition[i - 1][j] == null && disposition[i][j - 1] == null) {
                                        return true;
                                    }
                                default:
                                    if (disposition[i + 1][j] == null && disposition[i - 1][j] == null && disposition[i][j + 1] == null && disposition[i][j - 1] == null) {
                                        return true;
                                    }
                            }

                    }
                }
            }
        }
        return false;
    }

    private boolean adjacencyCheck(int x, int y) {
        if (disposition[x][y] != null) {
            switch (x) {
                case 0:
                case 9:
                    return true;
                default:
                    switch (y) {
                        case 0:
                        case 9:
                            return true;
                        default:
                            if (disposition[x + 1][y] == null || disposition[x - 1][y] == null || disposition[x][y + 1] == null && disposition[x][y - 1] == null) {
                                return true;
                            }
                    }
            }
        }
        return false;
    }
}
