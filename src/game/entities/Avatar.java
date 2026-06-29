package game.entities;

import engine.Entity;
import engine.Game;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public abstract class Avatar {
	
	public static final int East = 0;
	public static final int NorthEast = 1;
	public static final int North = 2;
	public static final int NorthWest = 3;
	public static final int West = 4;
	public static final int SouthWest = 5;
	public static final int South = 6;
	public static final int SouthEast = 7;
	
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
	
	public abstract void paint(Graphics g, int shiftPX);
	
	public int toPixel(double cm) {
		return (int) Math.round(cm*Game.pixelPerCm);
	}
}

