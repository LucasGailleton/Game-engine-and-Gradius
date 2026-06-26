package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class PowerUp extends Entity{

	public static final int Speed = 1;
	public static final int Double = 2;
	public static final int Laser = 3;
	public static final int Slave = 4;
	
	private PowerUpAvatar self;

	public PowerUp(String name) {
		super(name);
		this.setSize(this.isu.new Dimension(PowerUpAvatar.spriteSizeX*Main.windowScale, PowerUpAvatar.spriteSizeY*Main.windowScale));
        this.avatar = new PowerUpAvatar(this);
        //this.setCategory(engine.GAL.Category.Util);
	}

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
	public void tick(double elapsed, boolean shift) {
		// elle fait rien
	}

	public class PowerUpAvatar extends Avatar{

		public static final int spriteSizeX = 18;
		public static final int spriteSizeY = 16;
		private int index,indChg;

		public PowerUpAvatar(Entity e) {
			super(e);
			this.index = 0;
			this.indChg = 0;
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("sprites/all_Sprites.png");
            this.sprites = new BufferedImage[1][3];
            this.sprites[0][0] = imgSprites.getSubimage(302, 731, 18, 16);
            this.sprites[0][1] = imgSprites.getSubimage(262, 731, 18, 16);
            this.sprites[0][2] = imgSprites.getSubimage(282, 731, 18, 16);
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
				this.getSprites(g);

            ISU.Coord center = PowerUp.this.center();
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
			int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);

            g.drawImage(sprites[0][index], posX+shiftPX, posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));

            indChg++;
            if (indChg % 10 == 0)
            	index++;
            if (index > 2)
            	index = 0;
		}

	}

	

}
