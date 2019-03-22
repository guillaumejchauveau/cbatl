package cbatl.model.game;

import cbatl.model.player.Player;
import cbatl.model.player.RandomPlayer;
import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {
  @Test
  public void playerTerritory() {
    Game game = new Game();

    Territory t2 = new Territory(5, 5);
    t2.addBoat(new Boat(new Point(0, 0), 1, Boat.Orientation.NORTH));
    game.addPlayer(new Player("a"), t2);

    Player p = new Player("p");
    Territory t = new Territory(5, 5);
    Boat b = new Boat(new Point(0, 0), 1, Boat.Orientation.NORTH);
    t.addBoat(b);
    game.addPlayer(p, t);

    Assert.assertEquals(t, game.getPlayerTerritory(p));
    Assert.assertTrue(game.isPlayerAlive(p));
    Assert.assertEquals(2, game.getAlivePlayerCount().intValue());
    game.shoot(p, new Point(0, 0));
    Assert.assertEquals(1, b.getShotSections().size());
    Assert.assertFalse(game.isPlayerAlive(p));
  }

  @Test
  public void randomPlayerHandling() {
    Game game = new Game();
    Player human = new Player("Human");
    Territory t1 = new Territory(5, 5);
    t1.addBoat(new Boat(new Point(0, 0), 2, Boat.Orientation.NORTH));
    game.addPlayer(human, t1);

    Player random = new RandomPlayer();
    Territory t2 = new Territory(5, 5);
    t2.addBoat(new Boat(new Point(0, 0), 2, Boat.Orientation.NORTH));
    game.addPlayer(random, t2);

    game.shoot(random, new Point(0, 0));
    Assert.assertEquals(human, game.getCurrentPlayer());
    Assert.assertEquals(1, game.getPlayerTerritory(human).getReceivedShots().size());
  }
}
