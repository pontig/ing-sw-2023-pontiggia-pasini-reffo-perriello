package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void setDisposition() {

    }

    @Test
    void getDisposition() {
    }

    @Test
    void setPendingCells() {
    }

    @Test
    void getPendingCells() {
    }

    @Test
    void select() {
    }

    @Test
    void deselect() {
    }

    @Test
    void fill() {
    }

    @Test
    void removePendingItems() {
    }

    @Test
    void needToRefill() {
        Board testBoard =new Board();
        testBoard.setCell(0,0, new Item(null, 0));
    }
}