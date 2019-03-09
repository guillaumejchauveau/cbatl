package cbatl.model;

public class Point {
  public final Integer x;
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
