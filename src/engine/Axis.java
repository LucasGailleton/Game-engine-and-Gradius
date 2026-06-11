// = AXIS =
package engine;

/**
 * @apiNote Axis of a Torus with origin at 0
 * @implNote Coordinate ranges in [ -perimeter/2 ; perimeter/2 [
 * @implNote Negative coordinate are allowed
 */

public class Axis {

	// FIELDS

	boolean onTorus;
	public double perimeter;
	double halfPerimeter;

	// CONSTRUCTOR

	Axis(boolean onTorus, double perimeter) {
		if (perimeter <= 0)
			throw new IllegalArgumentException();
		
		this.onTorus = onTorus;
		this.perimeter = perimeter;
		this.halfPerimeter = perimeter/2;
	}

	// NORMALIZE INTEGER LENGTH

	/**
	 * @apiNote normalize _integer length_ according to the geometry
	 * @implNote returns positive values
	 * @return
	 *         <UL>
	 *         <LI>length % perimeter __&in; [0, perimeter-1]__ if onTorus</LI>
	 *         <LI>length if !onTorus</LI>
	 *         </UL>
	 */
	int normalize(int length) {
		if (onTorus)
			return modp(length, (int) Math.round(this.perimeter));
		return length;
	}

	/**
	 * @apiNote compute length modulo perimeter
	 * @return length % perimeter __&in; [0, perimeter-1]__
	 */
	int modp(int length, int perimeter) {
		int res = length % perimeter;
		if (res < 0)
			res += perimeter;
		return res;
	}

	// NORMALIZE REAL LENGTH

	/**
	 * @apiNote normalize _real length_ according to the geometry
	 * @implNote can return negative values
	 * @return
	 *         <UL>
	 *         <LI>length module perimeter <I>&in; [-perimeter/2 , perimeter/2[</I>
	 *         if onTorus</LI>
	 *         <LI>length if !onTorus</LI>
	 *         </UL>
	 */
	double normalize(double length) {
		if (onTorus)
			return modp(length, this.perimeter);
		return length;
	}

	/**
	 * @apiNote compute length modulo perimeter
	 * @return length % perimeter __&in; [0, perimeter[__
	 */
	double modp(double length, double perimeter) {
		double res = length % perimeter;
		if (res < 0)
			res += perimeter;
		return res;
	}

	// DISTANCE

	/**
	 * @apiNote The distance on a Torus is that of the shortest path, sometimes
	 *          going in the opposite direction and across the border is shorter.
	 * @implNote Look for the detail on internet.
	 */
	double distance(double position1, double position2) {
		double pos1 = normalize(position1);
		double pos2 = normalize(position2);
		double d = Math.abs(pos1 - pos2);
		if (onTorus && d > this.halfPerimeter)
			return this.perimeter - d;
		return d;
	}
}
