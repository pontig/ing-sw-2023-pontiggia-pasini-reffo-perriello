package it.polimi.ingsw.network.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.assets.PersonalGoalJson;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.tuples.Triplet;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The ServerImpl class implements the Server interface and provides the implementation for server-side operations
 */
public class ServerImpl extends UnicastRemoteObject implements Server {
    private static GameController controller;
    private static Game match;
    private final boolean fromScratch;
    private static final List<Client> clientList = new ArrayList<>();
    private static int countClient = 0;

    /**
     * Constructs a ServerImpl object
     *
     * @param fromScratch indicates whether the server is created from scratch or loaded from a file
     * @throws RemoteException if an error occurs during remote method invocation
     */
    public ServerImpl(boolean fromScratch) throws RemoteException {
        super();
        this.fromScratch = fromScratch;
        //matches = new ArrayList<>();
    }

    /**
     * Registers a client to the server
     *
     * @param client the client to register
     * @return true if the registration is successful, false otherwise
     * @throws RemoteException if an error occurs during remote method invocation
     */
    //nella registrazione posso semplicamente fare l'add observer, inviare un messaggio che richiede nome e numero giocatori
    @Override
    public boolean register(Client client) throws RemoteException {
        if (match == null) {
            try {
                if (fromScratch) {
                    match = new Game(getBoard(), getCommons(), getPersonals());
                }                       //da modificare il costruttore di game -> asset board, personal e common
                else {
                    match = (new ObjectMapper()).readValue((new File("status.json")), Game.class);
                    match.notFromScratch();
                }
                //System.out.println("Set match " + match);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            controller = new GameController(match, client);
            System.out.println("Game e controller creati da " + client);
        }

        //System.out.print("Controllo match - ");

        if(match.getNumberOfPlayers() == 0 || match.getNumberOfPlayers() > match.getPlayerList().size() || (!fromScratch && countClient < match.getNumberOfPlayers())) {
            //System.out.println("true");
            //System.out.println("Player lsit size" + match.getPlayerList().size());
            match.addObserverModel((o, arg) -> {
                try {
                    client.updateView(this, arg);                          //creo init connecting to server
                } catch (RemoteException e) {
                    System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
                }
            });
            countClient++;
            return true;
        } else {
            //System.out.println("false");
            return false;
        }
    }

    /**
     * Retrieves the board from a JSON file and returns it
     *
     * @return the board object
     */
    private Board getBoard() {
        ObjectMapper mapper = new ObjectMapper();

        int[][] board = new int[0][];
        try {
            board = mapper.readValue(ServerImpl.class.getResourceAsStream("/json/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Board(board);
        Board boardGame = new Board();
        return boardGame;
    }

    /**
     * Retrieves the list of commonGoals from a JSON file and returns it
     *
     * @return the list of commonGoals
     * @throws IOException if an error occurs while reading the JSON file
     */
    private List<CommonGoalName> getCommons() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> goals = objectMapper.readValue(ServerImpl.class.getResourceAsStream("/json/commonGoals.json"), new TypeReference<List<String>>() {
        });
        List<CommonGoalName> commonGoals = new ArrayList<>();
        for (String s : goals)
            commonGoals.add(CommonGoalName.valueOf(s));

        return commonGoals;
    }

    /**
     * Retrieves the list of personalGoals from a JSON file and returns it
     *
     * @return the list of personalGoals
     * @throws IOException if an error occurs while reading the JSON file
     */
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

    /**
     * Updates the model based on the client's action
     *
     * @param client the client who performed the action
     * @param arg the message containing the action details
     */
    public void updateModel(Client client, Message arg) {     //ViewChange invia già modifiche e dati forse un messaggio?
        if(arg.getInfo() == State.SET_NICKNAME) {
            if(match.getNumberOfPlayers() == 0 || match.getNumberOfPlayers() > clientList.size()){
                //System.out.println("Numero player: " + match.getNumberOfPlayers()  + " Client list: " + clientList.size());
                if(!clientList.contains(client)) {
                    clientList.add(client);
                    //System.out.println("Appena aggiunto: " + client);
                    //System.out.println("Clientlist appena aggiunto: " + clientList.size());
                }
            } else {
                System.out.println("Error list client");
            }
        }
        controller.update(client, arg);
    }

    /**
     * Sends a ping message to all connected clients
     *
     * @throws RemoteException if an error occurs during remote method invocation
     */
    public void ping() throws RemoteException {
            if (clientList != null || !clientList.isEmpty()) {
                for (Client c : clientList) {
                    c.updateView(this, new SendDataToServer(State.PING, null, 0, 0, false));           //arg è messaggio da view a controller - INIT per nome e num players
                    //System.out.println("Client: " + c + "ping dal server");
                }
            }
    }
}
