package it.polimi.ingsw.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.tuples.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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

        Set<Pair<Integer, Integer>> testPendingCells1 = new HashSet<>();                           // 0 elements in pendingCells
        Board testBoard1 = new Board();
        testBoard1.setCell(0, 0, null, 0);
        testBoard1.setCell(1, 0, null, 0);
        testBoard1.setCell(2, 0, null, 0);
        testBoard1.setCell(3, 0, null, 0);
        testBoard1.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard1.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard1.setCell(6, 0, null, 0);
        testBoard1.setCell(7, 0, null, 0);
        testBoard1.setCell(8, 0, null, 0);
        testBoard1.setCell(0, 1, null, 0);
        testBoard1.setCell(1, 1, null, 0);
        testBoard1.setCell(2, 1, null, 0);
        testBoard1.setCell(3, 1, new Item(Type.PLANTS, 0), 4);
        testBoard1.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(6, 1, null, 0);
        testBoard1.setCell(7, 1, null, 0);
        testBoard1.setCell(8, 1, null, 0);
        testBoard1.setCell(0, 2, null, 0);
        testBoard1.setCell(1, 2, null, 0);
        testBoard1.setCell(2, 2, new Item(Type.PLANTS, 0), 3);
        testBoard1.setCell(3, 2, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard1.setCell(7, 2, null, 0);
        testBoard1.setCell(8, 2, null, 0);
        testBoard1.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard1.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard1.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard1.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard1.setCell(8, 3, null, 0);
        testBoard1.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard1.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard1.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard1.setCell(0, 5, null, 0);
        testBoard1.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard1.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard1.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard1.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard1.setCell(0, 6, null, 0);
        testBoard1.setCell(1, 6, null, 0);
        testBoard1.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard1.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard1.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard1.setCell(7, 6, null, 0);
        testBoard1.setCell(8, 6, null, 0);
        testBoard1.setCell(0, 7, null, 0);
        testBoard1.setCell(1, 7, null, 0);
        testBoard1.setCell(2, 7, null, 0);
        testBoard1.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard1.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard1.setCell(6, 7, null, 0);
        testBoard1.setCell(7, 7, null, 0);
        testBoard1.setCell(8, 7, null, 0);
        testBoard1.setCell(0, 8, null, 0);
        testBoard1.setCell(1, 8, null, 0);
        testBoard1.setCell(2, 8, null, 0);
        testBoard1.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard1.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard1.setCell(5, 8, null, 0);
        testBoard1.setCell(6, 8, null, 0);
        testBoard1.setCell(7, 8, null, 0);
        testBoard1.setCell(8, 8, null, 0);

        testBoard1.setPendingCells(testPendingCells1);
        int sizeTestPendingCells1 = testBoard1.select(4,0);
        Pair<Integer, Integer> a1 = new Pair<>(4,0);
        for(Pair<Integer, Integer> pair : testPendingCells1){
            if(pair.getX().equals(a1.getX()) && pair.getY().equals(a1.getY())){
                boolean checkPair1 = true;
                assertTrue(checkPair1);
            }
        }
        assertEquals(1, sizeTestPendingCells1);

        Set<Pair<Integer, Integer>> testPendingCells2 = new HashSet<>();                          // 1 element in pendingCells
        Pair<Integer, Integer> a2 = new Pair<>(4,0);
        testPendingCells2.add(a2);
        Board testBoard2 = new Board();
        testBoard2.setCell(0, 0, null, 0);
        testBoard2.setCell(1, 0, null, 0);
        testBoard2.setCell(2, 0, null, 0);
        testBoard2.setCell(3, 0, null, 0);
        testBoard2.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard2.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard2.setCell(6, 0, null, 0);
        testBoard2.setCell(7, 0, null, 0);
        testBoard2.setCell(8, 0, null, 0);
        testBoard2.setCell(0, 1, null, 0);
        testBoard2.setCell(1, 1, null, 0);
        testBoard2.setCell(2, 1, null, 0);
        testBoard2.setCell(3, 1, new Item(Type.PLANTS, 0), 4);
        testBoard2.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard2.setCell(6, 1, null, 0);
        testBoard2.setCell(7, 1, null, 0);
        testBoard2.setCell(8, 1, null, 0);
        testBoard2.setCell(0, 2, null, 0);
        testBoard2.setCell(1, 2, null, 0);
        testBoard2.setCell(2, 2, new Item(Type.PLANTS, 0), 3);
        testBoard2.setCell(3, 2, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard2.setCell(7, 2, null, 0);
        testBoard2.setCell(8, 2, null, 0);
        testBoard2.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard2.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard2.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard2.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard2.setCell(8, 3, null, 0);
        testBoard2.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard2.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard2.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard2.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard2.setCell(0, 5, null, 0);
        testBoard2.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard2.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard2.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard2.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard2.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard2.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard2.setCell(0, 6, null, 0);
        testBoard2.setCell(1, 6, null, 0);
        testBoard2.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard2.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard2.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard2.setCell(7, 6, null, 0);
        testBoard2.setCell(8, 6, null, 0);
        testBoard2.setCell(0, 7, null, 0);
        testBoard2.setCell(1, 7, null, 0);
        testBoard2.setCell(2, 7, null, 0);
        testBoard2.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard2.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard2.setCell(6, 7, null, 0);
        testBoard2.setCell(7, 7, null, 0);
        testBoard2.setCell(8, 7, null, 0);
        testBoard2.setCell(0, 8, null, 0);
        testBoard2.setCell(1, 8, null, 0);
        testBoard2.setCell(2, 8, null, 0);
        testBoard2.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard2.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard2.setCell(5, 8, null, 0);
        testBoard2.setCell(6, 8, null, 0);
        testBoard2.setCell(7, 8, null, 0);
        testBoard2.setCell(8, 8, null, 0);

        testBoard2.setPendingCells(testPendingCells2);
        int sizeTestPendingCells2 = testBoard2.select(5,0);
        Pair<Integer, Integer> b2 = new Pair<>(5,0);
        for(Pair<Integer, Integer> pair : testPendingCells2){
            if(pair.getX().equals(b2.getX()) && pair.getY().equals(b2.getY())){
                boolean checkPair2 = true;
                assertTrue(checkPair2);
            }
        }
        assertEquals(2, sizeTestPendingCells2);

        Set<Pair<Integer, Integer>> testPendingCells3 = new HashSet<>();                          // 2 elements in pendingCells
        Pair<Integer, Integer> a3 = new Pair<>(4,0);
        Pair<Integer, Integer> b3 = new Pair<>(4,1);
        testPendingCells3.add(a3);
        testPendingCells3.add(b3);
        Board testBoard3 = new Board();
        testBoard3.setCell(0, 0, null, 0);
        testBoard3.setCell(1, 0, null, 0);
        testBoard3.setCell(2, 0, null, 0);
        testBoard3.setCell(3, 0, null, 0);
        testBoard3.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard3.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard3.setCell(6, 0, null, 0);
        testBoard3.setCell(7, 0, null, 0);
        testBoard3.setCell(8, 0, null, 0);
        testBoard3.setCell(0, 1, null, 0);
        testBoard3.setCell(1, 1, null, 0);
        testBoard3.setCell(2, 1, null, 0);
        testBoard3.setCell(3, 1, null, 4);
        testBoard3.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(6, 1, null, 0);
        testBoard3.setCell(7, 1, null, 0);
        testBoard3.setCell(8, 1, null, 0);
        testBoard3.setCell(0, 2, null, 0);
        testBoard3.setCell(1, 2, null, 0);
        testBoard3.setCell(2, 2, null, 3);
        testBoard3.setCell(3, 2, null, 2);
        testBoard3.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard3.setCell(7, 2, null, 0);
        testBoard3.setCell(8, 2, null, 0);
        testBoard3.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard3.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard3.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard3.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard3.setCell(8, 3, null, 0);
        testBoard3.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard3.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard3.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard3.setCell(0, 5, null, 0);
        testBoard3.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard3.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard3.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard3.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard3.setCell(0, 6, null, 0);
        testBoard3.setCell(1, 6, null, 0);
        testBoard3.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard3.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard3.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard3.setCell(7, 6, null, 0);
        testBoard3.setCell(8, 6, null, 0);
        testBoard3.setCell(0, 7, null, 0);
        testBoard3.setCell(1, 7, null, 0);
        testBoard3.setCell(2, 7, null, 0);
        testBoard3.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard3.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard3.setCell(6, 7, null, 0);
        testBoard3.setCell(7, 7, null, 0);
        testBoard3.setCell(8, 7, null, 0);
        testBoard3.setCell(0, 8, null, 0);
        testBoard3.setCell(1, 8, null, 0);
        testBoard3.setCell(2, 8, null, 0);
        testBoard3.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard3.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard3.setCell(5, 8, null, 0);
        testBoard3.setCell(6, 8, null, 0);
        testBoard3.setCell(7, 8, null, 0);
        testBoard3.setCell(8, 8, null, 0);

        testBoard3.setPendingCells(testPendingCells3);
        int sizeTestPendingCells3 = testBoard3.select(4,2);
        Pair<Integer, Integer> c3 = new Pair<>(4,2);
        for(Pair<Integer, Integer> pair : testPendingCells3){
            if(pair.getX().equals(c3.getX()) && pair.getY().equals(c3.getY())){
                boolean checkPair3 = true;
                assertTrue(checkPair3);
            }
        }
        assertEquals(3, sizeTestPendingCells3);

        Set<Pair<Integer, Integer>> testPendingCells4 = new HashSet<>();                          // 1 element in pendingCells and the second element rejected
        Pair<Integer, Integer> a4 = new Pair<>(4,0);
        testPendingCells4.add(a4);
        Board testBoard4 = new Board();
        testBoard4.setCell(0, 0, null, 0);
        testBoard4.setCell(1, 0, null, 0);
        testBoard4.setCell(2, 0, null, 0);
        testBoard4.setCell(3, 0, null, 0);
        testBoard4.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard4.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard4.setCell(6, 0, null, 0);
        testBoard4.setCell(7, 0, null, 0);
        testBoard4.setCell(8, 0, null, 0);
        testBoard4.setCell(0, 1, null, 0);
        testBoard4.setCell(1, 1, null, 0);
        testBoard4.setCell(2, 1, null, 0);
        testBoard4.setCell(3, 1, null, 4);
        testBoard4.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(6, 1, null, 0);
        testBoard4.setCell(7, 1, null, 0);
        testBoard4.setCell(8, 1, null, 0);
        testBoard4.setCell(0, 2, null, 0);
        testBoard4.setCell(1, 2, null, 0);
        testBoard4.setCell(2, 2, null, 3);
        testBoard4.setCell(3, 2, null, 2);
        testBoard4.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard4.setCell(7, 2, null, 0);
        testBoard4.setCell(8, 2, null, 0);
        testBoard4.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard4.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard4.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard4.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard4.setCell(8, 3, null, 0);
        testBoard4.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard4.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard4.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard4.setCell(0, 5, null, 0);
        testBoard4.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard4.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard4.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard4.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard4.setCell(0, 6, null, 0);
        testBoard4.setCell(1, 6, null, 0);
        testBoard4.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard4.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard4.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard4.setCell(7, 6, null, 0);
        testBoard4.setCell(8, 6, null, 0);
        testBoard4.setCell(0, 7, null, 0);
        testBoard4.setCell(1, 7, null, 0);
        testBoard4.setCell(2, 7, null, 0);
        testBoard4.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard4.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard4.setCell(6, 7, null, 0);
        testBoard4.setCell(7, 7, null, 0);
        testBoard4.setCell(8, 7, null, 0);
        testBoard4.setCell(0, 8, null, 0);
        testBoard4.setCell(1, 8, null, 0);
        testBoard4.setCell(2, 8, null, 0);
        testBoard4.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard4.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard4.setCell(5, 8, null, 0);
        testBoard4.setCell(6, 8, null, 0);
        testBoard4.setCell(7, 8, null, 0);
        testBoard4.setCell(8, 8, null, 0);

        testBoard4.setPendingCells(testPendingCells4);
        int sizeTestPendingCells4 = testBoard4.select(5,1);
        Pair<Integer, Integer> c4 = new Pair<>(5,1);
        boolean checkPair4 = false;
        for(Pair<Integer, Integer> pair : testPendingCells1){
            if(pair.getX().equals(c4.getX()) && pair.getY().equals(c4.getY())){
                checkPair4 = true;
            }
        }
        assertFalse(checkPair4);
        assertEquals(1, sizeTestPendingCells4);
    }

    @Test
    void deselect() {
        Set<Pair<Integer, Integer>> testPendingCells1 = new HashSet<>();                // 1 element in pendingCells

        Pair<Integer, Integer> a1 = new Pair<>(4, 0);
        testPendingCells1.add(a1);

        Board testBoard1 = new Board();
        testBoard1.setPendingCells(testPendingCells1);
        int sizeTestPendingCells1 = testBoard1.deselect(4,0);
        Pair<Integer, Integer> c1 = new Pair<>(4, 0);

        assertFalse(testPendingCells1.contains(c1));
        assertEquals(0, sizeTestPendingCells1);

        Set<Pair<Integer, Integer>> testPendingCells2 = new HashSet<>();                // 2 elements in pendingCells

        Pair<Integer, Integer> a2 = new Pair<>(4, 0);
        Pair<Integer, Integer> b2 = new Pair<>(5, 0);
        testPendingCells2.add(a2);
        testPendingCells2.add(b2);

        Board testBoard2 = new Board();
        testBoard2.setPendingCells(testPendingCells2);
        int sizeTestPendingCells2 = testBoard2.deselect(5,0);
        Pair<Integer, Integer> c2 = new Pair<>(5, 0);

        assertFalse(testPendingCells2.contains(c2));
        assertEquals(1, sizeTestPendingCells2);

        Set<Pair<Integer, Integer>> testPendingCells3 = new HashSet<>();              // 3 elements in pendingCells

        Pair<Integer, Integer> a3 = new Pair<>(4, 0);
        Pair<Integer, Integer> b3 = new Pair<>(5, 0);
        Pair<Integer, Integer> c3 = new Pair<>(4, 1);
        testPendingCells3.add(a3);
        testPendingCells3.add(b3);
        testPendingCells3.add(c3);

        Board testBoard3 = new Board();
        testBoard3.setPendingCells(testPendingCells3);
        int sizeTestPendingCells3 = testBoard3.deselect(4,1);
        Pair<Integer, Integer> d3 = new Pair<>(4, 1);

        assertFalse(testPendingCells3.contains(d3));
        assertEquals(2, sizeTestPendingCells3);
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
    void testClone() {
        Board testBoard = new Board();
        testBoard.fill(3, new Bag());
        Board testBoardCopy = testBoard.clone();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testBoard.getDisposition()[i][j].getContent(), testBoardCopy.getDisposition()[i][j].getContent());
            }
        }
    }

    @Test
    void removePendingItems() {
        Board testBoard = new Board();
        testBoard.fill(4, new Bag());
        Board testBoardCopy = testBoard.clone();

        int ret1 = testBoard.select(0, 4);
        assertEquals(1, ret1);
        int ret2 = testBoard.select(0, 5);
        assertEquals(2, ret2);

        List<Item> l = testBoard.removePendingItems();

        assertTrue(l.contains(testBoardCopy.getDisposition()[0][4].getContent()));
        assertTrue(l.contains(testBoardCopy.getDisposition()[0][5].getContent()));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 0 && (j == 4 || j == 5)) {
                    assertNull(testBoard.getDisposition()[i][j].getContent());
                } else {
                    assertEquals(testBoardCopy.getDisposition()[i][j].getContent(), testBoard.getDisposition()[i][j].getContent());
                }
            }
        }
        assertTrue(testBoard.getPendingCells().isEmpty());
    }

    @Test
    void needToRefill() {
        Board testBoard1 = new Board();                                                   // full board
        testBoard1.setCell(0, 0, null, 0);
        testBoard1.setCell(1, 0, null, 0);
        testBoard1.setCell(2, 0, null, 0);
        testBoard1.setCell(3, 0, null, 0);
        testBoard1.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard1.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard1.setCell(6, 0, null, 0);
        testBoard1.setCell(7, 0, null, 0);
        testBoard1.setCell(8, 0, null, 0);
        testBoard1.setCell(0, 1, null, 0);
        testBoard1.setCell(1, 1, null, 0);
        testBoard1.setCell(2, 1, null, 0);
        testBoard1.setCell(3, 1, new Item(Type.PLANTS, 0), 4);
        testBoard1.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(6, 1, null, 0);
        testBoard1.setCell(7, 1, null, 0);
        testBoard1.setCell(8, 1, null, 0);
        testBoard1.setCell(0, 2, null, 0);
        testBoard1.setCell(1, 2, null, 0);
        testBoard1.setCell(2, 2, new Item(Type.PLANTS, 0), 3);
        testBoard1.setCell(3, 2, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard1.setCell(7, 2, null, 0);
        testBoard1.setCell(8, 2, null, 0);
        testBoard1.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard1.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard1.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard1.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard1.setCell(8, 3, null, 0);
        testBoard1.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard1.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard1.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard1.setCell(0, 5, null, 0);
        testBoard1.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard1.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard1.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard1.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard1.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard1.setCell(0, 6, null, 0);
        testBoard1.setCell(1, 6, null, 0);
        testBoard1.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard1.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard1.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard1.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard1.setCell(7, 6, null, 0);
        testBoard1.setCell(8, 6, null, 0);
        testBoard1.setCell(0, 7, null, 0);
        testBoard1.setCell(1, 7, null, 0);
        testBoard1.setCell(2, 7, null, 0);
        testBoard1.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard1.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard1.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard1.setCell(6, 7, null, 0);
        testBoard1.setCell(7, 7, null, 0);
        testBoard1.setCell(8, 7, null, 0);
        testBoard1.setCell(0, 8, null, 0);
        testBoard1.setCell(1, 8, null, 0);
        testBoard1.setCell(2, 8, null, 0);
        testBoard1.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard1.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard1.setCell(5, 8, null, 0);
        testBoard1.setCell(6, 8, null, 0);
        testBoard1.setCell(7, 8, null, 0);
        testBoard1.setCell(8, 8, null, 0);

        assertFalse(testBoard1.needToRefill());

        Board testBoard2 = new Board();                                                       // refill triggered by an element on the first row
        testBoard2.setCell(0, 0, null, 0);
        testBoard2.setCell(1, 0, null, 0);
        testBoard2.setCell(2, 0, null, 0);
        testBoard2.setCell(3, 0, null, 0);
        testBoard2.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard2.setCell(5, 0, null, 3);
        testBoard2.setCell(6, 0, null, 0);
        testBoard2.setCell(7, 0, null, 0);
        testBoard2.setCell(8, 0, null, 0);
        testBoard2.setCell(0, 1, null, 0);
        testBoard2.setCell(1, 1, null, 0);
        testBoard2.setCell(2, 1, null, 0);
        testBoard2.setCell(3, 1, null, 4);
        testBoard2.setCell(4, 1, null, 2);
        testBoard2.setCell(5, 1, null, 2);
        testBoard2.setCell(6, 1, null, 0);
        testBoard2.setCell(7, 1, null, 0);
        testBoard2.setCell(8, 1, null, 0);
        testBoard2.setCell(0, 2, null, 0);
        testBoard2.setCell(1, 2, null, 0);
        testBoard2.setCell(2, 2, new Item(Type.PLANTS, 0), 3);
        testBoard2.setCell(3, 2, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard2.setCell(7, 2, null, 0);
        testBoard2.setCell(8, 2, null, 0);
        testBoard2.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard2.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard2.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard2.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard2.setCell(8, 3, null, 0);
        testBoard2.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard2.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard2.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard2.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard2.setCell(0, 5, null, 0);
        testBoard2.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard2.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard2.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard2.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard2.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard2.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard2.setCell(0, 6, null, 0);
        testBoard2.setCell(1, 6, null, 0);
        testBoard2.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard2.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard2.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard2.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard2.setCell(7, 6, null, 0);
        testBoard2.setCell(8, 6, null, 0);
        testBoard2.setCell(0, 7, null, 0);
        testBoard2.setCell(1, 7, null, 0);
        testBoard2.setCell(2, 7, null, 0);
        testBoard2.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard2.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard2.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard2.setCell(6, 7, null, 0);
        testBoard2.setCell(7, 7, null, 0);
        testBoard2.setCell(8, 7, null, 0);
        testBoard2.setCell(0, 8, null, 0);
        testBoard2.setCell(1, 8, null, 0);
        testBoard2.setCell(2, 8, null, 0);
        testBoard2.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard2.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard2.setCell(5, 8, null, 0);
        testBoard2.setCell(6, 8, null, 0);
        testBoard2.setCell(7, 8, null, 0);
        testBoard2.setCell(8, 8, null, 0);

        assertTrue(testBoard2.needToRefill());

        Board testBoard3 = new Board();                                                   // refill triggered by an element on the last row
        testBoard3.setCell(0, 0, null, 0);
        testBoard3.setCell(1, 0, null, 0);
        testBoard3.setCell(2, 0, null, 0);
        testBoard3.setCell(3, 0, null, 0);
        testBoard3.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard3.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard3.setCell(6, 0, null, 0);
        testBoard3.setCell(7, 0, null, 0);
        testBoard3.setCell(8, 0, null, 0);
        testBoard3.setCell(0, 1, null, 0);
        testBoard3.setCell(1, 1, null, 0);
        testBoard3.setCell(2, 1, null, 0);
        testBoard3.setCell(3, 1, new Item(Type.PLANTS, 0), 4);
        testBoard3.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(6, 1, null, 0);
        testBoard3.setCell(7, 1, null, 0);
        testBoard3.setCell(8, 1, null, 0);
        testBoard3.setCell(0, 2, null, 0);
        testBoard3.setCell(1, 2, null, 0);
        testBoard3.setCell(2, 2, new Item(Type.PLANTS, 0), 3);
        testBoard3.setCell(3, 2, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard3.setCell(7, 2, null, 0);
        testBoard3.setCell(8, 2, null, 0);
        testBoard3.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard3.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard3.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard3.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard3.setCell(8, 3, null, 0);
        testBoard3.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard3.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard3.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard3.setCell(0, 5, null, 0);
        testBoard3.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard3.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard3.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard3.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard3.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard3.setCell(0, 6, null, 0);
        testBoard3.setCell(1, 6, null, 0);
        testBoard3.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard3.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard3.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard3.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard3.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard3.setCell(7, 6, null, 0);
        testBoard3.setCell(8, 6, null, 0);
        testBoard3.setCell(0, 7, null, 0);
        testBoard3.setCell(1, 7, null, 0);
        testBoard3.setCell(2, 7, null, 0);
        testBoard3.setCell(3, 7, null, 2);
        testBoard3.setCell(4, 7, null, 2);
        testBoard3.setCell(5, 7, null, 4);
        testBoard3.setCell(6, 7, null, 0);
        testBoard3.setCell(7, 7, null, 0);
        testBoard3.setCell(8, 7, null, 0);
        testBoard3.setCell(0, 8, null, 0);
        testBoard3.setCell(1, 8, null, 0);
        testBoard3.setCell(2, 8, null, 0);
        testBoard3.setCell(3, 8, null, 3);
        testBoard3.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard3.setCell(5, 8, null, 0);
        testBoard3.setCell(6, 8, null, 0);
        testBoard3.setCell(7, 8, null, 0);
        testBoard3.setCell(8, 8, null, 0);

        assertTrue(testBoard3.needToRefill());

        Board testBoard4 = new Board();                                                   // refill triggered by an element on the first column
        testBoard4.setCell(0, 0, null, 0);
        testBoard4.setCell(1, 0, null, 0);
        testBoard4.setCell(2, 0, null, 0);
        testBoard4.setCell(3, 0, null, 0);
        testBoard4.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard4.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard4.setCell(6, 0, null, 0);
        testBoard4.setCell(7, 0, null, 0);
        testBoard4.setCell(8, 0, null, 0);
        testBoard4.setCell(0, 1, null, 0);
        testBoard4.setCell(1, 1, null, 0);
        testBoard4.setCell(2, 1, null, 0);
        testBoard4.setCell(3, 1, new Item(Type.PLANTS, 0), 4);
        testBoard4.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(6, 1, null, 0);
        testBoard4.setCell(7, 1, null, 0);
        testBoard4.setCell(8, 1, null, 0);
        testBoard4.setCell(0, 2, null, 0);
        testBoard4.setCell(1, 2, null, 0);
        testBoard4.setCell(2, 2, new Item(Type.PLANTS, 0), 3);
        testBoard4.setCell(3, 2, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard4.setCell(7, 2, null, 0);
        testBoard4.setCell(8, 2, null, 0);
        testBoard4.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard4.setCell(1, 3, null, 2);
        testBoard4.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard4.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard4.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard4.setCell(8, 3, null, 0);
        testBoard4.setCell(0, 4, null, 4);
        testBoard4.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard4.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(7, 4, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard4.setCell(0, 5, null, 0);
        testBoard4.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard4.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard4.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard4.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard4.setCell(8, 5, new Item(Type.BOOK, 0), 3);
        testBoard4.setCell(0, 6, null, 0);
        testBoard4.setCell(1, 6, null, 0);
        testBoard4.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard4.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard4.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard4.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard4.setCell(7, 6, null, 0);
        testBoard4.setCell(8, 6, null, 0);
        testBoard4.setCell(0, 7, null, 0);
        testBoard4.setCell(1, 7, null, 0);
        testBoard4.setCell(2, 7, null, 0);
        testBoard4.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard4.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard4.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard4.setCell(6, 7, null, 0);
        testBoard4.setCell(7, 7, null, 0);
        testBoard4.setCell(8, 7, null, 0);
        testBoard4.setCell(0, 8, null, 0);
        testBoard4.setCell(1, 8, null, 0);
        testBoard4.setCell(2, 8, null, 0);
        testBoard4.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard4.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard4.setCell(5, 8, null, 0);
        testBoard4.setCell(6, 8, null, 0);
        testBoard4.setCell(7, 8, null, 0);
        testBoard4.setCell(8, 8, null, 0);

        assertTrue(testBoard4.needToRefill());

        Board testBoard5 = new Board();                                                   // refill triggered by an element on the last column
        testBoard5.setCell(0, 0, null, 0);
        testBoard5.setCell(1, 0, null, 0);
        testBoard5.setCell(2, 0, null, 0);
        testBoard5.setCell(3, 0, null, 0);
        testBoard5.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        testBoard5.setCell(5, 0, new Item(Type.TROPHY, 0), 3);
        testBoard5.setCell(6, 0, null, 0);
        testBoard5.setCell(7, 0, null, 0);
        testBoard5.setCell(8, 0, null, 0);
        testBoard5.setCell(0, 1, null, 0);
        testBoard5.setCell(1, 1, null, 0);
        testBoard5.setCell(2, 1, null, 0);
        testBoard5.setCell(3, 1, new Item(Type.PLANTS, 0), 4);
        testBoard5.setCell(4, 1, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(5, 1, new Item(Type.BOOK, 0), 2);
        testBoard5.setCell(6, 1, null, 0);
        testBoard5.setCell(7, 1, null, 0);
        testBoard5.setCell(8, 1, null, 0);
        testBoard5.setCell(0, 2, null, 0);
        testBoard5.setCell(1, 2, null, 0);
        testBoard5.setCell(2, 2, new Item(Type.PLANTS, 0), 3);
        testBoard5.setCell(3, 2, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(4, 2, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(5, 2, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(6, 2, new Item(Type.BOOK, 0), 3);
        testBoard5.setCell(7, 2, null, 0);
        testBoard5.setCell(8, 2, null, 0);
        testBoard5.setCell(0, 3, new Item(Type.GAME, 0), 3);
        testBoard5.setCell(1, 3, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(2, 3, new Item(Type.GAME, 0), 2);
        testBoard5.setCell(3, 3, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(4, 3, new Item(Type.FRAME, 0), 2);
        testBoard5.setCell(5, 3, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(6, 3, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(7, 3, new Item(Type.BOOK, 0), 4);
        testBoard5.setCell(8, 3, null, 0);
        testBoard5.setCell(0, 4, new Item(Type.GAME, 0), 4);
        testBoard5.setCell(1, 4, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(2, 4, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(3, 4, new Item(Type.FRAME, 0), 2);
        testBoard5.setCell(4, 4, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(5, 4, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(7, 4, null, 2);
        testBoard5.setCell(8, 4, new Item(Type.BOOK, 0), 4);
        testBoard5.setCell(0, 5, null, 0);
        testBoard5.setCell(1, 5, new Item(Type.GAME, 0), 4);
        testBoard5.setCell(2, 5, new Item(Type.BOOK, 0), 2);
        testBoard5.setCell(3, 5, new Item(Type.TROPHY, 0), 2);
        testBoard5.setCell(4, 5, new Item(Type.FRAME, 0), 2);
        testBoard5.setCell(5, 5, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(6, 5, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(7, 5, new Item(Type.BOOK, 0), 2);
        testBoard5.setCell(8, 5, null, 3);
        testBoard5.setCell(0, 6, null, 0);
        testBoard5.setCell(1, 6, null, 0);
        testBoard5.setCell(2, 6, new Item(Type.BOOK, 0), 3);
        testBoard5.setCell(3, 6, new Item(Type.GAME, 0), 2);
        testBoard5.setCell(4, 6, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(5, 6, new Item(Type.PLANTS, 0), 2);
        testBoard5.setCell(6, 6, new Item(Type.CAT, 0), 3);
        testBoard5.setCell(7, 6, null, 0);
        testBoard5.setCell(8, 6, null, 0);
        testBoard5.setCell(0, 7, null, 0);
        testBoard5.setCell(1, 7, null, 0);
        testBoard5.setCell(2, 7, null, 0);
        testBoard5.setCell(3, 7, new Item(Type.GAME, 0), 2);
        testBoard5.setCell(4, 7, new Item(Type.CAT, 0), 2);
        testBoard5.setCell(5, 7, new Item(Type.PLANTS, 0), 4);
        testBoard5.setCell(6, 7, null, 0);
        testBoard5.setCell(7, 7, null, 0);
        testBoard5.setCell(8, 7, null, 0);
        testBoard5.setCell(0, 8, null, 0);
        testBoard5.setCell(1, 8, null, 0);
        testBoard5.setCell(2, 8, null, 0);
        testBoard5.setCell(3, 8, new Item(Type.GAME, 0), 3);
        testBoard5.setCell(4, 8, new Item(Type.CAT, 0), 4);
        testBoard5.setCell(5, 8, null, 0);
        testBoard5.setCell(6, 8, null, 0);
        testBoard5.setCell(7, 8, null, 0);
        testBoard5.setCell(8, 8, null, 0);

        assertTrue(testBoard5.needToRefill());

        Board testBoard6 = new Board();                                                   // 1 item on the board
        testBoard6.setCell(0, 0, null, 0);
        testBoard6.setCell(1, 0, null, 0);
        testBoard6.setCell(2, 0, null, 0);
        testBoard6.setCell(3, 0, null, 0);
        testBoard6.setCell(4, 0, null, 4);
        testBoard6.setCell(5, 0, null, 3);
        testBoard6.setCell(6, 0, null, 0);
        testBoard6.setCell(7, 0, null, 0);
        testBoard6.setCell(8, 0, null, 0);
        testBoard6.setCell(0, 1, null, 0);
        testBoard6.setCell(1, 1, null, 0);
        testBoard6.setCell(2, 1, null, 0);
        testBoard6.setCell(3, 1, null, 4);
        testBoard6.setCell(4, 1, null, 2);
        testBoard6.setCell(5, 1, null, 2);
        testBoard6.setCell(6, 1, null, 0);
        testBoard6.setCell(7, 1, null, 0);
        testBoard6.setCell(8, 1, null, 0);
        testBoard6.setCell(0, 2, null, 0);
        testBoard6.setCell(1, 2, null, 0);
        testBoard6.setCell(2, 2, null, 3);
        testBoard6.setCell(3, 2, null, 2);
        testBoard6.setCell(4, 2, null, 2);
        testBoard6.setCell(5, 2, null, 2);
        testBoard6.setCell(6, 2, null, 3);
        testBoard6.setCell(7, 2, null, 0);
        testBoard6.setCell(8, 2, null, 0);
        testBoard6.setCell(0, 3, null, 3);
        testBoard6.setCell(1, 3, null, 2);
        testBoard6.setCell(2, 3, null, 2);
        testBoard6.setCell(3, 3, null, 2);
        testBoard6.setCell(4, 3, null, 2);
        testBoard6.setCell(5, 3, null, 2);
        testBoard6.setCell(6, 3, null, 2);
        testBoard6.setCell(7, 3, null, 4);
        testBoard6.setCell(8, 3, null, 0);
        testBoard6.setCell(0, 4, null, 4);
        testBoard6.setCell(1, 4, null, 2);
        testBoard6.setCell(2, 4, null, 2);
        testBoard6.setCell(3, 4, null, 2);
        testBoard6.setCell(4, 4, null, 2);
        testBoard6.setCell(5, 4, null, 2);
        testBoard6.setCell(6, 4, new Item(Type.CAT, 0), 2);
        testBoard6.setCell(7, 4, null, 2);
        testBoard6.setCell(8, 4, null, 4);
        testBoard6.setCell(0, 5, null, 0);
        testBoard6.setCell(1, 5, null, 4);
        testBoard6.setCell(2, 5, null, 2);
        testBoard6.setCell(3, 5, null, 2);
        testBoard6.setCell(4, 5, null, 2);
        testBoard6.setCell(5, 5, null, 2);
        testBoard6.setCell(6, 5, null, 2);
        testBoard6.setCell(7, 5, null, 2);
        testBoard6.setCell(8, 5, null, 3);
        testBoard6.setCell(0, 6, null, 0);
        testBoard6.setCell(1, 6, null, 0);
        testBoard6.setCell(2, 6, null, 3);
        testBoard6.setCell(3, 6, null, 2);
        testBoard6.setCell(4, 6, null, 2);
        testBoard6.setCell(5, 6, null, 2);
        testBoard6.setCell(6, 6, null, 3);
        testBoard6.setCell(7, 6, null, 0);
        testBoard6.setCell(8, 6, null, 0);
        testBoard6.setCell(0, 7, null, 0);
        testBoard6.setCell(1, 7, null, 0);
        testBoard6.setCell(2, 7, null, 0);
        testBoard6.setCell(3, 7, null, 2);
        testBoard6.setCell(4, 7, null, 2);
        testBoard6.setCell(5, 7, null, 4);
        testBoard6.setCell(6, 7, null, 0);
        testBoard6.setCell(7, 7, null, 0);
        testBoard6.setCell(8, 7, null, 0);
        testBoard6.setCell(0, 8, null, 0);
        testBoard6.setCell(1, 8, null, 0);
        testBoard6.setCell(2, 8, null, 0);
        testBoard6.setCell(3, 8, null, 3);
        testBoard6.setCell(4, 8, null, 4);
        testBoard6.setCell(5, 8, null, 0);
        testBoard6.setCell(6, 8, null, 0);
        testBoard6.setCell(7, 8, null, 0);
        testBoard6.setCell(8, 8, null, 0);

        assertTrue(testBoard6.needToRefill());
    }

    @Test
    void setCell() {
        /*List<Type> types = new ArrayList<>();
        Cell[][] disposition = new Cell[9][9];
        types.add(Type.BOOK);
        types.add(Type.CAT);
        types.add(Type.FRAME);
        types.add(Type.GAME);
        types.add(Type.PLANTS);
        types.add(Type.TROPHY);
        Board testBoard = new Board();
        testBoard.setDisposition(disposition);
        testBoard.setCell(4, 0, new Item(Type.BOOK, 0), 4);
        assertEquals(Type.BOOK, );*/
    }

}