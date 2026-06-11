package engine.gal;

import java.util.HashMap;
import java.util.Map;

public class Mode {

	static Mode Blocking, Dying, Escaping, Fighting, Resting, Running, Searching, Sleeping, Waiting, Walking;
	static Map<String, Mode> modes;

	static {
		modes = new HashMap<String, Mode>();
	}

	// CONSTRUCTOR

	private String name;

	Mode(String name) {
		this.name = name;
	}

	// FACTORY

	Mode canonical(String name) {
		if (modes.containsKey(name))
			return modes.get(name);
		Mode mode = new Mode(name);
		modes.put(name, mode);
		return mode;
	}

}
