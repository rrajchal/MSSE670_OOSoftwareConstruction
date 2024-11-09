package com.topcard.service.player;

import com.topcard.domain.Player;
import com.topcard.exceptions.TopCardException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The PlayerService class implements the IPlayerService interface and provides
 * the business logic for player-related operations in the TopCard game.
 * <p>
 * This includes adding, removing, updating, and retrieving player information,
 * as well as managing player points and admin status.
 * </p>
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/03/2024
 * </p>
 */
public class PlayerService implements IPlayerService {
    private static final String ID_FILE_PATH = "src/main/resources/data/players.txt";

    public PlayerService() {

    }

    @Override
    public void addPlayer(Player player) {
        player.setPlayerId(getNewId());
        register(player);
    }

    @Override
    public void addPlayers(List<Player> players) {
        for (Player player : players) {
            addPlayer(player);
        }
    }

    @Override
    public void removePlayer(int playerId) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[,\\s]+");
                int existingId = Integer.parseInt(parts[0].trim());
                if (existingId != playerId) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }

        try (FileWriter writer = new FileWriter(ID_FILE_PATH)) {
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            throw new TopCardException("Error writing to player ID file", e);
        }
    }

    @Override
    public Player getPlayerById(int playerId) {
        Player player = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[,\\s]+");
                int existingId = Integer.parseInt(parts[0].trim());
                if (existingId == playerId) {
                    player = new Player(parts[1], parts[2], parts[3], parts[4], LocalDate.parse(parts[5]));
                    player.setPlayerId(existingId);
                    player.setPoints(Integer.parseInt(parts[6]));
                    player.setAdmin(Boolean.parseBoolean(parts[7]));
                    break;
                }
            }
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }
        return player;
    }

    @Override
    public void changePoints(int playerId, int points) {
        Player player = getPlayerById(playerId);
        if (player != null) {
            player.changePoints(points);
            updateProfile(player);
        }
    }

    @Override
    public boolean isPlayerAdmin(int playerId) {
        Player player = getPlayerById(playerId);
        return player != null && player.isAdmin();
    }

    @Override
    public void makePlayerAdmin(int playerId) {
        Player player = getPlayerById(playerId);
        if (player != null) {
            player.setAdmin(true);
            updateProfile(player);
        }
    }

    @Override
    public void updateProfile(Player player) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[,\\s]+");
                int existingId = Integer.parseInt(parts[0].trim());
                if (existingId == player.getPlayerId()) {
                    // Update only the specific fields that have changed
                    parts[1] = player.getUsername();
                    parts[2] = player.getPassword();
                    parts[3] = player.getFirstName();
                    parts[4] = player.getLastName();
                    parts[5] = player.getDateOfBirth().toString();
                    parts[6] = String.valueOf(player.getPoints());
                    parts[7] = String.valueOf(player.isAdmin());
                    lines.add(String.join(",", parts));
                } else {
                    lines.add(line);
                }
            }
            try (FileWriter writer = new FileWriter(ID_FILE_PATH)) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine + "\n");
                }
            } catch (IOException e) {
                throw new TopCardException("Error writing to player ID file", e);
            }
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }
    }


    @Override
    public void updateProfile(int playerId, String newFirstName, String newLastName, LocalDate newDateOfBirth) {
        Player player = getPlayerById(playerId);
        if (player != null) {
            player.setFirstName(newFirstName);
            player.setLastName(newLastName);
            player.setDateOfBirth(newDateOfBirth);
            updateProfile(player);
        }
    }

    @Override
    public void updateProfiles(List<Player> players) {
        for (Player player : players) {
            updateProfile(player);
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[,\\s]+");
                Player player = new Player(parts[1], parts[2], parts[3], parts[4], LocalDate.parse(parts[5]));
                player.setPlayerId(Integer.parseInt(parts[0].trim()));
                player.setPoints(Integer.parseInt(parts[6]));
                player.setAdmin(Boolean.parseBoolean(parts[7]));
                players.add(player);
            }
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }
        return players;
    }

    /**
     * Generates a new unique ID for a player by analyzing existing IDs.
     *
     * @return a new unique ID
     */
    private int getNewId() {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[,\\s]+");
                int existingId = Integer.parseInt(parts[0].trim());
                if (existingId > maxId) {
                    maxId = existingId;
                }
            }
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }
        return maxId + 1;
    }

    @Override
    public int retrievePointForPlayer(int playerId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[,\\s]+");
                int existingId = Integer.parseInt(parts[0].trim());
                if (existingId == playerId) {
                    return Integer.parseInt(parts[6]); // Assuming the points are at index 6
                }
            }
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }
        throw new TopCardException("Player not found with ID: " + playerId);
    }

    /**
     * Registers a player by writing their details to the file.
     *
     * @param player the player to be registered
     */
    private void register(Player player) {
        try (FileWriter writer = new FileWriter(ID_FILE_PATH, true)) {
            writer.write(player.getPlayerId() + "," + player.getUsername() + "," + player.getPassword() + "," + player.getFirstName() + "," + player.getLastName() + "," + player.getDateOfBirth().toString() + "," + player.getPoints() + "," + player.isAdmin() + "\n");
        } catch (IOException e) {
            throw new TopCardException("Error writing to player ID file", e);
        }
    }

    /**
     * Deletes all data in the player ID file.
     * This function is made private and will only be used in Unit Test
     */
    private void deleteAllPlayersData() {
        try (FileWriter writer = new FileWriter(ID_FILE_PATH)) {
            // Simply open the file in write mode, which clears its content.
            writer.write("");
        } catch (IOException e) {
            throw new TopCardException("Error clearing player ID file", e);
        }

    }
}