package cbatl.view.graphicalview.components;

import cbatl.model.events.player.PlayerListUpdatedEvent;
import cbatl.model.player.PlayerManager;
import javax.swing.table.AbstractTableModel;

public class PlayersTableModel extends AbstractTableModel {
  private PlayerManager playerManager;

  public PlayersTableModel(PlayerManager playerManager) {
    this.playerManager = playerManager;
    playerManager.addEventListener(PlayerListUpdatedEvent.class, event -> {
      this.fireTableDataChanged();
    });
  }

  @Override
  public int getRowCount() {
    return this.playerManager.getPlayers().size();
  }

  @Override
  public int getColumnCount() {
    return 2;
  }

  @Override
  public String getColumnName(int column) {
    if (column == 0) {
      return "Nom du joueur";
    } else {
      return "Score";
    }
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if (columnIndex == 0) {
      return this.playerManager.getPlayer(rowIndex).getName();
    } else {
      return this.playerManager.getPlayer(rowIndex).getScore();
    }
  }
}
