package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static it.polimi.ingsw.enums.State.SET_NUMPLAYERS;

public class AskNumPlayersSceneController extends ObservableView implements GenericSceneController  {
    @FXML
    private Button twoPlayersButton, threePlayersButton, fourPlayersButton;

    @FXML
    public void initialize()  {
        twoPlayersButton.setOnAction(event -> send(2));
        threePlayersButton.setOnAction(event -> send(3));
        fourPlayersButton.setOnAction(event -> send(4));

    }

    private void send(int n) {
        Message msg = new SendDataToServer(SET_NUMPLAYERS,  null, n, 0, false);
        setChangedView();
        notifyObserversView(msg);
        // close this scene
        //twoPlayersButton.getScene().getWindow().hide();
    }
}
