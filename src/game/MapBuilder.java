package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.view.View;
import game.entities.Borders;
import game.entities.Boss;
import game.entities.EnemyKamikaze;
import game.entities.MontFendu;
import game.entities.PowerUp;
import oop.graphics.Canvas;

//classe pour construire la map, placer les obstacles et les entités
//WORK IN PROGRESS

public class MapBuilder {
	private Model model;
	private View view;
	private ISU isu;

	public MapBuilder(Model model, View view) {
		this.model = model;
		this.view = view;
		this.isu = Game.game().isu;
	}
	
	public void placeObjects() {
		double mult = Game.cmPerCell*Main.windowScale;
		
		EnemyKamikaze kz = new EnemyKamikaze("kz1");	
		kz.setCoord(Game.game().isu.new Coord(20.0*mult, 1.8*mult+100));
		placeEntity(kz);
		
		// Exemple de placement de powerUps
		PowerUp puDouble = new PowerUp("double");
		puDouble.setCoord(isu.new Coord(18 *mult, 10 * mult));
		placeEntity(puDouble);

		PowerUp puSpeed = new PowerUp("speed");
		puSpeed.setCoord(isu.new Coord(22 * mult, 10 * mult));
		placeEntity(puSpeed);

		PowerUp puSlave = new PowerUp("slave");
		puSlave.setCoord(isu.new Coord(26 * mult, 10 * mult));
		placeEntity(puSlave);

		PowerUp puLaser = new PowerUp("laser");
		puLaser.setCoord(isu.new Coord(30 * mult, 10 * mult));
		placeEntity(puLaser);
		
	    Boss boss= new Boss();
	    boss.setCoord(isu.new Coord(30*mult,15* mult));
	    placeEntity(boss);
	    //boss.createGuns(model, view);
		
	    placeBorder();

		MontFendu mf = new MontFendu();
		mf.setCoord(isu.new Coord(18*mult, 25*mult));
		placeEntity(mf);
	}
	
	private void placeBorder() {
		
		double screenWidth = view.canvas().getWidth() / Main.windowScale; 
	    
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
	        
	        topBorder.setCoord(isu.new Coord(currentX + (width / 2.0), topY));
	       
	        topBorder.size = isu.new Dimension(width, 12); 
	        placeEntity(topBorder);
	        
	        // --- PLACEMENT EN BAS ---
	        Borders botBorder = new Borders("bot_" + id, spriteType, 0,view);
	        botBorder.setCoord(isu.new Coord(currentX + (width / 2.0), bottomY-adjustY));
	        botBorder.size = isu.new Dimension(width, 12); 
	        placeEntity(botBorder);
	        
	        currentX += width*2;
	        id++;
	        adjustY=0;
	    }
	}
	
	public void placeEntity(Entity e) {
		e.setBounding();
		model.add(e);
		view.add(e.getAvatar());
	}
}
