package cbatl.model;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class BoatTest {
  @Test
  public void sections() {
    Point head = new Point(0, 0);
    Integer length = 3;
    Boat.Orientation orientation = Boat.Orientation.NORTH;
    Boat boat = new Boat(head, length, orientation);
    Assert.assertEquals(head, boat.translateSectionToPoint(0));
    Assert.assertEquals(new Point(0, 1), boat.translateSectionToPoint(1));
    Assert.assertEquals(0, boat.translatePointToSection(head).intValue());
    Assert.assertEquals(1, boat.translatePointToSection(new Point(0, 1)).intValue());
    Collection<Point> sectionPoints = boat.getSectionsPoints();
    Assert.assertEquals(length.intValue(), sectionPoints.size());
    Assert.assertTrue(sectionPoints.contains(head));
    Assert.assertTrue(sectionPoints.contains(new Point(head.x, head.y + 2)));
    Assert.assertFalse(sectionPoints.contains(new Point(head.x + 5, head.y)));
    Assert.assertEquals(0, boat.getShotSections().size());
    Assert.assertFalse(boat.isSunk());
    boat.addShotSection(1);
    Assert.assertEquals(1, boat.getShotSections().size());
    Assert.assertEquals(new Point(0, 1), boat.getShotSectionsPoints().iterator().next());
    boat.addShotSection(0);
    boat.addShotSection(2);
    Assert.assertTrue(boat.isSunk());
  }
}
