package game.entities;

import engine.Entity;
import engine.ISU;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Canvas;
import oop.graphics.Graphics;
import oop.tasks.Runtime;

public class Background extends Entity {
	
	public static final int BackgroundSpeed = (int) (1*Main.windowScale);
	
	private BackgroundAvatar self;
	private boolean move;

	public Background() {
		super("Background");
		this.move = false;
	}
	
	public BackgroundAvatar avatar() {
		return this.self;
	}
	
	public void move(boolean m) {
		this.move = m;
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
		public void paint(Graphics g) {
			if (this.sprites == null)
				this.getSprites(g);
			
			ISU.Coord coords = e.center();
			int canvasWidth = canvas.getWidth();
			int canvasHeight = canvas.getHeight();
			g.drawImage(this.sprites[0][0], xPosition, 0, canvasWidth, canvasHeight);
			g.drawImage(this.sprites[0][0], canvasWidth+xPosition+1, 0, canvasWidth+1, canvasHeight);
			
			if (move) {
				xPosition -= BackgroundSpeed;
				if (-xPosition > canvasWidth)
					xPosition = 0;
			}
		}
		
	}
}
