package cbatl.model.territory;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {
  @Test
  public void point() {
    Point point = new Point(0, 0);
    Assert.assertEquals(0, point.x.intValue());
    Assert.assertEquals(0, point.y.intValue());
    Assert.assertEquals(point, new Point(0, 0));
    Assert.assertNotEquals(point, new Point(1, 1));
    Assert.assertEquals(0, point.xDelta(new Point(0, 0)).intValue());
    Assert.assertEquals(0, point.yDelta(new Point(0, 0)).intValue());
    Assert.assertEquals(-1, point.xDelta(new Point(1, 1)).intValue());
    Assert.assertEquals(-1, point.yDelta(new Point(1, 1)).intValue());
  }
}
