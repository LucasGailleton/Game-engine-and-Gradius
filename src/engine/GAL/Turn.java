
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
	 boolean exec(Entity e){ throw new UnsupportedOperationException("UNIMPLEMENTED METHOD `exec`"); }

}
