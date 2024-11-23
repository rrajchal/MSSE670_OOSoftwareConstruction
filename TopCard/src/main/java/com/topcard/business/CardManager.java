package com.topcard.business;

import com.topcard.domain.Card;
import com.topcard.domain.Deck;
import com.topcard.service.card.CardService;
import com.topcard.service.card.ICardService;
import com.topcard.service.factory.ServiceFactory;

public class CardManager {
    private final ICardService cardService;

    public CardManager() {
        this.cardService = ServiceFactory.createService(CardService.class);
    }

    public void shuffleDeck() {
        cardService.shuffleDeck();
    }

    public Deck createDeck() {
        return cardService.createDeck();
    }

    public Deck createShuffledDeck() {
        return cardService.createShuffledDeck();
    }

    public Card drawCard() {
        return cardService.drawCard();
    }

    public int getRemainingCards() {
        return cardService.getRemainingCards();
    }

    public int getCardsValue(Card[] cards) {
        return cardService.getCardsValue(cards);
    }

    public boolean isDeckEmpty() {
        return cardService.isDeckEmpty();
    }
}
