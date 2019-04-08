package cbatl.model.player;

import cbatl.model.ModelException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class PlayerManagerTest {
  @Test
  public void registry() throws ModelException {
    PlayerManager pm = new PlayerManager();
    Player player = new Player("A");
    pm.registerPlayer(player);
    Assert.assertTrue(pm.hasPlayer(player));
    Assert.assertTrue(pm.hasPlayer("A"));
    Assert.assertTrue(pm.hasPlayer(new Player(player.getName())));
    Assert.assertEquals(player, pm.getPlayer(player.getName()));
    pm.removePlayer(player);
    Assert.assertEquals(0, pm.getPlayers().size());
  }

  @Test
  public void save() throws IOException, ModelException {
    File saveFile = new File("saveFile");
    PlayerManager pm = new PlayerManager();
    pm.registerPlayer(new Player("Player"));
    pm.saveToFile(new FileWriter(saveFile));
    pm = PlayerManager.createFromFile(new FileReader(saveFile));
    Assert.assertEquals(1, pm.getPlayers().size());
    saveFile.delete();
  }
}
