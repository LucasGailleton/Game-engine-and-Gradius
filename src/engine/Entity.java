// == ENTITY ==
package engine;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.Grid.Cell;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.ISU.Vector;
import engine.shapes.Bounding;
import engine.shapes.Rect;
import engine.shapes.iShape;
import game.Model;

public class Entity {

	// FIELDS
	public Grid grid;
	public ISU isu;
	public String name;
	public Game game;

	// FIELDS
	public ISU.Dimension size; // dimension de l'entité
	private ISU.Dimension step; // dimension d'un pas de déplacement
	private Grid.Position position; // position dans la grille
	private ISU.Coord center; // coordonnées en cm du centre de l'entité
	private Bounding bounding;
	private Set<Grid.Cell> occupied;
	private engine.gal.Bot bot;
	private ISU.Coord boundingBoxTopLeft;
	private ISU.Coord boundingBoxBottomRight;
	private int orientation_degree; // orientation par rapport à l'axe des x

	private Stunt stunt;
	private Model model;
	private Vector lSpeed;
	private double aSpeed;

	// CONSTRUCTOR
	public Entity(String name) {
		this.name = name;
		this.game = Game.game();
		this.grid = game.grid;
		this.isu = game.isu;
		this.orientation_degree = 0;
		this.step = this.isu.new Dimension(Game.cmPerCell, Game.cmPerCell);
		this.bounding = new Bounding();
		this.occupied = new HashSet<Grid.Cell>();
		this.lSpeed = this.isu.new Vector(0, 0);
//		this.updateBoundingBox();
	}

	// SETTER
	public void setPosition(Grid.Position position) {
		this.position = position;
		this.center = position.toISUCoordCentered() ;
	}

	public void setCoord(ISU.Coord center) {
		this.center = center;
		this.position = center.toGridPosition();
	}

	public void setSize(Grid.Dimension dimension) {
		this.size = dimension.toISUDimension();
	}

	public void setSize(ISU.Dimension dimension) {
		this.size = dimension;
	}

	public void setStep(ISU.Dimension step) {
		this.step = step;
	}

	public void setBounding(Bounding bounding) {
		this.bounding = bounding;
	}

	public void setStunt(Stunt stunt) {
		this.stunt = stunt;
	}
	
	public void setlSpeed(double xSpeed, double ySpeed) {
		this.lSpeed = this.isu.new Vector(xSpeed, ySpeed);
	}
	
	public void setaSpeed(double aSpeed) {
		this.aSpeed = aSpeed;
	}
	
	public void setBot(engine.gal.Bot bot) {
		this.bot = bot;
	}

	// GETTER
	public ISU.Coord center() {
		return this.center;
	}

	public Grid.Position position() {
		return this.position;
	}

	public int orientation() {
		return this.orientation_degree;
	}

	public ISU.Dimension step() {
		return this.step;
	}

	public Bounding bounding() {
		return this.bounding;
	}

	public Vector lSpeed() {
		return this.lSpeed;
	}

	public double aSpeed() {
		return this.aSpeed;
	}

	public Stunt stunt() {
		return this.stunt;
	}
	
	public engine.gal.Bot bot(){
		return this.bot;
	}

	// BOUNDING BOX UPDATE
	public void updateBoundingBox() {
		double halfLongest = Math.max(this.size.x_cm / 2.0, this.size.y_cm / 2.0);
		this.boundingBoxTopLeft = this.isu.new Coord(this.center.x_cm-halfLongest-1, this.center.y_cm-halfLongest-1);
		this.boundingBoxBottomRight = this.isu.new Coord(this.center.x_cm+halfLongest+1, this.center.y_cm+halfLongest+1);
	}
	
	public Rect boundingBox() {
//		double longest = Math.max(this.size.x_cm, this.size.y_cm);
//		return new Rect(this.isu.new Coord(this.center.x_cm, this.center.y_cm), this.isu.new Dimension(longest, longest), 0);
		return new Rect(this.isu.new Coord(this.center.x_cm, this.center.y_cm), this.isu.new Dimension(this.size.x_cm, this.size.y_cm), 0);
	}

	// TRANSLATION
	public void translate(Grid.Vector v) {
		retract();
		this.position.translate(v);
		this.center = this.position.toISUCoordCentered();
		moveBoundings(v);
		deploy();
	}

	public void translate(ISU.Vector v) {
		retract();
		this.center.x_cm = this.isu.xAxis.normalize(this.center.x_cm + v.targetX_cm);
		this.center.y_cm = this.isu.yAxis.normalize(this.center.y_cm + v.targetY_cm);
		this.position = this.center.toGridPosition();
		moveBoundings(v);
		deploy();
	}
	
