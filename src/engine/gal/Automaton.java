package engine.gal;

import engine.Entity;

class Automaton {

	 State initial;
	/**
	 * @apiNote represents a collection of {@code Transition}
	 * @implNote Transitions are ordered by the order in which they were added so
	 *           that the automaton processes them in that order.
	 * @implNote Choose your representation carefully to efficiently identify the
	 *           potential transitions for triggering.
	 */
	 iTransitions transitions;

	// CONSTRUCTOR

	 Automaton(String name, State initial){ 
		 this.initial=initial;
	 }

	// BUILDER

	/**
	 * @apiNote add a transition to the automaton after the previous ones
	 */
	 void add(Transition t){ 
		 transitions.add(t);
	 }

	// AUTOMATON STEP = TRIGGER A TRANSITION or FAIL and STAY IN THE SAME STATE

	/**
	 * @apiNote Try to select and execute one valid transition
	 * @param e = the Entity whose bot evaluates the condition and whose stunt
	 *          executes the action
	 * @return {@code true} if there exists a transition which can be triggered by
	 *         the {@code bot}
	 *         <LI>{@code false} if no transition can be taken.</LI>
	 */
	 boolean step(Entity e){ 
		 for(Transition t:this.transitions.get(initial)) {
			 if(t.exec(e)) {
				 return true;
			 }
		 }
		 return false;
	 }

}
