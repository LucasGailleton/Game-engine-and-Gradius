package engine;

import java.util.List;

import engine.Entity;
import engine.Grid.Cell;
import engine.ISU.Vector;
import game.Model;
import game.entities.Avatar;

public abstract class Stunt {

	protected Model model;
	protected Entity entity;

	public int forbiddenDirection;
	public int direction;

	public Stunt(Model model, Entity entity) {
		this.model = model;
		this.entity = entity;
		forbiddenDirection = -1;
		direction = Avatar.West;
	}

	public void set(int orientation) {
		this.entity.turn(orientation - this.entity.orientation());
	}

	public void set(Cell c) {
		this.entity.setPosition(c.position);	
	}

	public void set(double x, double y) {
		this.entity.translate(this.entity.isu.new Vector(x, y));
	}
	
	public abstract void collision(Entity e);
	public abstract void collision(List<Entity> e);

	public abstract void walk(int i);

	public abstract void stop();

	public abstract void shootOnce();
}