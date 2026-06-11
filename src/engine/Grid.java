// = GRID =
package engine;

import java.io.PrintStream;
import java.util.List;

import engine.ISU.Coord;
import engine.ISU.Dimension;

public class Grid {

	// FIELDS
	public ISU isu;
	public Axis xAxis, yAxis;
	int width_ncell, height_ncell;
	public Cell[][] grid;
	public Game game;

	// CONSTRUCTOR
	public Grid(Game game) {
		this.game = game;
		this.width_ncell = game.width_ncell;
		this.height_ncell = game.height_ncell;
		this.xAxis = new Axis(Game.torusOnXaxis, width_ncell);
		this.yAxis = new Axis(Game.torusOnYaxis, height_ncell);
		init();
	}

	// INIT
	public void init() {
		this.grid = new Cell[width_ncell][height_ncell];
		for (int i=0; i<width_ncell; i++) {
			for (int j=0; j<height_ncell; j++) {
				this.grid[i][j] = new Cell(new Position(i, j));
			}
		}
	}

	// GETTER
	public int width() {
		return this.width_ncell;
	}

	public int height() {
		return this.height_ncell;
	}

	public Grid.Cell cellAt(Grid.Position p) {
		return this.grid[p.x][p.y];
	}

	// SHOW
	public void show(PrintStream ps) {
		ps.printf("Grid: %dx%d cells\n", this.width_ncell, this.height_ncell);
	}

	// == DIMENSION (nb cell) ==
	public class Dimension {
		int x_ncell, y_ncell;

		// CONSTRUCTOR
		public Dimension(int x_ncell, int y_ncell) {
			this.x_ncell = x_ncell;
			this.y_ncell = y_ncell;
		}

		// GETTER
		public int x() {
			return x_ncell;
		}

		public int y() {
			return y_ncell;
		}

		// GEOMETRY
		public void normalize() {
			this.x_ncell = xAxis.normalize(this.x_ncell);
			this.y_ncell = yAxis.normalize(this.y_ncell);
		}

		// EQUALS / EQUIV
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o instanceof Dimension)
				return this.x_ncell == ((Dimension) o).x_ncell && this.y_ncell == ((Dimension) o).y_ncell;
			return false;
		}

		public boolean equiv(Dimension d) {	
			if (d == null)
				return false;
			return xAxis.normalize(this.x_ncell) == xAxis.normalize(d.x_ncell) && yAxis.normalize(this.y_ncell) == yAxis.normalize(d.y_ncell);
		}

		// CONVERSION
		public ISU.Dimension toISUDimension() {
			double w_cm = this.x_ncell * Game.cmPerCell;
			double h_cm = this.y_ncell * Game.cmPerCell;
			return game.isu.new Dimension(w_cm, h_cm);
		}

		// SHOW
		public void show(PrintStream ps) {
			ps.printf("Dim_ncell(%d,%d)\n", this.x_ncell, this.y_ncell);
		}

	}

	// == VECTOR ==
	public class Vector {
		public int dy, dx;
		
		// CONSTRUCTOR
		public Vector(int x_ncell, int y_ncell) {
			this.dx = x_ncell;
			this.dy = y_ncell;
		}

		// OPERATION
		public void add(Vector v) {
			this.dx += v.dx;
		    this.dy += v.dy;
		}

		// SHOW
		public void show(PrintStream ps) {
			ps.printf("Vec_ncell(%d,%d)\n", this.dx, this.dy);
		}

	}

	// == POINT ==
	public class Position {
		public int x, y;

		// CONSTRUCTOR
		public Position(int x_ncell, int y_ncell) {
			this.x = xAxis.normalize(x_ncell);
			this.y = yAxis.normalize(y_ncell);
		}

		// COPY ? if needed
		public Grid.Position copy() {
			return new Position(x, y);
		}

		// EQUALS
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o instanceof Position)
				return ((Position) o).x == this.x && ((Position) o).y == this.y;
			return false;
		}

		// TRANSLATION
		public void translate(Vector v) {
			this.x = xAxis.normalize(this.x + v.dx);
			this.y = yAxis.normalize(this.y + v.dy);
		}

		public void moveNorth(int n_ncell) {
			this.y = yAxis.normalize(this.y - n_ncell);
		}
		
		public void moveSouth(int n_ncell) {
			this.y = yAxis.normalize(this.y + n_ncell);
		}

		public void moveEast(int n_ncell) {
			this.x = xAxis.normalize(this.x + n_ncell);
		}
		
		public void moveWest(int n_ncell) {
			this.x = xAxis.normalize(this.x - n_ncell);
		}

		// ROTATION ? if needed
		public void rotateAround(Grid.Position position, int angle_degree) {
			int dx = this.x - position.x;
			int dy = this.y - position.y;
			double cos = Math.cos(Math.toRadians(angle_degree));
			double sin = Math.sin(Math.toRadians(angle_degree));
			this.x = xAxis.normalize(position.x + (int) Math.round(dx * cos - dy * sin));
			this.y = yAxis.normalize(position.y + (int) Math.round(dx * sin + dy * cos));
		}

		// DISTANCE
		public double distanceTo(Position p) {
			double dx = xAxis.distance(this.x, p.x);
			double dy = yAxis.distance(this.y, p.y);
			return Math.sqrt(dx * dx + dy * dy);
		}

		// CONVERSION
		public ISU.Coord toISUCoord() {
			double x_cm = this.x * Game.cmPerCell;
			double y_cm = this.y * Game.cmPerCell;
			return game.isu.new Coord(x_cm, y_cm);
		}

		public ISU.Coord toISUCoordCentered() {
			double x_cm = (this.x + 0.5) * Game.cmPerCell;
			double y_cm = (this.y + 0.5) * Game.cmPerCell;
			return game.isu.new Coord(x_cm, y_cm);
		}

//		Picture.Pixel toPicturePixel() {
//			return null;
//		}

		// SHOW
		public void show(PrintStream ps) {
			ps.printf("Pos_ncell(%d,%d)\n", this.x, this.y);
		}

	}

	// === CELL ===
	public class Cell {

		public Grid.Dimension size;
		public Grid.Position position;
		public List<Entity> entities;

		// CONSTRUCTOR

		public Cell(Position p) {
			this.position = p;
			this.size = new Dimension(1, 1);
			this.entities = new java.util.ArrayList<Entity>();
		}

		// ADD
		public void add(Entity e) {
			if (this.entities != null && !this.entities.contains(e)) {
				this.entities.add(e);
			}
		}

		// REMOVE
		public void remove(Entity e) {
			if (this.entities != null) {
				this.entities.remove(e);
			}
		}

		// PREDICATE
		public boolean contains(Entity e) {
			if (this.entities == null)
				return false;
			return this.entities.contains(e);
		}

		// SHOW
		public void show(PrintStream ps) {
			if (this.entities != null)
				ps.printf("Cell at (%d,%d) containing %d entities\n", this.position.x, this.position.y, this.entities.size());
			else
				ps.printf("Cell at (%d,%d) containing 0 entities\n", this.position.x, this.position.y);
		}	

	}
}
