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
import game.entities.Borders;
import game.entities.MissilePlayer;
import game.entities.PacMan.PacManAvatar;
import game.entities.Ship;
import engine.Physic;
import engine.ShipStunt;

public class Model {
	
	private Grid grid;
	private List<Entity> entities;
	private Physic ps;
	private double seconds;
	private Profiler p;
	private View v;

	
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
	    
	    List<Entity> copyEntities = new ArrayList<>(this.entities);
	    
	    for (Entity entity : copyEntities) {

	        if (entity instanceof Ship)
	            ((Ship) entity).tick(seconds, shift);
	        
	        else if (entity instanceof MissilePlayer)
	            ((MissilePlayer) entity).tick(seconds, shift);
	        else if(entity.bot()!=null){
	        	entity.bot().tick(elapsed);
	        }else if(entity instanceof Mur)
	        	((Mur)entity).tick(elapsed, shift);
	        else if (entity instanceof Borders) {
	        	((Borders)entity).tick(elapsed, shift);
	        }
	        
	        if (entity.haslSpeed()) {
	            
	            
	            ISU.Vector speedVector = entity.lSpeed();
				
				List<Entity> boxCollisions = this.ps.getCollisionsForMovement(entity, seconds);
				double moveX = speedVector.targetX_cm * seconds;
				double moveY = speedVector.targetY_cm * seconds;
				ISU.Vector moveVec = entity.isu.new Vector(moveX, moveY);
				
				if (boxCollisions.isEmpty()) {
					entity.translate(moveVec);
				} 
				else {
					entity.translate(moveVec);
					
					List<Entity> trueCollisions = new ArrayList<>();
					
					for(Entity e : boxCollisions) {
						if(entity.bounding().intersects(e.bounding())) {
							trueCollisions.add(e);
						}
					}
					if(!trueCollisions.isEmpty()) {
						boolean isInvincible = (entity instanceof Ship) ? ((Ship)entity).getHit() : false;
					    if (!isInvincible) {
							entity.translate(entity.isu.new Vector(-moveX, -moveY));
							entity.setlSpeed(0, 0); 
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
							entity.collision(trueCollisions);
							
					    }
					}
				}
			}
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
