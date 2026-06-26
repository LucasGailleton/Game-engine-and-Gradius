package game;

import java.util.ArrayList;
import java.util.List;

import debugmode.Profiler;
import debugmode.ProfilingManager;
import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.Grid;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.ISU.Vector;
import engine.shapes.Rect;
import engine.view.View;
import game.entities.Avatar;
import game.entities.Background;
import game.entities.Enemy;
import game.entities.LaserShot;
import game.entities.MissileEnemy;
import game.entities.MissilePlayer;
import game.entities.MissileSpecial;
import game.entities.MontFendu;
import game.entities.PowerUp;
import game.entities.Ship;
import game.entities.Slave;
import engine.Physic;
import engine.ShipStunt;

public class Model {
	
	private Grid grid;
	private List<Entity> entities;
	private Physic ps;
	private double seconds;
	private Profiler p;
	private View v;
	private Ship player;

	
	public Model(View v) {
		if (ProfilingManager.PROFILING)
			this.p = ProfilingManager.getProfiler("model");
		this.grid = Game.game().grid;
		this.entities = new ArrayList<Entity>();
		this.v = v;
		this.ps = new Physic(this);
	}
	
	// ADD, REMOVE Entity
	public void add(Entity e) {
		this.entities.add(e);
		e.setModel(this);
	}

	public void remove(Entity e) {
		this.entities.remove(e);
	}
	
	public void setPlayer(Ship ship) {
	    this.player = ship;
	}
	
	public Ship getPlayer() {
	    return this.player;
	}
	
	public View getView() { // Méthode temporaire, structurellement pas ouf
		return this.v;
	}
	
	// GETTER
	public List<Entity> entities(){
		return this.entities;
	}
	
	public Grid grid() {
		return this.grid;
	}

	public void tick(long elapsed) {
	    if (Main.debug) p.start();
	    if (elapsed > 100) elapsed = 10;

	    boolean shift = v.shift();
	    this.seconds = elapsed / 1000.0;
	    
	    Main.mb.genAleatoire(seconds);
	    if (Main.Timer > 0)
	    	Main.Timer--;
	    
	    List<Entity> copyEntities = new ArrayList<>(this.entities);
	    
	    for (Entity entity : copyEntities) {
	        
	        boolean isShip = entity instanceof Ship;
	        boolean isLaser = entity instanceof LaserShot;
	        boolean isMissile = entity instanceof MissilePlayer;
	        boolean isEnemyMissile = entity instanceof MissileEnemy;
	        boolean isMissileSpecial = entity instanceof MissileSpecial;
	        entity.trueCollisions = new ArrayList<Entity>();
	         
	        if(entity.bot()!=null) {
	        	entity.bot().tick();
	        }
	        if (entity.haslSpeed()) {
	            
	            ISU.Vector speedVector = entity.lSpeed();
	            double moveX = speedVector.targetX_cm * seconds;
	            double moveY = speedVector.targetY_cm * seconds;
	            
	            if (isMissile || isLaser || isEnemyMissile || isMissileSpecial) {

	                int steps = 10; 
	                double stepX = moveX / steps;
	                double stepY = moveY / steps;
	                ISU.Vector stepVec = entity.isu.new Vector(stepX, stepY);
	                
	                for (int i = 0; i < steps; i++) {
	                    entity.translate(stepVec);

	                    for (Entity e : copyEntities) {
	                        if (e == entity) continue;
	                        if ((isMissile || isLaser) && (e instanceof MissilePlayer || e instanceof Ship || e instanceof Slave || e instanceof LaserShot || e instanceof PowerUp || e instanceof MontFendu)) {
	                            continue;
	                        }
	                        if ((isEnemyMissile || isMissileSpecial) && (e instanceof Enemy || e instanceof MissileEnemy || e instanceof PowerUp || e instanceof MontFendu)) {
	                            continue;
	                        }

	                        if (entity.bounding().intersects(e.bounding())) {
	                            if (!entity.trueCollisions.contains(e)) {
	                                entity.trueCollisions.add(e);
	                            }
	                        }
	                    }
	                    if (!entity.trueCollisions.isEmpty() && !isLaser) {
	                        break; 
	                    }
	                }
	            } else {
	                List<Entity> boxCollisions = this.ps.getCollisionsForMovement(entity, seconds);
	                ISU.Vector moveVec = entity.isu.new Vector(moveX, moveY);
	                entity.translate(moveVec);
	                
	                for (Entity e : boxCollisions) {
	                    if (e == entity || e instanceof MissilePlayer || e instanceof LaserShot) continue;
	                    if (entity.bounding().intersects(e.bounding())) {
	                        entity.trueCollisions.add(e);
	                    }
	                }
	            }
	            if(!entity.trueCollisions.isEmpty() && !(entity.trueCollisions.size() == 1 && entity.trueCollisions.get(0) instanceof PowerUp)) {
	                boolean isInvincible = false;
	                
	                if(isShip)
	                    isInvincible = ((Ship)entity).getHit();
	                
	                if (!isInvincible) {
	                    if(!isLaser) {
	                        entity.translate(entity.isu.new Vector(-moveX, -moveY));
	                        entity.setlSpeed(0, 0); 
	                    }
	                    
	                    if (entity.stunt() instanceof ShipStunt) {
	                        ShipStunt bs = ((ShipStunt) entity.stunt());
	                        bs.forbiddenDirection = bs.direction;
	                        switch (bs.direction) {
	                        case Avatar.West: bs.set(0.05*Main.windowScale, 0); break;
	                        case Avatar.North: bs.set(0, 0.05*Main.windowScale); break;
	                        case Avatar.East: bs.set(-0.05*Main.windowScale, 0); break;
	                        default: bs.set(0, -0.05*Main.windowScale); break;
	                        }
	                    }
	                    entity.collision(entity.trueCollisions);
	                }
	            }
	        }
	        
	        entity.tick(elapsed, shift);
	       
		}
		if (Main.debug)
			p.end();
	}
	
	public List<Entity> getCollision(Entity e){
		List<Entity> collisions = new ArrayList<Entity>();
		for (Entity entity : this.entities) {
			if (entity != e && entity.intersects(e))
				collisions.add(entity);
		}
		return collisions;
	}
}
