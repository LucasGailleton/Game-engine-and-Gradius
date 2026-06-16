package game.entities;

import java.util.List;

import engine.Entity;
import engine.ISU;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class EnemyWalking extends Entity{
	//Entité qui spawn avec chicken
	private WalkingAvatar avatar;
	

	public EnemyWalking(String name) {
		super(name);
		this.avatar =new WalkingAvatar(this);
	}
	
	public WalkingAvatar getAvatar() {
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

		private List<BufferedImage> death;
		
		public WalkingAvatar(Entity e) {
			super(e);
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[4][3];
            
            sprites[TopLeft][Neutral] = imgSprites.getSubimage(170, 297, 16, 16);
            sprites[TopLeft][Walk1] = imgSprites.getSubimage(150, 298, 16, 16);
            sprites[TopLeft][Walk2] = imgSprites.getSubimage(127, 298, 16, 16);
            
            sprites[TopRight][Neutral] = imgSprites.getSubimage(326, 297, 16, 16);
            sprites[TopRight][Walk1] = imgSprites.getSubimage(348, 298, 16, 16);
            sprites[TopRight][Walk2] = imgSprites.getSubimage(369, 298, 16, 16);
            
            sprites[DownLeft][Neutral] = imgSprites.getSubimage(234, 297, 16, 16);
            sprites[DownLeft][Walk1] = imgSprites.getSubimage(212, 298, 16, 16);
            sprites[DownLeft][Walk2] = imgSprites.getSubimage(191, 298, 16, 16);
            
            sprites[DownRight][Neutral] = imgSprites.getSubimage(262, 297, 16, 16);
            sprites[DownRight][Walk1] = imgSprites.getSubimage(284, 298, 16, 16);
            sprites[DownRight][Walk2] = imgSprites.getSubimage(305, 298, 16, 16);
            
            this.death.clear();
            this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
			
		}

		@Override
		public void paint(Graphics g) {
			this.getSprites(g);
			
			ISU.Coord center = EnemyWalking.this.center(); 
            int posX = this.toPixel(center.x_cm - (EnemyWalking.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (EnemyWalking.this.size.y_cm / 2));
            
            //A IMPLEMENTER : AFFICHER EN BAS / EN HAUT / DROITE OU GAUCHE DU JOUEUR
            g.drawImage(sprites[TopLeft][Neutral], posX, posY, 16, 16);
		}
		
	}

}
