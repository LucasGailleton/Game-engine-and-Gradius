package game;

import engine.Entity;
import engine.Game;
import engine.ISU.Coord;
import engine.view.View;
import game.entities.Borders;
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
		
		borders[0] = new Borders("n1",0,1);
		Borders b1 = borders[0];
		b1.setCoord(Game.game().isu.new Coord(canvasHeight*Main.windowScale, 10));
		placeEntity(b1);
		
		borders[1] = new Borders("n2",0,0);
		Borders b2 = borders[1];
		b2.setCoord(Game.game().isu.new Coord(canvasHeight*Main.windowScale, canvasHeight*1.75*Main.windowScale));
		placeEntity(b2);
		
		
		
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
