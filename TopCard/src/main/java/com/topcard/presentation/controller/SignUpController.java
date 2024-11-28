package com.topcard.presentation.controller;

import com.topcard.business.PlayerManager;
import com.topcard.debug.Debug;
import com.topcard.domain.Player;
import com.topcard.presentation.view.SignUpView;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class represents the controller for the sign-up view.
 * It manages the user interactions for signing up.
 *
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/30/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class SignUpController {

    private final SignUpView signUpView;
    private final JFrame loginFrame;

    /**
     * Constructor to initialize the sign-up controller with the given sign-up view and login frame.
     *
     * @param signUpView the sign-up view
     * @param loginFrame the login frame
     */
    public SignUpController(SignUpView signUpView, JFrame loginFrame) {
        this.signUpView = signUpView;
        this.loginFrame = loginFrame;
        initController();
    }

    /**
     * Initializes the controller by setting up the action listeners.
     */
    private void initController() {
        Debug.info("Initializing SignUp Controller");
        signUpView.getSignUpButton().addActionListener(e -> handleSignUp());
        // If SignUp view is closed, make the login View active again.
        signUpView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                loginFrame.setEnabled(true);
            }
        });
        loginFrame.setEnabled(false); // Disable the login frame when SignUpView is active
    }

    /**
     * Handles the sign-up process when the sign-up button is clicked.
     * It validates the input fields and adds a new player if all fields are valid.
     */
    private void handleSignUp() {
        boolean isValid = true;

        // Validate fields
        if (signUpView.getFirstNameField().getText().trim().isEmpty()) {
            signUpView.getFirstNameField().setBackground(Color.PINK);
            signUpView.getFirstNameField().setText(Constants.REQUIRED);
            isValid = false;
        } else {
            signUpView.getFirstNameField().setBackground(Color.WHITE);
        }

        if (signUpView.getLastNameField().getText().trim().isEmpty()) {
            signUpView.getLastNameField().setBackground(Color.PINK);
            signUpView.getLastNameField().setText(Constants.REQUIRED);
            isValid = false;
        } else {
            signUpView.getLastNameField().setBackground(Color.WHITE);
        }

        if (signUpView.getUsernameField().getText().trim().isEmpty()) {
            signUpView.getUsernameField().setBackground(Color.PINK);
            signUpView.getUsernameField().setText(Constants.REQUIRED);
            isValid = false;
        } else {
            signUpView.getUsernameField().setBackground(Color.WHITE);
        }

        if (new String(signUpView.getPasswordField().getPassword()).trim().isEmpty()) {
            signUpView.getPasswordField().setBackground(Color.PINK);
            signUpView.getPasswordField().setText("");
            isValid = false;
        } else {
            signUpView.getPasswordField().setBackground(Color.WHITE);
        }

        if (new String(signUpView.getRetypePasswordField().getPassword()).trim().isEmpty()) {
            signUpView.getRetypePasswordField().setBackground(Color.PINK);
            signUpView.getRetypePasswordField().setText("");
            isValid = false;
        } else {
            signUpView.getRetypePasswordField().setBackground(Color.WHITE);
        }

        if (signUpView.getDateOfBirthField().getText().trim().isEmpty()) {
            signUpView.getDateOfBirthField().setBackground(Color.PINK);
            signUpView.getDateOfBirthField().setText(Constants.REQUIRED);
            isValid = false;
        } else {
            signUpView.getDateOfBirthField().setBackground(Color.WHITE);
        }

        if (isValid) {
            // Check if passwords match
            String password = new String(signUpView.getPasswordField().getPassword());
            String retypePassword = new String(signUpView.getRetypePasswordField().getPassword());
            if (!password.equals(retypePassword)) {
                JOptionPane.showMessageDialog(signUpView.getSignUpDialog(), Constants.PASSWORD_NOT_MATCH, Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate date of birth format
            LocalDate dateOfBirth;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
                dateOfBirth = LocalDate.parse(signUpView.getDateOfBirthField().getText().trim(), formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(signUpView.getSignUpDialog(), Constants.INVALID_DATE, Constants.ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add player
            PlayerManager playerManager = new PlayerManager();
            Player newPlayer = new Player(
                    signUpView.getUsernameField().getText(),
                    password,
                    signUpView.getFirstNameField().getText(),
                    signUpView.getLastNameField().getText(),
                    dateOfBirth
            );

            playerManager.addPlayer(newPlayer);
            JOptionPane.showMessageDialog(signUpView.getSignUpDialog(), "Sign Up Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            signUpView.getSignUpDialog().dispose(); // Close the SignUpView
            loginFrame.setEnabled(true); // Re-enable the login frame
        }
    }
}
