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
    private String nickname;

    /**
     * Initializes the controller setting the event handler for the buttons
     */
    @FXML
    public void initialize()  {
        twoPlayersButton.setOnAction(event -> send(2));
        threePlayersButton.setOnAction(event -> send(3));
        fourPlayersButton.setOnAction(event -> send(4));

    }

    /**
     * Sends the number of players chosen by the user to the server
     * @param n the number of players chosen by the user
     */
    private void send(int n) {
        Message msg = new SendDataToServer(SET_NUMPLAYERS,  nickname, n, 0, false);
        System.out.println("AskNumPlayersSceneController: " + n + " from " + nickname);
        setChangedView();
        notifyObserversView(msg);
    }

    /**
     * Sets the nickname of the player
     * @param nickname the nickname of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
