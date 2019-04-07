package cbatl.view.graphicalview.panels;

import cbatl.model.Model;
import cbatl.model.events.game.CurrentPlayerChangedEvent;
import cbatl.model.game.Game;
import cbatl.model.player.Player;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlayingGameMenuPanel extends Panel {
  private Map<Player, TerritoryPanel> territoryPanels;
  private Game currentGame;
  private Model model;
  private JPanel panel;

  public PlayingGameMenuPanel(Model model) {
    this.model = model;
    this.currentGame = model.getCurrentGame();
    this.territoryPanels = new HashMap<>();

    this.panel = new JPanel();
    this.panel.setLayout(new GridLayout(1, currentGame.getPlayers().size()));
    this.setLayout(new BorderLayout());
    this.add(new JScrollPane(this.panel), BorderLayout.CENTER);

    for (Player player : currentGame.getPlayers()) {
      TerritoryPanel territoryPanel = new TerritoryPanel(currentGame, player);
      territoryPanel.addEventListener(this::dispatchEvent);
      this.territoryPanels.put(player, territoryPanel);
    }
    this.placeTerritories();

    this.currentGame.addEventListener(CurrentPlayerChangedEvent.class, event -> {
      this.placeTerritories();
    });
  }

  private void placeTerritories() {
    for (TerritoryPanel panel : this.territoryPanels.values()) {
      this.panel.remove(panel);
    }
    TerritoryPanel currentPlayerPanel =
      this.territoryPanels.get(this.currentGame.getCurrentPlayer());
    currentPlayerPanel.setBoatsVisible(true);
    currentPlayerPanel.setClickable(false);
    this.panel.add(currentPlayerPanel);

    for (Player player : this.currentGame.getPlayers()) {
      if (player == this.currentGame.getCurrentPlayer()) {
        continue;
      }
      TerritoryPanel playerPanel = this.territoryPanels.get(player);
      playerPanel.setBoatsVisible(this.model.cheat);
      playerPanel.setClickable(this.currentGame.isPlayerAlive(player));
      this.panel.add(playerPanel);
    }
    this.revalidate();
  }
}
