package engine.gal;

public class State {

	Mode mode;
	int id;

	// CONSTRUCTOR

	State(String mode, int id) {
		this.mode = new Mode(mode);
		this.id = id;
	}

	// EQUALS
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o instanceof State)
			return this.equals((State) o);
		return false;
	}

	public boolean equals(State s) {
		return this.hashCode() == s.hashCode();
	}

	// HASH
	@Override
	public int hashCode() {
		return this.id;
	}

}
