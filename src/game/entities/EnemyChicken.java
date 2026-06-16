package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.ISU;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class EnemyChicken extends Entity{
	//enemie qui crée des nouveaux ennemies (assez gros)
	public static final int spriteNeutral = 0;
	
	public static final int Top = 0;
    public static final int Down = 1;

    private ChickenAvatar avatar;
    
	public EnemyChicken(String name) {
		super(name);
		this.setSize(this.game.grid.new Dimension((int)(36/Main.windowScale),(int)(28/Main.windowScale)));
		this.avatar = new ChickenAvatar(this);
		
	}
	
	public void setBounding() {
		this.bounding().add(new Rect(this.center(), this.size, 0));
	}
	
	public ChickenAvatar getAvatar() {
		return this.avatar;
	}
	
	private class ChickenAvatar extends Avatar{
		
		private List<BufferedImage> death;

		public ChickenAvatar(Entity e) {
			super(e);
			this.direction = 0;
			this.death = new ArrayList<>();
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[4][3];
            
            this.sprites[Top][spriteNeutral] = imgSprites.getSubimage(218, 245, 36, 28);
            this.sprites[Down][spriteNeutral] = imgSprites.getSubimage(218, 214, 36, 28);
			//Animation à ajouter peut etre ?
            
			this.death.clear();
		    this.death.add(imgSprites.getSubimage(278, 753, 32, 32));
		    this.death.add(imgSprites.getSubimage(209, 796, 32, 32));
			
		}

		@Override
		public void paint(Graphics g) {
			if (this.sprites == null) {
                this.getSprites(g);
            }
			
			ISU.Coord center = EnemyChicken.this.center(); 
            int posX = this.toPixel(center.x_cm - (EnemyChicken.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (EnemyChicken.this.size.y_cm / 2));
            
            g.drawImage(sprites[Top][spriteNeutral], posX, posY,
            		(int) (36*Main.windowScale), 
            		(int) (28*Main.windowScale));

		}
		
	}

}
