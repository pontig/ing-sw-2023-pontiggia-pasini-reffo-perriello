package it.polimi.ingsw.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.assets.PersonalGoalJson;
import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.messages.SendChatMessage;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.ServerImpl;
import it.polimi.ingsw.tuples.Pair;
import it.polimi.ingsw.tuples.Triplet;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.enums.State.*;
import static org.junit.jupiter.api.Assertions.*;

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
        Game game = new Game("Test", 3, null, null);
        GameController gameController = new GameController(game, null);
        String nickname = "nickname1";
        int numPlayers = 3;
        gameController.setNumPlayers(nickname, numPlayers);
        assertEquals(numPlayers, gameController.getGame().getNumberOfPlayers());
    }
    @Test
    void gameReadyToStart() {
        Game game = new Game("Test", 3, null, null);
        GameController gameController = new GameController(game, null);               //Chiedi agli altri
        String nickname = "test2";
        gameController.setPlayer(nickname);
        List<Player> expected = new ArrayList<>();
        expected.add(new Player("Test", null));
        expected.add(new Player(nickname, null));
        assertEquals(3, game.getNumberOfPlayers());
        assertEquals(2, game.getPlayerList().size());
    }

    @Test
    void onItemClick() {
        ObjectMapper mapper = new ObjectMapper();

        int[][] board = new int[0][];
        try {
            board = mapper.readValue(new File("src/main/resources/json/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Board(board);
        Board boardGame = new Board();
        Bag bag = new Bag();
        boardGame.fill(3, bag);
        Game game = new Game("Test", 3, boardGame, null);
        GameController gameController = new GameController(game, null);
        Set<Pair<Integer, Integer>> pendingCells = new HashSet<>();
        Pair<Integer, Integer> a = new Pair<>(1,4);
        pendingCells.add(a);
        game.getBoard().setPendingCells(pendingCells);
        int x = 1;
        int y = 4;
        gameController.onItemClick(x, y);
        assertFalse(gameController.getGame().getBoard().getPendingCells().contains(a));
    }

    @Test
    void onConfirmItems() {
        ObjectMapper mapper = new ObjectMapper();

        int[][] board = new int[0][];
        try {
            board = mapper.readValue(new File("src/main/resources/json/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Board(board);
        Board boardGame = new Board();
        Bag bag = new Bag();
        boardGame.fill(3, bag);
        Game game = new Game("Test", 3, boardGame, null);
        GameController gameController = new GameController(game, null);
        Set<Pair<Integer, Integer>> pendingCells = new HashSet<>();
        Pair<Integer, Integer> a = new Pair<>(1,4);
        pendingCells.add(a);
        game.getBoard().setPendingCells(pendingCells);
        gameController.onConfirmItems();
        assertTrue(gameController.getGame().getBoard().getPendingCells().isEmpty());
    }

    @Test
    void onOrderItem() {
        Game game = new Game("Test", 3, null, null);
        GameController gameController = new GameController(game, null);
        Item a = new Item(Type.BOOK, 1);
        Item b = new Item(Type.PLANTS, 2);
        List<Item> tmp = new ArrayList<>();
        tmp.add(a);
        tmp.add(b);
        game.setTmpOrderedItems(tmp);
        gameController.onOrderItem(1,0);
        assertEquals(b, game.getConfirmedItems().get(0));
    }

    @Test
    void onColumnSelection()  {
        Game game = new Game("Test", 3, null, null);
        GameController gameController = new GameController(game, null);
        Player player = new Player("test", null);
        game.insertPlayer("test");
        game.setCurrentPlayer(player);
        Shelf shelf = player.getShelf();
        player.getShelf().setInsertableColumns(3);
        gameController.onColumnSelection(1);
    }

    @Test
    void onConfirmInsertion() throws IOException {
        Game game = new Game(new Board(), getCommons(), getPersonals());
        GameController gameController = new GameController(game, null);      //chiedi agli altri
        game.insertPlayer("test1");
        game.setNumberOfPlayers("test1", 2);
        game.insertPlayer("test2");
        game.itemClick(4,1);
        game.confirmItems();
        game.orderSelectedItem(0,1);
        game.selectColumn(2);
        gameController.onConfirmInsertion();
    }

    @Test
    void update() throws IOException {
        Game game = new Game(new Board(), getCommons(), getPersonals());
        GameController gameController = new GameController(game, null);      //chiedi agli altri
        gameController.update(null, new SendDataToServer(SET_NICKNAME, "test", 0, 0, false));
        gameController.update(null, new SendDataToServer(SET_NUMPLAYERS, "test", 2, 0, false));
        gameController.update(null, new SendDataToServer(GAME_READY, null, 5, 1, false));
        gameController.update(null, new SendDataToServer(SELECT_ITEM, null, 0, 0, false));
        gameController.update(null, new SendDataToServer(SELECT_COLUMN, null, 3, 2, false));
        gameController.update(null, new SendDataToServer(ORDER_ITEM, null, 1, 0, false));
        gameController.update(null, new SendDataToServer(CLIENT_DOWN, null, 0, 0, false));
        gameController.update(null, new SendChatMessage(CHAT_MESSAGE, "test1", "test2", "Hi"));
        gameController.update(null, new SendDataToServer(PING, null, 0, 0, false));
    }
    private List<CommonGoalName> getCommons() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> goals = objectMapper.readValue(ServerImpl.class.getResourceAsStream("/json/commonGoals.json"), new TypeReference<List<String>>() {
        });
        List<CommonGoalName> commonGoals = new ArrayList<>();
        for (String s : goals)
            commonGoals.add(CommonGoalName.valueOf(s));

        return commonGoals;
    }
    private List<PersonalGoal> getPersonals() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonalGoalJson[] personalGoalJsonArray = objectMapper.readValue(ServerImpl.class.getResourceAsStream("/json/personalGoals.json"), PersonalGoalJson[].class);

        List<PersonalGoal> personalGoalList = new ArrayList<>();
        int i = 1;
        for (PersonalGoalJson personalGoalJson : personalGoalJsonArray) {
            Set<Triplet<Integer, Integer, Type>> pg = personalGoalJson.toSet();
            personalGoalList.add(new PersonalGoal(pg, i));
            i++;
        }

        return personalGoalList;
    }
}
