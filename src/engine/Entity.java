// == ENTITY ==
package engine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.ISU.Vector;
import engine.GAL.Category;
import engine.shapes.Bounding;
import engine.shapes.Rect;
import engine.shapes.iShape;
import engine.view.View;
import game.Main;
import game.Model;
import game.entities.Avatar;
import game.entities.PowerUp;
import game.entities.Ship;

//Classe abstraite qui va servir à définir toutes les qctions
//qu'une entité peut faire et tous ses atttributs
public abstract class Entity {

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
	protected engine.GAL.Bot bot;
	private int orientation_degree; // orientation par rapport à l'axe des x
	
	protected Avatar avatar;
	public List<Entity> trueCollisions;

	protected Stunt stunt;
	protected Model model;
	private Vector lSpeed; //normal speed
	private double aSpeed; //angular speed
	Category category; //pour les automates
	
	protected View view;
	
	protected boolean alive = true;

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
		this.trueCollisions = new ArrayList<Entity>();
	}

	// SETTER
	public void setView(View view) {
		this.view=view;
	}
	
	public void setorient(int i) {
		this.orientation_degree=i;
	}
	public void setPosition(Grid.Position position) {
		this.position = position;
		this.center = position.toISUCoordCentered();
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
	
	public void setBounding() {
		this.bounding().add(new Rect(this.center(), this.size, this.orientation_degree));
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
	
	public void setBot(engine.GAL.Bot bot) {
		this.bot = bot;
	}
	
	public void setModel(Model model) { 
		this.model = model;
	}
	
	public void setCategory(Category cat) { 
		this.category = cat;
	}

	// GETTER
	public View view() {
		return this.view;
	}
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
	
	public engine.GAL.Bot bot(){
		return this.bot;
	}
	
	public List<Entity> getEntities() {
		return model.entities();
	}
	
	public Model getModel() {
		return model;
	}
	
	
	public Avatar getAvatar() {
		return this.avatar;
	}
	
	public boolean isAlive() {
        return this.alive;
    }
	
	public Category Category() {
		return this.category;
	}
	
	public int orient() {
		return this.orientation_degree;
	}
	
	public Rect boundingBox() {
		return new Rect(this.isu.new Coord(this.center.x_cm, this.center.y_cm), this.isu.new Dimension(this.size.x_cm, this.size.y_cm), this.orientation_degree);
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
		if(this.stunt!=null) {
			this.stunt.consumeMoveDistance(v.norm());
		}
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
		for (iShape shape : this.bounding.boundings()) {
            shape.rotateAround(this.center, angle_degree);
        }
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
		if (this.stunt != null) {
	        this.stunt.collision(e);
	    } else {
	        this.die(); 
	    }
	}

	public void collision(List<Entity> le) {
		if(this.stunt != null)
			this.stunt.collision(le); 
		else
			this.die();
	}

	public boolean haslSpeed() {
		return this instanceof Ship || (this.lSpeed != null && (this.lSpeed.targetX_cm != 0 || this.lSpeed.targetY_cm != 0));
	}

	public void deploy() {
		this.retract();
		if (this.center != null) {
			
			double halfX = this.size.x_cm / 2.0;
			double halfY = this.size.y_cm / 2.0;
			//double halfLongest = Math.max(this.size.x_cm / 2.0, this.size.y_cm / 2.0);
			
			int xmin = ((int) Math.round((this.center.x_cm - halfX) / Game.cmPerCell)) - 1;
			int ymin = ((int) Math.round((this.center.y_cm - halfY) / Game.cmPerCell)) - 1;
			int xmax = ((int) Math.floor((this.center.x_cm + halfX - 1e-9) / Game.cmPerCell)) + 1;
			int ymax = ((int) Math.floor((this.center.y_cm + halfY - 1e-9) / Game.cmPerCell)) + 1;

			for (int y = ymin; y <= ymax; y++) {
				for (int x = xmin; x <= xmax; x++) {
					
					try {
						Grid.Cell c = this.grid.cellAt(this.grid.new Position(this.grid.xAxis.normalize(x), this.grid.yAxis.normalize(y)));
						c.add(this);
						this.occupied.add(c);
					} catch (Exception e) {
						// En dehors du tore (on s'en blc)
					}
				}
			}
		}
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
		this.occupied.clear(); //au cas ou
	}
	
	public void die() {
		System.out.println("entité despawn : " + name);
        this.alive = false;
        
        this.retract();
        model.remove(this);
        model.getView().remove(avatar);
        this.avatar=null;
        this.bot=null;
        this.stunt=null;
    }
	
	public abstract void tick(double elapsed,boolean shift);
	
	public void spawnPowerUpWithProba() {
		if (Main.powerUpSpawnCooldown == 0 && Main.spawnedPowerUp < Main.maxPowerUps && (Math.random() * 100) < Main.powerUpSpawnChance) {
            PowerUp bonus = new PowerUp("powerup");
            bonus.setCoord(this.center());
            bonus.setBounding();
            model.add(bonus);
            model.getView().add(bonus.getAvatar());
            Main.spawnedPowerUp++;
            Main.powerUpSpawnCooldown = Main.powerUpSpawnCooldownOrig;
        }		
	}
}
