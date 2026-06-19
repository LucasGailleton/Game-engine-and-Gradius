// = BOSS =
package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

/**
 * @implNote Le Boss a la forme d'un `t`
 * @implNote il occupe 1 cellule au dessus de son centre, 2 au dessous de son
 *           centre, et 2 cellules à droite de son centre.
 * @implNote Le Boss a donc une dimension (x=3Cell,y=4Cell)
 * @implNote Cette forme a été choisie pour pouvoir tester les rotations.
 */
public class Boss extends Entity {
	
	

	// CONSTRUCTOR
	public Boss() {
		super("Boss");
		this.setSize(this.isu.new Dimension(Game.cmPerCell*29.0, Game.cmPerCell*32.0));
		this.avatar = new BossAvatar(this);
	}
	
	private class BossAvatar extends Avatar{
		private List<BufferedImage> death;
		
		public BossAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
			this.sprites = new BufferedImage[4][3];
			
			//FULL HP
			this.sprites[0][0]= imgSprites.getSubimage(83, 25, 54, 50);
			this.sprites[0][1]= imgSprites.getSubimage(146, 26, 54, 50);
			
			//LOW HP
			this.sprites[1][0] = imgSprites.getSubimage(87, 176, 54, 50);
			this.sprites[1][1] = imgSprites.getSubimage(150, 177, 54, 50);
			
			//this.death.add(imgSprites.getSubimage(South, East, West, North));
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			this.getSprites(g);
            
			ISU.Coord center = Boss.this.center(); 
            int posX = this.toPixel(center.x_cm - (Boss.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (Boss.this.size.y_cm / 2));
            
            g.drawImage(sprites[0][1], posX, posY,
            		(int) (54*Main.windowScale), 
            		(int) (60*Main.windowScale));
			
		}
		
	}

}
