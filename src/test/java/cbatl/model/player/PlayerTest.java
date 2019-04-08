package cbatl.model.player;

import cbatl.model.ModelException;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {
  @Test
  public void score() throws ModelException {
    Player player = new Player("A");
    Assert.assertEquals(0, player.getScore().intValue());
    player.incrementScore();
    Assert.assertEquals(1, player.getScore().intValue());
  }
}
