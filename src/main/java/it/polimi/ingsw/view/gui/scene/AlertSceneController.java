package it.polimi.ingsw.view.gui.scene;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class AlertSceneController {
    private final Stage stage;
    @FXML
    private Label alertText;
    @FXML
    private Button okButton;

    public AlertSceneController() {
        this.stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(200);
        stage.setResizable(false);
        stage.setTitle("Alert");
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    public void initialize() {
        okButton.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onClick);
    }

    public void setAlertText(Label alertText) {
        this.alertText = alertText;
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    private void onClick(Event e) {
        stage.close();
    }

    public void setAlertText(String text) {
        alertText.setText(text);
    }

    public void showAlert() {
        try {
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
