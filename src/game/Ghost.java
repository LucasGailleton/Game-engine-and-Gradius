// == GHOST ==
package game;

import engine.Entity;
import engine.Game;
import engine.ISU.Dimension;
import engine.shapes.Circle;
import engine.shapes.Rect;
import game.PacMan.PacManAvatar;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class Ghost extends Entity {
	
	public static final double ghostSpeed = 10*Main.windowScale;
	
	private GhostAvatar self;

	// CONSTRUCTOR
	public Ghost() {
		super("Ghost");
		this.setSize(this.isu.new Dimension((Game.cmPerCell*2)*Main.windowScale, (Game.cmPerCell*2)*Main.windowScale));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Rect(this.isu.new Coord(this.center().x_cm, this.center().y_cm+Game.cmPerCell/8), this.isu.new Dimension((Game.cmPerCell/2.0)*Main.windowScale, (Game.cmPerCell/4.0)*Main.windowScale), this.orientation()));
		this.bounding().add(new Circle(this.center(), (Game.cmPerCell/4.0)*Main.windowScale));
	}
	
	public class GhostAvatar extends Avatar {
		
		public static final int spriteSize = 14;
		
		private int type, index, indChg;

		public GhostAvatar(int type) {
			super(Ghost.this);
			Ghost.this.self = this;
			this.index = 0;
			this.indChg = 0;
			this.type = type;
		}

		@Override
		public void getSprites(Graphics g) {
			this.sprites = new BufferedImage[4][2];
			BufferedImage imgSprites = g.load("sprites.png");
			switch (type) {
			case 0:		// Blinky
				sprites[East][0] = imgSprites.getSubimage(1, 65, spriteSize, spriteSize);
				sprites[East][1] = imgSprites.getSubimage(17, 65, spriteSize, spriteSize);
				sprites[North][0] = imgSprites.getSubimage(65, 65, spriteSize, spriteSize);
				sprites[North][1] = imgSprites.getSubimage(81, 65, spriteSize, spriteSize);
				sprites[West][0] = imgSprites.getSubimage(33, 65, spriteSize, spriteSize);
				sprites[West][1] = imgSprites.getSubimage(49, 65, spriteSize, spriteSize);
				sprites[South][0] = imgSprites.getSubimage(97, 65, spriteSize, spriteSize);
				sprites[South][1] = imgSprites.getSubimage(113, 65, spriteSize, spriteSize);
				break;
			case 1:		// Pinky
				sprites[East][0] = imgSprites.getSubimage(1, 81, spriteSize, spriteSize);
				sprites[East][1] = imgSprites.getSubimage(17, 81, spriteSize, spriteSize);
				sprites[North][0] = imgSprites.getSubimage(65, 81, spriteSize, spriteSize);
				sprites[North][1] = imgSprites.getSubimage(81, 81, spriteSize, spriteSize);
				sprites[West][0] = imgSprites.getSubimage(33, 81, spriteSize, spriteSize);
				sprites[West][1] = imgSprites.getSubimage(49, 81, spriteSize, spriteSize);
				sprites[South][0] = imgSprites.getSubimage(97, 81, spriteSize, spriteSize);
				sprites[South][1] = imgSprites.getSubimage(113, 81, spriteSize, spriteSize);
				break;
			case 2:		// Inky
				sprites[East][0] = imgSprites.getSubimage(1, 97, spriteSize, spriteSize);
				sprites[East][1] = imgSprites.getSubimage(17, 97, spriteSize, spriteSize);
				sprites[North][0] = imgSprites.getSubimage(65, 97, spriteSize, spriteSize);
				sprites[North][1] = imgSprites.getSubimage(81, 97, spriteSize, spriteSize);
				sprites[West][0] = imgSprites.getSubimage(33, 97, spriteSize, spriteSize);
				sprites[West][1] = imgSprites.getSubimage(49, 97, spriteSize, spriteSize);
				sprites[South][0] = imgSprites.getSubimage(97, 97, spriteSize, spriteSize);
				sprites[South][1] = imgSprites.getSubimage(113, 97, spriteSize, spriteSize);
				break;
			default:	// Clyde
				sprites[East][0] = imgSprites.getSubimage(1, 113, spriteSize, spriteSize);
				sprites[East][1] = imgSprites.getSubimage(17, 113, spriteSize, spriteSize);
				sprites[North][0] = imgSprites.getSubimage(65, 113, spriteSize, spriteSize);
				sprites[North][1] = imgSprites.getSubimage(81, 113, spriteSize, spriteSize);
				sprites[West][0] = imgSprites.getSubimage(33, 113, spriteSize, spriteSize);
				sprites[West][1] = imgSprites.getSubimage(49, 113, spriteSize, spriteSize);
				sprites[South][0] = imgSprites.getSubimage(97, 113, spriteSize, spriteSize);
				sprites[South][1] = imgSprites.getSubimage(113, 113, spriteSize, spriteSize);
				break;
			}
		}

		@Override
		public void paint(Graphics g) {
			if (this.sprites == null)
		        getSprites(g);
		        
			if (!Ghost.this.haslSpeed())
				direction = East;
			else {
				if (Ghost.this.lSpeed().targetX_cm > 0)
					direction = East;
				if (Ghost.this.lSpeed().targetX_cm < 0)
			        direction = West;
				if (Ghost.this.lSpeed().targetY_cm > 0)
			        direction = South;
				if (Ghost.this.lSpeed().targetY_cm < 0)
			        direction = North;
			}
		    int posX = this.toPixel(Ghost.this.center().x_cm - (Ghost.this.size.x() / 2.0));
		    int posY = this.toPixel(Ghost.this.center().y_cm - (Ghost.this.size.y() / 2.0));
		    g.drawImage(sprites[direction][index], posX, posY, (int) (spriteSize*Main.windowScale), (int) (spriteSize*Main.windowScale));

		    if (indChg == 0)
		    	index++;
		    if (index > 1)
		    	index = 0;
		    indChg++;
		    if (indChg > 4)
		    	indChg = 0;
		}
		
	}

}
