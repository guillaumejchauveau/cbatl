package cbatl.model.player;

import cbatl.model.ModelException;
import cbatl.model.events.player.PlayerListUpdatedEvent;
import events.EventTarget;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
  private final List<Player> registeredPlayersList;

  public PlayerManager() {
    this.registeredPlayers = new HashMap<>();
    this.registeredPlayersList = new ArrayList<>();
  }

  public static PlayerManager createFromFile(FileReader fileReader) throws IOException,
    ModelException {
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
  public void registerPlayer(Player player) throws ModelException {
    if (player == null) {
      throw new ModelException("Null player");
    }
    if (this.hasPlayer(player)) {
      throw new ModelException("Duplicate player");
    }
    this.registeredPlayers.put(player.getName(), player);
    this.registeredPlayersList.add(player);
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
  public Player getPlayer(String playerName) throws ModelException {
    if (!this.hasPlayer(playerName)) {
      throw new ModelException("Unknown player");
    }
    return this.registeredPlayers.get(playerName);
  }

  public Player getPlayer(Integer index) {
    return this.registeredPlayersList.get(index);
  }

  /**
   * Retrieves the collection of all {@link Player} objects registered.
   *
   * @return The players' objects collection
   */
  public List<Player> getPlayers() {
    return this.registeredPlayersList;
  }

  /**
   * Removes a player of the registry.
   *
   * @param player The player to remove
   */
  public void removePlayer(Player player) throws ModelException {
    if (!this.hasPlayer(player)) {
      throw new ModelException("Unknown player");
    }
    this.registeredPlayers.remove(player.getName());
    this.registeredPlayersList.remove(player);
    this.dispatchEvent(new PlayerListUpdatedEvent());
  }

  public void saveToFile(FileWriter fileWriter) throws IOException {
    CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
    printer.printRecord("Name", "Score");
    for (Player player : this.getPlayers()) {
      printer.printRecord(player.getName(), player.getScore());
    }
    printer.close();
  }
}
