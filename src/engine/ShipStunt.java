package engine;

import java.util.List;

import engine.Grid.Cell;
import engine.ISU.Vector;
import game.Main;
import game.Model;
import game.entities.Ship;
import game.entities.MissilePlayer;
import game.entities.PowerUp;

public class ShipStunt extends Stunt {

	private Ship ship;

	public ShipStunt(Model model, Ship ship) {
		super(model, ship);
		this.ship = ship;
	}
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
		// pour pas que le vaisseau perde de la  vie avec ses propres tirs
		if (e instanceof MissilePlayer || e instanceof PowerUp)
			return;
		System.out.printf("%s s'est crashé avec %s.\n", this.entity.name, e.name);
		if(entity instanceof Ship) {
			((Ship) entity).gotHit();
		}
	}

	@Override
	public void collision(List<Entity> e) {
		for (Entity entity : e) {
			if (entity instanceof MissilePlayer || entity instanceof PowerUp)
				continue;
			System.out.printf("%s s'est crashé avec %s.\n", this.entity.name, entity.name);
			if(this.entity instanceof Ship) {
				((Ship) this.entity).gotHit();
			}
		}
	}
	
	public void walk(int degree) {
		double rad = Math.toRadians(degree);
		this.set(degree);
		
		double vx = Math.round(Math.cos(rad));
		double vy = Math.round(Math.sin(rad));
		this.entity.setlSpeed(vx*Ship.ShipSpeed, vy*Ship.ShipSpeed);
	}

	public void stop() {
		this.entity.setlSpeed(0, 0);
	}
	
	public void shootOnce() {
		
		this.ship.tryToShoot();
		
		//System.out.println("shot once ! ");
	}
	

}
