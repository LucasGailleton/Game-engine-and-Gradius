package game;

import engine.Entity;
import engine.Game;
import engine.ISU.Coord;
import engine.view.View;
import game.entities.Borders;
import game.entities.MontFendu;
import oop.graphics.Canvas;

//classe pour construire la map, placer les obstacles et les entités
//WORK IN PROGRESS
public class MapBuilder {
	

	public MapBuilder(Model model, View view) {
		
	}
	
	public static void placeObstacle(Model model, View view, Canvas c) {

		
	    double screenWidth = c.getWidth() / Main.windowScale; 
	    System.out.println("width" + screenWidth);
	    double topY = 10.0; 
	    double bottomY = (c.getHeight() / Main.windowScale) + 130;

	    double currentX = 0.0; 
	    int id = 0;
	    while (currentX < screenWidth) {
	    	int spriteType=0;
	        double rand = Math.random();
	        if (rand<0.2) {
	        	spriteType=0;
	        }else if(rand<0.4 && rand>=0.2) {
	        	spriteType=1;
	        }else if(rand<0.6 && rand>=0.4) {
	        	spriteType=2;
	        }else if(rand<0.8 && rand>=0.6) {
	        	spriteType=3;
	        }else if(rand>=0.8){
	        	spriteType=4;
	        }
	        
	        
	        double width = 63.0;
	        
	        // --- PLACEMENT EN HAUT ---
	        Borders topBorder = new Borders("top_" + id, spriteType, 1);
	        
	        topBorder.setCoord(Game.game().isu.new Coord(currentX + (width / 2.0), topY));
	       
	        topBorder.size = Game.game().isu.new Dimension(width, 12); 
	        placeEntity(model, view, topBorder);
	        
	        // --- PLACEMENT EN BAS ---
	        Borders botBorder = new Borders("bot_" + id, spriteType, 0);
	        botBorder.setCoord(Game.game().isu.new Coord(currentX + (width / 2.0), bottomY));
	        botBorder.size = Game.game().isu.new Dimension(width, 12);
	        placeEntity(model, view, botBorder);
	        currentX += width;
	        id++;
	    }
	}
	
	private static void placeEntity(Model model, View view, Entity e) {
		//e.setBounding();
		model.add(e);
		view.add(e.getAvatar());
	}
}
