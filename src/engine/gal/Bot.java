package engine.gal;

import engine.Entity;

public abstract class Bot {

	protected Entity entity;

	// CONSTRUCTOR

	Bot(Entity e) {
		this.entity = e;
	}

	// STUNT

	protected GALStunt stunt;

	void stunt(GALStunt stunt) {
		this.stunt = stunt;
	}

	// STATE

	protected State state;

	/**
	 * @apiNote The state can be used
	 *          <UL>
	 *          <LI>to drive the automaton</LI>
	 *          <LI>to select the appropriate avatar</LI>
	 *          </UL>
	 * @return the state of mind of the Bot
	 */
	State state() {
		return this.state;
	}

	void state(State state) {
		this.state = state;
	}

	// HEALTH

	/**
	 * @apiNote 0 &le; health &le; 100
	 */
	protected int healthPercent;

	// TICK & COLLISION & COMPLETED

	/**
	 * @apiNote wakes up the Bot so that it can take action
	 * @param elapsed_ms
	 */
	void tick(double elapsed_ms) {
		
	}
	
	public abstract void think();

	/**
	 * @apiNote notifies the Bot that a collision has occurred with {@code impactor}
	 *          after {@code elapsed_ms} so that the Bot can take action
	 * @param impactor
	 * @param elapsed_ms
	 */
	public abstract void collision(Entity impactor, double elapsed_ms);

	/**
	 * @apiNote notifies the Bot that the action of its Stunt is completed.
	 */
	public abstract void completed();

}
