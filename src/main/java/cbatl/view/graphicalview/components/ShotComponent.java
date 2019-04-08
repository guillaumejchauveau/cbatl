package cbatl.view.graphicalview.components;

import cbatl.model.territory.Point;
import cbatl.view.graphicalview.panels.TerritoryPanel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Draws a shot.
 */
public class ShotComponent extends JComponent {
  private final static Color WATER = new Color(54, 131, 200);
  private final static Integer SIZE = TerritoryPanel.SCALE / 2;
  private Point point;
  private Boolean onBoat;

  public ShotComponent(Point point, Boolean onBoat) {
    this.point = point;
    this.onBoat = onBoat;
  }

  @Override
  public void paint(Graphics g) {
    if (this.onBoat) {
      g.setColor(BoatComponent.SUNK);
    } else {
      g.setColor(WATER);
    }
    g.fillRect(point.x * TerritoryPanel.SCALE + SIZE / 2,
      point.y * TerritoryPanel.SCALE + SIZE / 2, SIZE, SIZE);
  }
}
