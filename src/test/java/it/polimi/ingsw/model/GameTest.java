package it.polimi.ingsw.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.enums.CommonGoalName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.enums.Type.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameTest {

    //Board from assets
    private Board initializeBoard() {
        ObjectMapper mapper = new ObjectMapper();

        int[][] board = new int[0][];
        try {
            board = mapper.readValue(GameTest.class.getResourceAsStream("/json/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Board assetBoard = new Board(board);
        Board boardGame = new Board();
        return boardGame;
    }

    //CommonGoal
    private List<CommonGoalName> initializeCommonGoals() {
        List<CommonGoalName> commonGoals = new ArrayList<>();
        commonGoals.add(CommonGoalName.SIXCOUPLES);
        commonGoals.add(CommonGoalName.SQUARE2X2);
        commonGoals.add(CommonGoalName.FOURANGLES);
        commonGoals.add(CommonGoalName.FOURADJACENT);
        commonGoals.add(CommonGoalName.FIVEX);
        commonGoals.add(CommonGoalName.FIVEDIAGONAL);
        commonGoals.add(CommonGoalName.FIVEDECRESING);
        commonGoals.add(CommonGoalName.EIGHTSAMETYPE);
        commonGoals.add(CommonGoalName.COLUMNS3ITEMS6);
        commonGoals.add(CommonGoalName.ROW4ITEMS5);
        commonGoals.add(CommonGoalName.COLUMNS2ITEMS6DIFFERENT);
        commonGoals.add(CommonGoalName.ROW2ITEMS5DIFFERENT);
        return commonGoals;
    }
    Game gameTwoPlayers = new Game("Tommi", 2, initializeBoard(), initializeCommonGoals());
    Game gameThreePlayers = new Game("Eli", 3, initializeBoard(), initializeCommonGoals());
    Game gameFourPlayers = new Game("Mauri", 4, initializeBoard(), initializeCommonGoals());

    @Test
    void getPlayerList() {}

    @Test
    void getPlayerState() {
        gameFourPlayers.setNumberOfPlayers(gameFourPlayers.getCurrentPlayer().getNickname(),4);
        assertEquals(4, gameFourPlayers.getNumberOfPlayers());
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void getFirstCommonGoal() {
    }

    @Test
    void getSecondCommonGoal() {
    }

    @Test
    void getBoard() {
        gameFourPlayers.setBoard(new Board());
        assertNotEquals(null, gameFourPlayers.getBoard());
    }

    @Test
    void getEndGame() {
        gameThreePlayers.setEndGame(false);
        assertEquals(false, gameThreePlayers.getEndGame());
    }

    @Test
    void getCanConfirmItems() {
    }

    @Test
    void getOrderOK() {
    }

    @Test
    void getColumnOK() {
    }

    @Test
    void getNumPendingItems() {
    }

    @Test
    void getConfirmedItems() {
    }

    @Test
    void getTmpOrderedItems() {
    }

    @Test
    void getColumnChosen() {
    }

    @Test
    void getGameResult() {
    }

    @Test
    void getBag() {
        gameThreePlayers.setBag(new Bag());
        Board board = new Board();
        board.fill(3, gameThreePlayers.getBag());
        //assertNotEquals(22, gameThreePlayers.getBag().getItems().get(BOOK));
    }

    @Test
    void itemClick() {
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        assertEquals(false, gameTwoPlayers.getCanConfirmItems());
        gameTwoPlayers.itemClick(0,0);
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        assertEquals(false, gameTwoPlayers.getCanConfirmItems());
        gameTwoPlayers.itemClick(4,0);
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        assertEquals(false, gameTwoPlayers.getCanConfirmItems());
        gameTwoPlayers.itemClick(5,0);
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        assertEquals(false, gameTwoPlayers.getCanConfirmItems());
        gameTwoPlayers.itemClick(4,4);
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        assertEquals(false, gameTwoPlayers.getCanConfirmItems());

        //select cell
        gameTwoPlayers.itemClick(4,1);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(3,1);
        assertEquals(2, gameTwoPlayers.getNumPendingItems());
        assertEquals(true, gameTwoPlayers.getCanConfirmItems());

        //deselect cell
        gameTwoPlayers.itemClick(4,1);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        assertEquals(true, gameTwoPlayers.getCanConfirmItems());
        gameTwoPlayers.itemClick(3,1);
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        assertEquals(false, gameTwoPlayers.getCanConfirmItems());

        //limited selection
        gameTwoPlayers.getCurrentPlayer().getShelf().clear();
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 1, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 2, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 3, new Item(CAT, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 4, new Item(GAME, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 5, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 0, new Item(CAT, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 1, new Item(PLANTS, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 2, new Item(BOOK, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 3, new Item(BOOK, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 4, new Item(CAT, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 5, new Item(CAT, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 0, new Item(BOOK, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 1, new Item(TROPHY, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 2, new Item(BOOK, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 3, new Item(TROPHY, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 4, new Item(GAME, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 5, new Item(BOOK,1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 1, new Item(BOOK,2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 2, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 3, new Item(PLANTS, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 4, new Item(BOOK, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 5, new Item(BOOK, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 0, new Item(BOOK,3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 1, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 2, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 3, new Item(PLANTS, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 4, new Item(GAME, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 5, new Item(BOOK, 1));
        gameTwoPlayers.itemClick(4,1);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        assertEquals(true, gameTwoPlayers.getCanConfirmItems());
        gameTwoPlayers.itemClick(3,1);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        assertEquals(true, gameTwoPlayers.getCanConfirmItems());
    }

    @Test
    void confirmItems() {
        //select item on board
        gameTwoPlayers.itemClick(4,1);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(3,1);
        assertEquals(2, gameTwoPlayers.getNumPendingItems());

        //get the item and check where to put them
        gameTwoPlayers.confirmItems();
        assertEquals(2, gameTwoPlayers.getNumPendingItems());
        assertEquals(0, gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().get(0));
        assertEquals(1, gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().get(1));
        assertEquals(2, gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().get(2));
        assertEquals(3, gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().get(3));
        assertEquals(4, gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().get(4));

        //select items with constrains
        gameTwoPlayers.setNumPendingItems(0);
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 1, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 2, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 3, new Item(CAT, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 4, new Item(GAME, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(0, 5, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 0, new Item(CAT, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 1, new Item(PLANTS, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 2, new Item(BOOK, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 3, new Item(BOOK, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 4, new Item(CAT, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(1, 5, new Item(CAT, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 0, new Item(BOOK, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 1, new Item(TROPHY, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 2, new Item(BOOK, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 3, new Item(TROPHY, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 4, new Item(GAME, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(2, 5, new Item(BOOK,1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 1, new Item(BOOK,2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 2, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 3, new Item(PLANTS, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 4, new Item(BOOK, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(3, 5, new Item(BOOK, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 0, new Item(BOOK,3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 1, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 2, new Item(BOOK, 1));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 3, new Item(PLANTS, 2));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 4, new Item(GAME, 3));
        gameTwoPlayers.getCurrentPlayer().getShelf().setItem(4, 5, new Item(BOOK, 1));
        gameTwoPlayers.itemClick(5,2);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(6,2);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(7,2);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());

        //get items and check valid columns on constrains
        gameTwoPlayers.confirmItems();
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        assertEquals(0, gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().get(0));
        assertEquals(3, gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().get(1));
    }

    @Test
    void orderSelectedItem() {
        gameTwoPlayers.itemClick(4,1);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(3,1);
        assertEquals(2, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.confirmItems();
        assertEquals(2, gameTwoPlayers.getNumPendingItems());
        //select from confirmed to tmp
        Item item = gameTwoPlayers.getConfirmedItems().get(1);
        gameTwoPlayers.orderSelectedItem(1,1);
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(0), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());
        item = gameTwoPlayers.getConfirmedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,1);
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(1), item);
        assertEquals(true, gameTwoPlayers.getOrderOK());
        // deselect from tmp to confirmed
        item = gameTwoPlayers.getTmpOrderedItems().get(1);
        gameTwoPlayers.orderSelectedItem(1,0);
        assertEquals(gameTwoPlayers.getConfirmedItems().get(0), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());
        item = gameTwoPlayers.getTmpOrderedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,0);
        assertEquals(gameTwoPlayers.getConfirmedItems().get(1), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());

        gameTwoPlayers.setNumPendingItems(0);
        gameTwoPlayers.itemClick(4,1);
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(3,1);
        assertEquals(0, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(5,2);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(4,2);
        assertEquals(2, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(3,2);
        assertEquals(3, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.confirmItems();
        assertEquals(3, gameTwoPlayers.getNumPendingItems());
        //select from confirmed to tmp
        item = gameTwoPlayers.getConfirmedItems().get(2);
        gameTwoPlayers.orderSelectedItem(2,1);
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(0), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());
        item = gameTwoPlayers.getConfirmedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,1);
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(1), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());
        item = gameTwoPlayers.getConfirmedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,1);
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(2), item);
        assertEquals(true, gameTwoPlayers.getOrderOK());
        // deselect from tmp to confirmed
        item = gameTwoPlayers.getTmpOrderedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,0);
        assertEquals(gameTwoPlayers.getConfirmedItems().get(0), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());
        item = gameTwoPlayers.getTmpOrderedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,0);
        assertEquals(gameTwoPlayers.getConfirmedItems().get(1), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());
        item = gameTwoPlayers.getTmpOrderedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,0);
        assertEquals(gameTwoPlayers.getConfirmedItems().get(2), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());

        //selection check not about the method itself
        gameTwoPlayers.setNumPendingItems(0);
        gameTwoPlayers.itemClick(7,3);
        assertEquals(1, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(6,3);
        assertEquals(2, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(5,3);
        assertEquals(3, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.itemClick(4,3);
        assertEquals(3, gameTwoPlayers.getNumPendingItems());
        gameTwoPlayers.confirmItems();
        assertEquals(3, gameTwoPlayers.getNumPendingItems());
    }

    @Test
    void selectColumn() {
        assertEquals(false, gameTwoPlayers.getColumnOK());
        gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().add(0);
        gameTwoPlayers.getCurrentPlayer().getShelf().getInsertableColumns().add(3);
        gameTwoPlayers.selectColumn(3);
        assertEquals(true, gameTwoPlayers.getColumnOK());
    }

    @Test
    void confirmInsertion() {
        gameTwoPlayers.itemClick(4,1);
        gameTwoPlayers.itemClick(3,1);
        gameTwoPlayers.confirmItems();

        Item item = gameTwoPlayers.getConfirmedItems().get(1);
        gameTwoPlayers.orderSelectedItem(1,1);
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(0), item);
        assertEquals(false, gameTwoPlayers.getOrderOK());
        item = gameTwoPlayers.getConfirmedItems().get(0);
        gameTwoPlayers.orderSelectedItem(0,1);
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(1), item);
        assertEquals(true, gameTwoPlayers.getOrderOK());

        gameTwoPlayers.selectColumn(3);
        assertEquals(true, gameTwoPlayers.getColumnOK());

        gameTwoPlayers.confirmInsertion();
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(0), gameTwoPlayers.getCurrentPlayer().getShelf().getItem(3,5));
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(1), gameTwoPlayers.getCurrentPlayer().getShelf().getItem(3,4));

        gameTwoPlayers.setNumPendingItems(0);
        gameTwoPlayers.getTmpOrderedItems().clear();
        gameTwoPlayers.itemClick(5,2);
        gameTwoPlayers.itemClick(4,2);
        gameTwoPlayers.itemClick(3,2);
        assertEquals(true, gameTwoPlayers.getCanConfirmItems());
        gameTwoPlayers.confirmItems();
        gameTwoPlayers.orderSelectedItem(2,1);
        gameTwoPlayers.orderSelectedItem(0,1);
        gameTwoPlayers.orderSelectedItem(0,1);
        assertEquals(true, gameTwoPlayers.getOrderOK());
        gameTwoPlayers.selectColumn(1);
        assertEquals(true, gameTwoPlayers.getColumnOK());
        gameTwoPlayers.confirmInsertion();
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(0), gameTwoPlayers.getCurrentPlayer().getShelf().getItem(1,5));
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(1), gameTwoPlayers.getCurrentPlayer().getShelf().getItem(1,4));
        assertEquals(gameTwoPlayers.getTmpOrderedItems().get(2), gameTwoPlayers.getCurrentPlayer().getShelf().getItem(1,3));
    }

    @Test
    void endTurnCheck() {
        gameTwoPlayers.itemClick(4,1);
        gameTwoPlayers.itemClick(3,1);
        gameTwoPlayers.confirmItems();
        gameTwoPlayers.orderSelectedItem(1,1);
        gameTwoPlayers.orderSelectedItem(0,1);
        gameTwoPlayers.selectColumn(3);
        gameTwoPlayers.confirmInsertion();
        assertEquals(false, gameTwoPlayers.endTurnCheck());
        assertEquals(0, gameTwoPlayers.getCurrentPlayer().getFirstCommonScore());
        assertEquals(0, gameTwoPlayers.getCurrentPlayer().getSecondCommonScore());
        assertEquals(0, gameTwoPlayers.getCurrentPlayer().getEndGameToken());
        assertEquals(null, gameTwoPlayers.getBoard().getDisposition()[4][1].getContent());
        assertEquals(null, gameTwoPlayers.getBoard().getDisposition()[3][1].getContent());

        gameTwoPlayers.setEndGame(true);
        gameTwoPlayers.setNumPendingItems(0);
        assertEquals(true, gameTwoPlayers.endTurnCheck());

        gameTwoPlayers.setEndGame(false);
        for(int i =0; i < 5; i++){
            for(int j = 0; j < 6; j++)
                gameTwoPlayers.getCurrentPlayer().getShelf().setItem(i, j, new Item(BOOK, 1));
        }
        gameTwoPlayers.endTurnCheck();
    }

    @Test
    void nextPlayer() {
        gameTwoPlayers.getPlayerList().clear();
        List<Player> players = new ArrayList<>();
        players.add(gameTwoPlayers.getCurrentPlayer());
        players.add(new Player("Ale", gameTwoPlayers.getCurrentPlayer().getPersonalGoal()));

        gameTwoPlayers.setPlayerList(players);
        assertEquals("Tommi", gameTwoPlayers.getCurrentPlayer().getNickname());
        gameTwoPlayers.nextPlayer();
        assertEquals("Ale", gameTwoPlayers.getCurrentPlayer().getNickname());
        gameTwoPlayers.nextPlayer();
        assertEquals("Tommi", gameTwoPlayers.getCurrentPlayer().getNickname());
    }

    @Test
    void endGame() {
        gameTwoPlayers.getPlayerList().clear();
        List<Player> players = new ArrayList<>();
        gameTwoPlayers.getCurrentPlayer().setEndGameToken(1);
        players.add(new Player("Ale", gameTwoPlayers.getCurrentPlayer().getPersonalGoal()));
        players.add(gameTwoPlayers.getCurrentPlayer());
        gameTwoPlayers.setPlayerList(players);
        gameTwoPlayers.endGame();
        assertEquals("Tommi", gameTwoPlayers.getGameResult().get(0).getX());
        assertEquals("Ale", gameTwoPlayers.getGameResult().get(1).getX());

        gameTwoPlayers.getPlayerList().clear();
        players.clear();
        gameTwoPlayers.getGameResult().clear();
        gameTwoPlayers.getCurrentPlayer().setEndGameToken(5);
        players.add(gameTwoPlayers.getCurrentPlayer());
        players.add(new Player("Ale", gameTwoPlayers.getCurrentPlayer().getPersonalGoal()));
        Player newPlayer = new Player("Eli", gameTwoPlayers.getCurrentPlayer().getPersonalGoal());
        newPlayer.setEndGameToken(2);
        players.add(newPlayer);
        gameTwoPlayers.setPlayerList(players);
        gameTwoPlayers.endGame();
        assertEquals("Tommi", gameTwoPlayers.getGameResult().get(0).getX());
        assertEquals("Eli", gameTwoPlayers.getGameResult().get(1).getX());
        assertEquals("Ale", gameTwoPlayers.getGameResult().get(2).getX());
    }
}