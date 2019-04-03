package cbatl.model.territory;

import cbatl.model.events.territory.TerritoryReceivedShotEvent;
import events.EventTarget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Territory extends EventTarget {
  public final static Integer[] BOATS_RULE = {5, 4, 3, 3, 2};
  public final Integer width = 10;
  public final Integer height = 10;
  private Collection<Boat> boats;
  private List<Point> receivedShots;

  /**
   *
   */
  public Territory() {
    this.boats = new ArrayList<>();
    this.receivedShots = new ArrayList<>();
    Random random = new Random();

    for (Integer boatType : Territory.BOATS_RULE) {
      Boat.Orientation orientation = Boat.Orientation.values()[random.nextInt(3)];
      Boat newBoat = new Boat(new Point(random.nextInt(10), random.nextInt(10)), boatType, orientation);
      while (true) {
        for (Boat existingBoat : this.getBoats()) {
          boolean ortho;
          switch (existingBoat.getOrientation()) {
            case NORTH:
            case SOUTH:
              System.out.println("N");
              break;
            case EAST:
            case WEST:
              System.out.println("O");
              break;
          }
        }
        break;
      }
    }
  }

  /**
   * @param point
   * @return
   */
  public Boolean isPointInTerritory(Point point) {
    return 0 <= point.x && point.x < this.width
      && 0 <= point.y && point.y < this.height;
  }

  /**
   * @return
   */
  public Collection<Boat> getBoats() {
    return this.boats;
  }

  /**
   * @return
   */
  public List<Point> getReceivedShots() {
    return this.receivedShots;
  }

  public void generateFleet() {
    this.addBoat(new Boat(new Point(0, 0), 5, Boat.Orientation.NORTH));
  }

  /**
   * @param boat
   */
  public void addBoat(Boat boat) {
    if (
      !this.isPointInTerritory(boat.getHead()) ||
        !this.isPointInTerritory(boat.translateSectionToPoint(boat.getLength() - 1))
    ) {
      throw new IllegalArgumentException("Boat is not in territory");
    }
    this.boats.add(boat);
  }

  /**
   * @param shot
   * @return
   */
  public Boat receiveShot(Point shot) {
    if (!this.isPointInTerritory(shot)) {
      throw new IllegalArgumentException("Shot is not in territory");
    }
    Boat shotBoat = null;

    for (Boat boat : this.getBoats()) {
      for (Point sectionPoint : boat.getSectionsPoints()) {
        if (sectionPoint.equals(shot)) {
          boat.addShotSection(boat.translatePointToSection(sectionPoint));
          shotBoat = boat;
        }
      }
    }
    this.receivedShots.add(shot);
    this.dispatchEvent(new TerritoryReceivedShotEvent(shot, shotBoat));
    return shotBoat;
  }
}
