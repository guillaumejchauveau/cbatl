package cbatl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Boat {
  public enum Orientation {
    NORTH,
    EAST,
    SOUTH,
    WEST
  }

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
        xOffset = 1;
        break;
      case SOUTH:
        yOffset = -1;
        break;
      case WEST:
        xOffset = -1;
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

  public Collection<Point> getSectionsPoints() {
    Collection<Point> points = new ArrayList<>();
    for (int i = 0; i < this.getLength(); i++) {
      points.add(this.translateSectionToPoint(i));
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

  public void addShotSection(Integer section) {
    if (section < 0 || section >= this.getLength()) {
      throw new IllegalArgumentException("Section number is illegal");
    }
    this.shotSections.add(section);
  }

  public Boolean isSunk() {
    return this.shotSections.size() == this.length;
  }
}
