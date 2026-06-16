package engine.GAL;

import java.util.List;

interface iTransitions {

	List<Transition> get(State state);

	void add(Transition t);
}
