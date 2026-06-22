package game.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.Grid.Position;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Vector;
import engine.shapes.Circle;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import game.Model;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;
import oop.tasks.Task;
import oop.utils.SoundPlayer;
import oop.tasks.Runnable;
import oop.tasks.Runtime;

public class Ship extends Entity implements Shooter{

	public static final double ShipSpeed = 5*Main.windowScale;

	public static final int missileSpeed = 150;
	
	private double shootCooldown = 0.0; // Temps d'attente restant en secondes
    private final double SHOOT_DELAY = 0.2; //DELAI (on peut modifier)

	private static boolean hit = false;
	private static int hp = 3;
	private Task task;
	
	private SoundPlayer tir1,tir2,degat,mort;
	private int indexAudio;

	
	public Ship() {
		super("Ship");
		
		this.setSize(game.isu.new Dimension(2.0*Game.cmPerCell*Main.windowScale, 1.0*Game.cmPerCell*Main.windowScale));
		this.avatar = new ShipAvatar();

		this.task = Runtime.task();
		
		try {
			this.tir1 = new SoundPlayer(new File("Audio/Piou1.wav"));
			this.tir2 = new SoundPlayer(new File("Audio/Piou2.wav"));
			this.degat = new SoundPlayer(new File("Audio/Aie.wav"));
			this.mort = new SoundPlayer(new File("Audio/PlayerBoom1.wav"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void tick(double seconds, boolean shift) {
		
	    double angularSpeed = aSpeed();
	    if (angularSpeed != 0) {
	        turn((int) (angularSpeed * seconds));
	        
	        
	        List<Entity> collisions = this.model.getCollision(this); 
	        
	        if (!collisions.isEmpty()) {
	            turn((int) -(angularSpeed * seconds));
	            setaSpeed(0);
	            collision(collisions);
	        }
	    }
	    
	    
	    if (shift) {
	        translate(isu.new Vector((-View.speed / 4.0) * Main.windowScale, 0.0));				
	    }
	    
	    if (this.shootCooldown > 0) {
            this.shootCooldown -= seconds;
        }
	}
	
	@Override
	public void tryToShoot() {
        
        if (this.shootCooldown <= 0.0) {
            this.shoot(); 
            this.shootCooldown = SHOOT_DELAY;
        }
    }

	
	
	private void shoot() {
		MissilePlayer msp = new MissilePlayer("missile 1 ");
		Position pos = this.position();
		msp.setCoord(Game.game().isu.new Coord(pos.x*Game.cmPerCell+20,pos.y*Game.cmPerCell));
		msp.setBounding();
		msp.setlSpeed(missileSpeed, 0);
		this.model.add(msp);
		this.model.getView().add(msp.getAvatar());
		
		if (indexAudio%2==0) {
			tir1.play(1);
			
		}else {
			tir2.play(1);
		}
		indexAudio++;
	
		
	}
	
	public boolean getHit() {
		return hit;
	}
	
	public void gotHit()  {
		if(hit) {
			return;
		}
		
		hit = true;
		
		
		this.degat.play(1);
		
		
		if(hp == 0) {
			this.mort.play(1);
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
