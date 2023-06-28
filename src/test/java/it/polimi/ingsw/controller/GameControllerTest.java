package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerTest {

    @Test
    void getGame() {
        Game game = new Game();
        GameController gameController = new GameController(game, null);
        gameController.setGame(game);
        assertEquals(game, gameController.getGame());
    }
    @Test
    void setGame() {
        Game game = new Game();
        GameController gameController = new GameController(game, null);
        gameController.setGame(game);
        assertEquals(game, gameController.getGame());
    }
    @Test
    void setPlayer() {
        Game game = new Game();
        GameController gameController = new GameController(game, null);      //chiedi agli altri
        String nickname = "nickname";
        gameController.setPlayer(nickname);
        assertEquals(nickname, gameController.getGame().getPlayerList().get(0).getNickname());
    }
    @Test
    void setNumPlayers() {
        Game game = new Game();
        GameController gameController = new GameController(game, null);
        String nickname = "nickname";
        int numPlayers = 2;
        gameController.setNumPlayers(nickname, numPlayers);
        assertEquals(numPlayers, gameController.getGame().getNumberOfPlayers());
    }
    @Test
    void gameReadyToStart() {

    }

    @Test
    void onItemClick() {
    }

    @Test
    void onConfirmItems() {
    }

    @Test
    void onOrderItem() {

    }

    @Test
    void onColumnSelection() {
    }

    @Test
    void onConfirmInsertion() {
    }

    @Test
    void update() {
    }


}
