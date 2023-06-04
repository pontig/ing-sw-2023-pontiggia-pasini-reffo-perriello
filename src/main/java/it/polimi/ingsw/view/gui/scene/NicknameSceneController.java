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

    public static void setView(GUI g){
        view = g;
    }

    @FXML
    private TextField textNickname;
    @FXML
    private Button buttonSubmitNickname;

    @FXML
    public void initialize() {
        buttonSubmitNickname.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onLoginBtnClick);
        textNickname.textProperty().addListener((observable, oldValue, newValue) -> {
        SceneController.getActiveScene().setOnKeyPressed(this::onKeyPressed);
        });
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) onLoginBtnClick(null);
    }

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

    public void letReSubmit() {
        buttonSubmitNickname.setDisable(false);
    }

}
