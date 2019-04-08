package cbatl.view.graphicalview.components;

import cbatl.model.player.Player;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

public class SelectedPlayersListModel extends AbstractListModel<String> {
  private List<Player> selectedPlayers;

  public SelectedPlayersListModel() {
    this.selectedPlayers = new ArrayList<>();
  }

  public List<Player> getSelectedPlayers() {
    return this.selectedPlayers;
  }

  public void addPlayer(Player player) {
    this.selectedPlayers.add(player);
    this.fireIntervalAdded(this, this.getSize() - 1, this.getSize() - 1);
  }

  public void removePlayer(Player player) {
    this.selectedPlayers.remove(player);
    this.fireContentsChanged(this, 0, this.getSize() - 1);
  }

  @Override
  public int getSize() {
    return this.selectedPlayers.size();
  }

  @Override
  public String getElementAt(int index) {
    return this.selectedPlayers.get(index).getName();
  }
}
