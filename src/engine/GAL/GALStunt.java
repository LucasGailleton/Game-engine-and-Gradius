
package engine.GAL;

import engine.Entity;

// = Stunt =


import engine.Entity;

class GALStunt  {

	// FIELDS

	Entity entity;

	// CONSTRUCTOR

	 GALStunt(Entity e){ 
		 this.entity=e;
	 }

	// STEP

	 double step_cm;

	void setStepLength(double cm){ 
		this.step_cm=cm;
	}

	 double stepLength(){ 
		 return this.step_cm;
	 }

	// SPEED

	 double max_cmPer_ms;

	void setMaxLinearSpeed(double cmPer_ms){ 
		this.max_cmPer_ms=cmPer_ms;
	}

	 double max_degPer_ms;

	void setMaxAngularSpeed(double degPer_ms){ 
		this.max_degPer_ms=degPer_ms;
	}

	// == DEFAULT IMPLEMENTATION of GAL Actions ==

	/**
	 * @apiNote the remaining time of the action in progress
	 */
	double action_ms;

	 double actionDuration(){ 
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

	 boolean startMoving(Direction direction, double intensity, double duration_ms){ 
		 new Move(direction,intensity);
		 return false;
		 
	 }

	// TURN

	 boolean startTurning(int angle_deg, double intensity){ 
		 new Turn(angle_deg,intensity);
		 return false;
	 }

}
