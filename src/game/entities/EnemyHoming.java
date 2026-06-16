package game.entities;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.ISU;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class EnemyHoming extends Entity {
	
    public static final int spriteNeutral = 0;
    public static final int Sprite45degrees = 1;
    public static final int sprite75degrees = 2;
    public static final int Top = 0;
    public static final int Down = 1;

    public EnemyHoming(String name) {
        super(name);
    }
	
    private class HomingAvatar extends Avatar {
		
        private List<BufferedImage> death;

        public HomingAvatar(Entity e) {
            super(e);
            this.death = new ArrayList<>();
        }

        @Override
        public void getSprites(Graphics g) {
        	BufferedImage imgSprites = g.load("all_Sprites.png");
            this.sprites = new BufferedImage[4][3];
            
           
            sprites[Top][spriteNeutral] = imgSprites.getSubimage(145, 158, 16, 16);
            sprites[Top][Sprite45degrees] = imgSprites.getSubimage(163, 154, 16, 16);
            sprites[Top][sprite75degrees] = imgSprites.getSubimage(182, 153, 16, 16);
            
            sprites[Down][spriteNeutral] = imgSprites.getSubimage(199, 158, 16, 16);
            sprites[Down][Sprite45degrees] = imgSprites.getSubimage(218, 154, 16, 16);
            sprites[Down][sprite75degrees] = imgSprites.getSubimage(236, 153, 16, 16);
            
            
            this.death.clear();
		    this.death.add(imgSprites.getSubimage(192, 791, 16, 16));
		    this.death.add(imgSprites.getSubimage(209, 796, 8, 16));
        }

        @Override
        public void paint(Graphics g) {
            if (this.sprites == null) {
                this.getSprites(g);
            }
            ISU.Coord center = EnemyHoming.this.center(); //para el Gruber
            int posX = this.toPixel(center.x_cm - (EnemyHoming.this.size.x_cm / 2));
            int posY = this.toPixel(center.y_cm - (EnemyHoming.this.size.y_cm / 2));
            
            if (sprites[Top][spriteNeutral] != null) {
                g.drawImage(sprites[Top][spriteNeutral], posX, posY, 24, 24);
            }
            
            //A IMPLEMENTER : DIFFERENTES ORIENTATION EN FONCTION DE LA POSITION DU JOUEUR PAR RAPPORT A L'ENNEMI
        }
    }
}