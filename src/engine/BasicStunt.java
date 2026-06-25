package engine;

import java.util.List;

import engine.Grid.Cell;
import game.Main;
import game.Model;
import game.entities.Avatar;
import game.entities.Damageable;
import game.entities.EnemyChicken;
import game.entities.LaserShot;
import game.entities.MissilePlayer;

public class BasicStunt extends Stunt {
	
	public static final double BasicSpeed = 5.0*Main.windowScale;

	public BasicStunt(Model model, Entity entity) {
		super(model, entity);
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
		
		if (e instanceof MissilePlayer || e instanceof LaserShot) {
            
			
            if (this.entity instanceof Damageable) {
            	System.out.println("collision détéctéTTTTTTTTTTTTTTTTTTTTT");
                ((Damageable) this.entity).gotHit();
            }
            e.die(); 
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

	@Override
	public void shootOnce() {
		// TODO Auto-generated method stub
		
	}
}
