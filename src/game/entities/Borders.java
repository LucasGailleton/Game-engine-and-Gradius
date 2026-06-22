package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

//A voir si on fait comme ça parce que ya pas les sprites pour le haut donc faurdrait faire des rotations
// En soit on peut aussi juste faire les sprites en les retournant dans un logiciel
// Mais pourquoi pas tenter de les tourner directemement dans le code
public class Borders extends Entity{

	public Borders(String name) {
		super(name);
		this.setSize(this.isu.new Dimension((Game.cmPerCell*63)/Main.windowScale, (Game.cmPerCell*12)/Main.windowScale));
		this.avatar = new BorderAvatar(this);
	}
	
	public class BorderAvatar extends Avatar{

		public BorderAvatar(Entity e) {
			super(e);
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("obstacles.png");
		    this.sprites = new BufferedImage[3][1];
		    
		    this.sprites[0][0] = imgSprites.getSubimage(5, 117, 63, 12);
		    
		    this.sprites[1][0] = imgSprites.getSubimage(71, 117, 47, 12);
		    
		    this.sprites[2][0] = imgSprites.getSubimage(121, 111, 47, 18);
		    
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			this.getSprites(g);
	           
            ISU.Coord center = Borders.this.center();
            int posX = this.toPixel(center.x_cm - (Borders.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (Borders.this.size.y_cm / 2));
			
            g.drawImage(sprites[0][0], posX, posY, (int)(63 * Main.windowScale), (int)(12*Main.windowScale));
		}
		
	}

}
