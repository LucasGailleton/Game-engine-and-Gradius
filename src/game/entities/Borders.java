package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

//A voir si on fait comme ça parce que ya pas les sprites pour le haut donc faurdrait faire des rotations
// En soit on peut aussi juste faire les sprites en les retournant dans un logiciel
// Mais pourquoi pas tenter de les tourner directemement dans le code

public class Borders extends Entity{
	protected int chosenSprite; 
	protected int chosenPos;
	private View view;
	public Borders(String name, int chosenSprite, int chosenPos, View view) {
		super(name);
		this.setSize(this.isu.new Dimension((Game.cmPerCell*63)/Main.windowScale + 69, (Game.cmPerCell*12)/Main.windowScale + 14));
		this.chosenSprite = chosenSprite;
		this.chosenPos = chosenPos;
		this.avatar = new BorderAvatar(this);
		this.view = view;		
	}
	
	@Override
	public void setBounding() {
		//pas de boundingbox
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		if (shift) {
	        translate(isu.new Vector((View.speed / 4.0) * Main.windowScale, 0.0));
	    }

	    // On calcule la position logique du bord GAUCHE de la caméra dans le monde.
	    // (J'utilise votre facteur 0.5 exactement comme dans votre missile)
	    double leftScreenEdge = -(view.shiftPX * 0.5);

	    // On vérifie si la bordure est passée à gauche de cette ligne
	    // (On soustrait aussi la moitié de sa taille pour qu'elle disparaisse sans effet de coupure "popping")
	    if (this.center().x_cm <= leftScreenEdge - (this.size.x_cm / 2.0)) {
	        this.die();
	    }
	}
	
	public class BorderAvatar extends Avatar{
		
		private static final int spriteSizeX = 63;
		private static final int spriteSizeY = 12;
		private final static int Top = 1;
		private final static int Bottom = 0;

		public BorderAvatar(Entity e) {
			super(e);
		}
		

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("obstacles.png");
		    this.sprites = new BufferedImage[5][2];
		    
		    this.sprites[0][Bottom] = imgSprites.getSubimage(5, 117, 63, 12);
		    this.sprites[0][Top] = imgSprites.getSubimage(80, 46, 63, 12);
		    
		    this.sprites[1][Bottom] = imgSprites.getSubimage(6, 157, 63, 12);
		    this.sprites[1][Top] = imgSprites.getSubimage(6, 177, 63, 12);
		    
		    this.sprites[2][Bottom] = imgSprites.getSubimage(74, 150, 63, 18); //+6
		    this.sprites[2][Top] = imgSprites.getSubimage(74, 177, 63, 18);
		    
		    this.sprites[3][Bottom] = imgSprites.getSubimage(142, 153, 63, 15); //+3
		    this.sprites[3][Top] = imgSprites.getSubimage(142, 177, 63, 15);
		    
		    this.sprites[4][Bottom] = imgSprites.getSubimage(213, 155, 63, 12);
		    this.sprites[4][Top] = imgSprites.getSubimage(213, 177, 63, 12);
		    
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (sprites == null)
				this.getSprites(g);
	           
            ISU.Coord center = Borders.this.center();
            int posX = this.toPixel(center.x_cm - (Borders.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (Borders.this.size.y_cm / 2));
            BufferedImage currentImg = sprites[chosenSprite][chosenPos];
            if(chosenSprite == 2) {
            	g.drawImage(currentImg, posX+shiftPX, posY, (int)(spriteSizeX* Main.windowScale), (int)(18*Main.windowScale));
            }else if (chosenSprite ==3) {
            	g.drawImage(currentImg, posX+shiftPX, posY, (int)(spriteSizeX* Main.windowScale), (int)(15*Main.windowScale));
            }else {
            	g.drawImage(currentImg, posX+shiftPX, posY, (int)(spriteSizeX * Main.windowScale), (int)(spriteSizeY*Main.windowScale));
            }
            
            
            
		}
		
	}

}
