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

    /**
     * Creates a new AlertSceneController instance
     */
    public AlertSceneController() {
        this.stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(250);
        stage.setResizable(false);
        stage.setTitle("Alert");
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    /**
     * Initializes the controller setting the event handler for the okButton
     */
    @FXML
    public void initialize() {
        okButton.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onClick);
    }

    /**
     * Sets the alert text
     * @param alertText the alert text in a FXML Label
     */
    public void setAlertText(Label alertText) {
        this.alertText = alertText;
    }

    /**
     * Sets the scene
     * @param scene the scene to be set
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Handles the click on the okButton closing the stage
     * @param e the MouseEvent that triggered the event handler
     */
    private void onClick(Event e) {
        stage.close();
    }

    /**
     * Sets the alert text
     * @param text the alert text, as a String
     */
    public void setAlertText(String text) {
        alertText.setText(text);
    }

    /**
     * Shows the stage
     */
    public void showAlert() {
        try {
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
