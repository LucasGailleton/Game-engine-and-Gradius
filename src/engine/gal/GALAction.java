package engine.gal;

import engine.Entity;

public abstract class GALAction  implements iGALAction{

	/**
	 * @param intensity &in; [0,1] ≃ %
	 */
	double intensity;
	public GALAction(){
		
	}
	// CONSTANT

	 public static Nothing NOTHING = new Nothing();

	 @Override
	 public abstract boolean exec(Entity e);
}
