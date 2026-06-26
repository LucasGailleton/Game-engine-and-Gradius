package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Canvas;
import oop.graphics.Graphics;
import oop.tasks.Runtime;

public class Background extends Entity {
	
	private BackgroundAvatar self;

	public Background() {
		super("Background");
	}
	
	public BackgroundAvatar avatar() {
		return this.self;
	}
	
	public class BackgroundAvatar extends Avatar {
		
		Canvas canvas;
		int xPosition;
		private int lastShiftPX = 0;
		private int shift_bg = 0;
		private boolean initialized = false;

		public BackgroundAvatar() {
			super(Background.this);
			Background.this.self = this;
			canvas = (Canvas) Runtime.task().find("canvas");
		}

		@Override
		public void getSprites(Graphics g) {
			this.sprites = new BufferedImage[1][1];
			this.sprites[0][0] = g.load("sprites/background.png");
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
				this.getSprites(g);

			if (!initialized) {
				lastShiftPX = shiftPX;
				initialized = true;
			}

			int taille_map = (int) (Game.game().width_ncell * Game.cmPerCell * Game.pixelPerCm);
			
			int delta = shiftPX - lastShiftPX;
			
			if (delta > 0) {
				delta -= taille_map;
			}
			
			shift_bg += delta;
			lastShiftPX = shiftPX;

			int w = canvas.getWidth();
			int h = canvas.getHeight();

			int x = shift_bg % w;

			g.drawImage(this.sprites[0][0], x, 0, w, h);
			g.drawImage(this.sprites[0][0], x + w, 0, w, h);
		}
	}

	@Override
	public void setBounding() {
		// TODO Auto-generated method stub
	}

	@Override
	public void tick(double elapsed, boolean shift) {
		
		
	}
}
