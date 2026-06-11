// = ISU =
package engine;

import java.io.PrintStream;

import engine.Grid.Dimension;
import engine.Grid.Position;

public class ISU {

	// FIELDS
	public Axis xAxis, yAxis;
	ISU isu;
	public Grid grid;
	public Game game;

	// CONSTRUCTOR
	public ISU(Game game) {
		this.game = game;
		this.xAxis = new Axis(Game.torusOnXaxis, game.width_ncell * Game.cmPerCell);
		this.yAxis = new Axis(Game.torusOnYaxis, game.height_ncell * Game.cmPerCell);
	}

	// SETTER
	public void set(Grid grid) {
		this.grid = grid;
	}

	// == DIMENSION (cm) ==
	public class Dimension {
		public double x_cm;
		public double y_cm;

		// CONSTRUCTOR
		public Dimension(double x_cm, double y_cm) {
			this.x_cm = x_cm;
			this.y_cm = y_cm;
		}

		// GEOMETRY
		public void normalize() {
			if (xAxis.onTorus && this.x_cm > xAxis.perimeter)
				this.x_cm = xAxis.perimeter;
			if (yAxis.onTorus && this.y_cm > yAxis.perimeter)
				this.y_cm = yAxis.perimeter;
		}
		
		// SETTER
		public void setxy(double x_cm, double y_cm) {
			this.x_cm = x_cm;
			this.y_cm = y_cm;
		}

		// GETTER
		public ISU isu() {
			return ISU.this;
		}

