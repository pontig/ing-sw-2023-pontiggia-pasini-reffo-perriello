package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;
import it.polimi.ingsw.view.gui.scene.AlertSceneController;
import it.polimi.ingsw.view.gui.scene.GenericSceneController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SceneController {

    private static Scene activeScene;
    private static String currFxml = "";
    private static Stage mainStage;
    private static GenericSceneController activeController;


    public static String getCurrFxml() {
        return currFxml;
    }

    public static Scene getActiveScene() {
        return activeScene;
    }

    public static void setActiveScene(Scene activeScene) {
        SceneController.activeScene = activeScene;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        SceneController.mainStage = mainStage;
    }

    public static GenericSceneController getActiveController() {
            return activeController;
    }

    public static void setActiveController(GenericSceneController activeController) {
        SceneController.activeController = activeController;
    }

    public void ChangeRootPane(List<ObserverView> observerList, String fxml) {

        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxml));
        Parent root = null;
            root = loader.load();
            ObservableView<Message> controller = loader.getController();
            // TODO: manca il setObserverList
            activeController = (GenericSceneController) controller;
            activeScene.setRoot(root);
            currFxml = fxml;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(String message) {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();

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
