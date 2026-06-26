
package engine.GAL;


import engine.Entity;

public class Turn  extends GALAction{

	int angle_deg;
	Direction dir;
	// 3 CONSTRUCTORS

	/**
	 * @param angle_deg &in; [-360,360]
	 * @param intensity &in; [0,1]
	 */
	 Turn(Direction dir){ 
		 this.dir=dir;
	 }

	 Turn(int angle_deg){ 
		 this.angle_deg=angle_deg;
	 }

	// EXEC
	 public boolean exec(Entity e){ 
		 GALStunt gs=(GALStunt)e.stunt();
		 if(this.dir!=null) {
			 if(this.dir.isRelative()){
				 if(this.dir==Direction.R) {
					 gs.startTurning(e.orientation()+90, angle_deg);
				 }
				 else if(this.dir==Direction.L) {
					 gs.startTurning(e.orientation()+270, angle_deg);
				 }
				 else if(this.dir==Direction.B) {
					 gs.startTurning(e.orientation()+180, angle_deg);
				 }
				 return true;
			 }
			 else {
				 gs.startTurning(this.dir.toAngle(), angle_deg);
				 return true;
			 }
		 }
		 else {
			 gs.startTurning(angle_deg, angle_deg);
			 return true;
		 }
	 }

}
