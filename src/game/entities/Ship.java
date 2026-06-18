package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.shapes.Circle;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class Ship extends Entity {

	public static final double ShipSpeed = 5*Main.windowScale;
	
	public Ship() {
		super("Ship");
		
		this.setSize(game.isu.new Dimension(2.0*Game.cmPerCell*Main.windowScale, 1.0*Game.cmPerCell*Main.windowScale));
		this.avatar = new ShipAvatar();
	}

	public void setBounding() {
		this.bounding().add(new Rect(this.center(), this.size, 0));
	}
	
	public ShipAvatar avatar() {
		return (ShipAvatar) this.avatar;
	}
	
	public class ShipAvatar extends Avatar {
		
		private List<BufferedImage> death;
	    // Les variables ship_up, ship_down, ship_neutral sont gérées dans le tableau sprites[][]
	   
	    public static final int spriteSizeX = 32; 
	    public static final int spriteSizeY = 16; 
	    public static final int spriteNeutral = 0;
	    public static final int spriteUp = 1;
	    public static final int spriteDown = 2;
	    
	    public ShipAvatar() {
	        super(Ship.this);
	        Ship.this.avatar = this;
	        this.direction = East;
	        this.death = new ArrayList<>();
	    }

	    @Override
	    public void getSprites(Graphics g) {
	    	BufferedImage imgSprites = g.load("all_Sprites.png");
	        this.sprites = new BufferedImage[4][3];
	        
	        // Vaisseau orienté Est (Ligne 1, partie droite)
	        sprites[East][spriteNeutral] = imgSprites.getSubimage(262, 4, 32, 16);
	        sprites[East][spriteUp] = imgSprites.getSubimage(295, 7, 32, 12);
	        sprites[East][spriteDown] = imgSprites.getSubimage(327, 4, 32, 16);
	        
	        // Animation de mort (Ligne 2, suite d'explosions)
	        this.death.clear();
	        this.death.add(imgSprites.getSubimage(238, 29, 24, 24));
	        this.death.add(imgSprites.getSubimage(267, 25, 24, 24));
	        this.death.add(imgSprites.getSubimage(298, 21, 24, 24));
	        this.death.add(imgSprites.getSubimage(330, 27, 24, 24));
	    }

	    @Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
				this.getSprites(g);
			
			ISU.Coord center = Ship.this.center();
			int posX = this.toPixel(center.x_cm)-((int)(spriteSizeX*Main.windowScale)/2);
		    int posY = this.toPixel(center.y_cm)-((int)(spriteSizeY*Main.windowScale)/2);
		    
		    int index;
		    ISU.Vector lSpeed = Ship.this.lSpeed();
		    if (lSpeed.targetY_cm == 0)
		    	index = spriteNeutral;
		    else if (lSpeed.targetY_cm > 0)
		    	index = spriteDown;
		    else
		    	index = spriteUp;
		    
		    g.drawImage(sprites[direction][index], posX-3+shiftPX, posY-3, (int) (spriteSizeX*Main.windowScale), (int) (spriteSizeY*Main.windowScale));
		}


	}
}
