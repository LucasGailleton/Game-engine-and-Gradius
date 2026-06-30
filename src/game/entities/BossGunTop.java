package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.Grid.Position;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.tasks.Runnable;

public class BossGunTop extends Entity implements Enemy, Shooter, Damageable{
	
	private int missileSpeed = 150;
	private double shootCooldown = 0.5; // Temps d'attente restant en secondes
	private double SHOOT_DELAY = 1.0;
	
	protected int hp = 5;
	private Boss master;
	private double masterCoordY;
	private boolean isDead =false;
	private Entity target;
	private boolean hit;
	private double offsetX = 10.0;
	private double offsetY = -60.0;
	
	//Classe Pour l'entité slave TOP du BOSS
	//Tire des missiles spéciaux (Voir MissileSpecial)
	//Voir BossGunBottom pour plus de commentaires
	public BossGunTop(View view,String name, Boss master, Entity target) {
		super(name);
		this.setSize(this.isu.new Dimension(26.0*Main.windowScale,15.0*Main.windowScale));
		this.avatar = new BossGunTopAvatar(this);
		this.master = master;
		this.masterCoordY = master.center().y_cm;
		setStunt(new BasicStunt(model, this));
		this.target=target;
		this.setCategory(engine.GAL.Category.Adversary);
		this.view=view;		
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
		shapeCenter = isu.new Coord(x, y-5);
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
    }
	
	
    @Override
	public void tick(double elapsed, boolean shift) {
    	
    	double seconds=elapsed/1000;
    	if (!master.isAlive()) {
			die();
			return;
		}
    	
    	if (this.shootCooldown > 0) {
			this.shootCooldown -= seconds;
			tryToShoot();
		}
    	double newX = master.center().x_cm + this.offsetX;
		double newY = master.center().y_cm + this.offsetY;
		
		this.setCoord(isu.new Coord(newX, newY));
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
		MissileSpecial mss = new MissileSpecial("mssTop",vew,target);
		mss.setCoord(isu.new Coord(pos.x * Game.cmPerCell - 50, pos.y * Game.cmPerCell));
		mss.setBounding();
		mss.setlSpeed(-missileSpeed * 2, 0);
		this.model.add(mss);
		vew.add(mss.getAvatar());
	}
	
	@Override
	public void gotHit() {
		if(!hit)
			hp--;
		System.out.println("HP : " + hp);
		if (hp <= 0) {
			model.score.addSubBossKill();
			isDead= true;
			((BossGunTopAvatar)this.avatar).exploser();
		}
		this.hit = true;
		Main.task.post(() -> {
			this.hit = false;
		}, 100);
	}
	
	private class BossGunTopAvatar extends Avatar{
		
		private List<BufferedImage> death;
		private int index,indChg;
		private boolean exploding = false;
		private boolean visible = true;
		private int deathIndex = 0;
		
		private static final int spriteSizeX = 26; 
		private static final int spriteSizeY = 15;
		
		public BossGunTopAvatar(Entity e) {
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
			BufferedImage imgSprites = g.load("sprites/Boss_Sprites.png");
            this.sprites = new BufferedImage[1][2];
            
            sprites[0][0] = imgSprites.getSubimage(242, 81, 25, 14);
            sprites[0][1] = imgSprites.getSubimage(242, 105, 25, 14);
            
            this.death.add(imgSprites.getSubimage(244, 125, 26, 15));
            this.death.add(imgSprites.getSubimage(245, 144, 26, 15));
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if(this.sprites == null)
				this.getSprites(g);
            
            ISU.Coord center = BossGunTop.this.center(); 
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            if (this.exploding  || !this.visible) {
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
		}	
	}
}
