package engine.GAL;

import engine.BasicStunt;
import engine.Entity;
import engine.Stunt;

class Shoot  extends GALAction{


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
	 Shoot(Direction dir, double intensity, int n){ 
		super();
		this.direction=dir;
		this.intensity=intensity;
		this.length_cm=n;
	 }

	 Shoot(Direction dir, double intensity){ 
		 super();
		 this.direction=dir;
		 this.intensity=intensity;
	 }

	 Shoot(Direction dir, int n){
		 super();
		 this.direction=dir;
	 }
	 
	 Shoot(Direction dir){
		 super();
		 this.direction=dir;
	 }

	 Shoot(){ 
		 
	 }

	// EXEC
	 public boolean exec(Entity e){ 
		 BasicStunt s=(BasicStunt)e.stunt();
		 s.shoot(0);
		 System.out.println("shoot");
		 return true;
	 }



}