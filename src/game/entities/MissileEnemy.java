package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.shapes.Circle;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MissileEnemy extends Entity {
	
	private View view;

	public MissileEnemy(String name, View view) {
		super(name);
		this.setSize(this.isu.new Dimension(1*Main.windowScale, 3*Main.windowScale));
		this.avatar = new MissileEnemyAvatar(this);
		this.view = view;
		
	}
	
	@Override
	public void setBounding() {
		this.bounding().add(new Circle(this.center(),this.size.x_cm/2));
	}
	
	//A modif je pense
	@Override
	public void tick(double elapsed, boolean shift) {
		if (!this.haslSpeed()) {

			die();
			return;
		}
		
		int taille_map = (int) (Game.game().width_ncell * Game.cmPerCell * Game.pixelPerCm);
	    
	    int entityX = (int) (this.center().x_cm * Game.pixelPerCm);
	    
	    int shift1 = View.shiftPX;
	    int screenX = entityX + shift1;
	    int w = view.canvas().getWidth();

	    if (screenX < -300) {
	        shift1 += taille_map;
	    } else if (screenX > w + 300) {
	        shift1 -= taille_map;
	    }

	    int bordX = entityX + shift1;

	    int marge = 50; 
	    if (bordX < -marge || bordX > (w + marge)) {
	        this.die();
	    }
		
	}
	
	private class MissileEnemyAvatar extends Avatar{
		public static final int spriteSizeX = 5;
	    public static final int spriteSizeY = 5;
		public MissileEnemyAvatar(Entity e) {
			super(e);
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("enemies_clearbg.png");
            this.sprites = new BufferedImage[1][2];
            
            this.sprites[0][0] = imgSprites.getSubimage(292, 31, spriteSizeX, spriteSizeY);
            this.sprites[0][1] = imgSprites.getSubimage(301, 31, spriteSizeX, spriteSizeY);
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			
			if (this.sprites==null) {
				this.getSprites(g);
			}
			
            
            ISU.Coord center = MissileEnemy.this.center();
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
			int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
            g.drawImage(sprites[0][0], posX+shiftPX , posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));
			
		}
		
	}

}
