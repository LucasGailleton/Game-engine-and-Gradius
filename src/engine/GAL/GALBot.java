package engine.GAL;

import engine.Entity;

public class GALBot  extends Bot{
	int speed=5;
	
	// CONSTRUCTOR

	 public GALBot(Entity e){ 
		 super(e);
		 e.setBot(this);
	 }

	// ENTITY SELECTED BY CONDITON

	 Entity selectedEndity;

	 public void selectedEntity(Entity e){ 
		 this.selectedEndity=e;
	 }

	 public Entity selected(){ 
		return this.selectedEndity;
	}

	// AUTOMATON

	 Automaton automaton;

	/**
	 * @apiNote change the automaton of the Bot
	 * @impNote Que devient l'état (State) du Bot ?
	 * @param automaton
	 */
	 public void set(Automaton automaton){
		 this.automaton=automaton;
		 this.state=automaton.initial;
	 }

	// TICK & COLLISION & COMPLETED

	/**
	 * @apiNote observe, choose an action and execute it
	 * @implNote The GAL automaton is one way to encode a behaviour
	 * @param elapsed is not used by the automaton
	 */

	 public void tick(double elapsed){ 
		 super.tick(elapsed);
	 }

	/**
	 * @apiNote stops the current action and then queries the PLC to select a new
	 *          action
	 */
	 public void collision(Entity impactor, double elapsed_ms) {
		 throw new UnsupportedOperationException("UNIMPLEMENTED METHOD `add`");
	 }

	/**
	 * @apiNote notifies the Bot that the action of its Stunt is completed.
	 */
	 public void completed() {
		 throw new UnsupportedOperationException("UNIMPLEMENTED METHOD `add`");
	 }

	@Override
	public void think(double elapsed_ms) {
		if(this.automaton!=null && elapsed_ms%this.speed==0) {
			boolean transitionFranchie = this.automaton.step(this.entity);
		}
		
	}

	@Override
	public void think() {
        if(this.automaton!=null) {
        	boolean transitionFranchie = this.automaton.step(this.entity);
        }
		
	}

}
