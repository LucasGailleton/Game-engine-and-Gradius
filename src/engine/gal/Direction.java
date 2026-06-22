package engine.gal;

import java.util.HashMap;
import java.util.Map;

public class Direction {

	// CONSTANTS

	public static Direction B; // B, Backward, Back
	public static Direction F; // F, Forward, Front
	public static Direction H; // H, Here
	public static Direction N; // N, North
	public static Direction S; // S, South
	public static Direction W; // W, West
	public static Direction E; // E, East

	// ...

	// STATIC

	/**
	 * @apiNote {@code directions} associates a Direction to a name in order to
	 *          ensure uniqueness (thus sharing) of the instance associated to that
	 *          name.
	 * @apiNote Advantages
	 *          <UL>
	 *          <LI>Sharing reduces memory consumption</LI>
	 *          <LI>Comparison can be done using {@code ==} instead of
	 *          {@code String:equals} on names.</LI>
	 *          <LI>it is easy to use: {@code Direction.canonical(name)} provides
	 *          the unique representative of the Direction associated to that
	 *          name.</LI>
	 *          </UL>
	 */
	static Map<String, Direction> directions = new HashMap<>();
	//Direction directions; // <-- FIXME

	// FACTORY

	/**
	 * @apiNote implements sharing : it avoids creating new direction each time the
	 *          parser encounters a direction.
	 * @return the existing direction associated to a name if it already exists
	 */
	public static Direction canonical(String name) {
		if(directions.containsKey(name)) {
			return directions.get(name);
		}
		Direction newDir = new Direction(name);
		directions.put(name, newDir);
		return newDir;
	}

	// STATIC INITIALIZATION

	/**
	 * @apiNote the global variables must be initialized in that section,
	 * @implNote which is executed at the class loading
	 */
	static {
		 Direction.B=new Direction("B");
		 Direction.F=new Direction("F");
		 Direction.H=new Direction("H");
		 Direction.N=new Direction("N");
		 Direction.S=new Direction("S");
		 Direction.W=new Direction("W");
		 Direction.E=new Direction("E");
		
	}

	// CONSTRUCTOR

	String name;

	Direction(String name) {
		this.name=name;
	}

	// PREDICATE

	boolean isAbsolute() {
		return this == N || this == S || this == E || this == W;
	}

	boolean isRelative() {
		return this == F || this == B || this == H;
	}

	// CONVERSION

	int toAngle() {
		if (!isAbsolute()) {
			throw new IllegalStateException("Cannot convert relative direction '" + name + "' to an absolute angle.");
		}
		if (this == N) {
			return 90;
		}
		if (this == E) {
			return 0;
		}
		if (this == S) {
			return 270;
		}
		if (this == W) {
			return 180;
		}
		throw new IllegalStateException("Cannot convert relative direction '" + name + "' to an absolute angle.");
	}
	
	static public Direction toDir(int i) {
		if (i == 90 || i==-270) {
			return N;
		}
		if (i == 0 || i==-180) {
			return E;
		}
		if (i == 270|| i==-90) {
			return S;
		}
		if (i == 180) {
			return W;
		}
		throw new IllegalStateException("Cannot convert relative direction '");
	}

}
