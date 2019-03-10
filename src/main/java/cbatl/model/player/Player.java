package cbatl.model.player;

import cbatl.model.events.player.PlayerUpdatedEvent;
import events.EventTarget;

/**
 *
 */
public class Player extends EventTarget {
  private String name;
  private Integer score;

  /**
   * @param name
   */
  public Player(String name) {
    this(name, 0);
  }

  /**
   * @param name
   * @param score
   */
  public Player(String name, Integer score) {
    if (!name.matches("^[a-zA-Z0-9]+$") || score < 0) {
      throw new IllegalArgumentException();
    }

    this.name = name;
    this.score = score;
  }

  /**
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return
   */
  public Integer getScore() {
    return this.score;
  }

  /**
   *
   */
  public void incrementScore() {
    this.score++;
    this.dispatchEvent(new PlayerUpdatedEvent());
  }
}
