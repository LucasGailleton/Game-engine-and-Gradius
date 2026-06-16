package DebugMode;

import engine.Entity;
import engine.ISU;
import game.Main;
import game.entities.Avatar;
import game.entities.PacMan;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class VectAvatar extends Avatar {

	public VectAvatar(Entity e) {
		super(e);
	}

	@Override
	public void getSprites(Graphics g) {
		
	}

	@Override
	public void paint(Graphics g, int shiftPX) {
		ISU.Vector v = e.lSpeed();
		ISU.Coord centre = e.center();
		g.setColor(Colors.yellow);
		int posX = this.toPixel(centre.x_cm - (e.size.x_cm / 2.0))+shiftPX;
	    int posY = this.toPixel(centre.y_cm - (e.size.y_cm / 2.0))+shiftPX;
		g.drawLine(posX, posY, (int)(posX + v.targetX_cm), (int)(posY + v.targetY_cm));
	}
}
