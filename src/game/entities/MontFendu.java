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
		this.avatar = new MontFenduAvatar(this);
		this.setCategory(engine.GAL.Category.Obstacle);
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		// TODO Auto-generated method stub
		
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
		
		// Partie du bas du volcan
		shapeSize = isu.new Dimension(x_cm, y_cm-(32*Main.windowScale));
		shapeCenter = isu.new Coord(x, y+(15*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
		
		shapeSize = isu.new Dimension(x_cm-(8*Main.windowScale), y_cm-(34*Main.windowScale));
		shapeCenter = isu.new Coord(x, y+(10*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
		
		shapeSize = isu.new Dimension(x_cm-(15*Main.windowScale), y_cm-(35*Main.windowScale));
		shapeCenter = isu.new Coord(x, y+(6.5*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
		
		// Partie du haut du volcan
		shapeSize = isu.new Dimension(x_cm-(28*Main.windowScale), y_cm-(35*Main.windowScale));
		shapeCenter = isu.new Coord(x+0.5*Main.windowScale, y-(6.25*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
		
		shapeSize = isu.new Dimension(x_cm-(33*Main.windowScale), y_cm-(34*Main.windowScale));
		shapeCenter = isu.new Coord(x+0.5*Main.windowScale, y-(9.65*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
		
		shapeSize = isu.new Dimension(x_cm-(37*Main.windowScale), y_cm-(35*Main.windowScale));
		shapeCenter = isu.new Coord(x, y-(13*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
		
		shapeSize = isu.new Dimension(x_cm-(43*Main.windowScale), y_cm-(34*Main.windowScale));
		shapeCenter = isu.new Coord(x+0.5*Main.windowScale, y-(16.5*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
	}

	
	public class MontFenduAvatar extends Avatar {
		 public static final int spriteSizeX = 96; 
		 public static final int spriteSizeY = 76; 

		public MontFenduAvatar(Entity e) {
			super(e);
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
