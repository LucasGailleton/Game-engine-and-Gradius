package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.view.View;
import game.entities.Borders;
import game.entities.EnemyKamikaze;
import game.entities.MontFendu;
import game.entities.PowerUp;
import oop.graphics.Canvas;

//classe pour construire la map, placer les obstacles et les entités
//WORK IN PROGRESS

public class MapBuilder {
	private Model model;
	private View view;

	public MapBuilder(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void placeObjects() {
		int nbBorders = 4;
		Borders[] borders = new Borders[nbBorders];
		int canvasHeight = view.canvas().getHeight();
		
		borders[0] = new Borders("n1", 0, 1, view);
		Borders b1 = borders[0];
		b1.setCoord(Game.game().isu.new Coord(canvasHeight*Main.windowScale, 10));
		placeEntity(b1);
		
		borders[1] = new Borders("n2", 0, 0, view);
		Borders b2 = borders[1];
		b2.setCoord(Game.game().isu.new Coord(canvasHeight*Main.windowScale, canvasHeight*1.75*Main.windowScale));
		placeEntity(b2);
		
		EnemyKamikaze kz = new EnemyKamikaze("kz1");	
		kz.setCoord(Game.game().isu.new Coord(20.0*Game.cmPerCell*Main.windowScale, 1.8*Game.cmPerCell*Main.windowScale+100));
		placeEntity(kz);
		
		// Exemple de placement de powerUps
		PowerUp puDouble = new PowerUp("double");
		puDouble.setCoord(Game.game().isu.new Coord(18 * Game.cmPerCell * Main.windowScale, 10 * Game.cmPerCell * Main.windowScale));
		placeEntity(puDouble);

		PowerUp puSpeed = new PowerUp("speed");
		puSpeed.setCoord(Game.game().isu.new Coord(22 * Game.cmPerCell * Main.windowScale, 10 * Game.cmPerCell * Main.windowScale));
		placeEntity(puSpeed);

		PowerUp puSlave = new PowerUp("slave");
		puSlave.setCoord(Game.game().isu.new Coord(26 * Game.cmPerCell * Main.windowScale, 10 * Game.cmPerCell * Main.windowScale));
		placeEntity(puSlave);

		PowerUp puLaser = new PowerUp("laser");
		puLaser.setCoord(Game.game().isu.new Coord(30 * Game.cmPerCell * Main.windowScale, 10 * Game.cmPerCell * Main.windowScale));
		placeEntity(puLaser);
		
	    double screenWidth = view.canvas().getWidth() / Main.windowScale; 
	    System.out.println("width" + screenWidth);
	    double topY = 10.0; 
	    double bottomY = (view.canvas().getHeight() / Main.windowScale) + 180;

	    double currentX = 0.0;
	    double adjustY =0.0;
	    int id = 0;
	    double width = 63.0;
	    for (int i=0;i<10;i++) {
	    	
	    	int spriteType=0;
	        double rand = Math.random();
	        if (rand<0.2) {
	        	spriteType=0;
	        }else if(rand<0.4 && rand>=0.2) {
	        	spriteType=1;
	        }else if(rand<0.6 && rand>=0.4) {
	        	spriteType=2;
	        	adjustY = 12;
	        }else if(rand<0.8 && rand>=0.6) {
	        	spriteType=3;
	        	adjustY = 6;
	        }else if(rand>=0.8){
	        	spriteType=4;
	        }
	        
	        // --- PLACEMENT EN HAUT ---
	        Borders topBorder = new Borders("top_" + id, spriteType, 1, view);
	        
	        topBorder.setCoord(Game.game().isu.new Coord(currentX + (width / 2.0), topY));
	       
	        topBorder.size = Game.game().isu.new Dimension(width, 12); 
	        placeEntity(topBorder);
	        
	        // --- PLACEMENT EN BAS ---
	        Borders botBorder = new Borders("bot_" + id, spriteType, 0,view);
	        botBorder.setCoord(Game.game().isu.new Coord(currentX + (width / 2.0), bottomY-adjustY));
	        botBorder.size = Game.game().isu.new Dimension(width, 12); 
	        placeEntity(botBorder);
	        
	        currentX += width*2;
	        id++;
	        adjustY=0;
	    }

		MontFendu mf = new MontFendu();
		mf.setCoord(Game.game().isu.new Coord(18*Game.cmPerCell*Main.windowScale, 25*Game.cmPerCell*Main.windowScale));
		placeEntity(mf);
	}
	
	public void placeEntity(Entity e) {
		e.setBounding();
		model.add(e);
		view.add(e.getAvatar());
	}
}
