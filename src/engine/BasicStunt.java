package engine;

import java.util.List;

import engine.Grid.Cell;
import game.Avatar;
import game.Model;
import game.PacMan;

public class BasicStunt extends Stunt {
	
	public int forbiddenDirection;
	public int direction;

	public BasicStunt(Model model, Entity entity) {
		super(model, entity);
		forbiddenDirection = -1;
		direction = Avatar.West;
	}

//	public
//	
//	public void setForbiddenDirection(int direction) {
//		this.forbiddenDirection = direction;
//	}
//	
//	public void setDirection(int direction) {
//		this.direction = direction;
//	}
	
	@Override
	public void set(int orientation) {
		this.entity.turn(orientation - this.entity.orientation());
	}

	@Override
	public void set(Cell c) {
		this.entity.setPosition(c.position);	
	}

	@Override
	public void set(double x, double y) {
		this.entity.translate(this.entity.isu.new Vector(x, y));
	}

	@Override
	public void collision(Entity e) {
		System.out.printf("%s s'est crashé avec %s.\n", this.entity.name, e.name);
	}

	@Override
	public void collision(List<Entity> e) {
		for (Entity entity : e) {
			System.out.printf("%s s'est crashé avec %s.\n", this.entity.name, entity.name);
		}
	}
	
	public void walk(int degree) {;
		double rad = Math.toRadians(degree);
		this.set(degree);
		double vx = Math.round(Math.cos(rad));
		double vy = Math.round(Math.sin(rad)); 
		this.entity.setlSpeed(vx*PacMan.PacManSpeed, vy*PacMan.PacManSpeed);
	}
	
}
