package cbatl.view.graphicalview.panels;

import cbatl.model.Model;
import cbatl.view.events.CreateGameMenuEvent;
import cbatl.view.graphicalview.components.PlayersTableModel;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainMenuPanel extends Panel {
  public MainMenuPanel(Model model) {
    JButton createGame = new JButton("Commencer une partie");
    createGame.addActionListener(e -> {
      this.dispatchEvent(new CreateGameMenuEvent());
    });
    createGame.setAlignmentX(CENTER_ALIGNMENT);

    JTable playersTable = new JTable(new PlayersTableModel(model.playerManager));
    playersTable.setCellSelectionEnabled(false);
    Dimension tableSize = new Dimension(300, 200);
    playersTable.setPreferredScrollableViewportSize(tableSize);
    JScrollPane scrollPane = new JScrollPane(playersTable);
    scrollPane.setMaximumSize(tableSize);

    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    this.add(scrollPane);
    this.add(Box.createRigidArea(new Dimension(0, 50)));
    this.add(createGame);
  }
}
