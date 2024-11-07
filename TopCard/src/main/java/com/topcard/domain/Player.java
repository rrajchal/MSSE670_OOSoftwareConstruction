package com.topcard.domain;

import com.topcard.debug.Debug;
import java.time.LocalDate;
import java.time.Period;

/**
 * The Player class represents a player in the TopCard game.
 * A player has a unique ID, username, password, first name, last name, date of birth, points, admin status, and login status.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/03/2024
 * Subject: MSSE 670 Object Oriented Software Construction
 * </p>
 */
public class Player {
    private int playerId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int points;
    private boolean isAdmin;

    private boolean isLoggedIn;
    private static final int AGE_THRESHOLD = 18;  // Eligible age to play this game
    // 3 by default for this TopCard card game. However, for scalability we can set differently
    private int numOfCards = 3;
    private Card[] hand;

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
        this.points = 0;
        this.isAdmin = false; // Default to false
        this.isLoggedIn = false;
    }

    // Getters and setters
    // Getters
    public int getPlayerId() {
        return playerId;
    }

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

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public int getNumOfCards() {
        if (this.numOfCards <= 0) {
            setNumOfCards(this.numOfCards); // will set to default number of cards.
        }
        return this.numOfCards;
    }

    public Card[] getHand() {
        if (this.hand == null) {
            this.hand = new Card[getNumOfCards()];
            setHand(this.hand);
        }
        return this.hand;
    }

    // Setters
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

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

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setNumOfCards(int numOfCards) {
        if (numOfCards > 0) {
            this.numOfCards = numOfCards;
        }
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

        // Other methods
    public void addPoints(int points) {
        this.points += points;
    }

    public boolean checkAdminStatus() {
        return this.isAdmin;
    }

    /**
     * Checks if the player is authorized based on the age threshold.
     *
     * @return true if the player's age is equal to or greater than the age threshold, false otherwise.
     */
    public boolean isAuthorized() {
        return LocalDate.now().minusYears(AGE_THRESHOLD).isAfter(dateOfBirth) ||
                LocalDate.now().minusYears(AGE_THRESHOLD).isEqual(dateOfBirth);
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
     * Draws a card from the deck and adds it to the player's hand.
     *
     * @param deck the deck to draw a card from
     */
    public void drawCard(Deck deck) {
        if (this.hand == null) {
            this.hand = getHand();
        }
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
     * Calculates and returns the player's age based on their date of birth.
     *
     * @return the age of the player
     */
    public int getAge() {
        LocalDate today = LocalDate.now();
        return Period.between(dateOfBirth, today).getYears();
    }

    /**
     * Returns a string representation of the player, including key details such as
     * username, full name, age, points, admin status, and login status.
     *
     * @return a string representation of the player
     */
    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + getAge() +
                ", points=" + points +
                ", isAdmin=" + isAdmin +
                ", isLoggedIn=" + isLoggedIn +
                '}';
    }

}