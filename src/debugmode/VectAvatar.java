package debugmode;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.view.View;
import game.Main;
import game.entities.Avatar;
import game.entities.Ship;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class VectAvatar extends Avatar {

	public VectAvatar(Ship ship) {
		super(ship);
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
	    

	    String scrolling = "Vect scrolling = (" + Ship.ShipSpeed + ", 0.0)";
	    String boost = "Vect boost = (" + v.targetX_cm + ", " + v.targetY_cm + ')';
	    String somme = "Somme = (" + (Ship.ShipSpeed + v.targetX_cm) + ", " + v.targetY_cm + ')';
	    
	    g.setFont(g.getFont("Monospaced", 0, 16));
	    g.drawString(scrolling, 90, 80);
	    g.drawString(boost, 90, 95);
	    g.drawString(somme, 90, 110);


		g.drawLine(posX+shiftPX, posY, (int)(posX+shiftPX + v.targetX_cm), (int)(posY + v.targetY_cm));
	}
}
