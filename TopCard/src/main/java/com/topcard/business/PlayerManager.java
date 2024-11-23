package com.topcard.business;

import com.topcard.domain.Player;
import com.topcard.service.factory.ServiceFactory;
import com.topcard.service.player.IPlayerService;
import com.topcard.service.player.PlayerService;

import java.time.LocalDate;
import java.util.List;

public class PlayerManager {
    private final IPlayerService playerService;

    public PlayerManager() {
        this.playerService = ServiceFactory.createService(PlayerService.class);
    }

    public void addPlayer(Player player) {
        playerService.addPlayer(player);
    }

    public void addPlayers(List<Player> players) {
        playerService.addPlayers(players);
    }

    public void removePlayer(int playerId) {
        playerService.removePlayer(playerId);
    }

    public Player getPlayerById(int playerId) {
        return playerService.getPlayerById(playerId);
    }

    public Player getPlayerByUsername(String userName) {
        return playerService.getPlayerByUsername(userName);
    }

    public void changePoints(int playerId, int points) {
        playerService.changePoints(playerId, points);
    }

    public boolean isPlayerAdmin(int playerId) {
        return playerService.isPlayerAdmin(playerId);
    }

    public void makePlayerAdmin(int playerId) {
        playerService.makePlayerAdmin(playerId);
    }

    public void updateProfile(Player player) {
        playerService.updateProfile(player);
    }

    public void updateProfile(int playerId, String newFirstName, String newLastName, LocalDate newDateOfBirth) {
        playerService.updateProfile(playerId, newFirstName, newLastName, newDateOfBirth);
    }

    public void updateProfiles(List<Player> players) {
        playerService.updateProfiles(players);
    }

    public int retrievePointForPlayer(int playerId) {
        return playerService.retrievePointForPlayer(playerId);
    }

    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}
