package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Vector;
import engine.view.View;
import game.entities.Avatar;
import oop.graphics.Graphics;

public class Mur extends Entity {

	public Mur(int x, int y) {
		super("mur");
		
		this.setSize(this.isu.new Dimension(
				(int)(150 * Game.cmPerCell * Main.windowScale),
				(int)(2 * Game.cmPerCell * Main.windowScale)
		));
		
		this.setCoord(isu.new Coord(
				x * Game.cmPerCell * Main.windowScale, 
				y * Game.cmPerCell * Main.windowScale
		));

		this.deploy();
		this.setBounding();		
		this.avatar = new MurAvatar();
	}
	
	public void tick(double seconds,boolean shift) {
		if (shift)
			translate(isu.new Vector((-View.speed / 4.0) * Main.windowScale, 0.0));

	}
	
	public class MurAvatar extends Avatar {
		public MurAvatar() {
			super(Mur.this);
		}

		@Override
		public void getSprites(Graphics g) {
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			
		}
	}
}