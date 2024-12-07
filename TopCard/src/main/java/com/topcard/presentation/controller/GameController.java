package com.topcard.presentation.controller;

import com.topcard.debug.Debug;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.regex.Pattern;

/**
 * This class represents the GameController.
 * It manages the user interactions for the game view.
 *
 * <p>
 * Author: Rajesh Rajchal
 * Date: 12/07/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class GameController {

    // ImageView elements
    @FXML
    private ImageView player1C1, player1C2, player1C3;
    @FXML
    private ImageView player2C1, player2C2, player2C3;
    @FXML
    private ImageView player3C1, player3C2, player3C3;
    @FXML
    private ImageView player4C1, player4C2, player4C3;

    // Button elements
    @FXML
    private Button startButton;

    // TextField elements
    @FXML
    private TextField winnerTextField;
    @FXML
    private TextField betAmountTextField;
    @FXML
    private TextField player1FirstName, player1Balance, player1Change;
    @FXML
    private TextField player2FirstName, player2Balance, player2Change;
    @FXML
    private TextField player3FirstName, player3Balance, player3Change;
    @FXML
    private TextField player4FirstName, player4Balance, player4Change;

    // Label elements
    @FXML
    private Label player1FirstNameLabel, player1BalanceLabel, player1ChangeLabel;
    @FXML
    private Label player2FirstNameLabel, player2BalanceLabel, player2ChangeLabel;
    @FXML
    private Label player3FirstNameLabel, player3BalanceLabel, player3ChangeLabel;
    @FXML
    private Label player4FirstNameLabel, player4BalanceLabel, player4ChangeLabel;

    int betAmount = 0;

    // Method to handle start button action
    @FXML
    private void startButtonPressed() {
        // TODO need to implement all game behavior
        System.out.println("Start Game button pressed");

        checkForBetAmount();
    }

    private void checkForBetAmount() {
        Pattern pattern = Pattern.compile("\\d+");
        String betAmountEntered = betAmountTextField.getText().trim();
        if (pattern.matcher(betAmountEntered).matches()) {
            winnerTextField.setText("");
            betAmount = Integer.parseInt(betAmountEntered);
        } else {
            winnerTextField.setStyle("-fx-text-fill: red;");
            winnerTextField.setText("Invalid amount in the Bet TextField");
        }

        Debug.info("Bet amount: " + betAmountEntered);
    }
}
