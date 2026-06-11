package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import oop.graphics.BufferedImage;
import oop.graphics.Canvas;
import oop.graphics.Graphics;

public abstract class Avatar {
	
	public static final int East = 0;
	public static final int North = 1;
	public static final int West = 2;
	public static final int South = 3;

	protected Entity e;
	protected BufferedImage[][] sprites;
	protected int direction;
	
	public Avatar(Entity e) {
		this.e = e;
		this.direction = 0;
	}
	
	public Entity entity() {
		return this.e;
	}
	
	public abstract void getSprites(Graphics g);
	
	public abstract void paint(Graphics g);
	
	public int toPixel(double cm) {
		return (int) (cm*Game.pixelPerCm);
	}
}

