package cbatl.view.terminalview;

import cbatl.model.territory.Boat;
import cbatl.model.territory.Point;
import java.util.Collection;
import java.util.List;

/**
 * Object containing the two grids for the players in the command line Interface
 */
public class TerminalView {

  private int row;
  private int col;
  private String[][] grid;

  public TerminalView(int row, int col) {
    this.row = row;
    this.col = col;
    grid = new String[this.row][this.col];
  }

  public int getRow() {
    return this.row;
  }

  public int getCol() {
    return this.col;
  }

  public String[][] getGrid() {
    return this.grid;
  }

  /**
   * Initializes the grid
   *//*
	public void init (String [][] grille) {
		int x=65;
		for (int i = 0; i <this.row+1; i++) {
			for (int j = 0; j < this.col+1; j++) {
			if (i==0) {
				if (j==0) grille[i][j]=" /";
				else {
					char c =(char) x;
					grille[i][j]= "" + c;
					x++;
				}
			}
			else if (j==0) {
				if (i<10 ) grille[i][j]=" "+i;
				else grille[i][j]=""+i;
			}
			else {
				grille[i][j]=" ";
			}
			}
		}
	}*/
  public void init() {
    for (int x = 0; x < this.row; x++) {
      for (int y = 0; y < this.col; y++) {
        this.grid[x][y] = ".";
      }
    }
  }

  public void init(Collection<Boat> listBoats) {
    this.init();
    for (Boat b : listBoats) {
      Collection<Point> listPoints = b.getSectionsPoints();
      for (Point p : listPoints) {
        int x = p.x;
        int y = p.y;
        this.grid[x][y] = "/";
      }
    }

  }

  public void update(int x, int y) {
    if (this.grid[x][y] == "/")
      this.grid[x][y] = "o";
    else
      this.grid[x][y] = "x";
  }

  public void update(Point shot) {
    this.update(shot.x, shot.y);
  }

  public void update(List<Point> listShot) {
    if (listShot.size() > 0) {
      Point shot = listShot.get(listShot.size() - 1);
      int x = shot.x;
      int y = shot.y;
      this.update(x, y);
    }
  }

  public String getGridElement(int a, int b) {
    return this.grid[a][b];
  }

  public void setGridElement(int x, int y, String symbol) {
    this.grid[x][y] = symbol;
  }

  /**
   * Prints grids in command line Interface
   */
  public void showGrille() {
    System.out.println("Player 1");
    for (int i = 0; i < this.row + 1; i++) {
      for (int j = 0; j < this.col + 1; j++) {
        System.out.print(grid[i][j]);
        System.out.print(" | ");
      }
      System.out.println("");
    }
  }

  /**
   * Change the grid of the player depending of a move coordinate
   *
   * @param x line of the move
   * @param y row of the move
   * @param t the grid where the move happens
   */
  public void touch(int x, int y, int t) {
    if (this.grid[x][y] == " ")
      this.grid[x][y] = "X";

    if (this.grid[x][y] == "/")
      this.grid[x][y] = "0";

  }
}
