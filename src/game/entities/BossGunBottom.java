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

public class BossGunBottom extends Entity implements Shooter{

	public BossGunBottom(String name) {
		super(name);
		this.setSize(this.isu.new Dimension(Game.cmPerCell*15.0, Game.cmPerCell*5.0));
		this.avatar = new BossGunBottomAvatar(this);
		
	}
	
	private class BossGunBottomAvatar extends Avatar{
		
		private List<BufferedImage> death;
		private int index,indChg;

		public BossGunBottomAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index = 0;
			this.indChg = 0;
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
            this.sprites = new BufferedImage[4][3];
            
            sprites[0][0] = imgSprites.getSubimage(280, 82, 26, 8);
            sprites[0][1] = imgSprites.getSubimage(280, 99, 26, 8);
            
            this.death.add(imgSprites.getSubimage(281, 127, 26, 8));
            this.death.add(imgSprites.getSubimage(281, 146, 26, 8));
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites ==null) {
				this.getSprites(g);
			}
            
            ISU.Coord center = BossGunBottom.this.center(); 
            int posX = this.toPixel(center.x_cm - (BossGunBottom.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (BossGunBottom.this.size.y_cm / 2));
            
           
            g.drawImage(sprites[0][index], posX, posY, (int)(26 * Main.windowScale), (int)(8*Main.windowScale));
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

	@Override
	public void tryToShoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

}
