package cbatl.model.player;

import cbatl.model.game.Game;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;
import java.util.Random;

public class RandomPlayer extends Player {
  private Random random;

  public RandomPlayer() {
    super("Joueur aléatoire");
    this.random = new Random();
  }

  public Object[] chooseShot(Game game) {
    if (game.isOver() || !game.isPlayerAlive(this)) {
      throw new IllegalStateException("Random player cannot play");
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
