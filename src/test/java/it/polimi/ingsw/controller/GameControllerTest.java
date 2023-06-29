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
import it.polimi.ingsw.tuples.Triplet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.polimi.ingsw.enums.State.*;
import static it.polimi.ingsw.enums.Type.*;
import static it.polimi.ingsw.enums.Type.PLANTS;
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
        Game game = new Game("Test", 3, null, null);
        GameController gameController = new GameController(game, null);      //chiedi agli altri
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
    void onConfirmInsertion() throws IOException {
        Game game = new Game(new Board(), getCommons(), getPersonals());
        GameController gameController = new GameController(game, null);      //chiedi agli altri
        Item a = new Item(Type.BOOK, 1);
        Item b = new Item(Type.PLANTS, 2);
        List<Item> tmp = new ArrayList<>();
        tmp.add(a);
        tmp.add(b);
        game.setTmpOrderedItems(tmp);
        int column = 2;
        game.setColumnChosen(column);

        Set<Triplet<Integer, Integer, Type>> pG = new HashSet<>();
        pG.add(new Triplet<>(4, 1, CAT));
        pG.add(new Triplet<>(4, 1, BOOK));
        pG.add(new Triplet<>(4, 1, GAME));
        pG.add(new Triplet<>(2, 0, FRAME));
        pG.add(new Triplet<>(2, 5, TROPHY));
        pG.add(new Triplet<>(0, 0, PLANTS));

        Player test = new Player("test", new PersonalGoal(pG, -1));
        List<Player> pList = new ArrayList<>();
        pList.add(test);

        game.setPlayerList(pList);
        game.setCurrentPlayer(test);

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
