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

public class BossGunTop extends Entity implements Enemy{

	public BossGunTop(String name) {
		super(name);
		this.setSize(this.isu.new Dimension(Game.cmPerCell*15.0, Game.cmPerCell*5.0));
		this.avatar = new BossGunTopAvatar(this);
		
	}
	
	public void setBounding() {
    	this.bounding().add(new Rect(this.center(), this.size, 0));
    	
    }
    
    public Avatar getAvatar() {
    	return this.avatar;
    }
	
	private class BossGunTopAvatar extends Avatar{
		
		private List<BufferedImage> death;
		private int index,indChg;
		
		private static final int spriteSizeX = 8; 
		private static final int spriteSizeY = 8;
		
		public BossGunTopAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index = 0;
			this.indChg = 0;
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
            this.sprites = new BufferedImage[4][3];
            
            sprites[0][0] = imgSprites.getSubimage(243, 81, 26, 8);
            sprites[0][1] = imgSprites.getSubimage(243, 98, 26, 8);
            
            this.death.add(imgSprites.getSubimage(244, 125, 26, 8));
            this.death.add(imgSprites.getSubimage(245, 144, 26, 8));
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if(this.sprites == null)
				this.getSprites(g);
            
            ISU.Coord center = BossGunTop.this.center(); 
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
           
            g.drawImage(sprites[0][index], posX+shiftPX, posY, (int)(26 * Main.windowScale), (int)(8*Main.windowScale));
            if (indChg == 0)
 		    	index++;
 		    if (index > 1)
 		    	index = 0;
 		    indChg++;
 		    if (indChg % 10 == 0) {
 		    	indChg = 0;
 		    }
			//A IMPLEMENTER : DEATH
		}
		
	}

	@Override
	public void tick(double elapsed, boolean shift) {
		// TODO Auto-generated method stub
		
	}

}
