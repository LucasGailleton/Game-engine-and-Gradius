package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class LaserShot extends MissilePlayer {

	public LaserShot(View view, String name) {
		super(view, name);
		this.setSize(this.isu.new Dimension(Game.cmPerCell*3*Main.windowScale, (Game.cmPerCell/3.0)*Main.windowScale));
		this.avatar = new LaserAvatar(this);
	}

	public class LaserAvatar extends Avatar {
		
		public static final int spriteSizeX = 15;
	    public static final int spriteSizeY = 3;

		public LaserAvatar(Entity e) {
			super(e);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[1][1];
            
            this.sprites[0][0] = imgSprites.getSubimage(315, 802, spriteSizeX, spriteSizeY);
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites==null) {
				this.getSprites(g);
			}
			ISU.Coord center = LaserShot.this.center();
            int posX = this.toPixel(center.x_cm)-((int)(spriteSizeX*Main.windowScale)/2);
            int posY = this.toPixel(center.y_cm)-((int)(spriteSizeY*Main.windowScale)/2);

            g.drawImage(sprites[0][0], posX+shiftPX , posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));
			
		}
	}
}
