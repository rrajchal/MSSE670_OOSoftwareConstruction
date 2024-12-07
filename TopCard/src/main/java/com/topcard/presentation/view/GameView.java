package com.topcard.presentation.view;

import com.topcard.debug.Debug;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class represents the GameView.
 * It initializes and displays the game view using the FXML layout.
 *
 * <p>
 * Author: Rajesh Rajchal
 * Date: 12/07/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class GameView {

    public void start(Stage primaryStage) throws Exception {
        Debug.info("Starting GameView");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/topcard/presentation/GameView.fxml")));
        primaryStage.setTitle("TopCard Game");
        primaryStage.setScene(new Scene(root, 680, 600));
        primaryStage.show();
    }
}
