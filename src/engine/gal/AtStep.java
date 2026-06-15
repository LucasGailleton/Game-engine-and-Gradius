
package engine.gal;

import engine.Entity;

class AtStep  extends GALCondition{

	int nbStep;

	// CONSTRUCTOR

	 AtStep(Direction dir, Category cat, int nbStep){ 
		 this.direction=dir;
		 this.category=cat;
		 this.nbStep=nbStep;
	 }

	// EVAL

	/**
	 * @apiNote check if the condition AtStep(...) is satisfied by the given entity
	 * @param e = the entity that does the evaluation
	 * @implNote AtStep(...) conditions are intensively used and must be efficient:
	 *           efficiency is perhaps more important than accuracy.
	 * @implNote There is plenty room for optimization here in collaboration with
	 *           the Model and the Bot.
	 */

	 boolean eval(Entity e){ 
		 
	 }

}
