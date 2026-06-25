package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.Grid.Position;
import engine.ISU.Coord;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class EnemyChicken extends Entity implements Enemy{
	//enemie qui crée des nouveaux ennemies (assez gros)
	public static final int spriteNeutral = 0;
	
	public static final int Top = 0;
    public static final int Down = 1;
    
    private View view;
    
	public EnemyChicken(View view, String name) {
		super(name);
		this.view = view;
		this.setSize(this.game.grid.new Dimension((int)(36/Main.windowScale),(int)(28/Main.windowScale)));
		this.avatar = new ChickenAvatar(this);
		
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		// TODO Auto-generated method stub
		
	}
	

	public void shoot() {
		//il tire pas lui il fait spawn des EnemyWalking
	}
	
	private class ChickenAvatar extends Avatar{
		
		private List<BufferedImage> death;
		
		 private static final int spriteSizeX = 36; 
		 private static final int spriteSizeY = 28;


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
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
                this.getSprites(g);
            
			ISU.Coord center = EnemyChicken.this.center(); 
			int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
            g.drawImage(sprites[Top][spriteNeutral], posX+shiftPX, posY,
            		(int) (36*Main.windowScale), 
            		(int) (28*Main.windowScale));
			
		}
		
	}

	

}
