package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.entities.Avatar;
import oop.graphics.Graphics;

public class Mur extends Entity {

	public Mur(int x, int y) {
		super("mur");
		
		this.setSize(this.isu.new Dimension(
				(int)(Game.game().width_ncell * Game.cmPerCell * Main.windowScale),
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