// = Circle =
package engine.shapes;

import engine.ISU;
import engine.ISU.Coord;
import engine.shapes.Rect.RectCircleIntersection;

public class Circle extends Shape implements iShape {

	private double radius;

	// CONSTRUCTOR
	public Circle(ISU.Coord center, double radius) {
		super(center);
		this.radius = radius;
	}
	
	// GETTER
	public double getRadius() {
		return this.radius;
	}

	// INTERSECTION
	public boolean intersects(Rect rect) {
		RectCircleIntersection rc = rect.new RectCircleIntersection(rect, this);
		return rc.intersects();
	}
	
	public boolean intersects(Circle circle) {
		return (this.center.distanceTo(circle.center) <= (this.radius + circle.radius));
	}

	@Override
	public void rotateAround(Coord c, int angle_degree) {
		this.center.rotateAround(c, angle_degree);
	}
}
