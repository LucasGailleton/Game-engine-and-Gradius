package game;

import java.util.ArrayList;
import java.util.List;

import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.Grid;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.ISU.Vector;
import engine.shapes.Rect;
import engine.Physic;
import game.PacMan.PacManAvatar;

public class Model {
	// FIELDS
	private Grid grid;
	private List<Entity> entities;
	private Physic ps;
	private double seconds;

	// CONSTRUCTOR
	public Model() {
		this.grid = Game.game().grid;
		this.entities = new ArrayList<Entity>();
	}
	
	// ADD, REMOVE Entity
	public void add(Entity e) {
		this.entities.add(e);
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
		if (elapsed > 100) { 
	        elapsed = 10;
	    }
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
	}
	
//	public void tick(long elapsed) {
//	    if (elapsed > 100) 
//	        elapsed = 10;
//	    this.seconds = elapsed / 1000.0;
//	    Physique ps = new Physique(this);
//	    
//	    List<Entity> copyEntities = new ArrayList<>(this.entities);
//	    
//	    for (Entity entity : copyEntities) {
//	        // 1. Rotation (inchangé)
//	        double angularSpeed = entity.aSpeed();
//	        if (angularSpeed != 0) {
//	            entity.turn((int) (angularSpeed * seconds));
//	            List<Entity> collisions = this.getCollision(entity);
//	            if (!collisions.isEmpty()) {
//	                entity.turn((int) -(angularSpeed * seconds));
//	                entity.setaSpeed(0);
//	                entity.collision(collisions);
//	            }
//	        }
//	        
//	        // Si l'entité ne bouge pas, on passe à la suivante
//	        if (!entity.haslSpeed() || (entity.lSpeed().targetX_cm == 0 && entity.lSpeed().targetY_cm == 0)) {
//	            continue;
//	        }
//
//	        // 2. PHASE DE DÉTECTION (On cherche le premier obstacle)
//	        double minTime = 1.0; 
//	        List<Entity> collidedEntities = new ArrayList<>();
//
//	        for (Entity other : entities) {
//	            if (entity == other) continue;
//	            
//	            Rect r_other = ps.superBoundingBox(other, seconds);
//	            Rect r_e = ps.superBoundingBox(entity, seconds);
//	            
//	            if (r_e.intersects(r_other)) {
//	                // On récupère le temps exact grâce au Swept AABB !
//	            	double timeToCrash = ps.getCollisionEntryTime(entity, other, seconds);
//	                
//	            	// AJOUTE LE >= 0.0 ICI PUTAIN !
//	            	if (timeToCrash >= 0.0 && timeToCrash < 1.0) {
//	            	    if (timeToCrash < minTime) {
//	            	        minTime = timeToCrash;
//	            	        collidedEntities.clear();
//	            	        collidedEntities.add(other);
//	            	    } else if (timeToCrash == minTime) {
//	            	        collidedEntities.add(other);
//	            	    }
//	            	}
//	            }
//	        }
//
//	        // 3. PHASE DE DÉPLACEMENT (En dehors de la boucle, UNE SEULE FOIS)
//	        ISU.Coord dest = ps.dest(entity, seconds);
//	        
//	        // On multiplie la distance par le temps d'impact (1.0 = aucun obstacle, 0.4 = tape à 40% du chemin)
//	        double moveX = dest.x_cm * minTime;
//	        double moveY = dest.y_cm * minTime;
//	        
//	        entity.translate(entity.isu.new Vector(moveX, moveY));
//
//	        // 4. RÉSOLUTION DE LA COLLISION
//	        if (minTime < 1.0) {
//	            System.out.println("YA COLLISION PUTAIN !");
//	            entity.setlSpeed(0, 0); // On stoppe l'entité contre le mur
//	            entity.collision(collidedEntities); // On déclenche l'événement
//	        }
//	    }					
//	}
	
	public List<Entity> getCollision(Entity e){
		List<Entity> collisions = new ArrayList<Entity>();
		for (Entity entity : this.entities) {
			if (entity != e && entity.intersects(e))
				collisions.add(entity);
		}
		return collisions;
	}
}
