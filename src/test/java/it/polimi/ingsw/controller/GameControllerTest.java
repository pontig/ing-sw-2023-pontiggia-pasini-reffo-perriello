package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Game game = new Game("Test", 3, null, null);
        GameController gameController = new GameController(game, null);      //chiedi agli altri
        String nickname = "test2";
        gameController.setPlayer(nickname);
        List<Player> expected = new ArrayList<>();
        expected.add(new Player("Test", null));
        expected.add(new Player(nickname, null));
        List<String> expectedNicknames = expected.stream().map(Player::getNickname).collect(Collectors.toList());
        List<String> inGame = game.getPlayerList().stream().map(Player::getNickname).collect(Collectors.toList());
        assertEquals(expectedNicknames, inGame);
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
