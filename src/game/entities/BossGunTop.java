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
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class BossGunTop extends Entity implements Enemy, Shooter{
	
	private int missileSpeed = 150;
	private double shootCooldown = 0.5; // Temps d'attente restant en secondes
	private double SHOOT_DELAY = 0.2;
	
	private Boss master;

	public BossGunTop(String name, Boss master) {
		super(name);
		this.setSize(this.isu.new Dimension(Game.cmPerCell*15.0, Game.cmPerCell*5.0));
		this.avatar = new BossGunTopAvatar(this);
		this.master = master;
		
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
		if (this.shootCooldown <= 0.0) {
			this.shoot();
			this.shootCooldown = SHOOT_DELAY;
		}
		
	}

	@Override
	public void shoot() {
		View vew = this.model.getView();
		Position pos = this.position();
		MissileSpecial mss = new MissileSpecial("mssTop",vew);
		mss.setCoord(isu.new Coord(pos.x * Game.cmPerCell - 30, pos.y * Game.cmPerCell));
		mss.setBounding();
		mss.setlSpeed(-missileSpeed * 2, 0);
		this.model.add(mss);
		vew.add(mss.getAvatar());
		
	}
	
	private class BossGunTopAvatar extends Avatar{
		
		private List<BufferedImage> death;
		private int index,indChg;
		
		private static final int spriteSizeX = 26; 
		private static final int spriteSizeY = 8;
		
		public BossGunTopAvatar(Entity e) {
			super(e);
			this.death = new ArrayList<>();
			this.index = 0;
			this.indChg = 0;
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
            this.sprites = new BufferedImage[4][3];
            
            sprites[0][0] = imgSprites.getSubimage(243, 81, 26, 8);
            sprites[0][1] = imgSprites.getSubimage(243, 98, 26, 8);
            
            this.death.add(imgSprites.getSubimage(244, 125, 26, 8));
            this.death.add(imgSprites.getSubimage(245, 144, 26, 8));
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if(this.sprites == null)
				this.getSprites(g);
            
            ISU.Coord center = BossGunTop.this.center(); 
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
           
            g.drawImage(sprites[0][index], posX+shiftPX, posY, (int)(spriteSizeX * Main.windowScale), (int)(spriteSizeY*Main.windowScale));
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
