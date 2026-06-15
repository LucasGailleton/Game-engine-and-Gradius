package engine.gal.aut.example;

import engine.gal.State;
import engine.gal.GALAction;
import engine.gal.Move;
import engine.gal.Category;
import engine.gal.Direction;
import engine.gal.Automaton;
import engine.gal.Transition;


public class Automata {

	Automaton mkBlocker() {
		State b1 = new State("Walking", 0);
		State b2 = new State("Blocked", 0);

		Automaton blocker = new Automaton("Blocker", b1);

		blocker.add(new Transition(b1, new AtStep(Direction.F, Category.Void, 1), new Move(), b1));
		blocker.add(new Transition(b1, GALCondition.TRUE, GALAction.NOTHING, b2));
		return blocker;
	}

	Automaton mkTraveller() {
		return null;
	}

}
