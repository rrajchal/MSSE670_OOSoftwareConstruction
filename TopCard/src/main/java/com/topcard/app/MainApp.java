package com.topcard.app;

import com.topcard.debug.Debug;
import com.topcard.presentation.common.InternalFrame;
import com.topcard.presentation.controller.GameController;
import com.topcard.presentation.controller.OptionsController;
import com.topcard.presentation.view.LoginView;
import com.topcard.presentation.controller.LoginController;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is the main entry point of the application.
 * The MainApp class initializes the application and sets up the main login frame.
 * It also configures the debug mode based on the settings in the config.properties file.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 12/07/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class MainApp {

    private static final JDesktopPane desktopPane = new JDesktopPane(); // Main desktop pane

    /**
     * The main method serves as the entry point of the application.
     * It initializes the login frame and sets the debug mode based on the configuration.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {



        // Set debug mode based on configuration file
        setDebugModeFromConfig();

        // Create the main application frame
        JFrame frame = new JFrame("TopCard Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center the frame on the monitor

        // Create and add a desktop pane to the main frame
        frame.add(desktopPane);
        frame.setVisible(true); // Make the main frame visible

        // Create and set up the menu bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Create the File menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Create the menu items
        JMenuItem loginMenuItem = new JMenuItem("Login");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Add menu items to the menu bar
        fileMenu.add(loginMenuItem);
        fileMenu.add(exitMenuItem);

        // Add action listeners to the menu items
        loginMenuItem.addActionListener(e -> openLogin());
        exitMenuItem.addActionListener(e -> System.exit(0));

        // Initialize and add the login view as an internal frame
        LoginView loginView = new LoginView();
        new LoginController(loginView, desktopPane);
        InternalFrame.addInternalFrame(desktopPane, "Login", loginView.getLoginPanel(), 400, 300, false);
    }

    /**
     * Opens the login view as an internal frame if not already opened.
     */
    private static void openLogin() {
        // Check if login view is already open
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame.getTitle().equals("Login")) {
                frame.toFront();
                try {
                    frame.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                    Debug.error("Error setting selected frame: " + e.getMessage());
                }
                return;
            }
        }

        // Initialize and add the login view as an internal frame
        LoginView loginView = new LoginView();
        new LoginController(loginView, desktopPane);
        InternalFrame.addInternalFrame(desktopPane, "Login", loginView.getLoginPanel(), 400, 300, false);
    }

    /**
     * Sets the debug mode based on the value in the config.properties file.
     * Set debug.mode=true for displaying debug message
     * Set debug.mode=false for not displaying debug message
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