	private void moveBoundings(Grid.Vector v) {
		for (iShape shape : this.bounding.boundings()) {
			shape.translate(v.dx, v.dy);
		}
	}
	
	private void moveBoundings(ISU.Vector v) {
		for (iShape shape : this.bounding.boundings()) {
			shape.translate(v);
		}
	}

	// TURN
	/**
	 * @apiNote turn is a rotation around the center of the entity.
	 * @param angle_degree
	 */
	public void turn(int angle_degree) {
		this.orientation_degree = (this.orientation_degree + angle_degree) % 360;
		if (this.orientation_degree < 0)
			this.orientation_degree += 360;
	}

	// SHOW
	public void show(PrintStream ps) {
		ps.printf("Entity '%s'\n", this.name);
		if (this.position != null) {
			ps.printf("Grid: ");
			this.position.show(ps);
		}
		if (this.center != null) {
			ps.printf("Center: ");
			this.center.show(ps);
		}
		if (this.lSpeed != null) {
			ps.printf("lSpeed: targetX_cm = %f, targetY_cm = %f\n", this.lSpeed.targetX_cm, this.lSpeed.targetY_cm);
		}
		ps.printf("Orientation: %d°\n", this.orientation_degree);
	}

	// === MOVE ===
	/**
	 * @apiNote déplacement vers le nord en nombre de pas
	 * @param nStep
	 */
	public void moveNorth(int nStep) {
		ISU.Vector v = this.isu.new Vector(0, -nStep * this.step.y_cm);
		this.translate(v);
	}

	public void moveSouth(int nStep) {
		ISU.Vector v = this.isu.new Vector(0, nStep * this.step.y_cm);
		this.translate(v);
	}

	public void moveEast(int nStep) {
		ISU.Vector v = this.isu.new Vector(nStep * this.step.x_cm, 0);
		this.translate(v);
	}

	public void moveWest(int nStep) {
		ISU.Vector v = this.isu.new Vector(-nStep * this.step.y_cm, 0);
		this.translate(v);
	}

	/**
	 * @apiNote déplacement vers l'est en cm
	 * @param length_cm
	 */
	public void moveNorth(double length_cm) {
		ISU.Vector v = this.isu.new Vector(0.0, -length_cm);
		this.translate(v);
	}

	public void moveSouth(double length_cm) {
		ISU.Vector v = this.isu.new Vector(0.0, length_cm);
		this.translate(v);
	}

	public void moveEast(double length_cm) {
		ISU.Vector v = this.isu.new Vector(length_cm, 0.0);
		this.translate(v);
	}

	public void moveWest(double length_cm) {
		ISU.Vector v = this.isu.new Vector(-length_cm, 0.0);
		this.translate(v);
	}

	// INTERSECTS
	public boolean intersects(Entity e) {
		if (Collections.disjoint(this.occupied, e.occupied))
			return false;
		return this.bounding.intersects(e.bounding());
	}

	// COLLISIONS
	public void collision(Entity e) {
		this.stunt.collision(e);
	}

	public void collision(List<Entity> le) {
		this.stunt.collision(le);
	}

	public boolean haslSpeed() {
		return this.lSpeed != null && (this.lSpeed.targetX_cm != 0 || this.lSpeed.targetY_cm != 0);
	}

	public void deploy() {
		if (this.position != null) {
			double halfLongest = Math.max(this.size.x_cm / 2.0, this.size.y_cm / 2.0);
			int xmin = ((int) Math.round((this.center.x_cm - halfLongest) / Game.cmPerCell)) - 1;
			int ymin = ((int) Math.round((this.center.y_cm - halfLongest) / Game.cmPerCell)) - 1;
			int xmax = ((int) Math.floor((this.center.x_cm + halfLongest - 1e-9) / Game.cmPerCell)) + 1;
			int ymax = ((int) Math.floor((this.center.y_cm + halfLongest - 1e-9) / Game.cmPerCell)) + 1;

			for (int y = ymin; y <= ymax; y++) {
				for (int x = xmin; x <= xmax; x++) {
					Grid.Cell c = this.grid.cellAt(this.grid.new Position(this.grid.xAxis.normalize(x), this.grid.yAxis.normalize(y)));
					c.add(this);
					this.occupied.add(c);
				}
			}
		}
		this.updateBoundingBox();
	}

	public void occupy(Grid.Position position) {
		retract();
		this.position = position;
		this.center = this.position.toISUCoordCentered();
		deploy();
	}

	public void retract() {
		for (Grid.Cell c : this.occupied) {
			c.remove(this);
		}
	}
}
