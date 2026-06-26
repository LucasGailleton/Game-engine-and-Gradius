
package engine.GAL;

import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.Stunt;
import engine.ISU.Coord;
import game.Main;
import game.Model;
import game.entities.Damageable;
import game.entities.LaserShot;
import game.entities.MissileEnemy;
import game.entities.MissilePlayer;

// = Stunt =


import engine.Entity;

public class GALStunt  extends Stunt {

	// FIELDS

	//Entity entity;

	// CONSTRUCTOR

	 public GALStunt(Model m,Entity e){ 
		 super(m,e);
	 }

	// STEP

	 double step_cm;

	 public void setStepLength(double cm){ 
		this.step_cm=cm;
	}

	 public double stepLength(){ 
		 return this.step_cm;
	 }

	// SPEED

	 double max_cmPer_ms;

	 public void setMaxLinearSpeed(double cmPer_ms){ 
		this.max_cmPer_ms=cmPer_ms;
	}

	 double max_degPer_ms;

	 public void setMaxAngularSpeed(double degPer_ms){ 
		this.max_degPer_ms=degPer_ms;
	}

	// == DEFAULT IMPLEMENTATION of GAL Actions ==

	/**
	 * @apiNote the remaining time of the action in progress
	 */
	double action_ms;

	public double actionDuration(){ 
		 return this.action_ms;
	 }

	// TICK

	/**
	 * @apiNote The tick regularly provides the elapsed time
	 * @apiNote informs the Bot when action is completed
	 * @param elapsed_ms
	 * @implNote {@code action_ms} is updated according to the {@code elapsed_ms}
	 */
	void tick(double elapsed_ms){ 
		this.action_ms-=elapsed_ms;
		if(this.action_ms==0) {
			
		}
	}

	// MOVE

	/*public boolean startMoving(Direction direction, double intensity, double duration_ms){ 
		 new Move(direction,intensity);
		 return true;
		 
	 }*/

	// TURN

	public boolean startTurning(int angle_deg, double intensity){ 
		if(this.entity.orientation()>=angle_deg-5 && this.entity.orientation()<=angle_deg+5) {
			this.entity.setaSpeed(0);
		}
		else {
			this.entity.setaSpeed(intensity);
		}
		return true;
	 }

	public void collision(Entity e) {
		// TODO Auto-generated method stub
		
	}

	public void collision(List<Entity> e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void walk(int degree) {
		double rad = Math.toRadians(degree);
		this.set(degree);
		double vx = Math.cos(rad);
		double vy = Math.sin(rad);
		this.entity.setlSpeed(vx*max_cmPer_ms, vy*max_cmPer_ms);
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void shootOnce() {
		// TODO Auto-generated method stub
		
	}
}
