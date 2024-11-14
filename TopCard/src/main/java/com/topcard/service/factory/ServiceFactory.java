package com.topcard.service.factory;

import com.topcard.domain.Player;
import com.topcard.marker.TopCardMarker;
import com.topcard.service.game.GameService;
import com.topcard.service.player.PlayerService;
import com.topcard.service.card.CardService;
import com.topcard.service.game.IGameService;
import com.topcard.service.player.IPlayerService;
import com.topcard.service.card.ICardService;

import java.util.List;

/**
 * ServiceFactory is a factory class responsible for creating service instances
 * for the TopCard game. This class implements the TopCardMarker interface.
 */
public class ServiceFactory implements TopCardMarker {

    /**
     * Creates an instance of IGameService.
     *
     * @param players the list of players for the game
     * @return a new instance of GameService
     */
    public static IGameService createGameService(List<Player> players) {
        return new GameService(players);
    }

    /**
     * Creates an instance of IPlayerService.
     *
     * @return a new instance of PlayerService
     */
    public static IPlayerService createPlayerService() {
        return new PlayerService();
    }

    /**
     * Creates an instance of ICardService.
     *
     * @return a new instance of CardService
     */
    public static ICardService createCardService() {
        return new CardService();
    }
}

