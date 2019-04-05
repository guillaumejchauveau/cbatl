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

  private Boolean collision(Boat newBoat) {
    Collection<Point> newBoatPoints = newBoat.getSectionsPoints();
    Point head = newBoat.getHead();

    switch (newBoat.getOrientation()) {
      case NORTH:
        newBoatPoints.add(new Point(head.x, head.y - 1));
        newBoatPoints.add(new Point(head.x, head.y + newBoat.getLength()));
        break;
      case EAST:
        newBoatPoints.add(new Point(head.x + 1, head.y ));
        newBoatPoints.add(new Point(head.x - newBoat.getLength(), head.y));
        break;
      case SOUTH:
        newBoatPoints.add(new Point(head.x, head.y + 1));
        newBoatPoints.add(new Point(head.x, head.y - newBoat.getLength()));
        break;
      case WEST:
        newBoatPoints.add(new Point(head.x - 1, head.y));
        newBoatPoints.add(new Point(head.x + newBoat.getLength(), head.y));
        break;
    }

    Collection<Point> newBoatPoints2 = new ArrayList<>(newBoatPoints);
    for (Point point : newBoatPoints) {
      if (newBoat.getOrientation().vertical()) {
        newBoatPoints2.add(new Point(point.x - 1, point.y));
        newBoatPoints2.add(new Point(point.x + 1, point.y));
      } else {
        newBoatPoints2.add(new Point(point.x, point.y - 1));
        newBoatPoints2.add(new Point(point.x, point.y + 1));
      }
    }

    for (Boat existingBoat : this.getBoats()) {
      for (Point point : newBoatPoints2) {
        if (existingBoat.getSectionsPoints().contains(point)) {
          return true;
        }
      }
    }
    return false;
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
