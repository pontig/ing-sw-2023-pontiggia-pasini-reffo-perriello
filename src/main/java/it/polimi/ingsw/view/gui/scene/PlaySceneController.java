package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class PlaySceneController extends ObservableView implements GenericSceneController {

    @FXML
    private GridPane livingroomGrid;
    @FXML
    private GridPane shelfGrid;

    @FXML
    public void initialize() {
        livingroomGrid.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onLivingroomGridClick);
    }

    @FXML
    private void onLivingroomGridClick(MouseEvent event) {
        System.out.println("Livingroom grid clicked");
    }


}
