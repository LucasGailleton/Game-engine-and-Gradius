package engine.gal;

import engine.Entity;

class Move  extends GALAction{

	Direction direction;
	double duration_ms;
	double length_cm;

	// 4 CONSTRUCTORS

	/**
	 * @apiNote asks for moving
	 *          <UL>
	 *          <LI>at celerity = intensity * maximal celerity
	 *          <LI>during duration (in ms) = n * simulationStep_ms
	 *          <LI>in the given direction
	 *          </UL>
	 * @param dir
	 * @param intensity : &in; [0,1] ≃ %
	 * @param n         : number of ticks? or number of steps? Choose your
	 *                  interpretation.
	 */
	 Move(Direction dir, double intensity, int n){ 
		this.direction=dir;
		this.intensity=intensity;
		this.length_cm=n;
	 }

	 Move(Direction dir, double intensity){ 
		 this.direction=dir;
		 this.intensity=intensity;
	 }

	 Move(Direction dir, int n){ 
		 this.direction=dir;
		 this.;
	 }

	 Move(){ 
		 
	 }

	// EXEC
	 boolean exec(Entity e){ 
		 
	 }

	 @Override
	 public boolean exec(Entity e) {
		// TODO Auto-generated method stub
		return false;
	 }


}
