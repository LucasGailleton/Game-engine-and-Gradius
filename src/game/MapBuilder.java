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
	
//	private MontFendu mfd;
//	private Borders[] borders;
//	private int nbBorders;
//	private Model model;
//	private View view;

	public MapBuilder(Model model, View view) {
//		this.mfd = new MontFendu();
//		this.nbBorders = 4;
//		this.borders = new Borders[nbBorders];
//		this.model = model;
//		this.view =view;
		
		//placeObstacle();
	}
	
	public static void placeObstacle(Model model, View view, Canvas c) {
		int nbBorders = 4;
		Borders[] borders = new Borders[nbBorders];
		
		borders[0] = new Borders("n1",0,1);
		Borders b1 = borders[0];
		b1.setCoord(Game.game().isu.new Coord(c.getWidth()/Main.windowScale, 10));
		placeEntity(model,view,b1);
		borders[1] = new Borders("n2",0,0);
		Borders b2 = borders[1];
		b2.setCoord(Game.game().isu.new Coord(c.getWidth()/Main.windowScale, c.getHeight()*1.75/Main.windowScale));
		placeEntity(model,view,b2);
	}
	
	private static void placeEntity(Model model, View view, Entity e) {
		e.setBounding();
		model.add(e);
		view.add(e.getAvatar());
	}
}
