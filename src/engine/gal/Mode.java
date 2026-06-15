package engine.gal;

import java.util.HashMap;
import java.util.Map;

class Mode {

	public static Mode Blocking, Dying, Escaping, Fighting, Resting, Running, Searching, Sleeping, Waiting,
			Walking;

	Map<String, Mode> modes = new HashMap<>();
	//modes;

	static {
		Blocking = new Mode("Blocking");
		Dying = new Mode("Dying");
		Escaping = new Mode("Escaping");
		Fighting = new Mode("Fighting");
		Resting = new Mode("Resting");
		Running = new Mode("Running");
		Searching = new Mode("Searching");
		Sleeping = new Mode("Sleeping");
		Waiting = new Mode("Waiting");
		Walking = new Mode("Walking");
	}

	// CONSTRUCTOR

	 String name;

	 Mode(String name){ 
		 this.name=name;
		 modes.put(name, this);
	 }

	// FACTORY

	 Mode canonical(String name){ 
			if(modes.containsKey(name)) {
				return modes.get(name);
			}
			Mode newMod = new Mode(name);
			return newMod;
	 }

}
