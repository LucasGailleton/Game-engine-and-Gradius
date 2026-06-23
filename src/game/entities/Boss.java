// = BOSS =
package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class Boss extends Entity {

	// CONSTRUCTOR
	public Boss() {
		super("Boss");
		this.setSize(this.isu.new Dimension(Game.cmPerCell*29.0, Game.cmPerCell*32.0));
		this.avatar = new BossAvatar(this);
	}
	
	private class BossAvatar extends Avatar{
		private List<BufferedImage> death;
		
		private static final int spriteSizeX = 50; 
		private static final int spriteSizeY = 50;
		
		public BossAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
			this.sprites = new BufferedImage[4][3];
			
			//FULL HP
			this.sprites[0][0]= imgSprites.getSubimage(83, 25, 54, 50);
			this.sprites[0][1]= imgSprites.getSubimage(146, 26, 54, 50);
			
			//LOW HP
			this.sprites[1][0] = imgSprites.getSubimage(87, 176, 54, 50);
			this.sprites[1][1] = imgSprites.getSubimage(150, 177, 54, 50);
			
			//this.death.add(imgSprites.getSubimage(South, East, West, North));
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if(this.sprites == null)
				this.getSprites(g);
            
			ISU.Coord center = Boss.this.center(); 
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
            g.drawImage(sprites[0][1], posX + shiftPX, posY,
            		(int) (54*Main.windowScale), 
            		(int) (60*Main.windowScale));
			
		}
		
	}

}
