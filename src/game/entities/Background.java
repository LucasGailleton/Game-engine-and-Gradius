package game.entities;

import engine.Entity;
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

		public BackgroundAvatar() {
			super(Background.this);
			Background.this.self = this;
			canvas = (Canvas) Runtime.task().find("canvas");
		}

		@Override
		public void getSprites(Graphics g) {
			this.sprites = new BufferedImage[1][1];
			this.sprites[0][0] = g.load("background.png");
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
				this.getSprites(g);
			
			int canvasWidth = canvas.getWidth();
			int canvasHeight = canvas.getHeight();
		
			
			
			if (xPosition+shiftPX < -canvasWidth)
				xPosition += canvasWidth;
			
			g.drawImage(this.sprites[0][0], xPosition+shiftPX, 0, canvasWidth, canvasHeight);
			g.drawImage(this.sprites[0][0], canvasWidth+xPosition+shiftPX+1, 0, canvasWidth+1, canvasHeight);
		}
	}

	@Override
	public void setBounding() {
		
	}

	@Override
	public void tick(double elapsed, boolean shift) {
		
		
	}
}
