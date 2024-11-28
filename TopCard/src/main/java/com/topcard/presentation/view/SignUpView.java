package com.topcard.presentation.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;

/**
 * This class represents the view for the sign-up screen.
 * It manages the layout and components of the sign-up interface.
 *
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/30/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class SignUpView {

    // Dialog for displaying the sign-up view
    private JDialog signUpDialog;

    // Panel for organizing the sign-up components
    private JPanel signUpPanel;

    // Text fields for user input
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField retypePasswordField;
    private JTextField dateOfBirthField;
    private JTextField bonusPointField;
    private JButton signUpButton;

    /**
     * Constructor to initialize the sign-up view.
     */
    public SignUpView(JFrame parentFrame) {
        initComponents(parentFrame);
    }

    /**
     * Initializes the components of the sign-up panel and sets up the layout.
     */
    private void initComponents(JFrame parentFrame) {
        signUpDialog = new JDialog(parentFrame, "Sign Up", true);
        signUpDialog.setSize(400, 400);
        signUpDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        signUpDialog.setLocationRelativeTo(null); // Center the dialog on the monitor

        signUpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // First Name
        JLabel firstNameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        signUpPanel.add(firstNameLabel, gbc);

        firstNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        signUpPanel.add(firstNameField, gbc);

        // Last Name
        JLabel lastNameLabel = new JLabel("Last Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpPanel.add(lastNameLabel, gbc);

        lastNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        signUpPanel.add(lastNameField, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        signUpPanel.add(usernameField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        signUpPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        signUpPanel.add(passwordField, gbc);

        // Re-type Password
        JLabel retypePasswordLabel = new JLabel("Re-type Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        signUpPanel.add(retypePasswordLabel, gbc);

        retypePasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        signUpPanel.add(retypePasswordField, gbc);

        // Date of Birth
        JLabel dateOfBirthLabel = new JLabel("Date of Birth (MM/DD/YYYY):");
        gbc.gridx = 0;
        gbc.gridy = 5;
        signUpPanel.add(dateOfBirthLabel, gbc);

        dateOfBirthField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 5;
        signUpPanel.add(dateOfBirthField, gbc);

        // Signup Bonus Point
        JLabel bonusPointLabel = new JLabel("Signup Bonus Point:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        signUpPanel.add(bonusPointLabel, gbc);

        bonusPointField = new JTextField("100", 15);
        bonusPointField.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 6;
        signUpPanel.add(bonusPointField, gbc);

        // Sign Up button
        signUpButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        signUpPanel.add(signUpButton, gbc);

        signUpDialog.add(signUpPanel);
    }

    // Getters for the components
    public JDialog getSignUpDialog() {
        return signUpDialog;
    }

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getRetypePasswordField() {
        return retypePasswordField;
    }

    public JTextField getDateOfBirthField() {
        return dateOfBirthField;
    }

    public JTextField getBonusPointField() {
        return bonusPointField;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    /**
     * Adds a window listener to the sign-up dialog.
     *
     * @param listener the window listener to be added to the sign-up dialog
     */
    public void addWindowListener(WindowAdapter listener) {
        signUpDialog.addWindowListener(listener);
    }

    /**
     * Makes the sign-up dialog visible to the user.
     */
    public void show() {
        signUpDialog.setVisible(true);
    }
}
