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
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import game.Model;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class Boss extends Entity implements Shooter{
	
	private int missileSpeed = 150;
	private double shootCooldown = 0.0; // Temps d'attente restant en secondes
	private double SHOOT_DELAY = 0.2;
	private BossGunBottom bgb;
	private BossGunTop bgt;
	

	// CONSTRUCTOR
	public Boss() {
		super("Boss");
		this.setSize(this.isu.new Dimension(Game.cmPerCell*29.0, Game.cmPerCell*32.0));
		this.avatar = new BossAvatar(this);
		
		
	}
	
	public void createGuns(Model model, View view) {
		ISU is = Game.game().isu;
		double x = this.center().x_cm;
		double y = this.center().y_cm;
		this.bgb = new BossGunBottom("bgb");
		this.bgb.setCoord(is.new Coord(x+10,y+65));
		this.bgb.setBounding();
		model.add(bgb);
		view.add(this.bgb.getAvatar());
		this.bgt = new BossGunTop("bgt");
		this.bgt.setCoord(is.new Coord(x+10,y-65));
		this.bgt.setBounding();
		model.add(bgt);
		view.add(this.bgt.getAvatar());
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {
		
		
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
		Position pos = this.position();
		MissileEnemy mse = new MissileEnemy("mse1");
		mse.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell - 50, pos.y * Game.cmPerCell));
		mse.setBounding();
		mse.setlSpeed(missileSpeed * 2, 0);
		this.model.add(mse);
		this.model.getView().add(mse.getAvatar());
	}
	
	private class BossAvatar extends Avatar{
		private List<BufferedImage> death;
		 private int index;
	     private int indChg;
		
		public BossAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index =0;
			this.indChg = 0;
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
			this.sprites = new BufferedImage[4][3];
			
			//FULL HP
			this.sprites[0][0]= imgSprites.getSubimage(83, 25, 54, 50);
			this.sprites[0][1]= imgSprites.getSubimage(146, 26, 54, 50);
			
			//LOW HP
			this.sprites[1][0] = imgSprites.getSubimage(87, 176, 54, 50);
			this.sprites[1][1] = imgSprites.getSubimage(150, 177, 54, 50);
			
			this.death.add(imgSprites.getSubimage(237, 186, 31, 31));
			this.death.add(imgSprites.getSubimage(279, 186, 22, 24));
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites ==null) {
				this.getSprites(g);
			}
			
            
			ISU.Coord center = Boss.this.center(); 
            int posX = this.toPixel(center.x_cm - (Boss.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (Boss.this.size.y_cm / 2));
            
            g.drawImage(sprites[0][index], posX+shiftPX, posY,
            		(int) (54*Main.windowScale), 
            		(int) (60*Main.windowScale));
            
            if (indChg == 0)
 		    	index++;
 		    if (index > 1)
 		    	index = 0;
 		    indChg++;
 		    if (indChg % 20 == 0) {
 		    	indChg = 0;
 		    }
			
		}
		
	}

	

}
