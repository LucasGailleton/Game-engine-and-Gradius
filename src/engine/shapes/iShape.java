package engine.shapes;

import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Vector;
import oop.graphics.Graphics;

public interface iShape {

	public boolean intersects(iShape shape);

	public boolean intersects(Circle circle);

	public boolean intersects(Rect rect);
	
	public void rotateAround(ISU.Coord c, int angle_degree);

	public void translate(int dx, int dy);

	public void translate(ISU.Vector v);

	public void paint(Graphics g, int shiftPX);
}
