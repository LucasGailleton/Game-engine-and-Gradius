package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.tasks.Runnable;
import oop.tasks.Task;
import oop.tasks.Runtime;

public class EnemyKamikaze extends Entity implements Enemy, Damageable{
	
	public static final int spriteNeutral = 0;
    public static final int spriteUp = 1;
    public static final int spriteDown = 2;
    
    private int hp = 1;
    private boolean isAlive;
	private boolean hit = false;    
   
	public EnemyKamikaze(String name) {
		super(name);
		
		this.setSize(this.isu.new Dimension(14*Main.windowScale, 14*Main.windowScale));
		this.avatar = new KamikazeAvatar(this);
		this.setCategory(engine.GAL.Category.Adversary);
		this.isAlive=true;	
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		
	}
	
	@Override
	public void setBounding() {
		double x_cm = this.size.x_cm;
		double y_cm = this.size.y_cm;
		
		ISU.Coord center = this.center();
		double x = center.x_cm;
		double y = center.y_cm;
		
		ISU.Dimension shapeSize;
		ISU.Coord shapeCenter;
		
		shapeSize = isu.new Dimension(x_cm/2, y_cm/2);
		shapeCenter = isu.new Coord(x, y);
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
	}
	
	@Override
	public void gotHit() {
		hp--;
		System.out.println("HP : " + hp);
		
		if (hp <= 0) {
			this.die();
		}
	}
	
	@Override
	public void die() {
		model.remove(this);
		((KamikazeAvatar)this.avatar).exploser();
		System.out.println("entité despawn : " + name);
        this.alive = false;
        this.model.score.addEnemyKill();
        
        this.spawnPowerUpWithProba();
        
        Main.task.post(() -> {
        	this.retract();
            model.getView().remove(avatar);
		}, 1000);
	}
	
	
	
	private class KamikazeAvatar extends Avatar {
		
		private List<BufferedImage> death;
		private static final int spriteSizeX = 18; 
        private static final int spriteSizeY = 18;
        
	        private int index;
	        private int indChg;
	        
	        private int deathIndex = 0;
	        private boolean exploding = false;
			private boolean visible = true;


		public KamikazeAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index =0;
			this.indChg = 0;
		}
		
		public void exploser() {
			this.exploding = true;
			this.visible = true;
			this.deathIndex = 0;
			avancerExplosion();
		}

		private void avancerExplosion() {
			if (this.deathIndex >= this.death.size()) {
				this.exploding = false;
				this.visible = false;
		
				return;
			}
			Main.task.post(new Runnable() {
				@Override
				public void run() throws Exception {
					deathIndex++;
					avancerExplosion();
				}
			}, 120);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
		    this.sprites = new BufferedImage[1][6];
		    
		   
		    sprites[0][0] = imgSprites.getSubimage(262, 194, 18, 18);
		    sprites[0][1] = imgSprites.getSubimage(282, 197, 18, 16);
		    sprites[0][2] = imgSprites.getSubimage(323, 197, 18, 16);
		    sprites[0][3] = imgSprites.getSubimage(303, 194, 18, 18);
		    sprites[0][4] = imgSprites.getSubimage(282, 197, 18, 16);
		    sprites[0][5] = imgSprites.getSubimage(323, 197, 18, 16);
		    
		    
		    
		    this.death.clear();
		    this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
		    this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
		    this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
	        }


			@Override
			public void paint(Graphics g, int shiftPX) {
				
				if(this.sprites == null)
					this.getSprites(g);
	           
	            ISU.Coord center = EnemyKamikaze.this.center();
	            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
	            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
	            
	            if (this.exploding) {
	            	g.drawImage(this.death.get(index), posX+shiftPX, posY, (int) (16 * Main.windowScale),(int) (16 * Main.windowScale));
	            }else if(this.visible) {
	            	g.drawImage(sprites[0][index], posX + shiftPX, posY, (int)(spriteSizeX * Main.windowScale), (int)(spriteSizeY*Main.windowScale));
	            }
	            
	            if (indChg == 0)
	 		    	index++;
	 		    if (index > 5)
	 		    	index = 0;
	 		    indChg++;
	 		    if (indChg % 40 == 0) {
	 		    	indChg = 0;
	 		    }
				
			}	
	    }


	


}
