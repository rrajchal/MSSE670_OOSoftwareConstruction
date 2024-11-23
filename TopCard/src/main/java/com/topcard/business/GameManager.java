package com.topcard.business;

import com.topcard.domain.Card;
import com.topcard.domain.Player;
import com.topcard.service.factory.ServiceFactory;
import com.topcard.service.game.IGameService;

import java.util.List;

public class GameManager {
    private final IGameService gameService;

    public GameManager(List<Player> players) {
        this.gameService = ServiceFactory.createGameService(players);
    }

    public void startGame() {
        gameService.startGame();
    }

    public List<Card[]> getHands() {
        return gameService.getHands();
    }

    public void showHands() {
        gameService.showHands();
    }

    public void dealCards() {
        gameService.dealCards();
    }

    public List<Player> executeBettingRound(int points) {
        return gameService.executeBettingRound(points);
    }

    public List<Player> determineWinner() {
        return gameService.determineWinner();
    }

    public void updateProfile(Player player) {
        gameService.updateProfile(player);
    }

    public void updateProfiles(List<Player> players) {
        gameService.updateProfiles(players);
    }

    public void displayWinners(List<Player> winners) {
        gameService.displayWinners(winners);
    }
}
