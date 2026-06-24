package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
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
		ISU is = Game.game().isu;

		double gameWidth = Game.game().width_cm;
	    double screenWidth = c.getWidth() / Main.windowScale; 
	    System.out.println("width" + screenWidth);
	    double topY = 10.0; 
	    double bottomY = (c.getHeight() / Main.windowScale) + 180;

	    double currentX = 0.0;
	    double adjustY =0.0;
	    int id = 0;
	    double width = 63.0;
	    

		    for (int i=0;i<5;i++) {
		    	
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
		        
		        topBorder.setCoord(is.new Coord(currentX + (width / 2.0), topY));
		       
		        topBorder.size = is.new Dimension(width, 12); 
		        placeEntity(model, view, topBorder);
		        
		        // --- PLACEMENT EN BAS ---
		        Borders botBorder = new Borders("bot_" + id, spriteType, 0,view);
		        botBorder.setCoord(is.new Coord(currentX + (width / 2.0), bottomY-adjustY));
		        botBorder.size = is.new Dimension(width, 12); 
		        placeEntity(model, view, botBorder);
		        
		        currentX += width*2;
		        id++;
		        adjustY=0;
		    }
	    }
	
	
	private static void placeEntity(Model model, View view, Entity e) {
		e.setBounding();
		model.add(e);
		view.add(e.getAvatar());
	}
	
	public void recreateBorders() {
		
	}
}
