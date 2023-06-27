package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerTest {

    private GameController gameController;
    private Game game;
    private Client client;
    private Message message;

    @Test
    void getGame() {
        game = new Game();
        gameController = new GameController(game, client);
        Game result = gameController.getGame();
        assertEquals(game, result);
    }

    @Test
    void setGame() {
        game = new Game();
        gameController = new GameController(game, client);
        Game testGame = new Game();
        gameController.setGame(testGame);
        assertEquals(testGame, gameController.getGame());
    }

    @Test
    void setPlayer() {
        game = new Game();                                      //Problemi
        gameController = new GameController(game, client);
        String nickname = "Player1";
        gameController.setPlayer(nickname);
    }

    @Test
    void setNumPlayers() {
        game = new Game();                                      //Problemi
        gameController = new GameController(game, client);
        String nickname = "Player1";
        int numPlayers = 4;
        gameController.setNumPlayers(nickname, numPlayers);
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