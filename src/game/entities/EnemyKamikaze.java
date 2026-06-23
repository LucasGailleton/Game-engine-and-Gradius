package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class EnemyKamikaze extends Entity{
	
	public static final int spriteNeutral = 0;
    public static final int spriteUp = 1;
    public static final int spriteDown = 2;
   
	public EnemyKamikaze(String name) {
		super(name);
		
		this.setSize(this.isu.new Dimension((Game.cmPerCell*18)/Main.windowScale, (Game.cmPerCell*18)/Main.windowScale));
		this.avatar = new KamikazeAvatar(this);
		
	}
	
	
	private class KamikazeAvatar extends Avatar{
		
		private List<BufferedImage> death;
		private final int spriteSizeX = 18; 
        private final int spriteSizeY = 18;
        
        private int index;
        private int indChg;

		public KamikazeAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index =0;
			this.indChg = 0;
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
		    this.sprites = new BufferedImage[1][6];
		    
		   
		    sprites[0][0] = imgSprites.getSubimage(262, 194, 18, 18);
		    sprites[0][1] = imgSprites.getSubimage(282, 197, 18, 16);
		    sprites[0][2] = imgSprites.getSubimage(323, 197, 18, 16);
		    sprites[0][3] = imgSprites.getSubimage(303, 194, 18, 18);
		    sprites[0][4] = imgSprites.getSubimage(282, 197, 18, 16);
		    sprites[0][5] = imgSprites.getSubimage(323, 197, 18, 16);
		    
		    
		    
		    this.death.clear();
		    this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
	        }


			@Override
			public void paint(Graphics g, int shiftPX) {
				
				if (this.sprites ==null) {
					this.getSprites(g);
				}
	           
	            ISU.Coord center = EnemyKamikaze.this.center();
	            int posX = this.toPixel(center.x_cm - (EnemyKamikaze.this.size.x_cm / 2));
	            int posY = this.toPixel(center.y_cm - (EnemyKamikaze.this.size.y_cm / 2));
	            
	            
	            
	             g.drawImage(sprites[0][index], posX, posY, (int)(spriteSizeX * Main.windowScale), (int)(spriteSizeY*Main.windowScale));
	             if (indChg == 0)
	 		    	index++;
	 		    if (index > 5)
	 		    	index = 0;
	 		    indChg++;
	 		    if (indChg % 10 == 0) {
	 		    	indChg = 0;
	 		    }
				
			}	
	    }


}
