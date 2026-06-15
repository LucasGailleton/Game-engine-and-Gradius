package game.entities;

import engine.Entity;
import game.Main;

public class Background extends Entity {
	
	public static final int BackgroundSpeed = (int) (5*Main.windowScale);

	public Background() {
		super("Background");
	}
	
	
}
