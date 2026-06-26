
package engine.GAL;

import engine.BasicStunt;
import engine.Entity;
import engine.ShipStunt;
import engine.Stunt;

class Move  extends GALAction{


	Direction direction;
	int angle;
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
		super();
		this.direction=dir;
		this.intensity=intensity;
		this.length_cm=n;
	 }

	 Move(Direction dir, double intensity){ 
		 super();
		 this.direction=dir;
		 this.intensity=intensity;
	 }

	 Move(Direction dir, int n){
		 super();
		 this.direction=dir;
	 }
	 
	 Move(Direction dir){
		 super();
		 this.direction=dir;
	 }

	 Move(int dir){
		 super();
		 this.angle=dir;
	 }
	 Move(){ 
		 
	 }

	// EXEC
	 public boolean exec(Entity e){ 
		 GALStunt s=(GALStunt)e.stunt();
		 s.setMaxLinearSpeed(intensity);
		 s.setStepLength(length_cm);
		 s.walk(0);
		 return true;
	 }



}
