package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.Grid.Position;
import engine.ISU;
import engine.ISU.Coord;
import engine.shapes.Circle;
import engine.shapes.Rect;
import game.Main;
import game.Model;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;
import oop.tasks.Task;
import oop.tasks.Runnable;
import oop.tasks.Runtime;

public class Ship extends Entity implements Shooter{

	public static final double ShipSpeed = 5*Main.windowScale;

	public static final int missileSpeed = 50;

	private static boolean hit = false;
	private static int hp = 3;
	private Task task;

	
	public Ship() {
		super("Ship");
		
		this.setSize(game.isu.new Dimension(2.0*Game.cmPerCell*Main.windowScale, 1.0*Game.cmPerCell*Main.windowScale));
		this.avatar = new ShipAvatar();

		this.task = Runtime.task();
	}

	
	@Override
	public void shoot() {
		MissilePlayer msp = new MissilePlayer("missile 1 ");
		Position pos = this.position();
		msp.setCoord(Game.game().isu.new Coord(pos.x*Game.cmPerCell+15,pos.y*Game.cmPerCell));
		msp.setBounding();
		msp.setlSpeed(missileSpeed, 0);
		this.model.add(msp);
		this.model.getView().add(msp.getAvatar());
	}
	
	public boolean getHit() {
		return hit;
	}
	
	public void gotHit() {
		if(hit) {
			return;
		}
		
		hit = true;
		
		if(hp == 0) {
			throw new IllegalStateException("GAME OVER\n");
		}
		
		hp--;
		
		if (!((ShipAvatar)avatar).clignote) {
			((ShipAvatar)avatar).clignoter();
		}
		task.post(new Runnable() {

			@Override
			public void run() throws Exception {
				hit = false;
			}
			
		}
				, 3000);
	}
	
	public class ShipAvatar extends Avatar {
		
		private List<BufferedImage> death;
	   
	    public static final int spriteSizeX = 32; 
	    public static final int spriteSizeY = 16; 
	    public static final int spriteNeutral = 0;
	    public static final int spriteUp = 1;
	    public static final int spriteDown = 2;
	    
	    private boolean visible = true;
	    private boolean clignote = false;
	    
	    public ShipAvatar() {
	        super(Ship.this);
	        Ship.this.avatar = this;
	        this.direction = East;
	        this.death = new ArrayList<>();
	    }

	    public void clignoter() {
	        if (!Ship.this.getHit()) {
	            this.visible = true;
	            this.clignote = false;
	            return;
	        }
	        
	        this.visible = !this.visible;
	        this.clignote = true;
	        
			task.post(new Runnable() {
		        @Override
		        public void run() throws Exception {
		            clignoter();
		        }
		    }, 150);

		}

		@Override
	    public void getSprites(Graphics g) {
	    	BufferedImage imgSprites = g.load("all_Sprites.png");
	        this.sprites = new BufferedImage[4][3];
	        
	      
	        sprites[East][spriteNeutral] = imgSprites.getSubimage(262, 4, 32, 16);
	        sprites[East][spriteUp] = imgSprites.getSubimage(295, 7, 32, 12);
	        sprites[East][spriteDown] = imgSprites.getSubimage(327, 4, 32, 16);
	       
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
		    
		    String vies = "Vies du joueur : " + Ship.hp;
		    
			g.setColor(Colors.green);
		    g.setFont(g.getFont("Monospaced", 0, 16));
		    g.drawString(vies, 40, 150);
		    
		    if(this.visible) {
		    	g.drawImage(sprites[direction][index], posX-3+shiftPX, posY-3, (int) (spriteSizeX*Main.windowScale), (int) (spriteSizeY*Main.windowScale));
		    }
		}


	}
}
