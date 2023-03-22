package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ClientApp {
    private Board board;
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
        while (!isMatchOver()) {
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
            int[] columns = player.getShelf().getInsertableColumns(selected.size());
            // notifica alla view le colonne selezionabili
            // Observer: ordine degli item e colonna
            List<Item> orderedItems = new ArrayList<Item>(); // arrivato dalla view
            int chosenColumn = 0; // arrivato dalla view
            orderedItems.forEach(e -> player.getShelf().insertItem(e, chosenColumn));
            board.extractPending();
        }
    }

    private void initializePersonalGoals() {
        String data = "";
        try {
            File myObj = new File("assets/personalGoals.json");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(data);
    }
}