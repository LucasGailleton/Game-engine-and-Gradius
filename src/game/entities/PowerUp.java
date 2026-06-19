package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class PowerUp extends Entity{

	public PowerUp(String name) {
		super(name);
		this.setSize(this.isu.new Dimension((Game.cmPerCell*1)/Main.windowScale, (Game.cmPerCell*1)/Main.windowScale));
        this.avatar = new PowerUpAvatar(this);
	}
	
	public class PowerUpAvatar extends Avatar{
		
		private int index,indChg;

		public PowerUpAvatar(Entity e) {
			super(e);
			this.index =0;
			this.indChg =0;
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[1][3];
            
            this.sprites[0][0] = imgSprites.getSubimage(262, 731, 18, 16);
            this.sprites[0][1] = imgSprites.getSubimage(282, 731, 18, 16);
            this.sprites[0][2] = imgSprites.getSubimage(301, 731, 18, 16);
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			this.getSprites(g);
            
            
            
            ISU.Coord center = PowerUp.this.center();
            int posX = this.toPixel(center.x_cm - (PowerUp.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (PowerUp.this.size.y_cm / 2));
            
            
            
             g.drawImage(sprites[0][index], posX, posY, (int)(18 * Main.windowScale), (int)(16*Main.windowScale));
             if (indChg == 0)
 		    	index++;
 		    if (index > 3)
 		    	index = 0;
 		    indChg++;
 		    if (indChg % 10 == 0) {
 		    	indChg = 0;
 		    }
			
		}
		
	}

}
