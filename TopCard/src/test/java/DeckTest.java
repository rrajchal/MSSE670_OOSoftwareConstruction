
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.topcard.Card;
import org.topcard.Deck;

public class DeckTest {

    @Test
    public void testDeckInitialization() {
        Deck deck = new Deck();
        assertEquals("ACE OF HEARTS", deck.deal().toString()); // The first card
        assertEquals("TWO OF HEARTS", deck.deal().toString()); // The second card
    }

    @Test
    public void testShuffle() {
        Deck deck = new Deck();
        Card firstCardBeforeShuffle = deck.deal();
        deck.shuffle();
        Card firstCardAfterShuffle = deck.deal();

        // After shuffle, the first card should most likely be different
        assertNotEquals(firstCardBeforeShuffle, firstCardAfterShuffle);
    }

    @Test
    public void testDeal() {
        Deck deck = new Deck();
        Card dealtCard = deck.deal();
        assertNotNull(dealtCard);

    }

    @Test
    public void testDealAllCards() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            assertNotNull(deck.deal());
        }
        assertNull(deck.deal()); // No more cards to deal
    }
}
