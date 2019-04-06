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

  public Point(Integer x, Integer y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Point && ((Point) o).x.equals(this.x) && ((Point) o).y.equals(this.y);
  }

  public Integer xDelta(Point o) {
    return this.x - o.x;
  }

  public Integer yDelta(Point o) {
    return this.y - o.y;
  }
}
