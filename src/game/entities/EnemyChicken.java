package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.Grid.Position;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.tasks.Runnable;
import oop.tasks.Runtime;
import oop.tasks.Task;

public class EnemyChicken extends Entity implements Enemy, Damageable{
	//enemie qui crée des nouveaux ennemies (assez gros)
	public static final int spriteNeutral = 0;
	
	public static final int Top = 0;
    public static final int Down = 1;
    
    private boolean hit = false;
	protected int hp = 3;
	private boolean isAlive;
	private int orientation;
	private Task task;
	
	public EnemyChicken(View view, String name, int orientation) {
		super(name);
		this.view = view;
		this.setSize(this.isu.new Dimension(36.0*Main.windowScale,28*Main.windowScale));
		this.avatar = new ChickenAvatar(this);
		this.isAlive=true;
		this.task = Runtime.task();
		this.setCategory(engine.GAL.Category.Adversary);
		this.view=view;
		this.orientation = orientation;
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
	
	public View view() {
		return this.view;
	}

	public void shoot() {
		//il tire pas lui il fait spawn des EnemyWalking
	}
	
	//Spawn des Enemy walking  
	public void egg() {
		EnemyKamikaze kamegg = new EnemyKamikaze("KamikazeFromEgg");
		kamegg.setCoord(isu.new Coord(this.center().x_cm-100,this.center().y_cm));
		kamegg.setBounding();
		model.add(kamegg);
		view.add(kamegg.getAvatar());
	}
	
	@Override
	public void gotHit() {
		hp--;
		System.out.println("HP of chicken : " + hp);
		
		if (hp <= 0) {
			isAlive =false;
			this.die();
		}
	}
	
	@Override
	public void die() {
		model.remove(this);
		((ChickenAvatar)this.avatar).exploser();
		System.out.println("entité despawn : " + name);
        this.alive = false;
        this.model.score.addEnemyKill();
        
        this.spawnPowerUpWithProba();
        
	Main.task.post(() -> {
        	this.retract();
            model.getView().remove(avatar);
		}, 1000);
	}
	
	private class ChickenAvatar extends Avatar{
		
		private List<BufferedImage> death;
		
		private static final int spriteSizeX = 36; 
		private static final int spriteSizeY = 28;

		private boolean exploding = false;
		private boolean visible = true;
		private int deathIndex = 0;
		
		private int index;
		private int indChg;
		
		public ChickenAvatar(Entity e) {
			super(e);
			this.direction = 0;
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
			task.post(new Runnable() {
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
            this.sprites = new BufferedImage[2][1];
            
            this.sprites[Top][spriteNeutral] = imgSprites.getSubimage(218, 245, 36, 28);
            this.sprites[Down][spriteNeutral] = imgSprites.getSubimage(218, 214, 36, 28);
			//Animation à ajouter peut etre ?
            
			this.death.clear();
		    this.death.add(imgSprites.getSubimage(278, 753, 32, 32));
		    this.death.add(imgSprites.getSubimage(209, 796, 32, 32));
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
                this.getSprites(g);
            
			ISU.Coord center = EnemyChicken.this.center(); 
			int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
            if (this.exploding) {
            	g.drawImage(this.death.get(index), posX+shiftPX, posY,(int) (32 * Main.windowScale),(int) (32 * Main.windowScale));
            }else if (this.visible){
            	g.drawImage(sprites[orientation][spriteNeutral], posX+shiftPX, posY,
                		(int) (36*Main.windowScale), 
                		(int) (28*Main.windowScale));
            }
            if (indChg == 0)
 		    	index++;
 		    if (index > 1)
 		    	index = 0;
 		    indChg++;
 		    if (indChg % 40 == 0) {
 		    	indChg = 0;
 		    }
			
		}
		
	}

	

}
