package cbatl.view.terminalview;

/**
 * Object containing the two grids for the players in the command line Interface
 */
public class TerminalView {

	private int row;
	private int col;
	private String [][] grille1 ;
	private String [][] grille2 ;

	public TerminalView(int row, int col) {
		this.row = row;
		this.col = col;
		grille1 = new String [this.row+1][this.col+1];
		grille2 = new String [this.row+1][this.col+1];
		this.init(grille1);
		this.init(grille2);
	}

	/**
	 * Initializes the grid
	 */
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
	}

	/**
	 * Prints grids in command line Interface
	 */
	public void showGrille() {
		System.out.println("Player 1");
		for (int i = 0; i <this.row+1; i++) {
			for (int j = 0; j < this.col+1; j++) {
				System.out.print(grille1[i][j]);
				System.out.print(" | ");
			}
			System.out.println("");
		}
		System.out.println("Player 2");
		for (int i = 0; i <this.row+1; i++) {
			for (int j = 0; j < this.col+1; j++) {
				System.out.print(grille1[i][j]);
				System.out.print(" | ");
			}
			System.out.println("");
		}
	}

	/**
	 * Change the grid of the player depending of a move coordinate
	 * @param x line of the move
	 * @param y row of the move
	 * @param t the grid where the move happens
	 */
	public void touch(int x,int y,int t) {
		grille1[x][y] = "X";
	}
}
