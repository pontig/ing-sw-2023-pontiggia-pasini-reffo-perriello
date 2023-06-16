package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.enums.State.SET_NICKNAME;

public class NicknameSceneController extends GUI implements GenericSceneController {

    private static GUI view;
    @FXML
    private TextField textNickname;
    @FXML
    private Button buttonSubmitNickname;

    /**
     * Sets the view
     * @param g the view to be set
     */
    public static void setView(GUI g) {
        view = g;
    }

    /**
     * Initializes the controller setting the event handler for the button and the text field
     */
    @FXML
    public void initialize() {
        buttonSubmitNickname.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onLoginBtnClick);
        textNickname.textProperty().addListener((observable, oldValue, newValue) -> {
            SceneController.getActiveScene().setOnKeyPressed(this::onKeyPressed);
        });
    }

    /**
     * Sends the nickname chosen by the user to the server
     * @param event the event that triggered the method
     */
    @FXML
    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) onLoginBtnClick(null);
    }

    /**
     * Sends the nickname chosen by the user to the server
     * @param event the event that triggered the method (mouse click on the login button)
     */
    @FXML
    private void onLoginBtnClick(MouseEvent event) {
        buttonSubmitNickname.setDisable(true);
        String nickname = textNickname.getText();
        if (nickname.equals("")) {
            letReSubmit();
            return;
        }
        view.setNickname(nickname);
        Message msg = new SendDataToServer(SET_NICKNAME, nickname, 0, 0, false);
        view.setChangedView();
        view.notifyObserversView(msg);
    }

    public void setError() {
        textNickname.setText("");
    }

    /**
     * Enables the button to be clicked again
     * used to prevent the user from spamming the button
     */
    public void letReSubmit() {
        buttonSubmitNickname.setDisable(false);
    }

}