		// EQUALS / EQUIV
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o instanceof Dimension)
				return this.x_cm == ((Dimension) o).x_cm && this.y_cm == ((Dimension) o).y_cm;
			return false;
		}

		public boolean equiv(Dimension d) {
			if (d == null)
				return false;
			return xAxis.normalize(this.x_cm) == xAxis.normalize(d.x_cm) && yAxis.normalize(this.y_cm) == yAxis.normalize(d.y_cm);
		}

		// GETTER
		public double x() {
			return x_cm;
		}

		public double y() {
			return y_cm;
		}

		// FACTORY
		public ISU.Vector mkScaledVector(double factor) {
			return ISU.this.new Vector(this.x_cm * factor, this.y_cm * factor);
		}

		public ISU.Vector mkScaledVector(double xFactor, double yFactor) {
			return ISU.this.new Vector(this.x_cm * xFactor, this.y_cm * yFactor);
		}

		public ISU.Vector mkVector() {
			return ISU.this.new Vector(this.x_cm, this.y_cm);
		}

		// SHOW
		public void show(PrintStream ps) {
			ps.printf("ISUDim(%.2fcm, %.2fcm)", this.x_cm, this.y_cm);
		}

	}

	// == POINT ==
	public class Coord {
		public double x_cm, y_cm;

		// CONSTRUCTOR
		public Coord(double x_cm, double y_cm) {
			this.x_cm = xAxis.normalize(x_cm);
			this.y_cm = yAxis.normalize(y_cm);
		}

		// SHOW
		public void show(PrintStream ps) {
			ps.printf("ISUCoord(%.2fcm, %.2fcm)\n", this.x_cm, this.y_cm);
		}

		// EQUALS
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o instanceof Coord)
				return ((Coord) o).x_cm == this.x_cm && ((Coord) o).y_cm == this.y_cm;
			return false;
		}

		// FACTORY
		public ISU.Vector mkVectorToward(Coord target) {
			double dx = xAxis.distance(this.x_cm, target.x_cm);
			double dy = yAxis.distance(this.y_cm, target.y_cm);
			return ISU.this.new Vector(dx, dy);
		}

		// CONVERSION
		public Grid.Position toGridPosition() {
			int gridX = (int) Math.floor(this.x_cm / Game.cmPerCell);
			int gridY = (int) Math.floor(this.y_cm / Game.cmPerCell);
			return grid.new Position(gridX, gridY);
		}

		// TRANSLATION
		public void translate(ISU.Vector v) {
			this.x_cm = xAxis.normalize(x_cm + v.targetX_cm);
			this.y_cm = yAxis.normalize(y_cm + v.targetY_cm);
		}

		public ISU.Coord mkTranslated(ISU.Vector v) {
			return ISU.this.new Coord(x_cm + v.targetX_cm, y_cm + v.targetY_cm);
		}

		// COPY
		public ISU.Coord mkCopy() {
			return ISU.this.new Coord(this.x_cm, this.y_cm);
		}

		// ROTATION
		/**
		 * @apiNote rotation around the origin (0,0)
		 * @param angle_degree
		 */
		public void rotation(int angle_degree) {
			double cos = Math.cos(Math.toRadians(angle_degree));
			double sin = Math.sin(Math.toRadians(angle_degree));
			double oldX = this.x_cm;
			this.x_cm = xAxis.normalize(oldX * cos - this.y_cm * sin);
			this.y_cm = yAxis.normalize(oldX * sin + this.y_cm * cos);
		}

		/**
		 * @apiNote rotation around the given center
		 * @param center
		 * @param angle_degree
		 */
		public void rotateAround(Coord center, int angle_degree) {
			double dx = this.x_cm - center.x_cm;
			double dy = this.y_cm - center.y_cm;
			double cos = Math.cos(Math.toRadians(angle_degree));
			double sin = Math.sin(Math.toRadians(angle_degree));
			this.x_cm = xAxis.normalize(center.x_cm + (dx * cos - dy * sin));
			this.y_cm = yAxis.normalize(center.y_cm + (dx * sin + dy * cos));
		}

		// DISTANCE
		public double distanceTo(Coord pt) {
			double dx = xAxis.distance(this.x_cm, pt.x_cm);
			double dy = yAxis.distance(this.y_cm, pt.y_cm);
			return Math.sqrt(dx * dx + dy * dy);
		}

		
	}

	// == VECTOR ==
	/**
	 * @apiNote The Vector class defines canonical vectors with origin in (0,0)
	 *          poiting at a target coordinate.
	 * @apiNote Canonocal vectors are defined by their target Coord.
	 */
	public class Vector {
		public double targetX_cm, targetY_cm;

		// CONSTRUCTOR
		public Vector(double targetX_cm, double targetY_cm) {
			this.targetX_cm = targetX_cm;
			this.targetY_cm = targetY_cm;
		}

		// OPERATOR
		public void add(Vector v) {
			this.targetX_cm += v.targetX_cm;
			this.targetY_cm += v.targetY_cm;
		}

		public void scale(double factor) {
			this.targetX_cm *= factor;
			this.targetY_cm *= factor;
		}

		public void scale(double xFactor, double yFactor) {
			this.targetX_cm *= xFactor;
			this.targetY_cm *= yFactor;
		}

		/**
		 * @apiNote produit scalaire
		 * @param v
		 * @return le produit scalaire de `this` et du vecteur v
		 */
		public double dot(ISU.Vector v) {
			return this.targetX_cm * v.targetX_cm + this.targetY_cm * v.targetY_cm;
		}

		public double norm() {
			return Math.sqrt(this.targetX_cm * this.targetX_cm + this.targetY_cm * this.targetY_cm);
		}

		/**
		 * @apiNote rend le vecteur unitaire, ie. de norme = 1
		 */
		public void unity() {
			double n = norm();
			if (n != 0) {
				this.targetX_cm /= n;
				this.targetY_cm /= n;
			}
		}

		// TURN
		/**
		 * @apiNote turn the vector itself
		 * @implNote the center of the rotation is the origin of the vector
		 * @param angle_degree
		 */
		public void turn(int angle_degree) {
			double cos = Math.cos(Math.toRadians(angle_degree));
			double sin = Math.sin(Math.toRadians(angle_degree));
			double oldDx = this.targetX_cm;
			this.targetX_cm = oldDx * cos - this.targetY_cm * sin;
			this.targetY_cm = oldDx * sin + this.targetY_cm * cos;
		}
	}
}
