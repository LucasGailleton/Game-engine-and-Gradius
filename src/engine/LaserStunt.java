package engine;

import java.util.List;

import engine.Grid.Cell;
import engine.ISU.Coord;
import engine.ISU.Vector;
import game.Main;
import game.Model;
import game.entities.LaserShot;
import game.entities.MissilePlayer;
import game.entities.PowerUp;
import game.entities.Ship;

public class LaserStunt extends Stunt {
	private Ship ship;

	public LaserStunt(Model model, Ship ship) {
		super(model, ship);
		this.ship = ship;
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
			collision(entity);
		}
	}
	
	public void walk(int degree) {
		double rad = Math.toRadians(degree);
		this.set(degree);
		
		double vx = Math.cos(rad);
		double vy = Math.sin(rad);
		this.entity.setlSpeed(vx*Ship.ShipSpeed, vy*Ship.ShipSpeed);
	}

	public void stop() {
		this.entity.setlSpeed(0, 0);
	}
	
	@Override
	public void shootOnce() {
		
		if (this.ship.canShoot()) {
			engine.Grid.Position pos = this.ship.position();

			LaserShot laser = new LaserShot(this.ship.getView(), "laser");
			laser.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 50, pos.y * Game.cmPerCell));
			laser.setlSpeed(Ship.missileSpeed * 2, 0);
			Main.mb.placeEntity(laser);
			
			if (Ship.double_tir) {
				MissilePlayer msp2 = new MissilePlayer(this.ship.getView(), "missile double");
				msp2.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 20, pos.y * Game.cmPerCell));
				msp2.setlSpeed(Ship.missileSpeed, Ship.missileSpeed);
				Main.mb.placeEntity(msp2);
			}

			this.ship.triggerSlaves();
			this.ship.playSound();
			this.ship.resetCooldown();
		}
	}
}
