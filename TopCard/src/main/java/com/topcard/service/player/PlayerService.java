package com.topcard.service.player;

import com.topcard.debug.Debug;
import com.topcard.domain.Player;
import com.topcard.exceptions.TopCardException;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * The PlayerService class implements the IPlayerService interface and provides
 * the business logic for player-related operations in the TopCard game.
 * <p>
 * This includes adding, removing, updating, and retrieving player information,
 * as well as managing player points and admin status.
 * </p>
 * <p>
 * Author: Rajesh Rajchal
 * Date: 11/21/2024
 * </p>
 */
public class PlayerService implements IPlayerService {

    private static final String FILE_PATH;

    static {
        Properties properties = new Properties();
        try (InputStream input = PlayerService.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new TopCardException("Unable to find config.properties");
            }
            properties.load(input);
            FILE_PATH = properties.getProperty("FILE_PATH");
        } catch (IOException e) {
            throw new TopCardException("Failed to load configuration properties", e);
        }
    }

    public PlayerService() {
        // Default constructor
    }

    @Override
    public void addPlayer(Player player) {
        if (getPlayerByUsername(player.getUsername()) == null) {
            player.setPlayerId(getNewId());
            player.setUsername(player.getUsername().toLowerCase());
            player.setPassword(encryptPassword(player.getPassword())); // Encrypt the password
            register(player);
            Debug.info("Player added: " + player);
        } else {
            Debug.warn("Player already exists. No player added.");
        }
    }

    @Override
    public void addPlayers(List<Player> players) {
        players.forEach(this::addPlayer);
    }

    @Override
    public void removePlayer(int playerId) {
        List<String> lines = readLinesFromFile();
        lines = lines.stream()
                .filter(line -> parseInt(splitLine(line)[0]) != playerId)
                .collect(Collectors.toList());
        writeLinesToFile(lines, false);
    }

    @Override
    public Player getPlayerById(int playerId) {
        return findPlayer(line -> parseInt(splitLine(line)[0]) == playerId);
    }

    @Override
    public Player getPlayerByUsername(String userName) {
        return findPlayer(line -> splitLine(line)[1].trim().equals(userName));
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
        List<String> lines = readLinesFromFile();
        lines = lines.stream()
                .map(line -> {
                    String[] parts = splitLine(line);
                    if (parts[0].equals(String.valueOf(player.getPlayerId()))) {
                        if (!BCrypt.checkpw(player.getPassword(), parts[2])) {
                            player.setPassword(encryptPassword(player.getPassword())); // Encrypt the password if it has changed
                        } else {
                            player.setPassword(parts[2]); // Keep the existing encrypted password
                        }
                        return playerToCsvString(player);
                    }
                    return line;
                })
                .collect(Collectors.toList());
        writeLinesToFile(lines, false);
        Debug.info("Player updated: " + player);
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
        players.forEach(this::updateProfile);
    }

    @Override
    public List<Player> getAllPlayers() {
        return readLinesFromFile().stream()
                .map(this::csvStringToPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public int retrievePointForPlayer(int playerId) {
        Player player = getPlayerById(playerId);
        if (player != null) {
            return player.getPoints();
        }
        throw new TopCardException("Player not found with ID: " + playerId);
    }

    /**
     * Encrypts a password using BCrypt. This way the data file will have encrypted text
     *
     * @param password the plain text password
     * @return the encrypted password
     */
    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifies a plain text password against an encrypted password.
     *
     * @param plainPassword the plain text password
     * @param encryptedPassword the encrypted password
     * @return true if the password matches, false otherwise
     */
    public boolean verifyPassword(String plainPassword, String encryptedPassword) {
        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }

    /**
     * Generates a new unique ID for a player by analyzing existing IDs.
     *
     * @return a new unique ID
     */
    private int getNewId() {
        return readLinesFromFile().stream()
                .mapToInt(line -> line.isEmpty() ? 0 : parseInt(splitLine(line)[0]))
                .max().orElse(0) + 1;
    }

    /**
     * Registers a player by writing their details to the file.
     *
     * @param player the player to be registered
     */
    private void register(Player player) {
        writeLinesToFile(Collections.singletonList(playerToCsvString(player)), true);
    }

    /**
     * Deletes all data in the player file.
     * This function is made private and used in Unit Test.
     */
    private void deleteAllPlayersData() {
        writeLinesToFile(Collections.emptyList(), false);
    }

    /**
     * Reads all lines from the player ID file.
     *
     * @return a list of strings, each representing a line from the file
     */
    private List<String> readLinesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }
    }

    /**
     * Writes lines to the player ID file.
     *
     * @param lines  the lines to be written
     * @param append whether to append to the file or overwrite it
     */
    private void writeLinesToFile(List<String> lines, boolean append) {
        try (FileWriter writer = new FileWriter(FILE_PATH, append)) {
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new TopCardException("Error writing to player ID file", e);
        }
    }

    /**
     * Splits a line from the file into an array of strings.
     *
     * @param line the line to be split
     * @return an array of strings resulting from the split
     */
    private String[] splitLine(String line) {
        return line.split("[,\\s]+");
    }

    /**
     * Parses an integer value from a string.
     *
     * @param value the string to be parsed
     * @return the parsed integer value
     */
    private int parseInt(String value) {
        return Integer.parseInt(value.trim());
    }

    /**
     * Finds a player based on a provided predicate.
     *
     * @param predicate the condition to match
     * @return the matching player, or null if not found
     */
    private Player findPlayer(LinePredicate predicate) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return reader.lines()
                    .filter(line -> !line.isEmpty())
                    .filter(predicate::test)
                    .map(this::csvStringToPlayer)
                    .findFirst().orElse(null);
        } catch (IOException e) {
            throw new TopCardException("Error reading player ID file", e);
        }
    }

    /**
     * Converts a CSV string to a Player object.
     *
     * @param line the CSV string representing a player
     * @return the Player object
     */
    private Player csvStringToPlayer(String line) {
        String[] parts = splitLine(line);
        Player player = new Player(parts[1], parts[2], parts[3], parts[4], LocalDate.parse(parts[5]));
        player.setPlayerId(parseInt(parts[0]));
        player.setPoints(parseInt(parts[6]));
        player.setAdmin(Boolean.parseBoolean(parts[7]));
        return player;
    }

    /**
     * Converts a Player object to a CSV string.
     *
     * @param player the player to be converted
     * @return the CSV string representing the player
     */
    private String playerToCsvString(Player player) {
        return String.join(",", String.valueOf(player.getPlayerId()), player.getUsername(), player.getPassword(),
                player.getFirstName(), player.getLastName(), player.getDateOfBirth().toString(),
                String.valueOf(player.getPoints()), String.valueOf(player.isAdmin()));
    }

    /**
     * A functional interface for evaluating conditions on lines from the players.csv file.
     */
    @FunctionalInterface
    private interface LinePredicate {
        boolean test(String line);
    }
}
