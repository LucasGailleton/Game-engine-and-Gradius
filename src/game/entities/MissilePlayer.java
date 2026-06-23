package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MissilePlayer extends Entity{
	
	

	private int age = 0;

	public MissilePlayer(String name) {
		super(name);
		this.setSize(this.isu.new Dimension((Game.cmPerCell*1)/Main.windowScale, (Game.cmPerCell*3)/Main.windowScale));
        this.avatar = new MissilePlayerAvatar(this);
	}
	
	
	public void tick() {
		this.age++;
		//  tir stoppé par une collision > on le supprime
		if (this.lSpeed().targetX_cm == 0 && this.lSpeed().targetY_cm == 0) {
			die();
			return;
		}
		int y = this.position().y;
		// supprime missile dès qu'il sort de la fenetre
		int screenX = (int) (this.center().x_cm * Game.pixelPerCm) + this.getModel().getView().shiftPX();
		int largeurFenetre = (int) (256 * Main.windowScale);
		boolean horsEcran = screenX > largeurFenetre + 100 || screenX < -100;
		// et pour les tirs à 45 degres
		boolean vertical = this.lSpeed().targetY_cm != 0;
		if (horsEcran || this.age > 1000 || (vertical && (y < 10 || y > this.grid.height() - 10))) {
			die();
		}
//		if (collision()) {
//			die();
//		}
	}
	

	
	private class MissilePlayerAvatar extends Avatar{

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
			this.getSprites(g);
            
            ISU.Coord center = MissilePlayer.this.center(); //para el Gruber
            int posX = this.toPixel(center.x_cm - (MissilePlayer.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (MissilePlayer.this.size.y_cm / 2));
            
           
            g.drawImage(sprites[0][0], posX + shiftPX, posY, (int)(6 * Main.windowScale), (int)(3*Main.windowScale));
			
		}
		
	}

}
