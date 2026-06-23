package engine.GAL;

import java.util.Objects;

public class State {

	String mode;
	int id;
	private final String toString;

	// CONSTRUCTOR

	public State(String mode, int id) {
		this.id=id;
		this.mode=mode;
		this.toString = mode + id; 
	}

	// EQUALS
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
        if (o == null) {
        	return false;
        }
        return o instanceof State s && this.id == s.id && this.mode.equals(s.mode);
	}

	
	boolean equals(State s) {
		return s.id==this.id && s.mode.equals(this.mode);
	}

	// HASH
	public int hashCode() {
		return Objects.hash(id, mode); // Basé sur le contenu
	}
	
	@Override
	public String toString(){
		return this.toString;
	}

}
