package org.topcard;

import java.util.Arrays;
import java.util.Collections;

public class Deck {
    private Card[] cards;
    private int currentIndex;
    private final int NUM_OF_CARDS_IN_DECK = 52;

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

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(Arrays.asList(cards));
        currentIndex = 0;
    }

    // Deal a card from the deck
    public Card deal() {
        if (currentIndex >= cards.length) {
            return null; // No more cards to deal
        }
        return cards[currentIndex++];
    }

}
