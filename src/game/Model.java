package game;

import java.util.ArrayList;
import java.util.List;

import DebugMode.Profiler;
import DebugMode.ProfilingManager;
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
import game.entities.PacMan.PacManAvatar;
import engine.Physic;

public class Model {
	// FIELDS
	private Grid grid;
	private List<Entity> entities;
	private Physic ps;
	private double seconds;
	private Profiler p;
	private View v;

	// CONSTRUCTOR
	public Model(View v) {
		if (ProfilingManager.PROFILING)
			this.p = ProfilingManager.getProfiler("model");
		this.grid = Game.game().grid;
		this.entities = new ArrayList<Entity>();
		this.v = v;
	}
	
	// ADD, REMOVE Entity
	public void add(Entity e) {
		this.entities.add(e);
		e.setModel(this);
	}

	public void remove(Entity e) {
		this.entities.remove(e);
	}
	
	// GETTER
	public List<Entity> entities(){
		return this.entities;
	}
	
	public Grid grid() {
		return this.grid;
	}
	
	public void tick(long elapsed) {
		if (Main.debug)
			p.start();
		if (elapsed > 100) 
	        elapsed = 10;

		v.shift();
		
		this.seconds = elapsed / 1000.0;
	    this.ps = new Physic(this);
		List<Entity> copyEntities = new ArrayList<>(this.entities);
		
		for (Entity entity : copyEntities) {
			ISU.Vector speedVector = entity.lSpeed();
			double angularSpeed = entity.aSpeed();
			
			if (angularSpeed != 0) {
				entity.turn((int) (angularSpeed * seconds));
				List<Entity> collisions = this.getCollision(entity);
				if (!collisions.isEmpty()) {
					entity.turn((int) -(angularSpeed * seconds));
					entity.setaSpeed(0);
					entity.collision(collisions);
				}
			}
			
			if (entity.haslSpeed()) {
				List<Entity> trueCollisions = this.ps.getCollisionsForMovement(entity, seconds);
				
				if (trueCollisions.isEmpty()) {
					double moveX = speedVector.targetX_cm * seconds;
					double moveY = speedVector.targetY_cm * seconds;
					entity.translate(entity.isu.new Vector(moveX, moveY));
				} else {
					entity.setlSpeed(0, 0); 
					if (entity.stunt() instanceof BasicStunt) {
						BasicStunt bs = ((BasicStunt) entity.stunt());
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
