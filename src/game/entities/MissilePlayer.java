package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class MissilePlayer extends Entity{
	
	private View view;
	private int age = 0;

	public MissilePlayer(View view, String name) {
		super(name);
		this.setSize(this.isu.new Dimension((Game.cmPerCell*1)/Main.windowScale, (Game.cmPerCell*3)/Main.windowScale));
        this.avatar = new MissilePlayerAvatar(this);
        this.view = view;
	}
	
	@Override
	public void setBounding() {
		double x_cm = this.size.x_cm;
		double y_cm = this.size.y_cm;
		
		ISU.Coord center = this.center();
		double x = center.x_cm;
		double y = center.y_cm;
		
		ISU.Dimension shapeSize = isu.new Dimension(x_cm, y_cm-(32*Main.windowScale));
		ISU.Coord shapeCenter = isu.new Coord(x, y+(15*Main.windowScale));
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
	}
	
	public void tick(double seconds, boolean shift) {
		if (!this.haslSpeed() || this.center().x_cm >= (Main.windowWidth*Game.pixelPerCm)-(view.shiftPX*0.5)) {
			die();
			return;
		}
	}
		
	private class MissilePlayerAvatar extends Avatar{
		
		public static final int spriteSizeX = 8;
	    public static final int spriteSizeY = 3;

		public MissilePlayerAvatar(Entity e) {
			super(e);			
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[1][1];
            
            this.sprites[0][0] = imgSprites.getSubimage(357, 800, 8, 3);
			
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			this.getSprites(g);
            
            ISU.Coord center = MissilePlayer.this.center();
            int posX = this.toPixel(center.x_cm)-((int)(spriteSizeX*Main.windowScale)/2);
            int posY = this.toPixel(center.y_cm)-((int)(spriteSizeY*Main.windowScale)/2);

            g.drawImage(sprites[0][0], posX+shiftPX , posY, (int)(spriteSizeX*Main.windowScale), (int)(spriteSizeY*Main.windowScale));
			
		}
		
	}

}
