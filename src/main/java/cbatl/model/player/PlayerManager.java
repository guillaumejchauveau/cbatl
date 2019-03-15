package cbatl.model.player;

import cbatl.model.events.player.PlayerListUpdatedEvent;
import events.EventTarget;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * Centralizes all operations concerning players.
 */
public class PlayerManager extends EventTarget {
  /**
   * A map between the player names and their corresponding player.
   */
  private final Map<String, Player> registeredPlayers;

  /**
   *
   */
  public PlayerManager() {
    this.registeredPlayers = new HashMap<>();
  }

  /**
   * @param fileReader
   * @return
   */
  public static PlayerManager createFromFile(FileReader fileReader) throws IOException {
    PlayerManager playerManager = new PlayerManager();
    CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader());
    for (CSVRecord record : parser) {
      Player player = new Player(record.get("Name"), Integer.parseInt(record.get("Score")));
      playerManager.registerPlayer(player);
    }
    parser.close();
    return playerManager;
  }

  /**
   * Registers a player and checks for any duplications.
   *
   * @param player The player to register
   */
  public void registerPlayer(Player player) {
    if (player == null) {
      throw new NullPointerException();
    }
    if (this.hasPlayer(player)) {
      throw new IllegalArgumentException();
    }
    this.registeredPlayers.put(player.getName(), player);
    this.dispatchEvent(new PlayerListUpdatedEvent());
  }

  /**
   * Determines if a given player is registered.
   *
   * @param player The player to test
   * @return The result of the test
   */
  public Boolean hasPlayer(Player player) {
    return this.registeredPlayers.containsKey(player.getName()) ||
      this.registeredPlayers.containsValue(player);
  }

  /**
   * Determines if a given player is registered.
   *
   * @param playerName The player's name to test
   * @return The result of the test
   */
  public Boolean hasPlayer(String playerName) {
    return this.registeredPlayers.containsKey(playerName);
  }

  /**
   * Retrieves the {@link Player} object corresponding to a given player
   * name.
   *
   * @param playerName The player's name
   * @return The player's object
   */
  public Player getPlayer(String playerName) {
    if (!this.hasPlayer(playerName)) {
      throw new IllegalArgumentException();
    }
    return this.registeredPlayers.get(playerName);
  }

  /**
   * Retrieves the collection of all {@link Player} objects registered.
   *
   * @return The players' objects collection
   */
  public Collection<Player> getPlayers() {
    return this.registeredPlayers.values();
  }

  /**
   * Removes a player of the registry.
   *
   * @param player The player to remove
   */
  public void removePlayer(Player player) {
    if (!this.hasPlayer(player)) {
      throw new IllegalArgumentException();
    }
    this.registeredPlayers.remove(player.getName());
    this.dispatchEvent(new PlayerListUpdatedEvent());
  }

  /**
   * @param fileWriter
   */
  public void saveToFile(FileWriter fileWriter) throws IOException {
    CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
    printer.printRecord("Name", "Score");
    for (Player player : this.getPlayers()) {
      printer.printRecord(player.getName(), player.getScore());
    }
    printer.close();
  }
}
