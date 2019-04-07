package cbatl.view.graphicalview.panels;

import cbatl.model.events.game.PlayerDiedEvent;
import cbatl.model.events.territory.TerritoryReceivedShotEvent;
import cbatl.model.game.Game;
import cbatl.model.player.Player;
import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import cbatl.view.events.ShootEvent;
import cbatl.view.graphicalview.components.BoatComponent;
import cbatl.view.graphicalview.components.ShotComponent;
import cbatl.view.graphicalview.components.TerritoryButton;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;

public class TerritoryPanel extends Panel {
  public final static Integer SCALE = 24;
  private JPanel buttonGrid;
  private Boolean clickable;
  private Collection<BoatComponent> boats;
  private JPanel grid;

  TerritoryPanel(Game game, Player player) {
    Territory territory = game.getPlayerTerritory(player);
    this.boats = new ArrayList<>();

    this.setPreferredSize(new Dimension(
      SCALE * (territory.width + 1),
      SCALE * (territory.height + 2)
    ));
    this.setLayout(new GridBagLayout());
    GridBagConstraints c;

    // Button grid.
    this.buttonGrid = new JPanel();
    this.buttonGrid.setLayout(new GridLayout(territory.height, territory.width));
    this.buttonGrid.setOpaque(false);
    for (int y = 0; y < territory.height; y++) {
      for (int x = 0; x < territory.width; x++) {
        TerritoryButton button = new TerritoryButton(new Point(x, y));
        this.buttonGrid.add(button);

        button.addActionListener(e -> {
          this.dispatchEvent(new ShootEvent(player, button.point));
        });
      }
    }

    // Boats and shot grid.
    this.grid = new JPanel();
    this.grid.setPreferredSize(new Dimension(
      SCALE * territory.width,
      SCALE * territory.height
    ));
    // ZOrder placeholders.
    this.grid.setLayout(new OverlayLayout(grid));
    java.awt.Component fake = new java.awt.Component() {
    };
    this.grid.add(fake);
    this.grid.setComponentZOrder(fake, 0);
    java.awt.Component fake1 = new java.awt.Component() {
    };
    this.grid.add(fake1);
    this.grid.setComponentZOrder(fake1, 1);

    for (Boat boat : territory.getBoats()) {
      BoatComponent boatC = new BoatComponent(boat);
      this.grid.add(boatC);
      this.grid.setComponentZOrder(boatC, 2);
      this.boats.add(boatC);
    }
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 1;
    c.gridwidth = territory.width;
    c.gridheight = territory.height;
    this.add(this.grid, c);

    // Coordinates labels
    for (int x = 0; x < territory.width; x++) {
      JLabel xLabel = new JLabel(Character.toString((char) ('A' + x)), SwingConstants.CENTER);
      xLabel.setPreferredSize(new Dimension(SCALE, SCALE));
      c = new GridBagConstraints();
      c.gridx = x + 1;
      c.gridy = 0;
      this.add(xLabel, c);
    }
    for (int y = 0; y < territory.height; y++) {
      JLabel yLabel = new JLabel(Integer.toString(y + 1), SwingConstants.CENTER);
      yLabel.setPreferredSize(new Dimension(SCALE, SCALE));
      c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = y + 1;
      this.add(yLabel, c);
    }

    // Territory label.
    JLabel territoryLabel = new JLabel(player.getName(), SwingConstants.CENTER);
    territoryLabel.setPreferredSize(new Dimension((territory.width + 1) * SCALE, SCALE));
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = territory.height + 1;
    c.gridwidth = territory.width + 1;
    this.add(territoryLabel, c);

    // Events.
    game.addEventListener(PlayerDiedEvent.class, event -> {
      if (event.player == player) {
        territoryLabel.setText("[MORT] " + player.getName());
      }
    });
    territory.addEventListener(TerritoryReceivedShotEvent.class, event -> {
      ShotComponent shotC = new ShotComponent(event.shot, event.shotBoat != null);
      this.grid.add(shotC);
      this.grid.setComponentZOrder(shotC, 1);
      this.grid.revalidate();
    });
  }

  void setClickable(Boolean clickable) {
    if (clickable == this.clickable) {
      return;
    }
    this.clickable = clickable;
    if (clickable) {
      this.grid.add(this.buttonGrid);
      this.grid.setComponentZOrder(this.buttonGrid, 0);
    } else {
      this.grid.remove(this.buttonGrid);
    }
  }

  void setBoatsVisible(Boolean visible) {
    for (BoatComponent boatC : this.boats) {
      boatC.setVisible(visible);
    }
  }
}
