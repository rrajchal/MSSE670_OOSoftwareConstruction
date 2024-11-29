package com.topcard.app;

import com.topcard.debug.Debug;
import com.topcard.presentation.view.LoginView;
import com.topcard.presentation.controller.LoginController;

import javax.swing.JFrame;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is the main entry point of the application.
 * The MainApp class initializes the application and sets up the main login frame.
 * It also configures the debug mode based on the settings in the config.properties file.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/30/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class MainApp {

    /**
     * The main method serves as the entry point of the application.
     * It initializes the login frame and sets the debug mode based on the configuration.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setDebugModeFromConfig();

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);

        LoginView loginView = new LoginView();
        new LoginController(loginView);

        frame.add(loginView.getLoginPanel());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the frame on the monitor
    }

    /**
     * Sets the debug mode based on the value in the config.properties file.
     * <p>
     * This method reads the debug mode setting from the config.properties file and
     * configures the Debug class accordingly. If the properties file is not found or
     * an error occurs while reading the file, the debug mode is not set.
     * </p>
     */
    private static void setDebugModeFromConfig() {
        Properties properties = new Properties();
        try (InputStream input = MainApp.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                Debug.setDebugMode(true);
                Debug.error("Sorry, unable to find config.properties");
                Debug.setDebugMode(false);
                return;
            }

            // Load the properties file
            properties.load(input);

            // Set debug mode based on the property
            boolean debugMode = Boolean.parseBoolean(properties.getProperty("debug.mode"));
            Debug.setDebugMode(debugMode);
        } catch (IOException ex) {
            Debug.setDebugMode(true);
            Debug.error(ex.toString());
            Debug.setDebugMode(false);
        }
    }
}
