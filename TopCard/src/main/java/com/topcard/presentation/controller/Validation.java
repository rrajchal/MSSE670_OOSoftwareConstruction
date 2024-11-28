package com.topcard.presentation.controller;

import javax.swing.JTextField;
import java.awt.Color;

/**
 * The Validation class provides methods to validate user inputs in the application.
 * It ensures that input fields meet the defined validation criteria
 * and sets appropriate error messages when validation fails.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/30/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class Validation {

    /**
     * Validates if the given text field contains any spaces.
     * If spaces are found, sets the error message to the text field.
     *
     * @param textField the text field to validate
     * @param text the text to check for spaces
     * @param errorMessage the error message to display if spaces are found
     * @return true if valid, false otherwise
     */
    public static boolean validateForSpaces(JTextField textField, String text, String errorMessage) {
        if (text.contains(" ")) {
            textField.setBackground(Color.PINK);
            textField.setToolTipText(errorMessage);
            return false;
        } else {
            textField.setBackground(Color.WHITE);
            textField.setToolTipText(null);
            return true;
        }
    }
}
