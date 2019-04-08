package cbatl.model.player;

import cbatl.model.ModelException;
import cbatl.model.events.player.PlayerUpdatedEvent;
import events.EventTarget;

/**
 * A player. The player's score is it's victory count.
 */
public class Player extends EventTarget {
  private String name;
  private Integer score;

  public Player(String name) throws ModelException {
    this(name, 0);
  }

  public Player(String name, Integer score) throws ModelException {
    if (!Player.isNameValid(name) || score < 0) {
      throw new ModelException("Invalid name");
    }

    this.name = name;
    this.score = score;
  }

  public static Boolean isNameValid(String name) {
    return name.length() > 0 & name.matches("^[a-zA-Z0-9]+$");
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
