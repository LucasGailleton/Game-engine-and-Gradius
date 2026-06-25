package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class Slave extends Entity {

	private Ship master;
	private final int index;
	boolean explode;

	public Slave(Ship master, int index) {
		super("Slave");
		this.master = master;
		this.explode = false;
		this.index = index;
		this.setSize(this.isu.new Dimension(Game.cmPerCell*Main.windowScale, (Game.cmPerCell/2.0)*Main.windowScale));
		this.setCoord(master.center());
		this.avatar = new SlaveAvatar(this);
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		
	}
	
	private class SlaveAvatar extends Avatar {

		private BufferedImage sprites[][];
		private static final int normal = 0;
		private static final int death = 1;
		
		private int indChg, indexSprite;

		public SlaveAvatar(Entity e) {
			super(e);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage img = g.load("all_Sprites.png");
			this.sprites = new BufferedImage[2][4];
			this.sprites[normal][0] = img.getSubimage(262, 4, 32, 16);
			
			this.sprites[death][0] = img.getSubimage(238, 29, 24, 12);
			this.sprites[death][1] = img.getSubimage(267, 25, 24, 12);
			this.sprites[death][2] = img.getSubimage(298, 21, 24, 12);
			this.sprites[death][3] = img.getSubimage(330, 27, 24, 12);
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
				this.getSprites(g);

			// recalcule la position a partir du vaisseau
			ISU.Coord c = Slave.this.master.slaveCoord(Slave.this.index);
			Slave.this.setCoord(c);

			int w = (int) (20 * Main.windowScale);
			int h = (int) (10 * Main.windowScale);
			int posX = this.toPixel(c.x_cm) - w / 2;
			int posY = this.toPixel(c.y_cm) - h / 2;
			if (!explode)
				g.drawImage(this.sprites[normal][0], posX+shiftPX, posY, w, h);
			else {
				if (indChg == 5) {
	 		    	indexSprite++;
	 		    	indChg = 0;
				}
				if (indexSprite < 4) {
					indChg++;
					g.drawImage(this.sprites[death][indexSprite], posX+shiftPX, posY, w, h);
				}
			}
		}
	}
}
