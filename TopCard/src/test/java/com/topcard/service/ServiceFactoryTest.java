package com.topcard.service;

import com.topcard.domain.Card;
import com.topcard.domain.Player;
import com.topcard.domain.PlayerTest;
import com.topcard.service.card.CardService;
import com.topcard.service.card.ICardService;
import com.topcard.service.factory.ServiceFactory;
import com.topcard.service.game.GameService;
import com.topcard.service.game.IGameService;
import com.topcard.service.player.IPlayerService;
import com.topcard.service.player.PlayerService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

/**
 * Unit tests for the ServiceFactory class.
 */
public class ServiceFactoryTest {

    @Test
    public void testCreateGameService() {
        List<Player> players = getPlayers();
        IGameService gameService = ServiceFactory.createGameService(players);
        assertNotNull(gameService);
        assertInstanceOf(GameService.class, gameService);
    }

    @Test
    public void testCreatePlayerService() {
        IPlayerService playerService = ServiceFactory.createPlayerService();
        assertNotNull(playerService);
        assertInstanceOf(PlayerService.class, playerService);
    }

    @Test
    public void testCreateCardService() {
        ICardService cardService = ServiceFactory.createCardService();
        assertNotNull(cardService);
        assertInstanceOf(CardService.class, cardService);
    }

    @Test
    public void testGameServiceStartGame() {
        ICardService cardService = ServiceFactory.createCardService();
        List<Player> players = getPlayers();
        IGameService gameService = ServiceFactory.createGameService(players);
        assertNotNull(gameService);
        gameService.startGame();

        assertEquals(3, players.get(0).getHand().length);  // three cards each player

        assertTrue(cardService.getCardsValue(players.get(0).getHand()) > 5); // Minimum total value of all three cards is at least 6.

    }

    @Test
    public void testPlayerServiceAddPlayer() {
        IPlayerService playerService = ServiceFactory.createPlayerService();
        deleteAllPlayersData();  // remove all data
        Player player = new Player("mickey", "password", "Mickey", "Mouse", LocalDate.of(1990, 1, 1));

        playerService.addPlayer(player);  // added the player whose Id is 1.

        Player playerInData = playerService.getPlayerByUserName("mickey");

        assertEquals(1, playerInData.getPlayerId());
    }

    @Test
    public void testCardServiceNotShuffled() {
        ICardService cardService = ServiceFactory.createCardService();
        Card firstCard = cardService.drawCard();
        Card secondCard = cardService.drawCard();
        Card thirdCard = cardService.drawCard();
        assertEquals(firstCard, new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        assertEquals(secondCard, new Card(Card.Suit.HEARTS, Card.Rank.TWO));
        assertNotEquals(thirdCard, new Card(Card.Suit.HEARTS, Card.Rank.TWO));
    }

    @Test
    public void deleteAllPlayersData() {
        IPlayerService playerService = ServiceFactory.createPlayerService();
        // Call the private method using reflection
        try {
            Method method = PlayerService.class.getDeclaredMethod("deleteAllPlayersData");
            method.setAccessible(true);
            method.invoke(playerService);

            // Check if all data has been deleted List<Player> allPlayers
            List<Player> allPlayers = playerService.getAllPlayers();
            assertTrue(allPlayers.isEmpty());
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    private List<Player> getPlayers() {
        PlayerTest playerTest = new PlayerTest();
        return playerTest.generatePlayers();
    }
}
