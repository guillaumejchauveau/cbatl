package cbatl.model.territory;

import org.junit.Assert;
import org.junit.Test;

public class TerritoryTest {
  @Test
  public void territory() {
    Territory territory = new Territory(new Point(10, 10));
    Assert.assertTrue(territory.isPointInTerritory(new Point(0, 0)));
    Assert.assertTrue(territory.isPointInTerritory(new Point(10, 10)));
    Assert.assertFalse(territory.isPointInTerritory(new Point(-10, -10)));
    Assert.assertFalse(territory.isPointInTerritory(new Point(11, 11)));
    Boat boat = new Boat(new Point(1, 1), 3, Boat.Orientation.NORTH);
    territory.addBoat(boat);
    Assert.assertEquals(1, territory.getBoats().size());
    Assert.assertEquals(boat, territory.getBoats().iterator().next());
    Assert.assertNull(territory.receiveShot(new Point(9, 9)));
    Assert.assertEquals(1, territory.getReceivedShots().size());
    Assert.assertEquals(boat, territory.receiveShot(new Point(1, 2)));
    Assert.assertEquals(1, territory.getReceivedShots().size());
    Assert.assertEquals(1, boat.getShotSections().size());
    Assert.assertEquals(1, boat.getShotSections().iterator().next().intValue());
  }
}
