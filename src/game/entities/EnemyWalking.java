package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.ISU;
import engine.Grid.Dimension;
import engine.ISU.Coord;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.tasks.Runnable;
import oop.tasks.Runtime;
import oop.tasks.Task;

public class EnemyWalking extends Entity implements Enemy, Shooter, Damageable {
	//Entité qui spawn avec chicken
	private int hp = 1;
    private boolean isAlive;
	private boolean hit = false;
	private int topOrBottom; //0 pour haut, 2 pour bas (faut pas chercher)
	private final double SPAWN_CHANCE = 0.1;

	public EnemyWalking(View view,String name, int topOrBottom) {
		super(name);
		this.setSize(this.isu.new Dimension(16.0*Main.windowScale,16.0*Main.windowScale));
		this.avatar =new WalkingAvatar(this);
		this.setCategory(engine.GAL.Category.Adversary);
		this.view=view;
		this.isAlive=true;
		this.topOrBottom = topOrBottom;
	}
	
	
	
	@Override
	public void tryToShoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(double elapsed, boolean shift) {
		// TODO Auto-generated method stub
		
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
			isAlive = false;
			
			((WalkingAvatar)this.avatar).exploser();
			Main.task.post(() -> {
				this.die();
			}, 1000);
			return;
		
		}
		
	}
	
	@Override
	public void die() {
		System.out.println("entité despawn : " + name);
        this.alive = false;
        this.model.score.addEnemyKill();
        
        this.spawnPowerUpWithProba();
        
        this.retract();
        model.remove(this);
        model.getView().remove(avatar);
	}

		
	private class WalkingAvatar extends Avatar{
		
		public static final int TopLeft = 0;
		public static final int TopRight = 1;
	    public static final int DownLeft = 2;
	    public static final int DownRight = 3;
	    
	    public static final int Neutral = 0;
	    public static final int Walk1 = 1;
	    public static final int Walk2 = 2;
	    
	    private static final int spriteSizeX = 18; 
        private static final int spriteSizeY = 18;

		private List<BufferedImage> death;
		
		private int index;
		private int indChg;
		
		private int deathIndex = 0;
        private boolean exploding = false;
		private boolean visible = true;
		
		public WalkingAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index =0;
			this.indChg=0;			
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
            this.sprites = new BufferedImage[4][3];
            
            sprites[TopLeft][Neutral] = imgSprites.getSubimage(170, 297, 18, 18);
            sprites[TopLeft][Walk1] = imgSprites.getSubimage(150, 298, 18, 18);
            sprites[TopLeft][Walk2] = imgSprites.getSubimage(127, 298, 18, 18);
            
            sprites[TopRight][Neutral] = imgSprites.getSubimage(326, 297, 18, 18);
            sprites[TopRight][Walk1] = imgSprites.getSubimage(348, 298, 18, 18);
            sprites[TopRight][Walk2] = imgSprites.getSubimage(369, 298, 18, 18);
            
            sprites[DownLeft][Neutral] = imgSprites.getSubimage(234, 297, 18, 18);
            sprites[DownLeft][Walk1] = imgSprites.getSubimage(212, 298, 18, 18);
            sprites[DownLeft][Walk2] = imgSprites.getSubimage(191, 298, 18, 18);
            
            sprites[DownRight][Neutral] = imgSprites.getSubimage(262, 297, 18, 18);
            sprites[DownRight][Walk1] = imgSprites.getSubimage(284, 298, 18, 18);
            sprites[DownRight][Walk2] = imgSprites.getSubimage(305, 298, 18, 18);
            
            this.death.clear();
            this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
			
		}


		@Override
		public void paint(Graphics g, int shiftPX) {

			if(this.sprites == null)
				this.getSprites(g);
			
			ISU.Coord center = EnemyWalking.this.center(); 
			int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
            //A IMPLEMENTER : AFFICHER EN BAS / EN HAUT / DROITE OU GAUCHE DU JOUEUR
            g.drawImage(sprites[topOrBottom][index], posX+shiftPX, posY, 
            		(int) (spriteSizeX*Main.windowScale), 
            		(int) (spriteSizeY*Main.windowScale));
            
            if (indChg == 0)
 		    	index++;
 		    if (index > 2)
 		    	index = 1;
 		    indChg++;
 		    if (indChg % 50 == 0) {
 		    	indChg = 0;
 		    }
			
		}	
	}
}
