
package engine.GAL;


import engine.Entity;

public class Transition {

	// FIELDS

	 State source;
	 iGALCondition condition;
	 iGALAction action;
	 State tgt;

	// CONSTRUCTOR

	 public Transition(State source, iGALCondition condition, GALAction action, State target){ 
		 this.source=source;
		 this.condition=condition;
		 this.action=(iGALAction) action;
		 this.tgt=target;
	 }
	 
	 

	// EXEC

	/**
	 * @apiNote Tries to execute the transition and update the {@code Bot} state
	 * @apiNote The transition is triggerd if
	 *          <UL>
	 *          <LI>The {@code src} state is the current state of the
	 *          {@code Bot}</LI>
	 *          <LI>The {@code condition} is satisfied</LI>
	 *          </UL>
	 * @implNote if the transition is triggered, the {@code Bot} state switches to
	 *           {@code tgt} state.
	 * @param e = the entity which evaluates the condition using its {@code Bot}
	 *          figures (health, state, ...) and triggers the {@code Stunt} action
	 * @return {@code true} if the condition is satisfied and the action can start
	 */
	 boolean exec(Entity e){ 
		 if(e.bot().state().equals(source) && this.condition.eval(e)) {
			 boolean b=this.action.exec(e);
			 if(b) {
				 e.bot().state(tgt);
				 return true;
			 }
		 }
		 return false;
	 }

}
