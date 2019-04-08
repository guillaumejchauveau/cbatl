package cbatl.view.graphicalview.panels;

import cbatl.model.Model;
import cbatl.model.ModelException;
import cbatl.model.player.Player;
import cbatl.view.events.PlayGameEvent;
import cbatl.view.graphicalview.components.AvailablePlayersListModel;
import cbatl.view.graphicalview.components.SelectedPlayersListModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CreateGameMenuPanel extends Panel {
  public CreateGameMenuPanel(Model model) {
    SelectedPlayersListModel selectedPlayersListModel = new SelectedPlayersListModel();
    JList<String> selectedPlayersList = new JList<>(selectedPlayersListModel);
    AvailablePlayersListModel availablePlayersListModel =
      new AvailablePlayersListModel(model.playerManager, selectedPlayersListModel);
    JList<String> availablePlayersList = new JList<>(availablePlayersListModel);

    JButton selectPlayer = new JButton("Ajouter a la partie");
    selectPlayer.addActionListener(e -> {
      if (availablePlayersListModel.getAvailablePlayers().size() == 0) {
        return;
      }
      try {
        for (String playerName : availablePlayersList.getSelectedValuesList()) {
          selectedPlayersListModel.addPlayer(model.playerManager.getPlayer(playerName));
        }
      } catch (ModelException ec) {
        throw new RuntimeException(ec);
      }
      availablePlayersList.clearSelection();
      selectedPlayersList.clearSelection();
    });
    JButton removePlayer = new JButton("Supprimer de la partie");
    removePlayer.addActionListener(e -> {
      if (selectedPlayersListModel.getSelectedPlayers().size() == 0) {
        return;
      }
      try {
        for (String playerName : selectedPlayersList.getSelectedValuesList()) {
          selectedPlayersListModel.removePlayer(model.playerManager.getPlayer(playerName));
        }
      } catch (ModelException ec) {
        throw new RuntimeException(ec);
      }
      availablePlayersList.clearSelection();
      selectedPlayersList.clearSelection();
    });
    JTextField newPlayer = new JTextField(10);
    JButton createPlayer = new JButton("Creer un joueur");
    createPlayer.addActionListener(e -> {
      String name = newPlayer.getText().trim();
      try {
        Player player = new Player(name);
        model.playerManager.registerPlayer(player);
        selectedPlayersListModel.addPlayer(player);
      } catch (ModelException ec) {
      }
      newPlayer.setText("");
    });

    JButton play = new JButton("Lancer");
    play.addActionListener(e -> {
      this.dispatchEvent(new PlayGameEvent(selectedPlayersListModel.getSelectedPlayers()));
    });

    int width = 400;
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.add(Box.createRigidArea(new Dimension(0, 20)));

    JPanel top = new JPanel();
    top.setLayout(new GridLayout(1, 2));
    top.add(new JScrollPane(availablePlayersList));
    top.add(new JScrollPane(selectedPlayersList));
    top.setMaximumSize(new Dimension(width, 150));
    this.add(top);

    this.add(Box.createRigidArea(new Dimension(0, 3)));

    JPanel mid1 = new JPanel();
    mid1.setLayout(new GridLayout(1, 2));
    mid1.add(selectPlayer);
    mid1.add(removePlayer);
    mid1.setMaximumSize(new Dimension(width, 30));
    this.add(mid1);

    this.add(Box.createRigidArea(new Dimension(0, 10)));

    JPanel mid2 = new JPanel();
    mid2.setLayout(new GridLayout(1, 2));
    mid2.add(newPlayer);
    mid2.add(createPlayer);
    this.add(mid2);
    mid2.setMaximumSize(new Dimension(width, 30));

    this.add(Box.createRigidArea(new Dimension(0, 30)));
    play.setAlignmentX(CENTER_ALIGNMENT);
    this.add(play);
  }
}
