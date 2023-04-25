package it.polimi.ingsw.network.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private GameController controller;
    //private List<GameController> matches; da capire se è impl che crea più match oppure se il server
    private Game match = null;

    public ServerImpl() throws RemoteException{
        super();
        //matches = new ArrayList<>();
    }

    //nella registrazione posso semplicamente fare l'add observer, inviare un messaggio che richiede nome e numero giocatori
    @Override
    public void register(Client client) throws RemoteException {
        if(match == null){
            this.match = new Game(getBoard(), getCommons());                            //da modificare il costruttore di game -> asset board, personal e common
            this.match.addObserverModel((o, arg) -> {
                try {
                    client.updateView(this, arg);                          //creo init connecting to server
                } catch (RemoteException e) {
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });
            this.controller = new GameController(match, client);
            System.out.println("Game e controller creati");//da creare sistemare con più client
        }
    }

    private Board getBoard(){
        ObjectMapper mapper = new ObjectMapper();

        int[][] board = new int[0][];
        try {
            board = mapper.readValue(new File("src/main/java/it/polimi/ingsw/assets/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Board assetBoard = new Board(board);
        Board boardGame = new Board();
        return boardGame;
        //return new Board(board); //da rimettere cone in game test
    }

    private List<CommonGoalName> getCommons(){
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

    public void updateModel(Client client, Message arg){     //ViewChange invia già modifiche e dati forse un messaggio?
        this.controller.update(client, arg);
    }
}
