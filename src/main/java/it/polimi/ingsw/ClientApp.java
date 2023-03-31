package it.polimi.ingsw;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;
import it.polimi.ingsw.tuples.Triplet;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*public class ClientApp {
    private List<Player> players;
    private boolean isMatchOver;
    private CommonGoalAbstract firstCommonGoal, secondCommonGoal;

    public ClientApp() {
        // iscrizione giocatori
        // [...]
        // inizializzazione match
        // [...]
        playMatch();
    }

    private void playMatch() {
       // while (!isMatchOver()) {
            players.forEach(e -> {
                new Turn(e);
                // Check common goals

                // Check for empty board
                // Check for full shelf
            });
        }
    }

    private class Turn {
        private Player player;

        public Turn(Player player) {
            this.player = player;
            play();
        }

        public void play() {
            firstToc();
            secondToc();
        }

        private void firstToc() {

            // Get the max number of items that can be selected
            Shelf currentShelf = player.getShelf();
            int maxSelectable = currentShelf.getMaxFreeSpace();
            maxSelectable = Math.min(maxSelectable, 3);

            Set<Item> items = new HashSet<Item>();
            while (items.size() < maxSelectable) {
                // Observer: 1 select item
                // Observer: 2 end selection
            }
            // Show to user the selected items
        }

        private void secondToc() {
            Set<Item> selected = new HashSet<Item>(); // arrivato dalla view
            List<Integer> columns = player.getShelf().getInsertableColumns();
            // notifica alla view le colonne selezionabili
            // Observer: ordine degli item e colonna
            List<Item> orderedItems = new ArrayList<Item>(); // arrivato dalla view
            int chosenColumn = 0; // arrivato dalla view
            orderedItems.forEach(e -> player.getShelf().insertItems(e, chosenColumn));
            board.extractPending();
        }
    }


    //private Set<Set<Triplet>> personalGoals;

    //private Board board;
    //private Set<CommonGoalAbstract> commonGoals;

    // Initialization of the deck of personal goals
    //private void initializePersonalGoals() {
       // ObjectMapper mapper = new ObjectMapper();

        // Read the json file
       // List<List<Triplet>> pgList = null;
        //try {
            /*pgList = mapper.readValue(new File("assets/personalGoals.json"), new TypeReference<List<List<Triplet>>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Convert the list of lists to a set of sets
        Set<Set<Triplet>> pgSet = new HashSet<Set<Triplet>>();
        for(List<Triplet> list : pgList) {
            Set<Triplet> set = new HashSet<Triplet>(list);
            pgSet.add(set);
        }
        this.personalGoals = pgSet;
    }

    // Initialization of the board
    private void initializeBoard() {
        ObjectMapper mapper = new ObjectMapper();

        int[][] board = new int[0][];
        try {
            board = mapper.readValue(new File("assets/livingroom.json"), int[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.board = new Board(board);

    }

    // Initialization of the deck of common goals: it has to be done every match because the score of the
    // common goals changes on how many players are playing

    // Initialization of the bag
    private void initializeBag() {

    }

}*/