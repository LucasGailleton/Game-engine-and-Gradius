package debugmode;

import engine.Game;
import engine.ISU;
import game.Main;
import game.entities.Avatar;
import game.entities.Ship;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class VectAvatar extends Avatar {
	
	private ISU.Vector attract;

	public VectAvatar(Ship ship) {
		super(ship);
		attract = ship.isu.new Vector(0, 0);
	}

	@Override
	public void getSprites(Graphics g) {}
	
	public void attract(ISU.Vector v) {
		this.attract = v;
	}

	@Override
	public void paint(Graphics g, int shiftPX) {
		ISU.Vector v = e.lSpeed();
		ISU.Coord centre = e.center();
		g.setColor(Colors.green);
		int posX = (int) (centre.x_cm*Game.pixelPerCm);
	    int posY = (int)(centre.y_cm*Game.pixelPerCm);

	    if (Main.debug) {
		    String scrolling = "Vect scrolling: (" + Ship.ShipSpeed + ", 0.0)";
		    String boost = "Vect boost: (" + v.targetX_cm + ", " + v.targetY_cm + ')';
		    String attraction = "Attraction: (" + this.attract.targetX_cm + ", " + this.attract.targetY_cm + ')';
		    String somme = "Sum: (" + (Ship.ShipSpeed + v.targetX_cm + attract.targetX_cm) + ", " + (v.targetY_cm + attract.targetY_cm) + ')';
		    
		    g.setFont(g.getFont("Monospaced", 0, 16));
		    g.drawString(scrolling, 10, 60);
		    g.drawString(boost, 10, 80);
		    g.drawString(attraction, 10, 100);
		    g.drawString(somme, 10, 120);
	    }
		g.drawLine(posX+shiftPX, posY, (int)(posX+shiftPX + v.targetX_cm), (int)(posY + v.targetY_cm));
	}
}
