package it.polimi.ingsw.model;

import it.polimi.ingsw.tuples.Pair;

import java.util.ArrayList;
import java.util.HashSet;
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
        this.disposition = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                disposition[i][j] = new Cell(emptyBoard[i][j].getCircumstance());
            }
        }
        pendingCells = new HashSet<>();
    }

    public void setDisposition(Cell[][] disposition) {
        this.disposition = disposition;
    }

    public Cell[][] getDisposition() {
        return this.disposition;
    }

    public void setCell(int x, int y, Item item, int cirumstance) {
        this.disposition[y][x] = new Cell(cirumstance);
        disposition[y][x].setContent(item);
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
        Pair<Integer, Integer> selectedPair = new Pair<>(x, y);
        if (pendingCells.contains(selectedPair)) {
            pendingCells.remove(selectedPair);
        }
        return pendingCells.size();
    }

    public void fill(int numPlayer, Bag bag) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.disposition[i][j].getContent() == null
                        && this.disposition[i][j].getCircumstance() != 0
                        && numPlayer >= this.disposition[i][j].getCircumstance()) {
                    this.disposition[i][j].setContent(bag.draw());
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
        }
        pendingCells.clear();
        return offPending;
    }

    public boolean needToRefill() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (disposition[j][i].getContent() != null) {
                    switch (i) {
                        case 0:
                            if (disposition[j + 1][i].getContent() == null && disposition[j][i + 1].getContent() == null && disposition[j - 1][i].getContent() == null) {
                                return true;
                            }
                            break;
                        case 8:
                            if (disposition[j][i - 1].getContent() == null && disposition[j + 1][i].getContent() == null && disposition[j - 1][i].getContent() == null) {

                                return true;
                            }
                            break;
                        default:
                            switch (j) {
                                case 0:

                                    if (disposition[j][i + 1].getContent() == null && disposition[j][i - 1].getContent() == null && disposition[j + 1][i].getContent() == null) {
                                        return true;
                                    }
                                    break;
                                case 8:
                                    if (disposition[j][i + 1].getContent() == null && disposition[j][i - 1].getContent() == null && disposition[j - 1][i].getContent() == null) {

                                        return true;
                                    }
                                    break;
                                default:

                                    if (disposition[j][i + 1].getContent() == null && disposition[j][i - 1].getContent() == null && disposition[j + 1][i].getContent() == null && disposition[j - 1][i].getContent() == null) {

                                        return true;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        }
        return false;
    }

    private boolean adjacencyCheck(int x, int y) {
        if (disposition[x][y].getContent() != null) {
            switch (x) {
                case 0:
                case 8:
                    return true;
                default:
                    switch (y) {
                        case 0:
                        case 8:
                            return true;
                        default:
                            if (disposition[x + 1][y].getContent() == null || disposition[x - 1][y].getContent() == null || disposition[x][y + 1].getContent() == null && disposition[x][y - 1].getContent() == null) {
                                return true;
                            }
                    }
            }
        }
        return false;
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (disposition[i][j].getContent() != null) {
                    System.out.print(disposition[i][j].getContent().getType().toString().charAt(0) + " ");
                } else {
                    System.out.print(disposition[i][j].getCircumstance() + " ");
                }
            }
            System.out.println();
        }
    }

    public Board clone() {
        Board clone = new Board();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                clone.disposition[i][j] = new Cell(this.disposition[i][j].getCircumstance());
                if (this.disposition[i][j].getContent() != null) {
                    clone.disposition[i][j].setContent(this.disposition[i][j].getContent());
                }
            }
        }
        return clone;
    }

}
