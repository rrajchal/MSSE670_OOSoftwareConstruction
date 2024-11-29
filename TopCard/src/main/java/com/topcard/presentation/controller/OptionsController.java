package com.topcard.presentation.controller;

import com.topcard.business.PlayerManager;
import com.topcard.debug.Debug;
import com.topcard.domain.Player;
import com.topcard.presentation.view.AddPlayerView;
import com.topcard.presentation.view.OptionsView;
import com.topcard.presentation.view.UpdateView;

import javax.swing.*;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class represents the controller for the options view.
 * It manages the user interactions for the options screen.
 *
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/30/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class OptionsController {

    private final OptionsView optionsView;

    /**
     * Constructor to initialize the options controller with the given options view.
     *
     * @param optionsView the options view
     * @param username the username of the authenticated player
     */
    public OptionsController(OptionsView optionsView, String username) {
        this.optionsView = optionsView;
        initController(username);
    }

    /**
     * Initializes the controller by setting up the visibility and action listeners.
     * It determines whether the authenticated user is an admin and sets the visibility
     * of the Add Player button accordingly.
     *
     * @param username the username of the authenticated player
     */
    private void initController(String username) {
        Debug.info("Initializing Option Controller");
        PlayerManager playerManager = new PlayerManager();
        Player player = playerManager.getPlayerByUsername(username);

        if (player != null && player.isAdmin()) {
            optionsView.setAddPlayerButtonVisibility(true);
        } else {
            optionsView.setAddPlayerButtonVisibility(false);
        }
        optionsView.getPlayGameButton().addActionListener(e -> handlePlayGame());
        optionsView.getUpdateButton().addActionListener(e -> handleUpdate(username));
        optionsView.getAddPlayerButton().addActionListener(e -> handleAddPlayer());

        optionsView.show();
    }

    // TODO TBD
    private void handlePlayGame() {
        JOptionPane.showMessageDialog(optionsView, "Play Game View is TBD", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the update process when the update button is clicked.
     * Opens the UpdateView and initializes the UpdateController.
     *
     * @param username the username of the authenticated player
     */
    private void handleUpdate(String username) {
        disableOptionsView();
        UpdateView updateView = new UpdateView();
        boolean isAdmin = new PlayerManager().getPlayerByUsername(username).isAdmin();
        new UpdateController(updateView, isAdmin);
        attachWindowListener(updateView);
        updateView.show();
    }

    /**
     * Handles the process for adding a new player when the Add Player button is clicked.
     * Opens the AddPlayerView and initializes the AddPlayerController.
     */
    private void handleAddPlayer() {
        disableOptionsView();
        AddPlayerView addPlayerView = new AddPlayerView();
        new AddPlayerController(addPlayerView);
        attachWindowListener(addPlayerView);
        addPlayerView.setVisible(true);
    }

    /**
     * Attaches a window listener to the given JFrame.
     *
     * @param frame the JFrame to which the window listener is attached
     */
    private void attachWindowListener(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                enableOptionsView();
            }
        });
    }

    /**
     * Disables the options view and fades it.
     */
    private void disableOptionsView() {
        optionsView.getUpdateButton().setEnabled(false);
        optionsView.getAddPlayerButton().setEnabled(false);
        fadeTheWindow(0.5f);
    }

    /**
     * Enables the options view and restores its opacity.
     */
    private void enableOptionsView() {
        optionsView.getUpdateButton().setEnabled(true);
        optionsView.getAddPlayerButton().setEnabled(true);
        fadeTheWindow(1.0f);
    }


    private void fadeTheWindow(float opacity) {
        Window window = SwingUtilities.getWindowAncestor(this.optionsView);
        if (window != null) {
            window.setOpacity(opacity);
        }
    }
}
