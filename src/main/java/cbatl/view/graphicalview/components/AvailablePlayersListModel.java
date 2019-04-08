package cbatl.view.graphicalview.components;

import cbatl.model.events.player.PlayerListUpdatedEvent;
import cbatl.model.player.Player;
import cbatl.model.player.PlayerManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class AvailablePlayersListModel extends AbstractListModel<String> {
  private PlayerManager playerManager;
  private SelectedPlayersListModel selectedPlayers;

  public AvailablePlayersListModel(PlayerManager playerManager,
                                   SelectedPlayersListModel selectedPlayers) {
    this.playerManager = playerManager;
    this.selectedPlayers = selectedPlayers;
    playerManager.addEventListener(PlayerListUpdatedEvent.class, event -> {
      this.fireContentsChanged(playerManager, 0, playerManager.getPlayers().size() - 1);
    });
    AvailablePlayersListModel that = this;
    selectedPlayers.addListDataListener(new ListDataListener() {
      @Override
      public void intervalAdded(ListDataEvent e) {
        that.fireContentsChanged(playerManager, 0, playerManager.getPlayers().size() - 1);
      }

      @Override
      public void intervalRemoved(ListDataEvent e) {
        that.fireContentsChanged(playerManager, 0, playerManager.getPlayers().size() - 1);
      }

      @Override
      public void contentsChanged(ListDataEvent e) {
        that.fireContentsChanged(playerManager, 0, playerManager.getPlayers().size() - 1);
      }
    });
  }

  public List<Player> getAvailablePlayers() {
    List<Player> availablePlayers = new ArrayList<>();
    for (Player player : this.playerManager.getPlayers()) {
      if (!this.selectedPlayers.getSelectedPlayers().contains(player)) {
        availablePlayers.add(player);
      }
    }
    return availablePlayers;
  }

  @Override
  public int getSize() {
    return this.getAvailablePlayers().size();
  }

  @Override
  public String getElementAt(int index) {
    return this.getAvailablePlayers().get(index).getName();
  }
}
