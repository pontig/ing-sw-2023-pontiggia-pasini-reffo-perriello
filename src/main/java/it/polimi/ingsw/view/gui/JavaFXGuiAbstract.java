package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXGuiAbstract {
    GUI gui = new GUI();

    /**
     * sets the gui
     * @param g the gui to be set
     */
    public void setGui(GUI g){
        this.gui = g;
    }
}
