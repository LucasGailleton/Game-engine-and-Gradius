
package engine.GAL;

import engine.BasicStunt;
import engine.Entity;
import engine.ShipStunt;
import engine.Stunt;

public class Move  extends GALAction{


	Direction direction;
	int angle=-1;
	double duration_ms;
	double length;
	double basicspeed=100;
	Variable var;
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
	public	 Move(Direction dir, double intensity, int n){ 
		super();
		this.direction=dir;
		this.intensity=intensity;
		this.length=n;
	 }

	public Move(Direction dir,Variable e,double intensity, int n){ 
		super();
		this.direction=dir;
		this.intensity=intensity;
		this.length=n;
		this.var=e;
	 }
	
	public Move(Direction dir, double intensity){ 
		 super();
		 this.direction=dir;
		 this.intensity=intensity;
	 }

	public Move(Direction dir,double intensity,Variable e ){ 
		 super();
		 this.direction=dir;
		 this.intensity=intensity;
		 this.var=e;
	 }
	 
	public	 Move(Direction dir, int n){
		 super();
		 this.direction=dir;
		 this.length=n;
	 }
	 
	public Move(Direction dir, Variable e,int n){
		 super();
		 this.direction=dir;
		 this.length=n;
		 this.var=e;
	 }
	 
	public	 Move(Direction dir){
		 super();
		 this.direction=dir;
	 }
	
	public	 Move(Direction dir,Variable e){
		 super();
		 this.direction=dir;
		 this.var=e;
	 }

	public	 Move(int dir){
		 super();
		 this.angle=dir;
	 }
	 
	public Move(int dir, double intensity, int n){ 
		super();
		this.angle=dir;
		this.intensity=intensity;
		this.length=n;
	 }

	 public	 Move(int dir, double intensity){ 
		 super();
		 this.angle=dir;
		 this.intensity=intensity;
	 }

	 public	 Move(int dir, int n){
		 super();
		 this.angle=dir;
		 this.length=n;
	 }
	 
	 public	 Move(double intensity, int n){
		 super();
		 this.intensity=intensity;
		 this.length=n;
	 }
	 
	// EXEC
	 public boolean exec(Entity e){ 
		 GALStunt s=(GALStunt)e.stunt();
		 s.setMaxLinearSpeed(intensity);
		 s.setStepLength(length);
		 if(this.direction!=null) {
			 if(this.direction.isRelative()) {
				 if(this.var!=null && this.var.content instanceof Entity ent) {
					 if(this.intensity!=0 && this.length!=0) {
						 s.move(intensity, (Direction.inverse(Direction.to_abs(direction, ent))).toAngle(), this.length*s.step_cm);
						 return true;
					 }
					 else if(this.intensity!=0) {
						 s.move(intensity, (Direction.inverse(Direction.to_abs(direction, ent))).toAngle());
						 return true;
					 }
					 else if(this.length!=0) {
						 s.move(basicspeed, (Direction.inverse(Direction.to_abs(direction, ent))).toAngle(), this.length*s.step_cm);						 return true;
					 }
					 else {
						 s.move(basicspeed, (Direction.inverse(Direction.to_abs(direction, ent))).toAngle());
						 return true;
					}
				 }
				 else {
					 if(this.intensity!=0 && this.length!=0) {
						 s.move(intensity, Direction.to_abs(direction, e).toAngle(), this.length*s.step_cm);
						 return true;
					 }
					 else if(this.intensity!=0) {
						 s.move(intensity, Direction.to_abs(direction, e).toAngle());
						 return true;
					 }
					 else if(this.length!=0) {
						 s.move(basicspeed, Direction.to_abs(direction, e).toAngle(), this.length*s.step_cm);						 return true;
					 }
					 else {
						 s.move(basicspeed, Direction.to_abs(direction, e).toAngle());
						 return true;
					}
				 }
			 }
			 else if(this.direction.isAbsolute()){
				 if(this.intensity!=0 && this.length!=0) {
					 s.move(intensity, this.direction.toAngle(), this.length*s.step_cm);
					 return true;
				 }
				 else if(this.intensity!=0) {
					 s.move(intensity, this.direction.toAngle());
					 return true;
				 }
				 else if(this.length!=0) {
					 s.move(basicspeed, this.direction.toAngle(), this.length*s.step_cm);
					 return true;
				 }
				 else {
					 s.move(basicspeed, this.direction.toAngle());
					 return true;
				 }
				 
			 }
		 }
		 if(this.angle!=-1) {
			 if(this.intensity!=0 && this.length!=0) {
				 s.move(intensity, angle, this.length*s.step_cm);
				 return true;
			 }
			 else if(this.intensity!=0) {
				 s.move(intensity, angle);
				 return true;
			 }
			 else if(this.length!=0) {
				 s.move(basicspeed, angle, this.length*s.step_cm);
				 return true;
			 }
			 else {
				 s.move(basicspeed, angle);
				 return true;
			 }
		 }
		 else {
			 if(this.intensity!=0 && this.length!=0) {
				 s.move(intensity, e.orientation(), this.length*s.step_cm);
				 return true;
			 }
		 }

		 return false;
	 }



}
