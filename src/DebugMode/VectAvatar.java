package DebugMode;

import engine.Entity;
import engine.ISU;
import game.entities.Avatar;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class VectAvatar extends Avatar {

	public VectAvatar(Entity e) {
		super(e);
	}

	@Override
	public void getSprites(Graphics g) {
		
	}

	public void paint(Graphics g) {

	}

	@Override
	public void paint(Graphics g, int shiftPX) {
		ISU.Vector v = e.lSpeed();
		ISU.Coord centre = e.center();
		g.setColor(Colors.yellow);
		int posX = this.toPixel(centre.x_cm - (e.size.x_cm / 1.8));
	    int posY = this.toPixel(centre.y_cm - (e.size.y_cm / 1.8));
		g.drawLine(posX, posY, (int)(posX + v.targetX_cm), (int)(posY + v.targetY_cm));
	}
}
