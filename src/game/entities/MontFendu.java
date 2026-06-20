package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MontFendu extends Entity {

	public MontFendu() {
		super("MontFendu");
		this.setSize(this.game.grid.new Dimension((int)(3.6*Game.cmPerCell*Main.windowScale),(int)(2.8*Game.cmPerCell*Main.windowScale)));
		this.avatar = new MontFenduAvatar();
	}
	
	@Override
	public void setBounding() {
		ISU.Dimension size1 = isu.new Dimension(this.size.x_cm/3, this.size.y_cm/3);
		ISU.Dimension size2 = isu.new Dimension(this.size.x_cm, this.size.y_cm);
		
		ISU.Coord centre1 = isu.new Coord(this.center().x_cm, this.center().y_cm);
		ISU.Coord centre2 = isu.new Coord(this.center().x_cm, this.center().y_cm+Game.cmPerCell*Main.windowScale);
		
		//this.bounding().add(new Circle(centre1, this.size.x_cm/2.0));
		
		this.bounding().add(new Rect(centre1, size1, 0));
		this.bounding().add(new Rect(centre2, size2, 0));
	}
	
	public class MontFenduAvatar extends Avatar {
		 public static final int spriteSizeX = 96; 
		 public static final int spriteSizeY = 76; 

		public MontFenduAvatar() {
			super(MontFendu.this);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage img = g.load("obstacles.png");
			this.sprites = new BufferedImage[1][1];
			
			this.sprites[0][0] = img.getSubimage(237, 54, 96, 76);
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null) {
                this.getSprites(g);
            }
			
			ISU.Coord center = MontFendu.this.center(); 
			int posX = this.toPixel(center.x_cm)-((int)(spriteSizeX*Main.windowScale)/2);
		    int posY = this.toPixel(center.y_cm)-((int)(spriteSizeY*Main.windowScale)/2);
		    
		    g.drawImage(sprites[0][0], posX-3+shiftPX, posY-3, (int) (spriteSizeX*Main.windowScale), (int) (spriteSizeY*Main.windowScale));
		}
		
	}
}
