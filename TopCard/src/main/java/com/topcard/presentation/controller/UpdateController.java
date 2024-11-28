package com.topcard.presentation.controller;

import com.topcard.business.PlayerManager;
import com.topcard.debug.Debug;
import com.topcard.domain.Player;
import com.topcard.presentation.view.UpdateView;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

/**
 * This class represents the controller for the update view.
 * It manages the user interactions for updating player information.
 *
 * <p>
 * Author: Rajesh Rajchal
 * Date: 12/01/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class UpdateController {

    private final UpdateView updateView;
    private final PlayerManager playerManager;
    private final boolean isAdmin;

    /**
     * Constructor to initialize the update controller with the given update view and admin status.
     *
     * @param updateView the update view
     * @param isAdmin whether the user is an admin
     */
    public UpdateController(UpdateView updateView, boolean isAdmin) {
        this.updateView = updateView;
        this.playerManager = new PlayerManager();
        this.isAdmin = isAdmin;
        initController();
    }

    /**
     * Initializes the controller by setting up the action listeners.
     */
    private void initController() {
        Debug.info("Initializing UpdateController");
        if (isAdmin) {
            updateView.getSearchButton().addActionListener(e -> handleSearch());
        } else {
            updateView.getSearchField().setVisible(false);
            updateView.getSearchButton().setVisible(false);
            updateView.getIsAdminCheckBox().setVisible(false);
        }
        updateView.getUpdateButton().addActionListener(e -> handleUpdate());
    }

    /**
     * Handles the search process when the search button is clicked.
     * It looks up the player by ID and populates the fields if the player is found.
     */
    private void handleSearch() {
        int playerId;
        try {
            playerId = Integer.parseInt(updateView.getSearchField().getText().trim());
        } catch (NumberFormatException e) {
            updateView.getMessageLabel().setForeground(Color.RED);
            updateView.getMessageLabel().setText(Constants.NO_PLAYER_FOUND);
            return;
        }

        Player player = playerManager.getPlayerById(playerId);

        if (player != null) {
            updateView.getIdField().setText(String.valueOf(player.getPlayerId()));
            updateView.getFirstNameField().setText(player.getFirstName());
            updateView.getLastNameField().setText(player.getLastName());
            updateView.getUsernameField().setText(player.getUsername());
            updateView.getPasswordField().setText(player.getPassword());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
            updateView.getDateOfBirthField().setText(player.getDateOfBirth().format(formatter));
            updateView.getPointsField().setText(String.valueOf(player.getPoints()));
            updateView.getIsAdminCheckBox().setSelected(player.isAdmin());
            updateView.getMessageLabel().setText("");
        } else {
            updateView.getFirstNameField().setText("");
            updateView.getLastNameField().setText("");
            updateView.getUsernameField().setText("");
            updateView.getPasswordField().setText("");
            updateView.getDateOfBirthField().setText("");
            updateView.getPointsField().setText("");
            updateView.getIsAdminCheckBox().setSelected(false);
            updateView.getMessageLabel().setText("");
            updateView.getMessageLabel().setForeground(Color.RED);
            updateView.getMessageLabel().setText(Constants.NO_PLAYER_FOUND);
        }
    }

    /**
     * Handles the update process when the update button is clicked.
     * It validates the input fields and updates the player information if all fields are valid.
     */
    private void handleUpdate() {
        boolean isValid = true;

        // Validate fields
        if (updateView.getFirstNameField().getText().trim().isEmpty()) {
            updateView.getFirstNameField().setBackground(Color.PINK);
            updateView.getFirstNameField().setToolTipText(Constants.REQUIRED);
            isValid = false;
        } else {
            updateView.getFirstNameField().setBackground(Color.WHITE);
        }

        if (updateView.getLastNameField().getText().trim().isEmpty()) {
            updateView.getLastNameField().setBackground(Color.PINK);
            updateView.getLastNameField().setToolTipText(Constants.REQUIRED);
            isValid = false;
        } else {
            updateView.getLastNameField().setBackground(Color.WHITE);
        }

        if (updateView.getUsernameField().getText().trim().isEmpty()) {
            updateView.getUsernameField().setBackground(Color.PINK);
            updateView.getUsernameField().setToolTipText(Constants.REQUIRED);
            isValid = false;
        } else {
            updateView.getUsernameField().setBackground(Color.WHITE);
        }

        if (updateView.getPasswordField().getPassword().length == 0) {
            updateView.getPasswordField().setBackground(Color.PINK);
            updateView.getPasswordField().setToolTipText(Constants.REQUIRED);
            isValid = false;
        } else {
            updateView.getPasswordField().setBackground(Color.WHITE);
        }

        if (updateView.getDateOfBirthField().getText().trim().isEmpty()) {
            updateView.getDateOfBirthField().setBackground(Color.PINK);
            updateView.getDateOfBirthField().setToolTipText(Constants.REQUIRED);
            isValid = false;
        } else {
            updateView.getDateOfBirthField().setBackground(Color.WHITE);
        }

        if (updateView.getPointsField().getText().trim().isEmpty()) {
            updateView.getPointsField().setBackground(Color.PINK);
            updateView.getPointsField().setToolTipText(Constants.REQUIRED);
            isValid = false;
        } else {
            updateView.getPointsField().setBackground(Color.WHITE);
        }

        if (isValid) {
            // Validate date of birth format
            LocalDate dateOfBirth;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
                dateOfBirth = LocalDate.parse(updateView.getDateOfBirthField().getText().trim(), formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(updateView.getUpdateFrame(), Constants.INVALID_DATE,
                        Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update player information
            Player updatedPlayer = new Player(
                    updateView.getUsernameField().getText(),
                    new String(updateView.getPasswordField().getPassword()),
                    updateView.getFirstNameField().getText(),
                    updateView.getLastNameField().getText(),
                    dateOfBirth);

            try {
                int points = Integer.parseInt(updateView.getPointsField().getText().trim());
                updatedPlayer.setPoints(points);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(updateView.getUpdateFrame(), Constants.INVALID_POINT, Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isAdmin) {
                updatedPlayer.setPlayerId(Integer.parseInt(updateView.getIdField().getText()));
                updatedPlayer.setAdmin(updateView.getIsAdminCheckBox().isSelected());
            }

            playerManager.updateProfile(updatedPlayer);
            JOptionPane.showMessageDialog(updateView.getUpdateFrame(),
                    "Player information updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
