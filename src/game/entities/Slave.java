package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class Slave extends Entity {

	private final Ship master;
	private final int index;

	public Slave(Ship master, int index) {
		super("Slave");
		this.master = master;
		this.index = index;
		this.setSize(this.isu.new Dimension(Game.cmPerCell*Main.windowScale, (Game.cmPerCell/2.0)*Main.windowScale));
		this.setCoord(master.center());
		this.avatar = new SlaveAvatar(this);
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		
	}

	private class SlaveAvatar extends Avatar {

		private BufferedImage sprite;

		public SlaveAvatar(Entity e) {
			super(e);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage img = g.load("all_Sprites.png");
			this.sprite = img.getSubimage(262, 4, 32, 16);
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprite == null)
				this.getSprites(g);

			// recalcule la position a partir du vaisseau
			ISU.Coord c = Slave.this.master.slaveCoord(Slave.this.index);
			Slave.this.setCoord(c);

			int w = (int) (20 * Main.windowScale);
			int h = (int) (10 * Main.windowScale);
			int posX = this.toPixel(c.x_cm) - w / 2 + shiftPX;
			int posY = this.toPixel(c.y_cm) - h / 2;
			g.drawImage(this.sprite, posX, posY, w, h);
		}
	}
}
