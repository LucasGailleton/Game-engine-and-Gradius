// = BOSS =
package game;

import engine.Entity;
import engine.Game;
import engine.ISU.Dimension;
import engine.shapes.Rect;

/**
 * @implNote Le Boss a la forme d'un `t`
 * @implNote il occupe 1 cellule au dessus de son centre, 2 au dessous de son
 *           centre, et 2 cellules à droite de son centre.
 * @implNote Le Boss a donc une dimension (x=3Cell,y=4Cell)
 * @implNote Cette forme a été choisie pour pouvoir tester les rotations.
 */
public class Boss extends Entity {

	// CONSTRUCTOR
	public Boss() {
		super("Boss");
		this.setSize(this.isu.new Dimension(Game.cmPerCell*5.0, Game.cmPerCell*5.0));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Rect(this.isu.new Coord(this.center().x_cm, this.center().y_cm+Game.cmPerCell/2), this.isu.new Dimension(Game.cmPerCell, Game.cmPerCell*4), this.orientation()));
		this.bounding().add(new Rect(this.isu.new Coord(this.center().x_cm+Game.cmPerCell*1.5, this.center().y_cm), this.isu.new Dimension(Game.cmPerCell*2, Game.cmPerCell), this.orientation()));
	}

}
