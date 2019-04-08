package cbatl.view.graphicalview.panels;

import cbatl.model.Model;
import cbatl.model.player.Player;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

public class CreateGameMenuPanel extends Panel {
  public CreateGameMenuPanel(Model model) {
    this.setLayout(new FlowLayout());
    DefaultListModel<String> listModel = new DefaultListModel<>();
    for (Player player : model.playerManager.getPlayers()) {
      listModel.addElement(player.getName());
    }
    JList list = new JList<>(listModel);
    JButton play = new JButton("Lancer");
    play.addActionListener(e -> {
      List<Player> players = new ArrayList<>();
      //this.dispatchEvent(new PlayGameEvent(players));
    });
    this.add(play);
  }
}
