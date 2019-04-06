package cbatl.model.territory;

import cbatl.model.events.territory.BoatSankEvent;
import cbatl.model.events.territory.BoatShotEvent;
import events.EventTarget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Boat extends EventTarget {
  private Point head;
  private Integer length;
  private Orientation orientation;
  private Set<Integer> shotSections;

  public Boat(Point head, Integer length, Orientation orientation) {
    this.head = head;
    this.length = length;
    this.orientation = orientation;
    this.shotSections = new HashSet<>();
  }

  public Point getHead() {
    return this.head;
  }

  public Integer getLength() {
    return this.length;
  }

  public Orientation getOrientation() {
    return this.orientation;
  }

  public Point translateSectionToPoint(Integer section) {
    if (section < 0 || section >= this.getLength()) {
      throw new IllegalArgumentException("Section number is illegal");
    }

    int xOffset = 0;
    int yOffset = 0;
    switch (this.getOrientation()) {
      case NORTH:
        yOffset = 1;
        break;
      case EAST:
        xOffset = -1;
        break;
      case SOUTH:
        yOffset = -1;
        break;
      case WEST:
        xOffset = 1;
        break;
    }
    Point head = this.getHead();
    return new Point(head.x + xOffset * section, head.y + yOffset * section);
  }

  public Integer translatePointToSection(Point point) {
    int xDelta = this.getHead().xDelta(point);
    int yDelta = this.getHead().yDelta(point);
    Integer section = null;
    switch (this.getOrientation()) {
      case NORTH:
        section = -yDelta;
        break;
      case EAST:
        section = xDelta;
        break;
      case SOUTH:
        section = yDelta;
        break;
      case WEST:
        section = -xDelta;
        break;
    }
    if (section < 0 || section >= this.getLength()) {
      throw new IllegalArgumentException("Section point is illegal");
    }
    return section;
  }

  public Collection<Integer> getSections() {
    Collection<Integer> sections = new ArrayList<>();
    for (int i = 0; i < this.getLength(); i++) {
      sections.add(i);
    }
    return sections;
  }

  public Collection<Point> getSectionsPoints() {
    Collection<Point> points = new ArrayList<>();
    for (Integer section : this.getSections()) {
      points.add(this.translateSectionToPoint(section));
    }
    return points;
  }

  public Collection<Integer> getShotSections() {
    return this.shotSections;
  }

  public Collection<Point> getShotSectionsPoints() {
    Collection<Point> points = new ArrayList<>();
    for (Integer section : this.getShotSections()) {
      points.add(this.translateSectionToPoint(section));
    }
    return points;
  }

  public Boolean isSunk() {
    return this.getShotSections().size() == this.getLength();
  }

  public void addShotSection(Integer section) {
    if (section < 0 || section >= this.getLength()) {
      throw new IllegalArgumentException("Section number is illegal");
    }
    this.shotSections.add(section);
    this.dispatchEvent(new BoatShotEvent(section));
    if (this.isSunk()) {
      this.dispatchEvent(new BoatSankEvent());
    }
  }

  public enum Orientation {
    NORTH (0),
    EAST (1),
    SOUTH (2),
    WEST (3);

    private final int orientation;

    Orientation(int orientation) {
      this.orientation = orientation;
    }

    public Orientation opposed() {
      switch (this.orientation) {
        case 0:
          return SOUTH;
        case 1:
          return WEST;
        case 2:
          return NORTH;
        case 3:
          return EAST;
        default:
          return null;
      }
    }

    public Boolean vertical() {
      return this.orientation == 0|| this.orientation == 2;
    }
  }
}
