package engine.GAL;

import engine.BasicStunt;
import engine.EnemyStunt;
import engine.Entity;
import engine.Game;
import engine.Stunt;
public class Shoot  extends GALAction{


	Direction direction;
	int angle;
	Variable target;
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
	 
	public Shoot(Direction dir){
		 super();
		 this.direction=dir;
	 }
	
	public Shoot(int dir){
		 super();
		 this.angle=dir;
	 }
	
	public Shoot(Variable e){
		 super();
		 this.target=e;
	 }
	
	public Shoot(Entity e){
		 super();
		 this.target.content=e;
	 }

	public Shoot(){ 
		 
	 }

	// EXEC
	 public boolean exec(Entity e){ 
		 EnemyStunt s=(EnemyStunt)e.stunt();
		 if(this.target!=null) {
			 if(this.target.content!=null) {
				 if(target.content instanceof Entity ent) {
					s.shootOnce(ent);
					return true;
				 }
			 }
		 }
		 else if(this.direction!=null) {
			 if(this.direction.isAbsolute()) {
				 s.shoot(this.direction.toAngle());
			 }
			 else {
				 s.shoot((this.direction.to_abs(direction, e)).toAngle());
			 }
			 return true;
		 }
		 else if(this.angle!=0) {
			 s.shoot(this.angle);
			 return true;
		 }
		 else {
			 s.shoot(e.orientation());
			 return true;
		 }
		 return false;
	 }



}