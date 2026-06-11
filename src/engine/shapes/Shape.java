package engine.shapes;

import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Vector;

public abstract class Shape implements iShape {

	// FIELD
	protected ISU isu;
	protected ISU.Coord center;

	// CONSTRUCTOR
	public Shape(ISU.Coord center) {
		this.center = center;
		this.isu = Game.game().isu;
	}
	
	public ISU.Coord center(){
		return this.center;
	}
	
	// == INTERSECTION ==
	public boolean intersects(iShape shape) {
		return shape.intersects(this);
	}
	
	public void translate(int dx, int dy) {
		this.center.translate(this.isu.new Vector(dx*Game.cmPerCell, dy*Game.cmPerCell));
	}

	public void translate(ISU.Vector v) {
		this.center.translate(v);
	}
	
}
