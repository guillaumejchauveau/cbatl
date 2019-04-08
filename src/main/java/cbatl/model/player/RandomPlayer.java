package cbatl.model.player;

import cbatl.model.ModelException;
import cbatl.model.game.Game;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import java.util.Random;

public class RandomPlayer extends Player {
  public static final String BOT_NAME = "BOT";
  private Random random;

  public RandomPlayer() throws ModelException {
    super(BOT_NAME);
    this.random = new Random();
  }

  public Object[] chooseShot(Game game) throws ModelException {
    if (game.isOver() || !game.isPlayerAlive(this)) {
      throw new ModelException("Random player cannot play");
    }

    Player targetedPlayer;
    do {
      targetedPlayer = game.getAlivePlayers().get(this.random.nextInt(game.getAlivePlayerCount()));
    } while (targetedPlayer == this);

    Territory targetedTerritory = game.getPlayerTerritory(targetedPlayer);

    Point shot;
    do {
      shot = new Point(
        this.random.nextInt(targetedTerritory.width),
        this.random.nextInt(targetedTerritory.height)
      );
    } while (targetedTerritory.getReceivedShots().contains(shot));

    return new Object[]{targetedPlayer, shot};
  }
}
