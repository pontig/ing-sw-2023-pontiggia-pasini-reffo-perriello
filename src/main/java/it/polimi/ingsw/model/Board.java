package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.network.server.ServerImpl;
import it.polimi.ingsw.tuples.Pair;

import java.io.IOException;
import java.util.*;

/**
 * Board is the class that represents the board of the game
 * <p>
 * It contains the matrix of cells that represents the board, the set of items that are currently pending and the
 * methods to manage them
 */
public class Board {

    private Cell[][] disposition;
    private static Cell[][] emptyBoard = new Cell[9][9];
    @JsonIgnore private Set<Pair<Integer, Integer>> pendingCells;

    /**
     * Constructs a new Board based on the provided matrix.
     *
     * @param matrix The matrix representing the board, where each value represents the circumstance of a cell.
     *               Use 0 for unusable, 2 for two players, etc.
     */
    public Board(int[][] matrix) {
        emptyBoard = new Cell[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                emptyBoard[i][j] = new Cell(matrix[i][j]);
            }
        }
    }

    /**
     * Constructs a new Board object.
     *
     * If the initial emptyBoard is not initialized, it loads the board matrix from a JSON file.
     * The matrix represents the board: for every cell, the value represents the circumstance (0 for unusable, 2 for two players, etc.).
     * The disposition of the board is then initialized based on the emptyBoard matrix, and pendingCells are set to an empty HashSet.
     */
    public Board() {

        if (emptyBoard[0][0] == null) {
            ObjectMapper mapper = new ObjectMapper();

            int[][] board = new int[0][];
            try {
                board = mapper.readValue(ServerImpl.class.getResourceAsStream("/json/livingroom.json"), int[][].class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new Board(board);
        }

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

    /**
     * setDisposition is used to set, in every cell of the board, an item
     *
     * @param disposition: a matrix of Cell that need to be filled
     */
    public void setDisposition(Cell[][] disposition) {
        this.disposition = disposition;
    }

    /**
     * Sets the disposition of the board based on the provided JSON representation.
     *
     * @param JSONdisposition The JSON representation of the board's disposition.
     */
    @JsonProperty("disposition")
    public void setDisposition(Object[][] JSONdisposition) {
        this.disposition = new Cell[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                LinkedHashMap<String, Object> cell = (LinkedHashMap<String, Object>) JSONdisposition[i][j];
                LinkedHashMap<String, Object> content = (LinkedHashMap<String, Object>) cell.get("content");
                    disposition[i][j] = new Cell((int) cell.get("circumstance"));
                if (content == null) {
                    disposition[i][j].setContent(null);
                } else {
                       disposition[i][j].setContent(new Item(Type.valueOf((String) content.get("type")), (int) content.get("variant")));
                }
            }
        }
    }

    /**
     * getDisposition is used to get the disposition of a certain cell from the board
     *
     * @return a matrix of cell
     */
    public Cell[][] getDisposition() {
        return this.disposition;
    }

    /**
     * Retrieves the cell at the specified coordinates on the board.
     * This method should only be used for testing purposes.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The cell at the specified coordinates.
     */
    public Cell getCell(int x, int y) {
        return disposition[y][x];
    }

    /**
     * Sets the specified cell on the board with the given item and circumstance.
     * This method should only be used for testing purposes.
     *
     * @param x           The x-coordinate of the cell.
     * @param y           The y-coordinate of the cell.
     * @param item        The item to be set in the cell.
     * @param circumstance The circumstance value to be set for the cell.
     */
    public void setCell(int x, int y, Item item, int circumstance) {
        this.disposition[y][x] = new Cell(circumstance);
        disposition[y][x].setContent(item);
    }

    /**
     * setPendingCells is used to set, in pendingCells, max 3 items
     *
     * @param pendingCells: a set of pair(items) that need to be set in pendingCells
     */
    public void setPendingCells(Set<Pair<Integer, Integer>> pendingCells) {
        this.pendingCells = pendingCells;
    }

    /**
     * getPendingCells is used to get a set of pair (x and y of a certain item) which are currently pending
     *
     * @return a set of pair(items)
     */
    public Set<Pair<Integer, Integer>> getPendingCells() {
        return this.pendingCells;
    }

    /**
     * select is used to add in pendingCells an item
     *
     * @param x: row of the selected cell
     * @param y: column of the selected cell
     * @return the current size (after select) of the set of items (pendingCells)
     */
    public int select(int x, int y) {
        boolean sameLineOrColumn = false;
        Pair<Integer, Integer> selectedPair = new Pair<>(x, y);
        int sizePending = pendingCells.size();
        if (adjacencyCheck(x, y) && sizePending < 3) {
            if (pendingCells.isEmpty()) {
                pendingCells.add(selectedPair);
            } else if (sizePending == 1) {
                for (Pair<Integer, Integer> pair : pendingCells) {
                    if ((selectedPair.getX().equals(pair.getX() + 1) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX() - 1) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() + 1)) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() - 1))) {
                        sameLineOrColumn = true;
                    } else {
                        sameLineOrColumn = false;

                    }
                }
            } else {
                List<Pair<Integer, Integer>> newList = new ArrayList<>(pendingCells);
                if ((newList.get(0).getX() > newList.get(1).getX() && newList.get(0).getY() == newList.get(1).getY()) || (newList.get(0).getX() == newList.get(1).getX() && newList.get(0).getY() > newList.get(1).getY())) {
                    int indice1 = 0;
                    int indice2 = 1;
                    Pair<Integer, Integer> pair0 = newList.get(indice1);
                    Pair<Integer, Integer> pair1 = newList.get(indice2);
                    newList.set(indice1, pair1);
                    newList.set(indice2, pair0);
                }
                if (((selectedPair.getX().equals(newList.get(0).getX() + 2) || selectedPair.getX().equals(newList.get(0).getX() - 1)) && selectedPair.getY().equals(newList.get(0).getY()) && (selectedPair.getX().equals(newList.get(1).getX() + 1) || selectedPair.getX().equals(newList.get(1).getX() - 2)) && selectedPair.getY().equals(newList.get(1).getY())) || (selectedPair.getX().equals(newList.get(0).getX()) && (selectedPair.getY().equals(newList.get(0).getY() + 2) || selectedPair.getY().equals(newList.get(0).getY() - 1)) && selectedPair.getX().equals(newList.get(1).getX()) && (selectedPair.getY().equals(newList.get(1).getY() + 1) || selectedPair.getY().equals(newList.get(1).getY() - 2)))) {
                    sameLineOrColumn = true;
                } else {
                    sameLineOrColumn = false;
                }
                Set<Pair<Integer, Integer>> pC = new LinkedHashSet<>();
                pC.addAll(newList);
                setPendingCells(pC);
            }
        }
        if (sameLineOrColumn) {
            pendingCells.add(selectedPair);
        }
        return pendingCells.size();
    }

    /**
     * deselect is used to eliminate an item from pendingCells
     *
     * @param x: row of the selected item
     * @param y: column of the selected item
     * @return the current size (after deselect) of the set of items (pendingCells)
     */
    public int deselect(int x, int y) {
        Pair<Integer, Integer> selectedPair = new Pair<>(x, y);
        boolean contains = false;
        boolean swapped;
        for (Pair<Integer, Integer> tmp : getPendingCells()) {
            if (tmp.getX().equals(selectedPair.getX()) && tmp.getY().equals(selectedPair.getY())) {
                contains = true;
                break;
            }
        }
        if (contains) {
            int size = getPendingCells().size();
            List<Pair<Integer, Integer>> newList = new ArrayList<>();
            for (Pair<Integer, Integer> tmp : getPendingCells()) {
                newList.add(tmp);
            }
            if (size == 1 || size == 2) {
                for (int i = 0; i < size; i++) {
                    if (newList.get(i).getX() == x && newList.get(i).getY() == y) {
                        newList.remove(i);
                        break;
                    }
                }
            } else {
                if (newList.get(0).getX().equals(newList.get(1).getX())) {
                    int pairX = newList.get(0).getX();
                    int[] array = new int[size];
                    for (int i = 0; i < size; i++) {
                        array[i] = newList.get(i).getY();
                    }
                    for (int i = 0; i < size - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < size - i - 1; j++) {
                            if (array[j] > array[j + 1]) {
                                int temp = array[j];
                                array[j] = array[j + 1];
                                array[j + 1] = temp;
                                swapped = true;
                            }
                        }
                        if (!swapped) {
                            break;
                        }
                    }
                    newList.clear();
                    for (int i = 0; i < size; i++) {
                        Pair<Integer, Integer> pair = new Pair<>(pairX, array[i]);
                        newList.add(pair);
                    }
                } else if (newList.get(0).getY().equals(newList.get(1).getY())) {
                    int pairY = newList.get(0).getY();
                    int[] array = new int[size];
                    for (int i = 0; i < size; i++) {
                        array[i] = newList.get(i).getX();
                    }
                    for (int i = 0; i < size - 1; i++) {
                        swapped = false;
                        for (int j = 0; j < size - i - 1; j++) {
                            if (array[j] > array[j + 1]) {
                                int temp = array[j];
                                array[j] = array[j + 1];
                                array[j + 1] = temp;
                                swapped = true;
                            }
                        }
                        if (!swapped) {
                            break;
                        }
                    }
                    newList.clear();
                    for (int i = 0; i < size; i++) {
                        Pair<Integer, Integer> pair = new Pair<>(array[i], pairY);
                        newList.add(pair);
                    }
                }
                for (int i = 0; i < size; i++) {
                    if (newList.get(i).getX() == x && newList.get(i).getY() == y && i != 1) {
                        newList.remove(i);
                        break;
                    }
                }
            }
            Set<Pair<Integer, Integer>> pC = new HashSet<>();
            pC.addAll(newList);
            setPendingCells(pC);
        }
        return pendingCells.size();
    }

    /**
     * fill is used to fill, at the start of any game, all cells on the board
     *
     * @param numPlayer: indicates how many players play the game
     * @param bag:       bag from where the items to be inserted in the cells of the board will be taken
     */
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

    /**
     * removePendingItems is used to clear the list of items which are currently pending
     *
     * @return a list of items that will be set in the shelf
     */
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

    /**
     * needToRefill is used to check if the board needs a refill
     *
     * @return true if every element of the board has 4 free sides, false if not
     */
    public boolean needToRefill() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell c = disposition[i][j];
                if (c.getContent() != null) {
                    if ((i != 0 && disposition[i - 1][j].getContent() != null) ||
                            (j != 0 && disposition[i][j - 1].getContent() != null) ||
                            (i != 8 && disposition[i + 1][j].getContent() != null) ||
                            (j != 8 && disposition[i][j + 1].getContent() != null)
                    ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * adjacencyCheck is used to check if  a certain item can be taken from a cell on the board
     *
     * @param x: row of the cell you want to check
     * @param y: column of the cell you want to check
     * @return true if the selected cell has at least one free side, false if not
     */
    private boolean adjacencyCheck(int x, int y) {
        if (disposition[x][y].getContent() != null) {
            switch (x) {
                case 0:
                    if (disposition[x][y + 1].getContent() != null && disposition[x][y - 1].getContent() != null && disposition[x + 1][y].getContent() != null) {
                        return false;
                    }
                    break;
                case 8:
                    if (disposition[x][y + 1].getContent() != null && disposition[x][y - 1].getContent() != null && disposition[x - 1][y].getContent() != null) {
                        return false;
                    }
                    break;
                default:
                    switch (y) {
                        case 0:
                            if (disposition[x + 1][y].getContent() != null && disposition[x - 1][y].getContent() != null && disposition[x][y + 1].getContent() != null) {
                                return false;
                            }
                            break;
                        case 8:
                            if (disposition[x + 1][y].getContent() != null && disposition[x - 1][y].getContent() != null && disposition[x][y - 1].getContent() != null) {
                                return false;
                            }
                            break;
                        default:

                            if (disposition[x + 1][y].getContent() != null && disposition[x - 1][y].getContent() != null && disposition[x][y + 1].getContent() != null && disposition[x][y - 1].getContent() != null) {
                                return false;

                            }
                            break;
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * printBoard is used to print the board at any time
     */
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

    /**
     * Creates and returns a clone of the board.
     *
     * @return A clone of the board, with the same cell circumstances and item contents.
     */
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

    /**
     * sendToString is used to have a representation of the board as a string
     *
     * @return The string representation of the send board, showing the items and their variants.
     * */
    public String sendToString() {
        StringBuilder board = new StringBuilder(" ");
        boolean selected = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (getDisposition()[j][i].getContent() != null) {
                    for (Pair<Integer, Integer> item : getPendingCells()) {
                        if (item.getX() == j && item.getY() == i) {
                            board.append("#").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                            selected = true;
                            break;
                        }
                    }
                    if (!selected) {
                        switch (getDisposition()[j][i].getContent().getType()) {
                            case BOOK:
                                board.append("W").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                                break;
                            case CAT:
                                board.append("G").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                                break;
                            case FRAME:
                                board.append("B").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                                break;
                            case GAME:
                                board.append("Y").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                                break;
                            case PLANTS:
                                board.append("P").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                                break;
                            case TROPHY:
                                board.append("L").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                                break;
                            default:
                                System.out.println("Error");
                                break;
                        }
                    }
                } else
                    board.append('■').append(" ").append(" ");

                selected = false;
            }
            board.append("\n ");
        }
        System.out.println(board);
        return board.toString();
    }

    /**
     * pendingToString is used to have a representation of pending cells ad a string
     *
     * @return a string representation of the pendindg cells, showing the items and their variants.
     */
    public String pendingToString() {
        StringBuilder pendingStr = new StringBuilder(" ");
        for (Pair<Integer, Integer> p : getPendingCells()) {
            switch (getDisposition()[p.getX()][p.getY()].getContent().getType()) {
                case BOOK:
                    pendingStr.append("W").append(getDisposition()[p.getX()][p.getY()].getContent().getVariant()).append(" ");
                    break;
                case CAT:
                    pendingStr.append("G").append(getDisposition()[p.getX()][p.getY()].getContent().getVariant()).append(" ");
                    break;
                case FRAME:
                    pendingStr.append("B").append(getDisposition()[p.getX()][p.getY()].getContent().getVariant()).append(" ");
                    break;
                case GAME:
                    pendingStr.append("Y").append(getDisposition()[p.getX()][p.getY()].getContent().getVariant()).append(" ");
                    break;
                case PLANTS:
                    pendingStr.append("P").append(getDisposition()[p.getX()][p.getY()].getContent().getVariant()).append(" ");
                    break;
                case TROPHY:
                    pendingStr.append("L").append(getDisposition()[p.getX()][p.getY()].getContent().getVariant()).append(" ");
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        }
        return pendingStr.toString();
    }
}
