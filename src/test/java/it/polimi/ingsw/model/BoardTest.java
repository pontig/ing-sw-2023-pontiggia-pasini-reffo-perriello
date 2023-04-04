package it.polimi.ingsw.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.tuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    static Board boardToTest;

    @BeforeAll
    static void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //InputStream inputStream = BoardTest.class.getClassLoader().getResourceAsStream("../assets/livingroom.json");
        int[][] board = new int[0][];
        try {
            board = mapper.readValue(new File("src/main/java/it/polimi/ingsw/assets/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boardToTest = new Board(board);

    }


    @Test
    void getDisposition() {
        Cell[][] test = new Cell[9][9];
        Board testBoard = new Board();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Random cells
                test[i][j] = new Cell((i + j) % 5);
            }
        }

        testBoard.setDisposition(test);
        assertEquals(test, testBoard.getDisposition());
    }

    @Test
    void getPendingCells() {
        Pair<Integer, Integer> a = new Pair<>(1, 2);
        Pair<Integer, Integer> b = new Pair<>(1, 3);
        Pair<Integer, Integer> c = new Pair<>(1, 4);

        Set<Pair<Integer, Integer>> testSet = new HashSet();
        testSet.add(a);
        testSet.add(b);
        testSet.add(c);

        Board testBoard = new Board();
        testBoard.setPendingCells(testSet);
        assertEquals(testSet, testBoard.getPendingCells());
    }

    @Test
    void select() {

    }

    @Test
    void deselect() {
    }

    @Test
    void fill() {

        Board b2 = new Board();
        Board b3 = new Board();
        Board b4 = new Board();

        b2.fill(2, new Bag());
        b3.fill(3, new Bag());
        b4.fill(4, new Bag());

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Since the disposition is the same for every board, we can use the same switch on b2
                switch (b2.getDisposition()[i][j].getCircumstance()) {
                    case 0:
                        assertNull(b2.getDisposition()[i][j]);
                        assertNull(b3.getDisposition()[i][j]);
                        assertNull(b4.getDisposition()[i][j]);
                        break;
                    case 2:
                        assertNotNull(b2.getDisposition()[i][j]);
                        assertNull(b3.getDisposition()[i][j]);
                        assertNull(b4.getDisposition()[i][j]);
                        break;
                    case 3:
                        assertNotNull(b2.getDisposition()[i][j]);
                        assertNotNull(b3.getDisposition()[i][j]);
                        assertNull(b4.getDisposition()[i][j]);
                        break;
                    case 4:
                        assertNotNull(b2.getDisposition()[i][j]);
                        assertNotNull(b3.getDisposition()[i][j]);
                        assertNotNull(b4.getDisposition()[i][j]);
                        break;
                }
            }
        }
    }

    @Test
    void removePendingItems() {

    }

    @Test
    void needToRefill() {
        Board testBoard = new Board();
        testBoard.setCell(0, 0, new Item(null, 0));
    }

    @Test
    void testSetDisposition() {
    }

    @Test
    void testGetDisposition() {
    }

    @Test
    void setCell() {
    }

    @Test
    void testSetPendingCells() {
    }

    @Test
    void testGetPendingCells() {
    }

    @Test
    void testSelect() {
    }

    @Test
    void testDeselect() {
    }

    @Test
    void testFill() {
    }

    @Test
    void testRemovePendingItems() {
    }

    @Test
    void testNeedToRefill() {
    }
}