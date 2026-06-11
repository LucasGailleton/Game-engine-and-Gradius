package engine.gal;

import java.util.HashMap;
import java.util.Map;

public class Direction {

	// CONSTANTS

	static Direction B; // B, Backward, Back
	static Direction F; // F, Forward, Front
	static Direction H; // H, Here
	static Direction N; // N, North
	static Direction S; // S, South
	static Direction E; // E, East
	static Direction W; // W, West

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
	static Map<String, Direction> directions;

	// FACTORY

	/**
	 * @apiNote implements sharing : it avoids creating new direction each time the
	 *          parser encounters a direction.
	 * @return the existing direction associated to a name if it already exists
	 */
	Direction canonical(String name) {
		if (directions.containsKey(name))
			return directions.get(name);
		Direction dir = new Direction(name);
		directions.put(name, dir);
		return dir;
	}

	// STATIC INITIALIZATION

	/**
	 * @apiNote the global variables must be initialized in that section,
	 * @implNote which is executed at the class loading
	 */
	static {
		directions = new HashMap<String, Direction>();
		B = new Direction("Backward");
		F = new Direction("Forward");
		H = new Direction("Here");
		N = new Direction("North");
		S = new Direction("South");
		E = new Direction("East");
		W = new Direction("West");
	}

	// CONSTRUCTOR

	private String name;

	Direction(String name) {
		this.name = name;
	}

	// PREDICATE
	public boolean isAbsolute() {
		return (this == N || this == S || this == E || this == W);
	}

	public boolean isRelative() {
		return !this.isAbsolute();
	}

	// CONVERSION

	public int toAngle() {
		if (this.isRelative())
			throw new IllegalStateException();
		if (this == E)
			return 0;
		if (this == N)
			return 90;
		if (this == W)
			return 180;
		if (this == S)
			return 270;
		return 0;
	}
}
