package com.topcard.presentation.controller;

import com.topcard.business.PlayerManager;
import com.topcard.debug.Debug;
import com.topcard.domain.Player;
import com.topcard.presentation.view.OptionsView;
import com.topcard.presentation.view.UpdateView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        optionsView.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdate(username);
            }
        });

        optionsView.show();
    }

    /**
     * Handles the update process when the update button is clicked.
     * Opens the UpdateView and initializes the UpdateController.
     *
     * @param username the username of the authenticated player
     */
    private void handleUpdate(String username) {
        UpdateView updateView = new UpdateView();
        boolean isAdmin = new PlayerManager().getPlayerByUsername(username).isAdmin();
        new UpdateController(updateView, isAdmin);
        updateView.show();
    }
}
