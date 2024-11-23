package com.topcard.service.factory;

import com.topcard.domain.Player;
import com.topcard.exceptions.TopCardException;
import com.topcard.marker.TopCardMarker;
import com.topcard.service.game.GameService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * ServiceFactory is a factory class responsible for creating service instances
 * for the TopCard game. This class implements the TopCardMarker interface.
 * <p>
 *  Author: Rajesh Rajchal
 *  Date: 11/21/2024
 */
public class ServiceFactory implements TopCardMarker {

    /**
     * Creates an instance of the specified service class.
     *
     * @param serviceClass the class of the service to create
     * @return a new instance of the specified service
     */
    public static <T> T createService(Class<T> serviceClass) {
        try {
            return serviceClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new TopCardException(e);
        }
    }

    /**
     * Creates an instance of GameService with a list of players.
     *
     * @param players the list of players for the game
     * @return a new instance of GameService
     */
    public static GameService createGameService(List<Player> players) {
        try {
            return GameService.class.getDeclaredConstructor(List.class).newInstance(players);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new TopCardException(e);
        }
    }
}

