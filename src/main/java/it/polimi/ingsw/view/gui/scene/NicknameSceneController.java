package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.observer.ObservableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.enums.State.SET_NICKNAME;

public class NicknameSceneController extends ObservableView implements GenericSceneController {
    
    @FXML
    private TextField textNickname;
    @FXML
    private Button buttonSubmitNickname;

    @FXML
    public void initialize() {
        buttonSubmitNickname.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onLoginBtnClick);
    }

    @FXML
    private void onLoginBtnClick(MouseEvent event) {
        String nickname = textNickname.getText();
        Message msg = new SendDataToServer(SET_NICKNAME, nickname, 0, 0, false);
        setChangedView();
        notifyObserversView(msg);
    }

    public void setError() {
        textNickname.setText("");
    }

}
