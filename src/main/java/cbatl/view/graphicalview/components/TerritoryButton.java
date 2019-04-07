package cbatl.view.graphicalview.components;

import cbatl.model.territory.Point;
import cbatl.view.graphicalview.panels.TerritoryPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;

public class TerritoryButton extends JButton {
  public Point point;
  private final static Color DEFAULT = new Color(0, 0, 0, 0);
  private final static Color HOVER = new Color(0, 0, 0, 40);
  private final static Color PRESSED = new Color(0, 0, 0, 60);

  public TerritoryButton(Point point) {
    this.point = point;
    this.setPreferredSize(new Dimension(TerritoryPanel.SCALE, TerritoryPanel.SCALE));
    this.setOpaque(false);
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(DEFAULT);

    if (this.getModel().isPressed()) {
      g.setColor(PRESSED);
    } else if (this.getModel().isRollover()) {
      g.setColor(HOVER);
    }
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
  }
}
