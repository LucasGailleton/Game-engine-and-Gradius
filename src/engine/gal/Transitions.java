package engine.gal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Transitions  implements iTransitions{
	private final Map<State, List<Transition>> map = new HashMap<>();
	
	 long serialVersionUID = 1L;
	 
	 public void add(Transition t){ 
		 this.map.computeIfAbsent(t.source, k -> new LinkedList<>()).add(t);
	 }
	 public List<Transition> get(State state) {
		 return this.map.get(state);
	 }
}