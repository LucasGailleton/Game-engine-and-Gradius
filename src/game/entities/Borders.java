package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

//A voir si on fait comme ça parce que ya pas les sprites pour le haut donc faurdrait faire des rotations
// En soit on peut aussi juste faire les sprites en les retournant dans un logiciel
// Mais pourquoi pas tenter de les tourner directemement dans le code
public class Borders extends Entity{
	protected int chosenSprite; //compris entre 0 et 1 pour l'instant
	protected int chosenPos; //entre 0 et 1
	public Borders(String name, int chosenSprite, int chosenPos) {
		super(name);
		this.setSize(this.isu.new Dimension((Game.cmPerCell*63)/Main.windowScale + 69, (Game.cmPerCell*12)/Main.windowScale + 14));
		this.chosenSprite = chosenSprite;
		this.chosenPos = chosenPos;
		this.avatar = new BorderAvatar(this);
		//this.setlSpeed(10, 0);
	}
	
	public class BorderAvatar extends Avatar{
		
		private final static int Top = 1;
		private final static int Bottom =0;

		public BorderAvatar(Entity e) {
			super(e);
		}
		

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("obstacles.png");
		    this.sprites = new BufferedImage[3][2];
		    
		    this.sprites[0][Bottom] = imgSprites.getSubimage(5, 117, 63, 12);
		    this.sprites[0][Top] = imgSprites.getSubimage(80, 46, 63, 12);
		    
		    this.sprites[1][Bottom] = imgSprites.getSubimage(71, 117, 47, 12);
		    this.sprites[1][Top] = imgSprites.getSubimage(146, 46, 47, 12);
		    
		    //this.sprites[2][0] = imgSprites.getSubimage(121, 111, 47, 18);
		    
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			this.getSprites(g);
	           
            ISU.Coord center = Borders.this.center();
            int posX = this.toPixel(center.x_cm - (Borders.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (Borders.this.size.y_cm / 2));
			
            g.drawImage(sprites[chosenSprite][chosenPos], posX+shiftPX, posY, (int)(63 * Main.windowScale), (int)(12*Main.windowScale));
            
		}
		
	}

}
