package engine.view;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.shapes.Rect;
import game.Main;
import game.entities.Avatar;
import oop.graphics.Canvas;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class View {
	Canvas canvas;
	List<Avatar> avatars;
	
	public View(Canvas canvas) {
		this.canvas = canvas;
		this.avatars = new ArrayList<Avatar>();
	}
	
	public void add(Avatar e) {
		this.avatars.add(e);
	}
	
	public void paint(Graphics g) {
		for (Avatar a : avatars) {
			if (Main.debug) {
	            Entity entity = a.entity();
	            g.setColor(Colors.red);
	            Rect boundingBox = entity.boundingBox();
	            ISU.Coord centre = boundingBox.center();
	            
	            double h = boundingBox.getHalfHeight();
	            double w = boundingBox.getHalfWidth();
	            
	            g.drawRect((int)(centre.x_cm-w) * Game.pixelPerCm, 
	                    (int)(centre.y_cm - h) * Game.pixelPerCm, 
	                    (int)w*2*Game.pixelPerCm, 
	                    (int)h*2*Game.pixelPerCm);
			}
            a.paint(g);
		}
	}	
}
