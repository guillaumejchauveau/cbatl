package cbatl.model.game;

import cbatl.model.events.game.CurrentPlayerShotPlayerInGameEvent;
import cbatl.model.events.game.GameCurrentPlayerChangedEvent;
import cbatl.model.events.game.GameOverEvent;
import cbatl.model.events.game.PlayerDiedInGameEvent;
import cbatl.model.player.Player;
import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import events.EventTarget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Game extends EventTarget {
  private Integer currentPlayerIndex;
  private List<Player> players;
  private Map<Player, Territory> playerTerritoryMap;

  /**
   *
   */
  public Game() {
    this.players = new ArrayList<>();
    this.playerTerritoryMap = new HashMap<>();
    this.currentPlayerIndex = 0;
  }

  /**
   * @return
   */
  public Collection<Player> getPlayers() {
    return this.players;
  }

  /**
   * @return
   */
  public Integer getPlayerCount() {
    return this.players.size();
  }

  /**
   * Determines if a given player is involved in the game.
   *
   * @param player The player to test
   * @return The result of the test
   */
  public Boolean hasPlayer(Player player) {
    return this.players.contains(player);
  }

  /**
   * Involves a player in the game.
   *
   * @param player The player to involve
   */
  public void addPlayer(Player player, Territory territory) {
    if (player == null || territory == null) {
      throw new NullPointerException();
    }
    if (this.hasPlayer(player)) {
      throw new IllegalArgumentException("Duplicate player");
    }
    if (this.playerTerritoryMap.containsValue(territory)) {
      throw new IllegalArgumentException("Duplicate territory");
    }
    this.players.add(player);
    this.playerTerritoryMap.put(player, territory);
  }

  /**
   * @param player
   * @return
   */
  public Territory getPlayerTerritory(Player player) {
    if (!this.hasPlayer(player)) {
      throw new IllegalArgumentException();
    }
    return this.playerTerritoryMap.get(player);
  }

  /**
   * @param player
   * @return
   */
  public Boolean isPlayerAlive(Player player) {
    if (!this.hasPlayer(player)) {
      throw new IllegalArgumentException();
    }

    for (Boat boat : this.getPlayerTerritory(player).getBoats()) {
      if (!boat.isSunk()) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return
   */
  public List<Player> getAlivePlayers() {
    List<Player> players = new ArrayList<>();
    for (Player player : this.getPlayers()) {
      if (this.isPlayerAlive(player)) {
        players.add(player);
      }
    }
    return players;
  }

  /**
   * @return
   */
  public Integer getAlivePlayerCount() {
    return this.getAlivePlayers().size();
  }

  /**
   * @return
   */
  public Player getCurrentPlayer() {
    return this.getAlivePlayers().get(this.currentPlayerIndex);
  }

  /**
   * Determines if the game is over.
   */
  public Boolean isOver() {
    return this.getAlivePlayerCount() <= 1;
  }

  /**
   * @return The player who won the game (null if the game is not over)
   */
  public Player getWinner() {
    if (!this.isOver()) {
      return null;
    }
    return this.getCurrentPlayer();
  }

  /**
   *
   */
  public void shoot(Player targetedPlayer, Point shot) {
    if (!this.hasPlayer(targetedPlayer) || !this.isPlayerAlive(targetedPlayer)) {
      throw new IllegalArgumentException();
    }
    this.dispatchEvent(new CurrentPlayerShotPlayerInGameEvent(targetedPlayer, shot));
    this.getPlayerTerritory(targetedPlayer).receiveShot(shot);
    if (!this.isPlayerAlive(targetedPlayer)) {
      this.dispatchEvent(new PlayerDiedInGameEvent(targetedPlayer));
    }

    if (this.isOver()) {
      this.getWinner().incrementScore();
      this.dispatchEvent(new GameOverEvent());
      return;
    }

    this.currentPlayerIndex++;
    // Loops back to the first player if the previous was actually the last.
    if (this.currentPlayerIndex >= this.getAlivePlayerCount()) {
      this.currentPlayerIndex = 0;
    }
    this.dispatchEvent(new GameCurrentPlayerChangedEvent());
  }
}
