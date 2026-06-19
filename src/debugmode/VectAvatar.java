package debugmode;

import engine.Entity;
import engine.Game;
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
		g.setColor(Colors.green);
		int posX = (int) (centre.x_cm*Game.pixelPerCm)-3;
	    int posY = (int)(centre.y_cm*Game.pixelPerCm)-3;
		g.drawLine(posX, posY, (int)(posX + v.targetX_cm), (int)(posY + v.targetY_cm));
	}
}
