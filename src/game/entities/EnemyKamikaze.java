package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.ISU;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class EnemyKamikaze extends Entity{
	
	public static final int spriteNeutral = 0;
    public static final int spriteUp = 1;
    public static final int spriteDown = 2;
    
    private KamikazeAvatar avatar;

	public EnemyKamikaze(String name) {
		super(name);
		this.avatar = new KamikazeAvatar(this);
		
	}
	
	public KamikazeAvatar getAvatar() {
		return this.avatar;
	}
	
	private class KamikazeAvatar extends Avatar{
		
		private List<BufferedImage> death;
		private final int spriteSizeX = 18; 
        private final int spriteSizeY = 18;

		public KamikazeAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
		    this.sprites = new BufferedImage[1][4];
		    
		   
		    sprites[0][0] = imgSprites.getSubimage(262, 194, 18, 18);
		    sprites[0][1] = imgSprites.getSubimage(285, 197, 18, 18);
		    sprites[0][2] = imgSprites.getSubimage(303, 194, 18, 18);
		    sprites[0][3] = imgSprites.getSubimage(323, 197, 18, 18);
		    
		    
		    this.death.clear();
		    this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
	        }

	        @Override
	        public void paint(Graphics g) {
	            if (this.sprites == null) {
	                this.getSprites(g);
	            }
	            
	            // Logique d'affichage temporaire (à lier avec la physique plus tard)
	            // Utilisation des méthodes supposées héritées de Entity/Avatar
	            ISU.Coord center = EnemyKamikaze.this.center();
	            int posX = this.toPixel(center.x_cm - (EnemyKamikaze.this.size.x_cm / 2));
	            int posY = this.toPixel(center.y_cm - (EnemyKamikaze.this.size.y_cm / 2));
	            
	            
	            if (sprites[0][spriteNeutral] != null) {
	                g.drawImage(sprites[0][spriteNeutral], posX, posY, spriteSizeX, spriteSizeY);
	            }
	        }	
	    }


}
