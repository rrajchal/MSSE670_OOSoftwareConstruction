package org.topcard;

/**
 * The Card class represents a playing card with a specific suit and rank.
 * It is immutable, meaning once it is created, its suit and rank cannot be changed.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 10/27/2024
 * Subject: MSSE 670 Object Oriented Software construction
 */
public class Card {
    private final Suit suit;
    private final Rank rank;

    /**
     * Constructs a new Card with the specified suit and rank.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * The Suit enum represents the four possible suits of a playing card.
     */
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    /**
     * The Rank enum represents the possible ranks of a playing card.
     * Each rank has an associated value.
     */
    public enum Rank {
        ACE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10);

        private final int value;

        /**
         * Constructs a new Rank with the specified value.
         *
         * @param value the value of the rank
         */
        Rank(int value) {
            this.value = value;
        }

        /**
         * Returns the value of the rank.
         *
         * @return the value of the rank
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns a string representation of the card in the format "RANK OF SUIT".
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return rank + " OF " + suit;
    }
}
