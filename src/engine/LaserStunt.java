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
		if(entity instanceof Ship ship) {
			ship.gotHit();
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

	        ISU.Coord center = this.ship.center();

	        LaserShot laser = new LaserShot(this.ship.view(), "laser");

	        laser.setCoord(Game.game().isu.new Coord(center.x_cm + 50, center.y_cm)); //AJouster ici si collision Ship
	        laser.setlSpeed(Ship.missileSpeed * 2, 0);
	        Main.mb.placeEntity(laser);
	        
	        if (Ship.double_tir) {
	            MissilePlayer msp2 = new MissilePlayer(this.ship.view(), "missile double");
	            msp2.setCoord(Game.game().isu.new Coord(center.x_cm + 30, center.y_cm+30)); //Ajuster ici aussi si besoin
	            msp2.setlSpeed(Ship.missileSpeed, Ship.missileSpeed);
	            Main.mb.placeEntity(msp2);
	        }

	        this.ship.triggerSlaves();
	        this.ship.playSound();
	        this.ship.resetCooldown();
	    }
	}

	@Override
	public void set(int orientation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(Cell c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(double x, double y) {
		// TODO Auto-generated method stub
		
	}
}
