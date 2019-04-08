package cbatl.model.territory;

import cbatl.model.events.territory.TerritoryReceivedShotEvent;
import events.EventTarget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * A collection of boats in a plane. Each point of the territory can be shot, and if a boat is
 * that point, the boat is shot.
 */
public class Territory extends EventTarget {
  private final static Integer[] BOATS_RULE = {5, 4, 3, 3, 2};
  public final Integer width = 10;
  public final Integer height = 10;
  private Collection<Boat> boats;
  private List<Point> receivedShots;

  public Territory() {
    this.boats = new ArrayList<>();
    this.receivedShots = new ArrayList<>();
  }

  private Boolean collision(Boat newBoat) {
    for (Boat existingBoat : this.getBoats()) {
      for (Point point : newBoat.getSectionsPoints()) {
        if (existingBoat.getSectionsPoints().contains(point)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Automatically generates boats for the territory based on rules.
   */
  public void generateFleet() {
    Random random = new Random();

    for (Integer boatType : Territory.BOATS_RULE) {
      Boat newBoat;
      do {
        Boat.Orientation orientation = Boat.Orientation.values()[random.nextInt(3)];
        int lowerHeadXBound = 0;
        int upperHeadXBound = 10;
        int lowerHeadYBound = 0;
        int upperHeadYBound = 10;
        switch (orientation) {
          case NORTH:
            upperHeadYBound = this.height - boatType - 1;
            break;
          case EAST:
            lowerHeadXBound = boatType;
            break;
          case SOUTH:
            lowerHeadYBound = boatType;
            break;
          case WEST:
            upperHeadXBound = this.width - boatType - 1;
            break;
        }

        Point head = new Point(
          random.nextInt(upperHeadXBound - lowerHeadXBound) + lowerHeadXBound,
          random.nextInt(upperHeadYBound - lowerHeadYBound) + lowerHeadYBound
        );
        newBoat = new Boat(head, boatType, orientation);
      } while (this.collision(newBoat));
      this.addBoat(newBoat);
    }
  }

  public Boolean isPointInTerritory(Point point) {
    return 0 <= point.x && point.x < this.width
      && 0 <= point.y && point.y < this.height;
  }

  public Collection<Boat> getBoats() {
    return this.boats;
  }

  public List<Point> getReceivedShots() {
    return this.receivedShots;
  }

  public void addBoat(Boat boat) {
    if (
      !this.isPointInTerritory(boat.getBow()) ||
        !this.isPointInTerritory(boat.translateSectionToPoint(boat.getLength() - 1))
    ) {
      throw new IllegalArgumentException("Boat is not in territory");
    }
    this.boats.add(boat);
  }

  /**
   * Processes an incoming shot.
   *
   * @param shot The shot
   * @return The boat shot if any
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
