package com.topcard.app;

import com.topcard.debug.Debug;
import com.topcard.presentation.view.LoginView;
import com.topcard.presentation.controller.LoginController;

import javax.swing.JFrame;

/**
 * This is the main entry point of the application.
 */
public class MainApp {
    public static void main(String[] args) {
        // Set true/false for debugging logs
        Debug.setDebugMode(true);

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        LoginView loginView = new LoginView();
        new LoginController(loginView);

        frame.add(loginView.getLoginPanel());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the frame on the monitor
    }
}
