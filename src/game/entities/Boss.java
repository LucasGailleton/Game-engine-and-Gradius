// = BOSS =
package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.Grid.Position;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.ISU.Vector;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import game.Model;
import game.entities.Ship.ShipAvatar;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.tasks.Runnable;
import oop.tasks.Runtime;
import oop.tasks.Task;
import engine.BasicStunt;

public class Boss extends Entity implements Enemy, Shooter, Damageable {
	
	private int missileSpeed = 15;
	private double shootCooldown = 0.5; // Temps d'attente restant en secondes
	private double SHOOT_DELAY = 1.5;
	
	private Ship target ;
	private BossGunBottom bgb;
	private BossGunTop bgt;
	
	
	private boolean hit = false;
	protected int hp = 10;
	private boolean isAlive;
	
	private Task task;
	

	// CONSTRUCTOR
	public Boss() {
		super("Boss");
		this.setSize(this.isu.new Dimension(29.0*Main.windowScale,32.0*Main.windowScale));
		this.avatar = new BossAvatar(this);
		this.isAlive=true;
		this.task = Runtime.task();
		setStunt(new BasicStunt(model, this));
		
	}
	
	public void createGuns(Model model, View view) {
		ISU is = Game.game().isu;
		this.target = model.getPlayer();
		double x = this.center().x_cm;
		double y = this.center().y_cm;
		this.bgb = new BossGunBottom("bgb",this,target);
		this.bgb.setCoord(is.new Coord(x+10,y+60));
		this.bgb.setBounding();
		model.add(bgb);
		view.add(this.bgb.getAvatar());
		this.bgt = new BossGunTop("bgt",this,target);
		this.bgt.setCoord(is.new Coord(x+10,y-60));
		this.bgt.setBounding();
		model.add(bgt);
		view.add(this.bgt.getAvatar());
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
		
		shapeSize = isu.new Dimension(x_cm-12*Main.windowScale, y_cm-7*Main.windowScale);
		shapeCenter = isu.new Coord(x+5*Main.windowScale, y);
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
		
		shapeSize = isu.new Dimension(x_cm-17*Main.windowScale, y_cm-14*Main.windowScale);
		shapeCenter = isu.new Coord(x-8*Main.windowScale, y);
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));

	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		double seconds = elapsed/1000;
		
		if (shift)
			translate(isu.new Vector((-View.speed / 2.0) * Main.windowScale, 0.0));
		
		if (this.shootCooldown > 0) {
			this.shootCooldown -= seconds;
			
		}
		tryToShoot();
		
		
	}

	@Override
	public void tryToShoot() {
		if (this.shootCooldown <= 0.0) {
			 this.shoot();
			this.shootCooldown = SHOOT_DELAY;
		}
		
	}

	@Override
	public void shoot() {
		View view = this.model.getView();
		Position pos = this.position();
		MissileEnemy mse = new MissileEnemy("mse1",view);
		mse.setCoord(isu.new Coord(pos.x * Game.cmPerCell - 50, pos.y * Game.cmPerCell+20));
		mse.setBounding();
		mse.setlSpeed(-missileSpeed * 2, 0);
		this.model.add(mse);
		view.add(mse.getAvatar());
		
		MissileEnemy mse2 = new MissileEnemy("mse2",view);
		mse2.setCoord(isu.new Coord(pos.x * Game.cmPerCell - 50, pos.y * Game.cmPerCell-20));
		mse2.setBounding();
		mse2.setlSpeed(-missileSpeed * 2, 0);
		this.model.add(mse2);
		view.add(mse2.getAvatar());
		
		
	}
	
	@Override
	public void gotHit() {
		if (hit || Main.GAMEOVER)
			return;
		hp--;
		System.out.println("HP : " + hp);

		if (hp <= 0) {
			isAlive =false;
			// game over : explosion + le boss disparait
			//this.mort.play(1);
//			Main.GAMEOVER = true;
			
			((BossAvatar)this.avatar).exploser();
			task.post(() -> {
				this.die();
			}, 2000);
			return;
		}

		//this.degat.play(1);
		//pas de hit = true, le vaisseau n'a pas d'invicibilité

	}
	
	private class BossAvatar extends Avatar{
		private List<BufferedImage> death;
		private int index;
		private int indChg;
		private boolean exploding = false;
		private boolean visible = true;
		private int deathIndex = 0;
		
		private static final int spriteSizeX = 54; 
		private static final int spriteSizeY = 50;
		
		public BossAvatar(Entity e) {
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
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
			this.sprites = new BufferedImage[4][3];
			
			//FULL HP
			this.sprites[0][0]= imgSprites.getSubimage(83, 25, 54, spriteSizeY);
			this.sprites[0][1]= imgSprites.getSubimage(146, 26, 54, spriteSizeY);
			
			//LOW HP
			this.sprites[1][0] = imgSprites.getSubimage(87, 176, 54, spriteSizeY);
			this.sprites[1][1] = imgSprites.getSubimage(150, 177, 54, spriteSizeY);
			
			for (int i=0;i<10;i++) {
				deathAnimation(imgSprites);
			}
		}
		
		private void deathAnimation(BufferedImage imgSprites) {
			this.death.add(imgSprites.getSubimage(237, 186, 31, 31));
			this.death.add(imgSprites.getSubimage(279, 186, 22, 24));
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites ==null) {
				this.getSprites(g);
			}
			
			ISU.Coord center = Boss.this.center(); 
            int posX = this.toPixel(center.x_cm) - ((int)(spriteSizeX*Main.windowScale)/ 2);
            int posY = this.toPixel(center.y_cm) - ((int)(spriteSizeY*Main.windowScale)/ 2);
            if (this.exploding) {
            	g.drawImage(this.death.get(index), posX+shiftPX, posY, (int) (spriteSizeX * Main.windowScale),(int) (spriteSizeY * Main.windowScale));
            }else if(hp < 5 && hp >= 0) {
            	g.drawImage(sprites[1][index], posX+shiftPX, posY,
                 		(int) (spriteSizeX*Main.windowScale), 
                 		(int) (spriteSizeY*Main.windowScale));
            }
            else if (this.visible){
            	 g.drawImage(sprites[0][index], posX+shiftPX, posY,
                 		(int) (spriteSizeX*Main.windowScale), 
                 		(int) (spriteSizeY*Main.windowScale));
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
