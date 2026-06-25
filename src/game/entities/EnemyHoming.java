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
import oop.tasks.Runtime;
import oop.tasks.Task;

public class EnemyHoming extends Entity implements Enemy, Shooter {
	
    public static final int spriteNeutral = 0;
    public static final int Sprite45degrees = 1;
    public static final int sprite75degrees = 2;
    public static final int Top = 0;
    public static final int Down = 1;
    
    private Task task;

    public EnemyHoming(String name) {
        super(name);
        this.setSize(this.isu.new Dimension((Game.cmPerCell*16)/Main.windowScale, (Game.cmPerCell*16)/Main.windowScale));
        this.avatar = new HomingAvatar(this);
        this.task = Runtime.task();
    }
    
    @Override
	public void tick(double elapsed, boolean shift) {
		
		
	}


	@Override
	public void tryToShoot() {
		
		
	}


	@Override
	public void shoot() {
		
		
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
		
		shapeSize = isu.new Dimension(x_cm*2, y_cm*2);
		shapeCenter = isu.new Coord(x, y);
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
	}

    
    private class HomingAvatar extends Avatar {
		
        private List<BufferedImage> death;
        
        private static final int spriteSizeX = 15; 
        private static final int spriteSizeY = 15;
	
        private int index;
        private int indChg;
        
        private int deathIndex = 0;
        private boolean exploding = false;
		private boolean visible = true;

        public HomingAvatar(Entity e) {
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
        	BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[1][2];
            
           
            sprites[0][0] = imgSprites.getSubimage(178, 96, 15, 15);
            sprites[0][1] = imgSprites.getSubimage(198, 98, 15, 15); //13
            
            
            
            
            
            this.death.clear();
		    this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
        }

		@Override
		public void paint(Graphics g, int shiftPX) {
			
			if(this.sprites == null)
				this.getSprites(g);
            
            ISU.Coord center = EnemyHoming.this.center(); //para el Gruber
            int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
            int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
            
            if (this.exploding) {
            	g.drawImage(this.death.get(index), posX+shiftPX, posY, (int) (spriteSizeX * Main.windowScale),(int) (spriteSizeY * Main.windowScale));
            }else if (this.visible) {
            	g.drawImage(sprites[0][index], posX+shiftPX, posY, (int)(spriteSizeX * Main.windowScale), (int)(spriteSizeY*Main.windowScale));
            }
            
               
            if (indChg == 0)
 		    	index++;
 		    if (index > 1)
 		    	index = 0;
 		    indChg++;
 		    if (indChg % 100 == 0) {
 		    	indChg = 0;
 		    }
            //Change de position en fonction de la position du joueur
		}
    }


	
}
