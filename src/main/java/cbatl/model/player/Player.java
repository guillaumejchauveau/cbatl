package cbatl.model.player;

import cbatl.model.events.player.PlayerUpdatedEvent;
import events.EventTarget;

/**
 * A player. The player's score is it's victory count.
 */
public class Player extends EventTarget {
  private String name;
  private Integer score;

  public Player(String name) {
    this(name, 0);
  }

  public Player(String name, Integer score) {
    if (name.length() < 1 || !name.matches("^[a-zA-Z0-9]+$") || score < 0) {
      throw new IllegalArgumentException();
    }

    this.name = name;
    this.score = score;
  }

  public String getName() {
    return this.name;
  }

  public Integer getScore() {
    return this.score;
  }

  public void incrementScore() {
    this.score++;
    this.dispatchEvent(new PlayerUpdatedEvent());
  }
}
