package engine.GAL;

import java.util.HashMap;
import java.util.Map;

import engine.Entity;

public class Direction {

	// CONSTANTS

	public static Direction B; // B, Backward, Back
	public static Direction F; // F, Forward, Front
	public static Direction H; // H, Here
	public static Direction R; // R, Right
	public static Direction L; // L, Left
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
	// STATIC INITIALIZATION

	/**
	 * @apiNote the global variables must be initialized in that section,
	 * @implNote which is executed at the class loading
	 */
	static {
		 Direction.B=new Direction("B");
		 directions.put("B", Direction.B);
		 Direction.F=new Direction("F");
		 directions.put("F", Direction.F);
		 Direction.H=new Direction("H");
		 directions.put("H", Direction.H);
		 Direction.R=new Direction("R");
		 directions.put("R", Direction.R);
		 Direction.L=new Direction("L");
		 directions.put("L", Direction.L);
		 Direction.N=new Direction("N");
		 directions.put("N", Direction.N);
		 Direction.S=new Direction("S");
		 directions.put("S", Direction.S);
		 Direction.W=new Direction("W");
		 directions.put("W", Direction.W);
		 Direction.E=new Direction("E");
		 directions.put("E", Direction.E);
		
	}


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
		return !this.isAbsolute();
	}

	// CONVERSION
   //Utilisable uniquement par les directions absolues
	int toAngle() {
		if (!isAbsolute()) {
			throw new IllegalStateException("Cannot convert relative direction '" + name + "' to an absolute angle.");
		}
		if (this == N) {
			return 270;
		}
		if (this == E) {
			return 0;
		}
		if (this == S) {
			return 90;
		}
		if (this == W) {
			return 180;
		}
		throw new IllegalStateException("Cannot convert relative direction '" + name + "' to an absolute angle.");
	}
	
	//transforme un angle en direction absolue
	static public Direction toDir(int i) {
		int j = ((i % 360) + 360) % 360;
		if ((j <= 135 && j>45 )) {
			return N;
		}
		if ((j <= 45 || j>315 )) {
			return E;
		}
		if ((j <= 315 && j>225)) {
			return S;
		}
		if (j <= 225 && j>135) {
			return W;
		}
		return null;
	}
	
	//transforme une direction relative en direction absolue
	static public Direction to_abs(Direction dir,Entity e) {
		if(dir==Direction.F) {
			return Direction.toDir(e.orientation());
		}
		if(dir==Direction.B) {
			return Direction.toDir(e.orientation()+180);
		}
		if(dir==Direction.R) {
			return Direction.toDir(e.orientation()+270);
		}
		if(dir==Direction.L) {
			return Direction.toDir(e.orientation()+90);
		}
		else {
			return null;
		}
	}
	
	static public Direction inverse(Direction d) {
		if (d == N) {
			return S;
		}
		if (d == E) {
			return W;
		}
		if (d == S) {
			return N;
		}
		if (d == W) {
			return E;
		}
		if (d == B) {
			return F;
		}
		if (d == F) {
			return B;
		}
		if (d == L) {
			return R;
		}
		if (d == R) {
			return L;
		}
		throw new IllegalStateException("Cannot inverse '" );
	}

}
