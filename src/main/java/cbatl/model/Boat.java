package cbatl.model;

public class Boat {
  private Point headCoordinates;
  private Integer length;
  public enum Orientation {
    NORTH,
    EAST,
    SOUTH,
    WEST
  }
  private Orientation orientation;

  public Boat(Point headCoordinates, Integer length, Orientation orientation) {
    this.headCoordinates = headCoordinates;
    this
  }
}
