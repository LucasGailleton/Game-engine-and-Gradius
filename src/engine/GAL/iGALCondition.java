package engine.GAL;

import engine.Entity;

interface iGALCondition {

	boolean eval(Entity e);
	
	Variable variable();

}
