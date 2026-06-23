package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class PowerUp extends Entity{

	public static final int SPEED = 0;
	public static final int DOUBLE = 1;
	public static final int LASER = 2;
	public static final int SLAVE = 3;

	private int type;

	public PowerUp(String name, int type) {
		super(name);
		this.type = type;
		this.setSize(this.isu.new Dimension(18.0*Main.windowScale/Game.pixelPerCm, 16.0*Main.windowScale/Game.pixelPerCm));
        this.avatar = new PowerUpAvatar(this);
	}

	public int type() {
		return this.type;
	}

	public void setBounding() {
		// powerup doit pas etre un obstacle pour le vaisseay
	}

	public class PowerUpAvatar extends Avatar{

		public static final int spriteSizeX = 18;
		public static final int spriteSizeY = 16;
		private int index,indChg;

		public PowerUpAvatar(Entity e) {
			super(e);
			this.index =0;
			this.indChg =0;
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
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
            int posX = this.toPixel(center.x_cm - (PowerUp.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (PowerUp.this.size.y_cm / 2));

            g.drawImage(sprites[0][index], posX+shiftPX, posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));

            indChg++;
            if (indChg % 10 == 0)
            	index++;
            if (index > 2)
            	index = 0;
		}

	}

}
