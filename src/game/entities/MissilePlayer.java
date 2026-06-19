package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MissilePlayer extends Entity{

	public MissilePlayer(String name) {
		super(name);
		this.setSize(this.isu.new Dimension((Game.cmPerCell*1)/Main.windowScale, (Game.cmPerCell*1)/Main.windowScale));
        this.avatar = new MissilePlayerAvatar(this);
	}
	
	private class MissilePlayerAvatar extends Avatar{

		public MissilePlayerAvatar(Entity e) {
			super(e);
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[1][1];
            
            this.sprites[0][0] = imgSprites.getSubimage(357, 800, 6, 3);
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			this.getSprites(g);
            
            ISU.Coord center = MissilePlayer.this.center(); //para el Gruber
            int posX = this.toPixel(center.x_cm - (MissilePlayer.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (MissilePlayer.this.size.y_cm / 2));
            
           
            g.drawImage(sprites[0][0], posX, posY, (int)(6 * Main.windowScale), (int)(3*Main.windowScale));
			
		}
		
	}

}
