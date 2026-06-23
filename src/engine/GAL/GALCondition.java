package engine.GAL;

import engine.Entity;

public abstract class GALCondition  implements iGALCondition{


	Direction direction;
	Category category;

	public GALCondition(Direction d,Category c){
		this.direction=d;
		this.category=c;
	}
	public abstract boolean eval(Entity e);
	// CONSTANT

	 public static final True TRUE = new True();

}
