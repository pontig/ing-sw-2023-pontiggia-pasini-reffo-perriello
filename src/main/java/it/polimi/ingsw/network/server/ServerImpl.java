package it.polimi.ingsw.network.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.assets.PersonalGoalJson;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.tuples.Triplet;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private static GameController controller;
    //private List<GameController> matches; da capire se è impl che crea più match oppure se il server
    private static Game match = null;

    public ServerImpl() throws RemoteException{
        super();
        //matches = new ArrayList<>();
    }

    //nella registrazione posso semplicamente fare l'add observer, inviare un messaggio che richiede nome e numero giocatori
    @Override
    public void register(Client client) throws RemoteException{
        if(match == null){
            try {
                match = new Game(getBoard(), getCommons(), getPersonals());                            //da modificare il costruttore di game -> asset board, personal e common
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            controller = new GameController(match, client);
            System.out.println("Game e controller creati da " + client);//da creare sistemare con più client
        }
        match.addObserverModel((o, arg) -> {
            try {
                client.updateView(this, arg);                          //creo init connecting to server
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
            }
        });
    }

    private Board getBoard(){
        ObjectMapper mapper = new ObjectMapper();

        int[][] board = new int[0][];
        try {
            board = mapper.readValue(new File("src/main/java/it/polimi/ingsw/assets/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Board(board);
        Board boardGame = new Board();
        return boardGame;
    }

    private List<CommonGoalName> getCommons() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> goals = objectMapper.readValue(new File("src/main/java/it/polimi/ingsw/assets/commonGoals.json"), new TypeReference<List<String>>(){});
        List<CommonGoalName> commonGoals = new ArrayList<>();
        for(String s: goals)
            commonGoals.add(CommonGoalName.valueOf(s));

        return commonGoals;
    }

    private List<PersonalGoal> getPersonals() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonalGoalJson[] personalGoalJsonArray = objectMapper.readValue(new File("src/main/java/it/polimi/ingsw/assets/personalGoals.json"), PersonalGoalJson[].class);

        List<PersonalGoal> personalGoalList = new ArrayList<>();
        int i = 1;
        for (PersonalGoalJson personalGoalJson : personalGoalJsonArray) {
            Set<Triplet<Integer, Integer, Type>> pg = personalGoalJson.toSet();
            personalGoalList.add(new PersonalGoal(pg, i));
            i++;
        }

        return personalGoalList;
    }

    public void updateModel(Client client, Message arg){     //ViewChange invia già modifiche e dati forse un messaggio?
        controller.update(client, arg);
    }

}
