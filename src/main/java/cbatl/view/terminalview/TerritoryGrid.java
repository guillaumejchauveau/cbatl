package cbatl.view.terminalview;

import cbatl.model.events.territory.TerritoryReceivedShotEvent;
import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;

/**
 *
 */
class TerritoryGrid {
  final static String WATER = ".";
  final static String SHOT_WATER = "o";
  final static String BOAT = "+";
  final static String SHOT_BOAT = "!";
  final static String SUNK_BOAT = "x";

  private Territory territory;
  final String[][] hiddenGrid;
  final String[][] visibleGrid;

  TerritoryGrid(Territory territory) {
    this.territory = territory;
    this.hiddenGrid = new String[this.territory.height][this.territory.width];
    this.visibleGrid = new String[this.territory.height][this.territory.width];
    this.forceUpdate();
    this.territory.addEventListener(TerritoryReceivedShotEvent.class, event -> {
      this.markShotWater(event.shot);
      if (event.shotBoat != null) {
        this.markShotSection(event.shot);
        if (event.shotBoat.isSunk()) {
          this.markSunkBoat(event.shotBoat);
        }
      }
    });
  }

  Integer getWidth() {
    return this.hiddenGrid[0].length;
  }

  Integer getHeight() {
    return this.hiddenGrid.length;
  }

  private void forceUpdate() {
    for (int y = 0; y < this.territory.height; y++) {
      for (int x = 0; x < this.territory.width; x++) {
        this.hiddenGrid[y][x] = TerritoryGrid.WATER;
        this.visibleGrid[y][x] = TerritoryGrid.WATER;
      }
    }

    for (Point point : this.territory.getReceivedShots()) {
      this.markShotWater(point);
    }

    for (Boat boat : this.territory.getBoats()) {
      if (boat.isSunk()) {
        this.markSunkBoat(boat);
      } else {
        this.markBoat(boat);
        for (Point point : boat.getShotSectionsPoints()) {
          this.markShotSection(point);
        }
      }
    }
  }

  private void markShotWater(Point point) {
    this.hiddenGrid[point.y][point.x] = TerritoryGrid.SHOT_WATER;
    this.visibleGrid[point.y][point.x] = TerritoryGrid.SHOT_WATER;
  }

  private void markBoat(Boat boat) {
    for (Point point : boat.getSectionsPoints()) {
      this.visibleGrid[point.y][point.x] = TerritoryGrid.BOAT;
    }
  }

  private void markShotSection(Point point) {
    this.hiddenGrid[point.y][point.x] = TerritoryGrid.SHOT_BOAT;
    this.visibleGrid[point.y][point.x] = TerritoryGrid.SHOT_BOAT;
  }

  private void markSunkBoat(Boat boat) {
    for (Point point : boat.getSectionsPoints()) {
      this.hiddenGrid[point.y][point.x] = TerritoryGrid.SUNK_BOAT;
      this.visibleGrid[point.y][point.x] = TerritoryGrid.SUNK_BOAT;
    }
  }
}
