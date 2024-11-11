package com.topcard.service.game;

import com.topcard.domain.Card;
import com.topcard.domain.Game;
import com.topcard.domain.Player;
import com.topcard.service.card.CardService;
import com.topcard.service.player.PlayerService;

import java.util.List;

public class GameService implements IGameService {
    private final Game game;
    private final PlayerService playerService;

    public GameService(List<Player> players) {
        this.game = new Game(players);
        this.playerService = new PlayerService();
    }

    @Override
    public void startGame() {
        game.startGame();
    }

    @Override
    public List<Card[]> getHands() {
        return game.getHands();
    }

    @Override
    public void showHands() {
        game.showHand();
    }

    @Override
    public List<Player> executeBettingRound(int points) {
        return game.betAndUpdatePlayerPoints(points, game.getPlayers());
    }

    @Override
    public List<Player> determineWinner() {
        return game.determineWinner();
    }

    @Override
    public void updateProfile(Player player) {
        playerService.updateProfile(player);
    }

    @Override
    public void updateProfiles(List<Player> players) {
        for (Player player : players) {
            updateProfile(player);
        }
    }

    @Override
    public void displayWinners(List<Player> winners) {
        game.displayWinners(winners);
    }
}
