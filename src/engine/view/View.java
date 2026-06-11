package engine.view;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.shapes.Rect;
import game.Avatar;
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
            Entity entity = a.entity();
            g.setColor(Colors.red);
            Rect boundingBox = entity.boundingBox();
            g.drawRect((int)(boundingBox.center().x_cm-boundingBox.getHalfWidth()) * Game.pixelPerCm, 
                    (int)(boundingBox.center().y_cm - boundingBox.getHalfHeight()) * Game.pixelPerCm, 
                    (int)boundingBox.getHalfWidth()*2*Game.pixelPerCm, 
                    (int)boundingBox.getHalfHeight()*2*Game.pixelPerCm);			
            a.paint(g);
		}
	}	
}
