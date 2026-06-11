// = BOUNDING =
package engine.shapes;

import java.util.HashSet;
import java.util.Set;

import engine.ISU;

public class Bounding {

	// FIELDS
	private Set<iShape> boundings;

	// CONSTRUCTOR
	public Bounding() {
		this.boundings = new HashSet<iShape>();
	}
	
	// GETTER
	public Set<iShape> boundings() {
		return this.boundings;
	}

	// BUILDER
	public void add(iShape shape) {
		this.boundings.add(shape);
	}

	// INTERSECTION
	public boolean intersects(iShape shape) {
		for (iShape s : boundings) {
			if (s.intersects(shape))
				return true;
		}
		return false;
	}

	public boolean intersects(Bounding bounding) {
		for (iShape s1 : this.boundings) {
			for (iShape s2 : bounding.boundings()) {
				if (s1.intersects(s2))
					return true;
			}
		}
		return false;
	}
	
	public void rotateAround(ISU.Coord c, int angle_degree) {
		for (iShape s : boundings)
			s.rotateAround(c, angle_degree);
	}

}
