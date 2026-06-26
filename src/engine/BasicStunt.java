package engine;

import java.util.List;

import engine.Grid.Cell;
import engine.ISU.Coord;
import game.Main;
import game.Model;
import game.entities.Avatar;
import game.entities.Damageable;
import game.entities.EnemyChicken;
import game.entities.LaserShot;
import game.entities.MissilePlayer;
import game.entities.PowerUp;

public class BasicStunt extends Stunt {
	
	public static final double BasicSpeed = 5.0*Main.windowScale;

	public BasicStunt(Model model, Entity entity) {
		super(model, entity);
	}

	@Override
	public void collision(Entity e) {
		
		if (e instanceof MissilePlayer || e instanceof LaserShot) {
            
			
            if (this.entity instanceof Damageable) {
            	System.out.println("collision détéctéTTTTTTTTTTTTTTTTTTTTT");
                ((Damageable) this.entity).gotHit();
            }
            if (!(e instanceof LaserShot)) {
                e.die(); 
            }
        }
	}

	@Override
	public void collision(List<Entity> e) {
		for (Entity entities : e) {
            this.collision(entities); 
        }
	}
	
	public void walk(int degree) {
		double rad = Math.toRadians(degree);
		this.set(degree);
		double vx = Math.cos(rad);
		double vy = Math.sin(rad);
		this.entity.setlSpeed(vx, vy);
	}

	public void stop() {
		this.entity.setlSpeed(0, 0);
	}
	
	public void shoot(int degree) {
		
		
	}
	
//	public void spawnPowerUp(Coord coord) {
//		PowerUp power = new PowerUp("powerUp");
//		power.setCoord(coord);
//	}
//	
//	public void placeEntity(Entity e) {
//		e.setBounding();
//		model.add(e);
//		model.getView().add(e.getAvatar());
//	}

	@Override
	public void shootOnce() {
		// TODO Auto-generated method stub
		
	}

}
