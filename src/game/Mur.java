package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Vector;
import engine.view.View;
import game.entities.Avatar;
import oop.graphics.Graphics;

public class Mur extends Entity {

	public Mur() {
		super("mur");
		this.setSize(this.isu.new Dimension(Game.game().width_ncell*Game.cmPerCell*Main.windowScale, Game.game().height_ncell*Game.cmPerCell*Main.windowScale));
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
