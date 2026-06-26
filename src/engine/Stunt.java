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

	// distance null = infini
	protected Double moveDistanceLeft = null;

	// déplace l'entité sur une distance donnée
	public void move(double vitesse, double angle, Double distance) {
		double rad = Math.toRadians(angle);
		this.entity.setlSpeed(vitesse * Math.cos(rad), vitesse * Math.sin(rad));
		this.moveDistanceLeft = distance;
	}

	// avance à l'infini dans la direction donnée
	public void move(double vitesse, double angle) {
		this.move(vitesse, angle, null);
	}

	// retire la distance parcourue et stoppe l'entité quand move() est fini
	public void consumeMoveDistance(double parcouru_cm) {
		if (this.moveDistanceLeft == null)
			return;
		this.moveDistanceLeft -= parcouru_cm;
		if (this.moveDistanceLeft <= 0) {
			this.entity.setlSpeed(0, 0);
			this.moveDistanceLeft = null;
		}
	}

	public abstract void collision(Entity e);
	public abstract void collision(List<Entity> e);

	public abstract void walk(int i);

	public abstract void stop();

	public abstract void shootOnce();
}