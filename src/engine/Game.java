// == GAME ==
package engine;

import java.io.PrintStream;

public class Game {

	// CONSTANT
	public static final boolean torusOnXaxis = true; // vrai si l'axe X est une boucle fermée
	public static final boolean torusOnYaxis = true; // vrai si l'axe Y est une boucle fermée
	public static final double cmPerCell = 3.7; // échelle qui relie l'unité ncell à cm
	public static final int pixelPerCm = 2; // échelle qui relie l'unité pixel à cm

	// FIELDS
	public int width_ncell; // largeur du monde en nombre de cellules
	public int height_ncell; // hauteur du monde en nombre de celluls
	double width_cm; // largeur du monde en cm
	double height_cm; // hauteur du monde en cm
	public Grid grid; // permet la création de coordonnées en unités ncell
	public ISU isu; // permet la création de coordonnées en unités cm
	// protected Picture pict; // permet la création de coordonnées en unités pixel, ne sera utilisé qu'à
					// partir de Task2

	// CONSTRUCTORS
	public Game(int w_ncell, int h_ncell) {
		if (w_ncell <= 0 || h_ncell <= 0)
			throw new IllegalArgumentException();

		this.width_ncell = w_ncell;
		this.height_ncell = h_ncell;
		this.width_cm = w_ncell * Game.cmPerCell;
		this.height_cm = h_ncell * Game.cmPerCell;
		init();
	}

	public Game(double w_cm, double h_cm) {
		if (w_cm <= 0 || h_cm <= 0)
			throw new IllegalArgumentException();
		
		this.width_ncell = (int) Math.round(w_cm / Game.cmPerCell);
		this.height_ncell = (int) Math.round(h_cm / Game.cmPerCell);
		this.width_cm = w_cm;
		this.height_cm = h_cm;
		init();
	}
	
	private void init() {
		if (game != null)
			throw new IllegalStateException();
		game = this;
		this.grid = new Grid(this);
		this.isu = new ISU(this);
		this.isu.set(this.grid);
	}

	// GETTER
	private static Game game;

	public static Game game() {
		return game;
	}

	// SHOW
	public void show(PrintStream ps) {
		ps.printf("Game State:\n");
		ps.printf("- Grid Size: %dx%d cells\n", this.width_ncell, this.height_ncell);
		ps.printf("- World Size: %.2fcm x %.2fcm\n", this.width_cm, this.height_cm);
		ps.printf("- Torus: X=%b, Y=%b\n", Game.torusOnXaxis, Game.torusOnYaxis);
	}
}
