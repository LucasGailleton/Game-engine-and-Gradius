package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Circle;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class Ship extends Entity {

	public static final double ShipSpeed = 5*Main.windowScale;
	
	private ShipAvatar self;
	
	public Ship() {
		super("Ship");
		this.setSize(game.isu.new Dimension(2.0*Game.cmPerCell*Main.windowScale, 1.0*Game.cmPerCell*Main.windowScale));
	}

	public void setBounding() {
		this.bounding().add(new Rect(this.center(), this.size, 0));
	}
	
	public ShipAvatar avatar() {
		return this.self;
	}
	
	public class ShipAvatar extends Avatar {

		public static final int spriteSizeX = 28;
		public static final int spriteSizeY = 14;
		public static final int spriteFlat = 0;
		public static final int spriteUp = 1;
		public static final int spriteDown = 2;
		
		public ShipAvatar() {
			super(Ship.this);
			Ship.this.self = this;
			this.direction = East;
		}

		@Override
		public void getSprites(Graphics g) {
			this.sprites = new BufferedImage[4][3];
			BufferedImage imgSprites = g.load("sprites.png");
			
			sprites[West][spriteFlat] = imgSprites.getSubimage(224, 4, spriteSizeX, spriteSizeY);
			sprites[West][spriteUp] = imgSprites.getSubimage(191, 4, spriteSizeX, spriteSizeY);
			sprites[West][spriteDown] = imgSprites.getSubimage(159, 4, spriteSizeX, spriteSizeY);
			
			sprites[East][spriteFlat] = imgSprites.getSubimage(262, 4, spriteSizeX, spriteSizeY);
			sprites[East][spriteUp] = imgSprites.getSubimage(295, 4, spriteSizeX, spriteSizeY);
			sprites[East][spriteDown] = imgSprites.getSubimage(327, 4, spriteSizeX, spriteSizeY);
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
				this.getSprites(g);
			
			ISU.Coord center = Ship.this.center();
			int posX = this.toPixel(center.x_cm)-((int)(spriteSizeX*Main.windowScale)/2);
		    int posY = this.toPixel(center.y_cm)-((int)(spriteSizeY*Main.windowScale)/2);
		    
		    int index;
		    ISU.Vector lSpeed = Ship.this.lSpeed();
		    if (lSpeed.targetY_cm == 0)
		    	index = spriteFlat;
		    else if (lSpeed.targetY_cm > 0)
		    	index = spriteDown;
		    else
		    	index = spriteUp;
		    
		    g.drawImage(sprites[direction][index], posX-3, posY-3, (int) (spriteSizeX*Main.windowScale), (int) (spriteSizeY*Main.windowScale));
		}
		
	}
}
