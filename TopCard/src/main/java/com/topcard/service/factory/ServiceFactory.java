package com.topcard.service.factory;

import com.topcard.domain.Player;
import com.topcard.service.game.GameService;
import com.topcard.service.player.PlayerService;
import com.topcard.service.card.CardService;
import com.topcard.service.game.IGameService;
import com.topcard.service.player.IPlayerService;
import com.topcard.service.card.ICardService;

import java.util.List;

public class ServiceFactory {

    public static IGameService createGameService(List<Player> players) {
        return new GameService(players);
    }

    public static IPlayerService createPlayerService() {
        return new PlayerService();
    }

    public static ICardService createCardService() {
        return new CardService();
    }
}
