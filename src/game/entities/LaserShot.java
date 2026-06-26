package game.entities;

import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class LaserShot extends MissilePlayer {

	public LaserShot(View view, String name) {
		super(view, name);
		this.setSize(this.isu.new Dimension(15.0*Main.windowScale, 3.0*Main.windowScale));
		this.avatar = new LaserAvatar(this);
	}
	
	@Override
	public void setBounding() {
		double x_cm = this.size.x_cm;
		double y_cm = this.size.y_cm;
		
		ISU.Coord center = this.center();
		double x = center.x_cm;
		double y = center.y_cm;
		
		ISU.Dimension shapeSize;
		ISU.Coord shapeCenter;
		
		shapeSize = isu.new Dimension(x_cm/2, y_cm/2);
		shapeCenter = isu.new Coord(x, y);
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
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
	}

	public class LaserAvatar extends Avatar {
		
		public static final int spriteSizeX = 15;
	    public static final int spriteSizeY = 3;

		public LaserAvatar(Entity e) {
			super(e);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[1][1];
            
            this.sprites[0][0] = imgSprites.getSubimage(315, 802, spriteSizeX, spriteSizeY);
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites==null) {
				this.getSprites(g);
			}
			ISU.Coord center = LaserShot.this.center();
            int posX = this.toPixel(center.x_cm)-((int)(spriteSizeX*Main.windowScale)/2);
            int posY = this.toPixel(center.y_cm)-((int)(spriteSizeY*Main.windowScale)/2);

            g.drawImage(sprites[0][0], posX+shiftPX , posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));
			
		}
	}
}
