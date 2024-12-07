package com.topcard.presentation.view;

import com.topcard.debug.Debug;
import com.topcard.domain.Player;
import com.topcard.presentation.controller.GameController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
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
    private final List<Player> players;

    /**
     * Constructs a new GameView with the specified list of players.
     *
     * @param players the list of players participating in the game
     */
    public GameView(List<Player> players) {
        this.players = players;
    }

    /**
     * Starts the GameView by loading the FXML file and displaying the primary stage.
     * Passes the list of players to the GameController.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if there is an issue loading the FXML file
     */
    public void start(Stage primaryStage) throws Exception {
        Debug.info("Starting GameView");
        try {
            String fxmlPath = "/com/topcard/presentation/GameView.fxml";
            Debug.info("Attempting to load FXML from: " + fxmlPath);

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath), "FXML resource not found"));
            Parent root = loader.load();

            // Pass the players to the controller
            GameController controller = loader.getController();
            controller.setPlayersName(players);
            controller.setPlayersPoints(players);
            // Set up the primary stage
            primaryStage.setTitle("TopCard Game");
            primaryStage.setScene(new Scene(root, 680, 600));
            primaryStage.setResizable(false);

            // Center the stage on the screen
            Platform.runLater(() -> {
                primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2);
            });

            primaryStage.show();
        } catch (Exception e) {
            Debug.error("Failed to load FXML file: " + e.getMessage());
        }
    }
}
