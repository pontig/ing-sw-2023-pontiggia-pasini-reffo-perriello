package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.gui.scene.AlertSceneController;
import it.polimi.ingsw.view.gui.scene.AskNumPlayersSceneController;
import it.polimi.ingsw.view.gui.scene.GenericSceneController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SceneController {

    private static Scene activeScene;
    private static String currFxml = "";
    private static Stage mainStage;
    private static GenericSceneController activeController;


    /**
     * @return the current fxml
     */
    public static String getCurrFxml() {
        return currFxml;
    }

    /**
     * @return the active scene
     */
    public static Scene getActiveScene() {
        return activeScene;
    }

    /**
     * Sets the active scene
     * @param activeScene the active scene to be set
     */
    public static void setActiveScene(Scene activeScene) {
        SceneController.activeScene = activeScene;
    }

    /**
     * @return the main stage
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * Sets the main stage
     * @param mainStage the main stage to be set
     */
    public static void setMainStage(Stage mainStage) {
        SceneController.mainStage = mainStage;
    }

    /**
     * @return the active scene controller
     */
    public static GenericSceneController getActiveController() {
        return activeController;
    }

    /**
     * Sets the active scene controller
     * @param activeController the scene controller to be set
     */
    public static void setActiveController(GenericSceneController activeController) {
        SceneController.activeController = activeController;
    }

    /**
     * Changes the root pane to be showed
     * @param observerList the list of observers of the view
     * @param fxml the name of the fxml to load
     */
    public static void changeRootPane(Vector<ObserverView<? extends ObservableView<Message>, Message>> observerList, String fxml) {

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            ObservableView<Message> controller = loader.getController();
            observerList.forEach(controller::addObserverView);
            activeController = (GenericSceneController) controller;
            activeScene.setRoot(root);
            currFxml = fxml;
            if (fxml.equals("PlayScene.fxml")) {
                //mainStage.setWidth(1300);
                //mainStage.setHeight(1000);
                //mainStage.setFullScreen(true);
                mainStage.setMaximized(true);
            }
            if (fxml.equals("EndGameScene.fxml")) {
                mainStage.setWidth(1300);
                mainStage.setHeight(820);
                mainStage.setResizable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows an alert message
     * @param message the message to be shown
     */
    public static void showMessage(String message) {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/AlertScene.fxml"));

            Parent parent;

            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            AlertSceneController alertSceneController = loader.getController();
            Scene alertScene = new Scene(parent);
            alertSceneController.setScene(alertScene);
            alertSceneController.setAlertText(message);
            alertSceneController.showAlert();
        });
    }
}
