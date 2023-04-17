package it.polimi.ingsw.model;

public class Cell {
    private Item content;
    private int circumstance;

    public Cell(int circumstance){
        this.content = null;
        this.circumstance = circumstance;
    }

    /** setContent is used to set, on a cell of the board, a certain item
     * @param content: item that will be inserted in the cell
     */
    public void setContent(Item content) {
        this.content = content;
    }

    /** getContent is used to get, from a cell of the board, the corresponding item
     * @return the content of the cell (Item)
     */
    public Item getContent(){return this.content;}

    /** setCircumstance is used to set, on a cell of the board, a certain circumstance
     * @param circumstance: an int (0, 2, 3, 4) which indicates whether that cell will be used based on the number of players
     */
    public void setCircumstance(int circumstance) {
        this.circumstance = circumstance;
    }

    /** getCircumstance is used to get, from a cell of the board, the corresponding circumstance
     * @return an int (0, 2, 3, 4) which indicates if the cell is used based on the number of players
     */
    public int getCircumstance(){return this.circumstance;}
}

