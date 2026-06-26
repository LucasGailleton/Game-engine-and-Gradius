package game.entities;

import java.util.List;

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
	private int chosen;
	
	public static final int RedMissile = 0;
	public static final int BlueMissile = 1;
	
	public MissileEnemy(String name, View view, int chosen) {
		super(name);
		this.setSize(this.isu.new Dimension(1*Main.windowScale, 3*Main.windowScale));
		this.avatar = new MissileEnemyAvatar(this);
		this.view = view;
		this.setCategory(engine.GAL.Category.Missile);
		this.chosen = chosen;
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
	
	@Override
	public void collision(List<Entity> le) {
		boolean touche = false;
		for (Entity e : le) {

			if (e instanceof Enemy || e instanceof MissileEnemy || e instanceof PowerUp) {
				continue;
			}
			
			touche = true;

			if (e instanceof Ship ship) {
				ship.gotHit();
			}
		}

		if (touche) {
			this.die();
		}
	}
	
	private class MissileEnemyAvatar extends Avatar{
		public static final int spriteSizeX = 5;
	    public static final int spriteSizeY = 5;
	    
	    private int index;
		private int indChg;
		public MissileEnemyAvatar(Entity e) {
			super(e);
			this.index =0;
			this.indChg = 0;
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("sprites/enemies_clearbg.png");
            this.sprites = new BufferedImage[2][2];
            
            this.sprites[0][0] = imgSprites.getSubimage(292, 31, spriteSizeX, spriteSizeY);
            this.sprites[0][1] = imgSprites.getSubimage(301, 31, spriteSizeX, spriteSizeY);
            
            this.sprites[1][0] = imgSprites.getSubimage(310, 31, spriteSizeX, spriteSizeY);
            this.sprites[1][1] = imgSprites.getSubimage(319, 31, spriteSizeX, spriteSizeY);
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			
			if (this.sprites==null) {
				this.getSprites(g);
			}
			
            
            ISU.Coord center = MissileEnemy.this.center();
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
			int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            //Rappel : 0 pour les rouges, 1 pour les bleus
            g.drawImage(sprites[chosen][index], posX+shiftPX , posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));
			
            
            if (indChg == 0)
 		    	index++;
 		    if (index > 1)
 		    	index = 0;
 		    indChg++;
 		    if (indChg % 40 == 0) {
 		    	indChg = 0;
 		    }
		}
		
	}

}
