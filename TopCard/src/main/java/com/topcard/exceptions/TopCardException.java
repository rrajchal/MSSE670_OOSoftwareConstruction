package com.topcard.exceptions;

/**
 * The TopCardException class is a custom exception that extends Exception.
 * This exception is used to handle specific error scenarios in the TopCard application.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 10/27/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 */
public class TopCardException extends RuntimeException {

    /**
     * Constructs a new TopCardException with the specified detail message.
     *
     * @param message the detail message
     */
    public TopCardException(String message) {
        super(message);
    }

    /**
     * Constructs a new TopCardException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public TopCardException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new TopCardException with the cause of the exception.
     *
     * @param cause the cause of the exception
     */
    public TopCardException(Throwable cause) {
        super(cause);
    }
}
