package com.topcard.presentation.controller;

import com.topcard.business.PlayerManager;
import com.topcard.debug.Debug;
import com.topcard.domain.Player;
import com.topcard.presentation.view.LoginView;
import com.topcard.presentation.view.OptionsView;
import com.topcard.presentation.view.SignUpView;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * This class represents the controller for the login view.
 * It manages the user interactions for logging in.
 *
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/30/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class LoginController {

    private final LoginView loginView;
    /**
     * Constructor to initialize the login controller with the given login view.
     *
     * @param loginView the login view
     */
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        initController();
    }

    /**
     * Initializes the controller by setting up the action listeners.
     */
    private void initController() {
        Debug.info("Initializing Login Controller");
        // for login button
        loginView.getLoginButton().addActionListener(e -> handleLogin());

        // for signup link
        loginView.getSignUpLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleSignUp();
            }
        });
    }

    /**
     * Handles the login process when the login button is clicked.
     * It gathers the user inputs and initiates the authentication process.
     */
    private void handleLogin() {
        String username = loginView.getUsernameField().getText();
        String password = new String(loginView.getPasswordField().getPassword());

        if (validateInputs(loginView.getUsernameField(), username, Constants.USERNAME_CANNOT_HAVE_SPACES) &&
            validateInputs(loginView.getPasswordField(), password, Constants.PASSWORD_CANNOT_HAVE_SPACES) &&
            authenticate(username, password)) {
            loginView.getLoginPanel().getTopLevelAncestor().setVisible(false); // Close the LoginView
            OptionsView optionsView = new OptionsView();
            new OptionsController(optionsView, username);
        } else {
            loginView.getMessageLabel().setForeground(Color.RED);
            loginView.getMessageLabel().setText(Constants.INVALID_USERNAME_OR_PASSWORD);
        }
    }

    /**
     * Validates the user inputs for username and password.
     *
     * @param textField the text field to validate
     * @param text the text entered by the user
     * @param errorMessage the error message to display if invalid
     * @return true if inputs are valid, false otherwise
     */
    private boolean validateInputs(JTextField textField, String text, String errorMessage) {
        return Validation.validateForSpaces(textField, text, errorMessage);
    }

    /**
     * Authenticates the user by verifying the provided username and password.
     *
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return true if the username and password are valid, false otherwise
     */
    private boolean authenticate(String username, String password) {
        PlayerManager playerManager = new PlayerManager();
        Player player = playerManager.getPlayerByUsername(username);

        return username != null && !username.isEmpty() &&
                password != null && !password.isEmpty() &&
                player != null && player.getUsername().equals(username) &&
                playerManager.verifyPassword(password, player.getPassword());
    }

    /**
     * Handles the sign-up process when the sign-up link is clicked.
     * It opens the SignUpView and disables the Login frame.
     */
    private void handleSignUp() {
        SignUpView signUpView = new SignUpView((JFrame) loginView.getLoginPanel().getTopLevelAncestor());
        new SignUpController(signUpView, (JFrame) loginView.getLoginPanel().getTopLevelAncestor());
        signUpView.show();
    }
}
