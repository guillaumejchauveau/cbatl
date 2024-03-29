package cbatl.model.territory;

import cbatl.model.ModelException;
import org.junit.Assert;
import org.junit.Test;

public class TerritoryTest {
  @Test
  public void territory() throws ModelException {
    Territory territory = new Territory();
    Assert.assertTrue(territory.isPointInTerritory(new Point(0, 0)));
    Assert.assertTrue(territory.isPointInTerritory(new Point(9, 9)));
    Assert.assertFalse(territory.isPointInTerritory(new Point(11, 11)));
    territory.generateFleet();
    Assert.assertEquals(5, territory.getBoats().size());
  }
}
