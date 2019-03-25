package cbatl.model.territory;

/**
 * A point in a 2D space.
 */
public class Point {
  /**
   * Horizontal coordinate.
   */
  public final Integer x;
  /**
   * Vertical coordinate.
   */
  public final Integer y;

  /**
   * @param x
   * @param y
   */
  public Point(Integer x, Integer y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Coordinates must be positive");
    }
    this.x = x;
    this.y = y;
  }

  /**
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    return o instanceof Point && ((Point) o).x.equals(this.x) && ((Point) o).y.equals(this.y);
  }

  /**
   * @param o
   * @return
   */
  public Integer xDelta(Point o) {
    return this.x - o.x;
  }

  /**
   * @param o
   * @return
   */
  public Integer yDelta(Point o) {
    return this.y - o.y;
  }
}
