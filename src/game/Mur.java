package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.entities.Avatar;
import oop.graphics.Graphics;

public class Mur extends Entity {

	public Mur() {
		super("mur");
		this.setSize(this.isu.new Dimension(150*Game.cmPerCell*Main.windowScale, 2*Game.cmPerCell*Main.windowScale));
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