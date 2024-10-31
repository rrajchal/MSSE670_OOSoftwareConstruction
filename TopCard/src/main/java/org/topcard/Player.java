package org.topcard;

import org.topcard.debug.Debug;
import java.time.LocalDate;
import java.time.Period;

/**
 * The Player class represents a player in the card game.
 * A player has a first name, last name, date of birth, points, admin status, and a hand of cards.
 * Author: Rajesh Rajchal
 * Date: 10/27/2024
 * Subject: MSSE 670 Object Oriented Software construction
 */
public class Player {
    private String username;
    private String password; // In a real application, passwords should be hashed
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int points;
    private boolean isAdmin;
    private boolean isLoggedIn;
    private final int NUM_OF_CARD_IN_HAND = 3;
    private Card[] hand = new Card[NUM_OF_CARD_IN_HAND];  // This card game has 3 cards in hand
    private static final int AGE_THRESHOLD = 18;  // Eligible age to play this game

    /**
     * Constructs a Player with the specified details, excluding admin status.
     * isAdmin is set to false by default.
     *
     * @param username    the player's username
     * @param password    the player's password
     * @param firstName   the player's first name
     * @param lastName    the player's last name
     * @param dateOfBirth the player's date of birth
     */
    public Player(String username, String password, String firstName, String lastName, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.points = 100;
        this.isAdmin = false; // Default to false
        this.isLoggedIn = false;
    }

    /**
     * Draws a card from the deck and adds it to the player's hand.
     *
     * @param deck the deck to draw a card from
     */
    public void drawCard(Deck deck) {
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] == null) {
                Card card = deck.deal();
                if (card != null) {
                    hand[i] = card;
                }
                break;
            }
        }
    }

    /**
     * Calculates and returns the total value of the player's hand.
     *
     * @return the total value of the hand
     */
    public int getHandValue() {
        int totalValue = 0;
        for (Card card : hand) {
            if (card != null) {
                totalValue += card.getRank().getValue();
            }
        }
        return totalValue;
    }

    /**
     * Displays the cards in the player's hand.
     */
    public void showHand() {
        for (Card card : hand) {
            if (card != null) {
                System.out.println(card);
            } else {
                Debug.error("Error: There is no card");
            }
        }
    }

    /**
     * Adds points to the player's total points.
     *
     * @param points the points to add
     */
    public void addPoints(int points) {
        this.points += points;
        // update points
    }

    /**
     * Calculates and returns the player's age based on their date of birth.
     *
     * @return the age of the player
     */
    public int getAge() {
        LocalDate today = LocalDate.now();
        return Period.between(dateOfBirth, today).getYears();
    }

    /**
     * Checks if the player is authorized (18 years old or older).
     *
     * @return true if the player is 18 years old or older, false otherwise
     */
    public boolean isAuthorized() {
        return getAge() >= AGE_THRESHOLD;
    }

    /**
     * Register player
     */
    public void register() {
        // saves this object
    }

    /**
     * Update profile
     */
    public void updateProfile(String newFirstName, String newLastName, LocalDate newDateOfBirth, String newPassword) {
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.dateOfBirth = newDateOfBirth;
        if (newPassword != null && !newPassword.isEmpty()) {
            this.password = newPassword;
        }

        // updates this profile
    }

    /**
     * Attempts to log in the player with the given username and password.
     *
     * @param username the username
     * @param password the password
     * @return true if the login is successful, false otherwise
     */
    public boolean authenticate(String username, String password) {
        // TODO: Need to find Player from stored database. Currently, using this.username and this.password
        this.isLoggedIn = this.username.equals(username) && this.password.equals(password);
        return isLoggedIn;
    }

    // Manage method (only for admin)
    public void manage(String targetUsername, String newFirstName, String newLastName, LocalDate newDateOfBirth,
                       String newPassword, int newPoints, int additionalPoints) {
        if (!isAdmin) {
            return;
        }

        // find Player
        // updateProfile(newFirstName, newLastName, newDateOfBirth, newPassword);
        // addPoints(additionalPoints);
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getPoints() {
        return points;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Card[] getHand() {
        return hand;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

}
