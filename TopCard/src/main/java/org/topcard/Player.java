package org.topcard;

import org.topcard.debug.Debug;

import java.time.LocalDate;
import java.time.Period;

/**
 * The Player class represents a player in the card game.
 * A player has a first name, last name, date of birth, points, admin status, and a hand of cards.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 10/27/2024
 * Subject: MSSE 670 Object Oriented Software construction
 */
public class Player {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int points;
    private boolean isAdmin;
    private final int NUM_OF_CARD_IN_HAND = 3;
    private Card[] hand = new Card[NUM_OF_CARD_IN_HAND];  // This card game has 3 cards in hand
    private static final int AGE_THRESHOLD = 18;  // Eligible age to play this game

    /**
     * Constructs a Player with the specified details, excluding admin status.
     * isAdmin is set to false by default.
     *
     * @param firstName   the player's first name
     * @param lastName    the player's last name
     * @param dateOfBirth the player's date of birth
     */
    public Player(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.isAdmin = false; // Default to false
    }

    // Getters
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

    // Setters
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

    // Other methods

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
}
