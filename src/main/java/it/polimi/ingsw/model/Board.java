package it.polimi.ingsw.model;

import it.polimi.ingsw.tuples.Pair;

import java.util.*;

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

    /** setDisposition is used to set, in every cell of the board, an item
     * @param disposition: a matrix of Cell that need to be filled
     */
    public void setDisposition(Cell[][] disposition) {
        this.disposition = disposition;
    }

    /** getDisposition is used to get the disposition of a certain cell from the board
     * @return a matrix of cell
     */
    public Cell[][] getDisposition() {
        return this.disposition;
    }

    public void setCell(int x, int y, Item item, int circumstance) {
        // WARNING: Must be used only for testing purposes
        this.disposition[y][x] = new Cell(circumstance);
        disposition[y][x].setContent(item);
    }

    /** setPendingCells is used to set, in pendingCells, max 3 items
     * @param pendingCells: a set of pair(items) that need to be set in pendingCells
     */
    public void setPendingCells(Set<Pair<Integer, Integer>> pendingCells) {
        this.pendingCells = pendingCells;
    }

    /** getPendingCells is used to get a set of pair (x and y of a certain item) which are currently pending
     * @return a set of pair(items)
     */
    public Set<Pair<Integer, Integer>> getPendingCells() {
        return this.pendingCells;
    }

    /** select is used to add in pendingCells an item
     * @param x: row of the selected cell
     * @param y: column of the selected cell
     * @return the current size (after select) of the set of items (pendingCells)
     */
    public int select(int x, int y) {
        boolean sameLineOrColumn = false;
        Pair<Integer, Integer> selectedPair = new Pair<>(x, y);
        int sizePending = pendingCells.size();
        if(adjacencyCheck(x, y) && sizePending < 3) {
            if(pendingCells.isEmpty()){
                pendingCells.add(selectedPair);
            }else for(Pair<Integer, Integer> pair : pendingCells){
                if(sizePending == 1){
                    if((selectedPair.getX().equals(pair.getX() + 1) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX() - 1) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() + 1)) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() - 1))){
                        sameLineOrColumn = true;
                    } else {
                        sameLineOrColumn = false;
                    }
                }else{
                    if((selectedPair.getX().equals(pair.getX() + 1) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX() - 1) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() + 1)) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() - 1)) || (selectedPair.getX().equals(pair.getX() + 2) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX() - 2) && selectedPair.getY().equals(pair.getY())) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() + 2)) || (selectedPair.getX().equals(pair.getX()) && selectedPair.getY().equals(pair.getY() - 2))){
                        sameLineOrColumn = true;
                    } else {
                        sameLineOrColumn = false;
                    }
                }
            }
        }
        if (sameLineOrColumn) {
            pendingCells.add(selectedPair);
        }
        return pendingCells.size();
    }

    /** deselect is used to eliminate an item from pendingCells
     * @param x: row of the selected item
     * @param y: column of the selected item
     * @return the current size (after deselect) of the set of items (pendingCells)
     */
    public int deselect(int x, int y) {
        Pair<Integer, Integer> selectedPair = new Pair<>(x, y);
        boolean contains = false;
        for(Pair<Integer, Integer> tmp : getPendingCells()){
            if(tmp.getX().equals(selectedPair.getX()) && tmp.getY().equals(selectedPair.getY())){
                contains = true;
                break;
            }
        }
        if (contains) {
            int size = getPendingCells().size();
            List<Pair<Integer, Integer>> newList = new ArrayList<>();
            for(Pair<Integer, Integer> tmp : getPendingCells())
                newList.add(tmp);

            for(int i = 0; i < size; i++){
                if(newList.get(i).getX() == x && newList.get(i).getY() == y){
                    newList.remove(i);
                    break;
                }
            }
            Set<Pair<Integer, Integer>> pC = new HashSet<>();
            pC.addAll(newList);
            setPendingCells(pC);
        }
        return pendingCells.size();
    }

    /** fill is used to fill, at the start of any game, all cells on the board
     * @param numPlayer: indicates how many players play the game
     * @param bag: bag from where the items to be inserted in the cells of the board will be taken
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

    /** removePendingItems is used to clear the list of items which are currently pending
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

    /** needToRefill is used to check if the board needs a refill
     * @return true if at least one element of the board has 4 free sides, false if not
     */
    public boolean needToRefill() {
        boolean refill = false;
        boolean emptyBoard = true;
        int row = -1;
        int column = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(disposition[j][i].getContent() != null){
                    emptyBoard = false;
                    break;
                }
            }
        }
        if(emptyBoard){
            return true;
        }
        else{
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (disposition[j][i].getContent() != null) {
                        switch (i) {
                            case 0:
                                if (disposition[j + 1][i].getContent() == null && disposition[j][i + 1].getContent() == null && disposition[j - 1][i].getContent() == null) {
                                    refill = true;
                                    row = i;
                                    column = j;
                                }
                                break;
                            case 8:
                                if (disposition[j][i - 1].getContent() == null && disposition[j + 1][i].getContent() == null && disposition[j - 1][i].getContent() == null) {
                                    refill = true;
                                    row = i;
                                    column = j;
                                }
                                break;
                            default:
                                switch (j) {
                                    case 0:
                                        if (disposition[j][i + 1].getContent() == null && disposition[j][i - 1].getContent() == null && disposition[j + 1][i].getContent() == null) {
                                            refill = true;
                                            row = i;
                                            column = j;
                                        }
                                        break;
                                    case 8:
                                        if (disposition[j][i + 1].getContent() == null && disposition[j][i - 1].getContent() == null && disposition[j - 1][i].getContent() == null) {
                                            refill = true;
                                            row = i;
                                            column = j;
                                        }
                                        break;
                                    default:
                                        if (disposition[j][i + 1].getContent() == null && disposition[j][i - 1].getContent() == null && disposition[j + 1][i].getContent() == null && disposition[j - 1][i].getContent() == null) {
                                            refill = true;
                                            row = i;
                                            column = j;
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                }
            }
            if (refill) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if(disposition[j][i].getContent() != null){
                            switch(i){
                                case 0:
                                    if(adjacencyCheck(i, j) && adjacencyCheck(i+1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j+1) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j-1) && i != row && j != column){
                                        return false;
                                    }
                                    break;
                                case 8:
                                    if(adjacencyCheck(i, j) && adjacencyCheck(i-1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j+1) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j-1) && i != row && j != column){
                                        return false;
                                    }
                                    break;
                                default:
                                    switch(j){
                                        case 0:
                                            if(adjacencyCheck(i, j) && adjacencyCheck(i+1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i-1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j+1) && i != row && j != column){
                                                return false;
                                            }
                                            break;
                                        case 8:
                                            if(adjacencyCheck(i, j) && adjacencyCheck(i+1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i-1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j-1) && i != row && j != column){
                                                return false;
                                            }
                                            break;
                                        default:
                                            if(adjacencyCheck(i, j) && adjacencyCheck(i+1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i-1, j) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j+1) && i != row && j != column || adjacencyCheck(i, j) && adjacencyCheck(i, j-1) && i != row && j != column){
                                                return false;
                                            }
                                            break;
                                    }
                                break;
                            }
                        }

                    }
                }
                return true;
            }
            else{
                return false;
            }
        }
    }


    /** adjacencyCheck is used to check if  a certain item can be taken from a cell on the board
     * @param x: row of the cell you want to check
     * @param y: column of the cell you want to check
     * @return true if the selected cell has at least one free side, false if not
     */
    private boolean adjacencyCheck(int x, int y) {
        if (disposition[x][y].getContent() != null) {
            switch (x) {
                case 0:
                    if(disposition[x][y+1].getContent() != null && disposition[x][y-1].getContent() != null && disposition[x+1][y].getContent() != null){
                        return false;
                    }
                    break;
                case 8:
                    if(disposition[x][y+1].getContent() != null && disposition[x][y-1].getContent() != null && disposition[x-1][y].getContent() != null){
                        return false;
                    }
                    break;
                default:
                    switch (y) {
                        case 0:
                            if(disposition[x+1][y].getContent() != null && disposition[x-1][y].getContent() != null && disposition[x][y+1].getContent() != null){
                                return false;
                            }
                            break;
                        case 8:
                            if(disposition[x+1][y].getContent() != null && disposition[x-1][y].getContent() != null && disposition[x][y-1].getContent() != null){
                                return false;
                            }
                            break;
                        default:

                            if (disposition[x+1][y].getContent() != null && disposition[x-1][y].getContent() != null && disposition[x][y+1].getContent() != null && disposition[x][y-1].getContent() != null) {
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

    /** printBoard is used to print the board at any time
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

    public String toString(){
        StringBuilder board = new StringBuilder(" ");
        boolean selected = false;
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (getDisposition()[j][i].getContent() != null) {
                    for(Pair<Integer, Integer> item: getPendingCells()){
                        if(item.getX() == j && item.getY() == i) {
                            board.append("#").append(getDisposition()[j][i].getContent().getVariant()).append(" ");
                            selected = true;
                            break;
                        }
                    }
                    if(!selected) {
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
        return board.toString();
    }

    public String pendingToString(){
        StringBuilder pendingStr = new StringBuilder(" ");
        for(Pair<Integer, Integer> p: getPendingCells()){
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
