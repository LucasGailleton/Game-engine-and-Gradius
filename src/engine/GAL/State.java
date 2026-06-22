package engine.GAL;



public class State {

	String mode;
	int id;

	// CONSTRUCTOR

	public State(String mode, int id) {
		this.id=id;
		this.mode=mode;
	}

	// EQUALS
	@Override
	public boolean equals(Object o) {
		return (o instanceof State) && ((State)o).id==this.id && ((State)o).mode==this.mode;
	}

	boolean equals(State s) {
		return s.id==this.id && s.mode==this.mode;
	}

	// HASH
	public int hashCode() {
		return this.id;
	}

}
