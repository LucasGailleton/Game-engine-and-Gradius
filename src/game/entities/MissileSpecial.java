package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MissileSpecial extends Entity{
	
	private View view;
	private int shift;
	private Entity target;
	
	private double explosionRadius = 10.0; // Distance pour exploser
	private double attractionRadius = 50.0; // Zone d'effet de l'attraction
	private double pullForce = 100.0; // Force d'attraction

	public MissileSpecial(String name, View view, Entity target) {
		super(name);
		this.setSize(this.isu.new Dimension(9*Main.windowScale,5*Main.windowScale));
		this.avatar =new MissileSpecialAvatar(this);
		this.view=view;
		this.target = target;
		
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
	public void tick(double elapsed, boolean shift) {
		double seconds = elapsed / 1000.0;
		if (!this.haslSpeed()) {

			die();
			return;
		}
		
		//CHERCHER LE PLAYER
		//Distance entre missile et player
		double dx = this.center().x_cm - target.center().x_cm;
		double dy = this.center().y_cm - target.center().y_cm;
		double distance = Math.sqrt(dx * dx + dy * dy);
		if (target instanceof Ship) {
			if (distance <= explosionRadius) {
				((Ship)target).gotHit(); 
				this.die();
				return;
			}
			else if (distance <= attractionRadius) {
				
				double pullX = (dx / distance) * pullForce * seconds;
				double pullY = (dy / distance) * pullForce * seconds;
				
				target.translate(target.isu.new Vector(pullX, pullY));
				
			}
		}
		
		if (distance <= explosionRadius) {
			target.gotHit();
			this.die();
			return;
		} else if (distance <= attractionRadius) {

			double pullX = (dx / distance) * pullForce * seconds;
			double pullY = (dy / distance) * pullForce * seconds;

			target.translate(target.isu.new Vector(pullX, pullY));

		}
		
		int taille_map = (int) (Game.game().width_ncell * Game.cmPerCell * Game.pixelPerCm);
		int entityX = (int) (this.center().x_cm * Game.pixelPerCm);

		int shift1 = view.shiftPX;
		int screenX = entityX + shift1;
		int w = view.canvas().getWidth();

		if (screenX < -300) {
			shift1 += taille_map;
		} else if (screenX > w + 300) {
			shift1 -= taille_map;
		}

		int bordX = entityX + shift1;

		int marge = 50;
		if (bordX < -marge || bordX > (w + marge)) {
			this.die();
		}

	}
	
	private class MissileSpecialAvatar extends Avatar{
		public static final int spriteSizeX = 9;
	    public static final int spriteSizeY = 5;
		public MissileSpecialAvatar(Entity e) {
			super(e);
			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("Boss_Sprites.png");
            this.sprites = new BufferedImage[1][2];
            
            this.sprites[0][0] = imgSprites.getSubimage(264, 165, spriteSizeX, spriteSizeY);
            
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null) {
				this.getSprites(g);
			}

			ISU.Coord center = MissileSpecial.this.center();
			int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
			int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);

			g.drawImage(sprites[0][0], posX + shiftPX, posY, (int) (spriteSizeX * Main.windowScale), (int) (spriteSizeY * Main.windowScale));
			// g.drawOval(topX+shiftPX, topY, 100, 100);
		}
		
	}

}
