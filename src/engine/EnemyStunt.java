package engine;

import java.util.List;

import engine.GAL.GALStunt;
import engine.ISU.Coord;
import game.Main;
import game.Model;
import game.entities.Damageable;
import game.entities.LaserShot;
import game.entities.MissileEnemy;
import game.entities.MissilePlayer;

public class EnemyStunt extends GALStunt {

	public EnemyStunt(Model m, Entity e) {
		super(m, e);
	}

	public static final double BasicSpeed = 20.0*Main.windowScale;
	

	@Override
	public void collision(Entity e) {
		
		if (e instanceof MissilePlayer || e instanceof LaserShot) {
            
			
            if (this.entity instanceof Damageable) {
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
	
	
	@Override
	public void stop() {
		this.entity.setlSpeed(0, 0);
	}
	


	public void shootOnce(Entity target) {
        engine.Grid.Position pos = this.entity.position();
        MissileEnemy msp = new MissileEnemy("missile simple",(this.entity).view(),1); 
        msp.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 20, pos.y * Game.cmPerCell));
        viser(msp,target.center(),BasicSpeed);
        Main.mb.placeEntity(msp);

	}
	
	public void shoot(int angle) {
        engine.Grid.Position pos = this.entity.position();
        MissileEnemy msp = new MissileEnemy("missile simple",(this.entity).view(),1); 
        msp.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 20, pos.y * Game.cmPerCell));
        viserAngle(msp,angle,BasicSpeed);
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
	
	// Nouvelle méthode d'aide pour viser selon un angle donné
	public void viserAngle(Entity projectile, double angleEnDegres, double vitesse) {
	    // Conversion en radians (ex: 0° = Droite, 90° = Bas ou Haut selon ton repère)
	    double radians = Math.toRadians(angleEnDegres);
	    
	    // Calcul des composantes X et Y de la vitesse
	    double vx = vitesse * Math.cos(radians);
	    double vy = vitesse * Math.sin(radians); 
	    
	    projectile.setlSpeed(vx, vy);
	}
	
	@Override
	public void shootOnce() {
		
		
	}
}
