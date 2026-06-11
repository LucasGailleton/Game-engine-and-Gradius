package engine.gal;

import engine.Entity;

public class GALBot extends Bot {

	// CONSTRUCTOR

	public GALBot(Entity e) {
		super(e);
	}

	// ENTITY SELECTED BY CONDITON

	Entity selectedEndity;

	void selectedEntity(Entity e) {
		this.selectedEndity = e;
	}

	Entity selected() {
		return this.selectedEndity;
	}

	// AUTOMATON

	Automaton automaton;

	/**
	 * @apiNote change the automaton of the Bot
	 * @impNote Que devient l'état (State) du Bot ?
	 * @param automaton
	 */
	void set(Automaton automaton) {
		this.automaton = automaton;
	}

	// TICK & COLLISION & COMPLETED

	/**
	 * @apiNote observe, choose an action and execute it
	 * @implNote The GAL automaton is one way to encode a behaviour
	 * @param elapsed is not used by the automaton
	 */

	void tick(double elapsed) {
		
	}

	/**
	 * @apiNote stops the current action and then queries the PLC to select a new
	 *          action
	 */
	void collision(Entity impactor, double elapsed_ms);

	/**
	 * @apiNote notifies the Bot that the action of its Stunt is completed.
	 */
	void completed();

	@Override
	public void think() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collision(Entity impactor, double elapsed_ms) {
		// TODO Auto-generated method stub
		
	}

}
