package game.entities;

import engine.Entity;
import engine.Grid;
import engine.ISU;
import engine.Grid.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MontFendu extends Entity {

	public MontFendu() {
		super("MontFendu");
		this.setSize(this.game.grid.new Dimension((int)(96/Main.windowScale),(int)(76/Main.windowScale)));
		this.avatar = new MontFenduAvatar();
	}
	
	@Override
	public void setBounding() {
		ISU.Dimension size1 = isu.new Dimension(this.size.x_cm/3, this.size.y_cm/3);
		ISU.Dimension size2 = isu.new Dimension(this.size.x_cm/3, this.size.y_cm/3);
		
		ISU.Coord centre1 = isu.new Coord(this.center().x_cm+10, this.center().y_cm+4);
		ISU.Coord centre2 = isu.new Coord(this.center().x_cm+10, this.center().y_cm+40);
		
		this.bounding().add(new Rect(centre1, size1, 0));
		this.bounding().add(new Rect(centre2, size2, 0));
	}
	
	public class MontFenduAvatar extends Avatar {

		public MontFenduAvatar() {
			super(MontFendu.this);
			this.direction = 0;
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
            int posX = this.toPixel(center.x_cm - (MontFendu.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (MontFendu.this.size.y_cm / 2));
            
            g.drawImage(sprites[0][0], posX-3, posY-3, (int) (96*Main.windowScale), (int) (76*Main.windowScale));
		}
		
	}
}
