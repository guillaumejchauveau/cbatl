package cbatl.view.graphicalview.components;

import cbatl.model.events.territory.BoatSankEvent;
import cbatl.model.territory.Boat;
import cbatl.view.graphicalview.panels.TerritoryPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JComponent;

/**
 * Draws a boat.
 */
public class BoatComponent extends JComponent {
  private final static Integer SIZE = 16;
  private final static Color DEFAULT = new Color(80, 80, 80);
  final static Color SUNK = new Color(200, 52, 50);

  private Boat boat;
  private Boolean visible;

  public BoatComponent(Boat boat) {
    this.boat = boat;
    this.visible = false;
    boat.addEventListener(BoatSankEvent.class, event -> {
      this.repaint();
    });
  }

  private Integer bowX() {
    return boat.getBow().x * TerritoryPanel.SCALE + TerritoryPanel.SCALE / 2;
  }

  private Integer bowY() {
    return boat.getBow().y * TerritoryPanel.SCALE + TerritoryPanel.SCALE / 2;
  }

  private Integer sternX() {
    int offset = 0;
    switch (this.boat.getOrientation()) {
      case EAST:
        offset = -this.boat.getLength() + 1;
        break;
      case WEST:
        offset = this.boat.getLength() - 1;
        break;
    }
    return this.bowX() + offset * TerritoryPanel.SCALE;
  }

  private Integer sternY() {
    int offset = 0;
    switch (this.boat.getOrientation()) {
      case NORTH:
        offset = this.boat.getLength() - 1;
        break;
      case SOUTH:
        offset = -this.boat.getLength() + 1;
        break;
    }
    return this.bowY() + offset * TerritoryPanel.SCALE;
  }

  private Polygon render() {
    Polygon polygon = new Polygon();

    switch (boat.getOrientation()) {
      case NORTH:
        polygon.addPoint(this.bowX() - SIZE / 2, this.bowY() + SIZE / 2);
        polygon.addPoint(this.bowX(), this.bowY());
        polygon.addPoint(this.bowX() + SIZE / 2, this.bowY() + SIZE / 2);
        break;
      case EAST:
        polygon.addPoint(this.bowX() - SIZE / 2, this.bowY() - SIZE / 2);
        polygon.addPoint(this.bowX(), this.bowY());
        polygon.addPoint(this.bowX() - SIZE / 2, this.bowY() + SIZE / 2);
        break;
      case SOUTH:
        polygon.addPoint(this.bowX() - SIZE / 2, this.bowY() - SIZE / 2);
        polygon.addPoint(this.bowX(), this.bowY());
        polygon.addPoint(this.bowX() + SIZE / 2, this.bowY() - SIZE / 2);
        break;
      case WEST:
        polygon.addPoint(this.bowX() + SIZE / 2, this.bowY() - SIZE / 2);
        polygon.addPoint(this.bowX(), this.bowY());
        polygon.addPoint(this.bowX() + SIZE / 2, this.bowY() + SIZE / 2);
        break;
    }

    if (boat.getOrientation().vertical()) {
      polygon.addPoint(this.sternX() + SIZE / 2, this.sternY());
      polygon.addPoint(this.sternX() - SIZE / 2, this.sternY());
    } else {
      polygon.addPoint(this.sternX(), this.sternY() + SIZE / 2);
      polygon.addPoint(this.sternX(), this.sternY() - SIZE / 2);
    }
    return polygon;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
    this.repaint();
  }

  @Override
  public void paint(Graphics g) {
    if (this.boat.isSunk()) {
      g.setColor(SUNK);
    } else if (this.visible) {
      g.setColor(DEFAULT);
    } else {
      return;
    }
    g.fillPolygon(this.render());
  }
}
