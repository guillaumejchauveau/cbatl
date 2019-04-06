package cbatl.view.terminalview;

import cbatl.model.events.territory.TerritoryReceivedShotEvent;
import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import cbatl.model.territory.Territory;

/**
 * An object representing a {@link Territory} with a grid a characters.
 */
class TerritoryGrid {
  final static String WATER = " ";
  final static String SHOT_WATER = "o";
  final static String BOAT = "+";
  final static String SHOT_BOAT = "!";
  final static String SUNK_BOAT = "x";

  private Territory territory;
  /**
   * The grid only with shots (normal mode).
   */
  final String[][] hiddenGrid;
  /**
   * The grid with every boats (cheat mode).
   */
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

  /**
   * The width of the territory.
   *
   * @return The width of the territory
   */
  Integer getWidth() {
    return this.hiddenGrid[0].length;
  }

  /**
   * The height of the territory.
   *
   * @return The height of the territory
   */
  Integer getHeight() {
    return this.hiddenGrid.length;
  }

  /**
   * Re-renders the grids completely.
   */
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

  /**
   * Updates the grids with a shot in water.
   *
   * @param point The shot
   */
  private void markShotWater(Point point) {
    this.hiddenGrid[point.y][point.x] = TerritoryGrid.SHOT_WATER;
    this.visibleGrid[point.y][point.x] = TerritoryGrid.SHOT_WATER;
  }

  /**
   * Updates the grids with a new boat.
   *
   * @param boat The boat
   */
  private void markBoat(Boat boat) {
    for (Point point : boat.getSectionsPoints()) {
      this.visibleGrid[point.y][point.x] = TerritoryGrid.BOAT;
    }
  }

  /**
   * Updates the grids with a shot section.
   *
   * @param point The shot
   */
  private void markShotSection(Point point) {
    this.hiddenGrid[point.y][point.x] = TerritoryGrid.SHOT_BOAT;
    this.visibleGrid[point.y][point.x] = TerritoryGrid.SHOT_BOAT;
  }

  /**
   * Updates the grids with a sunk boat.
   *
   * @param boat The boat
   */
  private void markSunkBoat(Boat boat) {
    for (Point point : boat.getSectionsPoints()) {
      this.hiddenGrid[point.y][point.x] = TerritoryGrid.SUNK_BOAT;
      this.visibleGrid[point.y][point.x] = TerritoryGrid.SUNK_BOAT;
    }
  }
}
