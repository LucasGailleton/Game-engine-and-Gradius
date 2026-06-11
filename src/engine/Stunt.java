package engine;

import java.util.List;

import engine.Entity;
import engine.Grid.Cell;
import game.Model;

public abstract class Stunt {

	protected Model model;
	protected Entity entity;

	public Stunt(Model model, Entity entity) {
		this.model = model;
		this.entity = entity;
	}
	
	public abstract void set(int orientation);
	public abstract void set(Cell c);
	public abstract void set(double x,double y);
	public abstract void collision(Entity e);
	public abstract void collision(List<Entity> e);
}