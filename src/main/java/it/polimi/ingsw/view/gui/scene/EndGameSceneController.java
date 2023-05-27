package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.tuples.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Arrays;

public class EndGameSceneController extends ObservableView implements GenericSceneController {
    @FXML
    private Label firstRank, secondRank, thirdRank, fourthRank;
    private Label[] podium;

    @FXML
    public void initialize() {
        podium = new Label[]{firstRank, secondRank, thirdRank, fourthRank};
    }

    public void assignRanks(String model) {
        String results = model;
        Pair[] ranks = Arrays.stream(results.split("\n"))
                .filter(e -> !e.contains("MATCH RANKING"))
                .map(e -> new Pair<String, Integer>(e.split("- ")[1], Integer.valueOf(e.split("Total score: ")[1])))
                .toArray(Pair[]::new);

        for (int i = 0; i < ranks.length; i++) {
            podium[i].setText(i + 1 + ": " + ranks[i].getX() + " - " + ranks[i].getY() + " points");
        }
    }

}
