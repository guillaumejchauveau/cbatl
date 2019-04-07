package cbatl.view.graphicalview.components;

import cbatl.model.events.player.PlayerUpdatedEvent;
import cbatl.model.player.Player;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class PlayerComponent extends JLabel {
  private Player player;

  public PlayerComponent(Player player) {
    this.player = player;
    this.setText(player.getName());
    this.setPreferredSize(new Dimension(50, 20));
    this.player.addEventListener(PlayerUpdatedEvent.class, event -> {
      this.setText(player.getName());
    });
  }
}
