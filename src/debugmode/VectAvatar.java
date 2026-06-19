package debugmode;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.view.View;
import game.Main;
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
		int posX = (int) (centre.x_cm*Game.pixelPerCm);
	    int posY = (int)(centre.y_cm*Game.pixelPerCm);
		g.drawLine(posX+shiftPX, posY, (int)(posX+shiftPX + v.targetX_cm), (int)(posY + v.targetY_cm));
	}
}
