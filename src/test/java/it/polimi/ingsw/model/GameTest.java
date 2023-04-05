package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();

    @Test
    void getPlayerList() {
    }

    @Test
    void getPlayerState() {
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
    }

    @Test
    void getEndGame() {
    }

    @Test
    void getCanConfirmItems() {
        assertEquals(false, game.getCanConfirmItems());
        game.setCanConfirmItem(true);
        assertEquals(true, game.getCanConfirmItems());
    }

    @Test
    void getOrderOK() {
        assertEquals(false, game.getOrderOK());
        game.setOrderOK(true);
        assertEquals(true, game.getOrderOK());
    }

    @Test
    void getColumnOK() {
        assertEquals(false, game.getColumnOK());
        game.setColumnOK(true);
        assertEquals(true, game.getColumnOK());
    }

    @Test
    void getNumPendingItems() {
        assertEquals(0, game.getNumPendingItems());
        game.setNumPendingItems(1);
        assertEquals(1, game.getNumPendingItems());
        game.setNumPendingItems(2);
        assertEquals(2, game.getNumPendingItems());
        game.setNumPendingItems(3);
        assertEquals(3, game.getNumPendingItems());
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
    }

    @Test
    void itemClick() {
    }

    @Test
    void confirmItems() {
    }

    @Test
    void orderSelectedItem() {
    }

    @Test
    void selectColumn() {
    }

    @Test
    void confirmInsertion() {
    }

    @Test
    void endTurnCheck() {
    }

    @Test
    void nextPlayer() {
    }

    @Test
    void endGame() {
    }
}