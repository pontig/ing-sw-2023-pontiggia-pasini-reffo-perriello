package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.gui.scene.NicknameSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX class which starts the main stage and scene.
 * we have to choose between swing or javafx
 * It extends {@link Application}
 */
public class JavaFXGui extends Application {
    private static GUI view;

    public static void setCustomClassInstance(GUI instance) {
        view = instance;
    }

    private GUI getView(){
        return view;
    }

    /**
     * Starts the JavaFXGui instance
     *
     * @param stage the stage
     */
    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/NicknameScene.fxml"));

        Parent rootLayout = null;
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            System.exit(1);
        }

        NicknameSceneController.setView(getView());
        NicknameSceneController controller = loader.getController();

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        SceneController.setActiveScene(scene);
        SceneController.setActiveController(controller);
        SceneController.setMainStage(stage);
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(600);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.setTitle("MyShelfie");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        stage.show();
    }

    /**
     * Stops the ongoing instance
     */
    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
