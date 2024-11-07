package com.topcard.service;

import com.topcard.domain.Player;
import com.topcard.service.player.PlayerService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerServiceTest {

    private PlayerService testPlayer;

    @Before
    public void setUp() {
        testPlayer = new PlayerService();
        deleteAllPlayersData();  // clean all data before starting each test
    }

    @Test
    public void testAddPlayer() {
        Player player = new Player("Mickey", "mouse123", "Mickey", "Mouse", LocalDate.of(1928, 11, 18));
        testPlayer.addPlayer(player);
        List<Player> allPlayers = testPlayer.getAllPlayers();
        assertEquals(1, allPlayers.size());
    }

    @Test
    public void testDeleteAllPlayersData() {
        // Call the private method using reflection
        try {
            Method method = PlayerService.class.getDeclaredMethod("deleteAllPlayersData");
            method.setAccessible(true);
            method.invoke(testPlayer);

            // Check if all data has been deleted
            List<Player> allPlayers = testPlayer.getAllPlayers();
            assertTrue(allPlayers.isEmpty());
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    @Test
    public void testRemovePlayer() {
        Player player = new Player("Minnie", "mouse123", "Minnie", "Mouse", LocalDate.of(1928, 11, 18));
        testPlayer.addPlayer(player);
        int playerId = player.getPlayerId();

        testPlayer.removePlayer(playerId);
        Player removedPlayer = testPlayer.getPlayerById(playerId);
        assertNull(removedPlayer);
    }


    @Test
    public void testGetPlayerById() {
        Player player = new Player("Donald", "duck123", "Donald", "Duck", LocalDate.of(1934, 6, 9));
        testPlayer.addPlayer(player);
        int playerId = player.getPlayerId();

        Player fetchedPlayer = testPlayer.getPlayerById(playerId);
        assertEquals(player.getUsername(), fetchedPlayer.getUsername());
        assertEquals(player.getFirstName(), fetchedPlayer.getFirstName());
    }

    @Test
    public void testGetAllPlayers() {
        createSamplePlayerData();
        List<Player> playerList = testPlayer.getAllPlayers();
        assertEquals(5, playerList.size());
    }

    void createSamplePlayerData() {
        Player mickey = new Player("Mickey", "mouse123", "Mickey", "Mouse", LocalDate.of(1928, 11, 18));
        Player minnie = new Player("Minnie", "mouse123", "Minnie", "Mouse", LocalDate.of(1928, 11, 18));
        Player donald = new Player("Donald", "duck123", "Donald", "Duck", LocalDate.of(1934, 6, 9));
        Player goofy = new Player("Goofy", "goofy123", "Goofy", "Goof", LocalDate.of(1932, 5, 25));
        Player daisy = new Player("Daisy", "duck123", "Daisy", "Duck", LocalDate.of(1940, 1, 1));
        testPlayer.addPlayer(mickey);
        testPlayer.addPlayer(minnie);
        testPlayer.addPlayer(donald);
        testPlayer.addPlayer(goofy);
        testPlayer.addPlayer(daisy);
    }

    void deleteAllPlayersData() {
        // Call the private method using reflection
        try {
            Method method = PlayerService.class.getDeclaredMethod("deleteAllPlayersData");
            method.setAccessible(true);
            method.invoke(testPlayer);
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    @Test
    public void testAddPoints() {
        Player player = new Player("Goofy", "goofy123", "Goofy", "Goof", LocalDate.of(1932, 5, 25));
        testPlayer.addPlayer(player);
        int playerId = player.getPlayerId();

        testPlayer.addPoints(playerId, 50);

        Player updatedPlayer = testPlayer.getPlayerById(playerId);
        assertEquals(50, updatedPlayer.getPoints());
    }

    @Test
    public void testIsPlayerAdmin() {
        Player player = new Player("Daisy", "duck123", "Daisy", "Duck", LocalDate.of(1940, 1, 1));
        testPlayer.addPlayer(player); // by default admin is false
        int playerId = player.getPlayerId();

        assertFalse(testPlayer.isPlayerAdmin(playerId));
        testPlayer.makePlayerAdmin(playerId); // make player to admin
        assertTrue(testPlayer.isPlayerAdmin(playerId));
    }

    @Test
    public void testUpdateProfile() {
        Player player = new Player("Pluto", "dog123", "Pluto", "Dog", LocalDate.of(1930, 9, 1));
        testPlayer.addPlayer(player);
        int playerId = player.getPlayerId();

        testPlayer.updateProfile(playerId, "NewPluto", "NewDog", LocalDate.of(1930, 9, 5));

        Player updatedPlayer = testPlayer.getPlayerById(playerId);
        assertEquals("NewPluto", updatedPlayer.getFirstName());
        assertEquals("NewDog", updatedPlayer.getLastName());
        assertEquals(LocalDate.of(1930, 9, 5), updatedPlayer.getDateOfBirth());

    }
}
