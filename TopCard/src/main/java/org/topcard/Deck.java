package org.topcard;

import java.util.Arrays;
import java.util.Collections;

/**
 * The Deck class represents a deck of playing cards.
 * It provides methods to shuffle the deck and deal a card from it.
 * <p>
 * Author: Rajesh Rajchal
 * Date: 10/27/2024
 * Subject: MSSE 670 Object Oriented Software construction
 */
public class Deck {
    private Card[] cards;
    private int currentIndex;
    private final int NUM_OF_CARDS_IN_DECK = 52;

    /**
     * Constructs a new Deck with 52 cards, including all suits and ranks.
     */
    public Deck() {
        cards = new Card[NUM_OF_CARDS_IN_DECK];
        currentIndex = 0;
        // Create the deck
        int index = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards[index++] = new Card(suit, rank);
            }
        }
    }

    /**
     * Shuffles the deck, randomizing the order of the cards.
     * Gets ready to deal card. Card will be dealt from the top (currentIndex 0)
     */
    public void shuffle() {
        Collections.shuffle(Arrays.asList(cards));
        currentIndex = 0;
    }

    /**
     * Deals a card from the deck. If no more cards are available, returns null.
     *
     * @return the dealt card, or null if no more cards are available
     */
    public Card deal() {
        if (currentIndex >= cards.length) {
            return null; // No cards to deal
        }
        return cards[currentIndex++];
    }
}
