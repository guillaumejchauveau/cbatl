package cbatl.model.game;

import cbatl.model.ModelException;
import cbatl.model.events.game.CurrentPlayerChangedEvent;
import cbatl.model.events.game.CurrentPlayerShotEvent;
import cbatl.model.events.game.GameOverEvent;
import cbatl.model.events.game.PlayerDiedEvent;
import cbatl.model.player.Player;
import cbatl.model.player.RandomPlayer;
import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import events.EventTarget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends EventTarget {
  private Integer currentPlayerIndex;
  private List<Player> players;
  private Map<String, Player> playerNames;
  private Map<Player, Territory> playerTerritoryMap;

  public Game() {
    this.players = new ArrayList<>();
    this.playerNames = new HashMap<>();
    this.playerTerritoryMap = new HashMap<>();
    this.currentPlayerIndex = 0;
  }

  public List<Player> getPlayers() {
    return this.players;
  }

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
  public void addPlayer(Player player, Territory territory) throws ModelException {
    if (player == null || territory == null) {
      throw new ModelException("Player or territory is null");
    }
    if (this.getPlayerCount() == 0 && player instanceof RandomPlayer) {
      throw new ModelException("First player cannot be a random player");
    }
    if (this.hasPlayer(player)) {
      throw new ModelException("Duplicate player");
    }
    if (this.playerTerritoryMap.containsValue(territory)) {
      throw new ModelException("Duplicate territory");
    }
    this.players.add(player);
    this.playerNames.put(player.getName(), player);
    this.playerTerritoryMap.put(player, territory);
  }

  public Player getPlayer(String playerName) {
    return this.playerNames.get(playerName);
  }

  public Territory getPlayerTerritory(Player player) throws ModelException {
    if (!this.hasPlayer(player)) {
      throw new ModelException("Unknown player");
    }
    return this.playerTerritoryMap.get(player);
  }

  public Boolean isPlayerAlive(Player player) throws ModelException {
    if (!this.hasPlayer(player)) {
      return false;
    }

    for (Boat boat : this.getPlayerTerritory(player).getBoats()) {
      if (!boat.isSunk()) {
        return true;
      }
    }
    return false;
  }

  public List<Player> getAlivePlayers() {
    List<Player> players = new ArrayList<>();
    try {
      for (Player player : this.getPlayers()) {
        if (this.isPlayerAlive(player)) {
          players.add(player);
        }
      }
    } catch (ModelException e) {
      throw new RuntimeException(e);
    }
    return players;
  }

  public Integer getAlivePlayerCount() {
    return this.getAlivePlayers().size();
  }

  public Integer getCurrentPlayerIndex() {
    return this.currentPlayerIndex;
  }

  public Player getCurrentPlayer() {
    return this.getAlivePlayers().get(this.getCurrentPlayerIndex());
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

  public void shoot(Player targetedPlayer, Point shot) throws ModelException {
    if (this.isOver()) {
      throw new ModelException("Game is over");
    }
    this.dispatchEvent(new CurrentPlayerShotEvent(targetedPlayer, shot));
    this.getPlayerTerritory(targetedPlayer).receiveShot(shot);
    if (!this.isPlayerAlive(targetedPlayer)) {
      this.currentPlayerIndex--;
      this.dispatchEvent(new PlayerDiedEvent(targetedPlayer));
    }

    if (this.isOver()) {
      this.currentPlayerIndex = 0;
      this.getWinner().incrementScore();
      this.dispatchEvent(new GameOverEvent());
      return;
    }

    this.currentPlayerIndex++;
    // Loops back to the first player if the previous was actually the last.
    if (this.getCurrentPlayerIndex() >= this.getAlivePlayerCount()) {
      this.currentPlayerIndex = 0;
    }

    if (this.getCurrentPlayer() instanceof RandomPlayer) {
      Object[] result = ((RandomPlayer) this.getCurrentPlayer()).chooseShot(this);
      this.shoot((Player) result[0], (Point) result[1]);
      return;
    }
    this.dispatchEvent(new CurrentPlayerChangedEvent());
  }
}
