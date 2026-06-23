package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.ISU;
import engine.Grid.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class EnemyWalking extends Entity implements Enemy{
	//Entité qui spawn avec chicken
	
	

	public EnemyWalking(String name) {
		super(name);
		this.setSize(this.game.grid.new Dimension((int)(16/Main.windowScale),(int)(16/Main.windowScale)));
		this.avatar =new WalkingAvatar(this);
	}
	
	public void setBounding() {
		this.bounding().add(new Rect(this.center(), this.size, 0));
	}
	
	public Avatar getAvatar() {
		return this.avatar;
	}
	
	private class WalkingAvatar extends Avatar{
		public static final int TopLeft = 0;
		public static final int TopRight = 1;
	    public static final int DownLeft = 2;
	    public static final int DownRight = 3;
	    
	    public static final int Neutral = 0;
	    public static final int Walk1 = 1;
	    public static final int Walk2 = 2;
	    
	    private static final int spriteSizeX = 18; 
		private static final int spriteSizeY = 18;

		private List<BufferedImage> death;
		
		private int index;
		private int indChg;
		
		public WalkingAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index =0;
			this.indChg=0;
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[4][3];
            
            sprites[TopLeft][Neutral] = imgSprites.getSubimage(170, 297, 18, 18);
            sprites[TopLeft][Walk1] = imgSprites.getSubimage(150, 298, 18, 18);
            sprites[TopLeft][Walk2] = imgSprites.getSubimage(127, 298, 18, 18);
            
            sprites[TopRight][Neutral] = imgSprites.getSubimage(326, 297, 18, 18);
            sprites[TopRight][Walk1] = imgSprites.getSubimage(348, 298, 18, 18);
            sprites[TopRight][Walk2] = imgSprites.getSubimage(369, 298, 18, 18);
            
            sprites[DownLeft][Neutral] = imgSprites.getSubimage(234, 297, 18, 18);
            sprites[DownLeft][Walk1] = imgSprites.getSubimage(212, 298, 18, 18);
            sprites[DownLeft][Walk2] = imgSprites.getSubimage(191, 298, 18, 18);
            
            sprites[DownRight][Neutral] = imgSprites.getSubimage(262, 297, 18, 18);
            sprites[DownRight][Walk1] = imgSprites.getSubimage(284, 298, 18, 18);
            sprites[DownRight][Walk2] = imgSprites.getSubimage(305, 298, 18, 18);
            
            this.death.clear();
            this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
			
		}


		@Override
		public void paint(Graphics g, int shiftPX) {

			if(this.sprites == null)
				this.getSprites(g);
			
			ISU.Coord center = EnemyWalking.this.center(); 
			int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
            //A IMPLEMENTER : AFFICHER EN BAS / EN HAUT / DROITE OU GAUCHE DU JOUEUR
            g.drawImage(sprites[DownLeft][index], posX+shiftPX, posY, 
            		(int) (16*Main.windowScale), 
            		(int) (16*Main.windowScale));
            
            if (indChg == 0)
 		    	index++;
 		    if (index > 2)
 		    	index = 1;
 		    indChg++;
 		    if (indChg % 50 == 0) {
 		    	indChg = 0;
 		    }
			
		}
		
	}

}
