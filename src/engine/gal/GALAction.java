package engine.gal;

import engine.Entity;

abstract class GALAction  implements iGALAction{

	/**
	 * @param intensity &in; [0,1] ≃ %
	 */
	double intensity;

	// CONSTANT

	 public static Nothing NOTHING = new Nothing();

	 @Override
	 public abstract boolean exec(Entity e);
}
