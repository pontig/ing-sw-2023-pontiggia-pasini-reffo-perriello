package it.polimi.ingsw.model;

/*import com.fasterxml.jackson.databind.ObjectMapper;*/

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.enums.Type;
import it.polimi.ingsw.tuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
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
                        assertNull(b2.getDisposition()[i][j].getContent());
                        assertNull(b3.getDisposition()[i][j].getContent());
                        assertNull(b4.getDisposition()[i][j].getContent());
                        break;
                    case 2:
                        assertNotNull(b2.getDisposition()[i][j].getContent());
                        assertNotNull(b3.getDisposition()[i][j].getContent());
                        assertNotNull(b4.getDisposition()[i][j].getContent());
                        break;
                    case 3:
                        assertNull(b2.getDisposition()[i][j].getContent());
                        assertNotNull(b3.getDisposition()[i][j].getContent());
                        assertNotNull(b4.getDisposition()[i][j].getContent());
                        break;
                    case 4:
                        assertNull(b2.getDisposition()[i][j].getContent());
                        assertNull(b3.getDisposition()[i][j].getContent());
                        assertNotNull(b4.getDisposition()[i][j].getContent());
                        break;

                }
            }
        }
    }

    @Test
    void removePendingItems() {
        Board testBoard = new Board();
        // create a copy of testBoard
        Board testBoardCopy = new Board();
        testBoardCopy.setDisposition(testBoard.getDisposition());
        testBoard.fill(4, new Bag());
        testBoard.select(0, 4);
        testBoard.select(0, 5);

        testBoard.removePendingItems();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 0 && (j == 4 || j == 5)) {
                    assertNull(testBoard.getDisposition()[i][j]);
                } else {
                    assertEquals(testBoardCopy.getDisposition()[i][j], testBoard.getDisposition()[i][j]);
                }
            }
        }
        assertNull(testBoard.getPendingCells());
    }

    @Test
    void needToRefill() {
        Board testBoard =new Board();
        testBoard.setCell(0,0, null,0);
        testBoard.setCell(1,0, null,0);
        testBoard.setCell(2,0, null,0);
        testBoard.setCell(3,0, null,0);
        testBoard.setCell(4,0, new Item(Type.BOOK, 0),4);
        testBoard.setCell(5,0, new Item(Type.TROPHY, 0),3);
        testBoard.setCell(6,0, null,0);
        testBoard.setCell(7,0, null,0);
        testBoard.setCell(8,0, null,0);

        testBoard.setCell(0,1, null,0);
        testBoard.setCell(1,1, null,0);
        testBoard.setCell(2,1, null,0);
        testBoard.setCell(3,1, new Item(Type.PLANTS, 0),4);
        testBoard.setCell(4,1, new Item(Type.CAT, 0),2);
        testBoard.setCell(5,1, new Item(Type.BOOK, 0),2);
        testBoard.setCell(6,1, null,0);
        testBoard.setCell(7,1, null,0);
        testBoard.setCell(8,1, null,0);

        testBoard.setCell(0,2, null,0);
        testBoard.setCell(1,2, null,0);
        testBoard.setCell(2,2, new Item(Type.PLANTS, 0),3);
        testBoard.setCell(3,2, new Item(Type.CAT, 0),2);
        testBoard.setCell(4,2, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(5,2, new Item(Type.CAT, 0),2);
        testBoard.setCell(6,2, new Item(Type.BOOK, 0),3);
        testBoard.setCell(7,2, null,0);
        testBoard.setCell(8,2, null,0);

        testBoard.setCell(0,3, new Item(Type.GAME, 0),3);
        testBoard.setCell(1,3, new Item(Type.CAT, 0),2);
        testBoard.setCell(2,3, new Item(Type.GAME, 0),2);
        testBoard.setCell(3,3, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(4,3, new Item(Type.FRAME, 0),2);
        testBoard.setCell(5,3, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(6,3, new Item(Type.CAT, 0),2);
        testBoard.setCell(7,3, new Item(Type.BOOK, 0),4);
        testBoard.setCell(8,3, null,0);

        testBoard.setCell(0,4, new Item(Type.GAME, 0),4);
        testBoard.setCell(1,4, new Item(Type.CAT, 0),2);
        testBoard.setCell(2,4, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(3,4, new Item(Type.FRAME, 0),2);
        testBoard.setCell(4,4, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(5,4, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(6,4, new Item(Type.CAT, 0),2);
        testBoard.setCell(7,4, new Item(Type.BOOK, 0),2);
        testBoard.setCell(8,4, new Item(Type.BOOK, 0),4);

        testBoard.setCell(0,5, null,0);
        testBoard.setCell(1,5, new Item(Type.GAME, 0),4);
        testBoard.setCell(2,5, new Item(Type.BOOK, 0),2);
        testBoard.setCell(3,5, new Item(Type.TROPHY, 0),2);
        testBoard.setCell(4,5, new Item(Type.FRAME, 0),2);
        testBoard.setCell(5,5, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(6,5, new Item(Type.CAT, 0),2);
        testBoard.setCell(7,5, new Item(Type.BOOK, 0),2);
        testBoard.setCell(8,5, new Item(Type.BOOK, 0),3);

        testBoard.setCell(0,6, null,0);
        testBoard.setCell(1,6, null,0);
        testBoard.setCell(2,6, new Item(Type.BOOK, 0),3);
        testBoard.setCell(3,6, new Item(Type.GAME, 0),2);
        testBoard.setCell(4,6, new Item(Type.CAT, 0),2);
        testBoard.setCell(5,6, new Item(Type.PLANTS, 0),2);
        testBoard.setCell(6,6, new Item(Type.CAT, 0),3);
        testBoard.setCell(7,6, null,0);
        testBoard.setCell(8,6, null,0);

        testBoard.setCell(0,7, null,0);
        testBoard.setCell(1,7, null,0);
        testBoard.setCell(2,7, null,0);
        testBoard.setCell(3,7, new Item(Type.GAME, 0),2);
        testBoard.setCell(4,7, new Item(Type.CAT, 0),2);
        testBoard.setCell(5,7, new Item(Type.PLANTS, 0),4);
        testBoard.setCell(6,7, null,0);
        testBoard.setCell(7,7, null,0);
        testBoard.setCell(8,7, null,0);

        testBoard.setCell(0,8, null,0);
        testBoard.setCell(1,8, null,0);
        testBoard.setCell(2,8, null,0);
        testBoard.setCell(3,8, new Item(Type.GAME, 0),3);
        testBoard.setCell(4,8, new Item(Type.CAT, 0),4);
        testBoard.setCell(5,8, null,0);
        testBoard.setCell(6,8, null,0);
        testBoard.setCell(7,8, null,0);
        testBoard.setCell(8,8, null,0);

        assertFalse(testBoard.needToRefill());
    }

    @Test
    void setCell() {
    }

}