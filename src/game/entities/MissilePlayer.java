package game.entities;

import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MissilePlayer extends Entity {
	
	private View view;

	public MissilePlayer(View view, String name) {
		super(name);
		this.setSize(this.isu.new Dimension(4*Main.windowScale, 3*Main.windowScale));
        this.avatar = new MissilePlayerAvatar(this);
        this.view = view;
        this.setCategory(engine.GAL.Category.Missile);
	}
	
	@Override
	public void setBounding() {
		double x_cm = this.size.x_cm;
		double y_cm = this.size.y_cm;
		
		ISU.Coord center = this.center();
		double x = center.x_cm;
		double y = center.y_cm;
		
		ISU.Dimension shapeSize = isu.new Dimension(x_cm, y_cm/2);
		this.bounding().add(new Rect(this.center(), shapeSize, 0));
	}
	
	public void tick(double seconds, boolean shift) {
		if (!this.haslSpeed()) {
	        this.die();
	        return;
	    }
	    int taille_map = (int) (Game.game().width_ncell * Game.cmPerCell * Game.pixelPerCm);
	    
	    int entityX = (int) (this.center().x_cm * Game.pixelPerCm);
	    
	    int shift1 = view.shiftPX;
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
			if (e instanceof Ship || e instanceof Slave || e instanceof PowerUp || e instanceof MissilePlayer || e instanceof Borders)
				continue;
			
			touche = true;
			System.out.println("Le missile a percuté : " + e.name);
	        
	       
	        e.collision(this);
		 }
		
		if (touche)
			this.die();
	}
		
	private class MissilePlayerAvatar extends Avatar {
		
		public static final int spriteSizeX = 8;
	    public static final int spriteSizeY = 3;

		public MissilePlayerAvatar(Entity e) {
			super(e);			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[1][1];
            
            this.sprites[0][0] = imgSprites.getSubimage(357, 800, 8, 3);
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if(this.sprites == null)
				this.getSprites(g);
            
            ISU.Coord center = MissilePlayer.this.center();
            int posX = this.toPixel(center.x_cm)-((int)(spriteSizeX*Main.windowScale)/2);
            int posY = this.toPixel(center.y_cm)-((int)(spriteSizeY*Main.windowScale)/2);

            g.drawImage(sprites[0][0], posX+shiftPX , posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));
			
		}
		
	}
	
	

}
