package cbatl.model.territory;

import cbatl.model.events.territory.TerritoryReceivedShotEvent;
import events.EventTarget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class Territory extends EventTarget {
  private Point endPoint;
  private Collection<Boat> boats;
  private List<Point> receivedShots;

  /**
   * @param endPoint
   */
  public Territory(Point endPoint) {
    this.endPoint = endPoint;
    this.boats = new ArrayList<>();
    this.receivedShots = new ArrayList<>();
  }

  /**
   * @param point
   * @return
   */
  public Boolean isPointInTerritory(Point point) {
    return 0 <= point.x && point.x <= this.endPoint.x
      && 0 <= point.y && point.y <= this.endPoint.y;
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

    for (Boat boat : this.getBoats()) {
      for (Point sectionPoint : boat.getSectionsPoints()) {
        if (sectionPoint.equals(shot)) {
          boat.addShotSection(boat.translatePointToSection(sectionPoint));
          this.dispatchEvent(new TerritoryReceivedShotEvent(shot, boat));
          return boat;
        }
      }
    }
    this.receivedShots.add(shot);
    this.dispatchEvent(new TerritoryReceivedShotEvent(shot));
    return null;
  }
}
