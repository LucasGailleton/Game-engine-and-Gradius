package engine.gal;

import java.util.List;

interface iTransitions {

	List<Transition> get(State state);

	void add(Transition t);
}
