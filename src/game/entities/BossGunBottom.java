package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.BasicStunt;
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
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.tasks.Runnable;
import oop.tasks.Runtime;
import oop.tasks.Task;

public class BossGunBottom extends Entity implements Enemy, Shooter, Damageable{
	
	private int missileSpeed = 150;
	private double shootCooldown = 0.5; // Temps d'attente restant en secondes
	private double SHOOT_DELAY = 1.0;
	protected int hp = 5;
	
	private Boss master;
	private Task task;
	private boolean isDead =false;
	private Ship target;

	public BossGunBottom(String name, Boss master, Ship target) {
		super(name);
		this.setSize(this.isu.new Dimension(Game.cmPerCell*15.0, Game.cmPerCell*5.0));
		this.avatar = new BossGunBottomAvatar(this);
		this.master= master;
		this.task = Runtime.task();
		setStunt(new BasicStunt(model, this));
		this.target=target;
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		double seconds=elapsed/1000;
		if (!master.isAlive()) {
			die();
		}
		
		if (shift)
			translate(isu.new Vector((-View.speed / 4.0) * Main.windowScale, 0.0));
		if (this.shootCooldown > 0) {
			this.shootCooldown -= seconds;
			tryToShoot();
		}
		
	}

	@Override
	public void tryToShoot() {
		if (this.shootCooldown <= 0.0 && !isDead) {
			this.shoot();
			this.shootCooldown = SHOOT_DELAY;
		}
		
	}

	@Override
	public void shoot() {
		View vew = this.model.getView();
		Position pos = this.position();
		MissileSpecial mss = new MissileSpecial("mssBot",vew,target);
		mss.setCoord(isu.new Coord(pos.x * Game.cmPerCell - 30, pos.y * Game.cmPerCell));
		mss.setBounding();
		mss.setlSpeed(-missileSpeed * 2, 0);
		this.model.add(mss);
		vew.add(mss.getAvatar());
	}
	
	@Override
	public void gotHit() {
		hp--;
		System.out.println("HP : " + hp);
		if (hp <= 0) {
			isDead=true;
			((BossGunBottomAvatar)this.avatar).exploser();
		}
	}
	
	private class BossGunBottomAvatar extends Avatar{
		
		private List<BufferedImage> death;
		private int index,indChg;
		private boolean exploding = false;
		private boolean visible = true;
		private int deathIndex = 0;
		
		
		private static final int spriteSizeX = 26; 
		private static final int spriteSizeY = 8;

		public BossGunBottomAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index = 0;
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
            this.sprites = new BufferedImage[1][2];
            
            sprites[0][0] = imgSprites.getSubimage(280, 82, 26, 8);
            sprites[0][1] = imgSprites.getSubimage(280, 99, 26, 8);
            
            this.death.add(imgSprites.getSubimage(281, 127, 26, 8));
            this.death.add(imgSprites.getSubimage(281, 146, 26, 8));
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if(this.sprites == null)
				this.getSprites(g);
            
            ISU.Coord center = BossGunBottom.this.center(); 
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            if (this.exploding || !this.visible) {
            	g.drawImage(this.death.get(index), posX+shiftPX, posY, (int) (spriteSizeX * Main.windowScale),(int) (spriteSizeY * Main.windowScale));
            }else {
            	g.drawImage(sprites[0][index], posX+shiftPX, posY, (int)(spriteSizeX * Main.windowScale), (int)(spriteSizeY*Main.windowScale));
            }
            
            if (indChg == 0)
 		    	index++;
 		    if (index > 1)
 		    	index = 0;
 		    indChg++;
 		    if (indChg % 10 == 0) {
 		    	indChg = 0;
 		    }
			//A IMPLEMENTER : DEATH
			
		}
		
	}

	

	

}
