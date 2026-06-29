package engine;

import java.util.List;

import game.Main;
import game.Model;
import game.entities.Damageable;
import game.entities.LaserShot;
import game.entities.MissileEnemy;
import game.entities.MissilePlayer;

public class BasicStunt extends Stunt {
	
	public static final double BasicSpeed = 5.0*Main.windowScale;

	public BasicStunt(Model model, Entity entity) {
		super(model, entity);
	}

	@Override
	public void collision(Entity e) {
		if (e instanceof MissilePlayer || e instanceof LaserShot) {
            if (this.entity instanceof Damageable) {
            	System.out.println("collision détéctée");
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
		this.entity.setlSpeed(vx*30, vy*30);
	}

	public void stop() {
		this.entity.setlSpeed(0, 0);
	}

	public void shootOnce(Entity target) {
        engine.Grid.Position pos = this.entity.position();
        MissileEnemy msp = new MissileEnemy("missile simple",(this.entity).view(),1); 
        msp.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 20, pos.y * Game.cmPerCell));
        viser(msp,target.center(),1000);
        Main.mb.placeEntity(msp);
	}
	
	public void viser(Entity projectile, ISU.Coord cible, double vitesse) {
	    ISU.Coord from = projectile.center();
	    double dx = cible.x_cm - from.x_cm;
	    double dy = cible.y_cm - from.y_cm;
	    double dist = from.distanceTo(cible);
	    if (dist == 0) dist = 1;
	    projectile.setlSpeed(vitesse * dx / dist, vitesse * dy / dist);
	}
	
	@Override
	public void shootOnce() {}
}
