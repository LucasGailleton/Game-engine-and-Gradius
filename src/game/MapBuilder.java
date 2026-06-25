package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.view.View;
import game.entities.Borders;
import game.entities.Borders.BorderAvatar;
import game.entities.Boss;
import game.entities.EnemyKamikaze;
import game.entities.MontFendu;
import game.entities.PowerUp;
import oop.graphics.Canvas;

//classe pour construire la map, placer les obstacles et les entités
//WORK IN PROGRESS

public class MapBuilder {

	public static final double mult = Game.cmPerCell * Main.windowScale;
	public static final double borderStartX = 40 * mult;

	private Model model;
	private View view;
	private ISU isu;
	
	public static double Timer = 10000;
	public static int nbEnemies = 0;

	public MapBuilder(Model model, View view) {
		this.model = model;
		this.view = view;
		this.isu = Game.game().isu;
	}
	
	public void placeObjects() {

		EnemyKamikaze kz = new EnemyKamikaze("kz1");
		kz.setCoord(isu.new Coord(20.0 * mult, 1.8 * mult + 100));
		placeEntity(kz);
		
		// Exemple de placement de powerUps
		PowerUp puDouble = new PowerUp("double");
		puDouble.setCoord(isu.new Coord(18 * mult, 10 * mult));
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
	    boss.createGuns(model, view);
		
	    
	    placeBorder();

		MontFendu mf = new MontFendu();
		mf.setCoord(isu.new Coord(10 * mult, 25 * mult));
		placeEntity(mf);
	}
	
	private void placeBorder() {

//		double topY = -4.8;
		double topY = 8.7;
		double bottomY = 28 * mult; // 28 cases semble être la bonne hauteur minimale
		double currentX = borderStartX;

		Borders firstTopBorder = new Borders("top_init", 0, BorderAvatar.Top, view);
		firstTopBorder.setCoord(isu.new Coord(currentX, topY));
		placeEntity(firstTopBorder);

		Borders firstBottomBorder = new Borders("bot_init", 0, BorderAvatar.Bottom, view);
		firstBottomBorder.setCoord(isu.new Coord(currentX, bottomY));
		placeEntity(firstBottomBorder);

		for (int i = 0; i < 10; i++) {
			int spriteType = (int) (Math.random() * 4) + 1;

			// --- PLACEMENT EN HAUT ---
			Borders topBorder = new Borders("top_" + i, spriteType, BorderAvatar.Top, view);
			topBorder.setCoord(isu.new Coord(currentX+topBorder.size.x_cm, topY));
			placeEntity(topBorder);

			// --- PLACEMENT EN BAS ---
			Borders botBorder = new Borders("bot_" + i, spriteType, BorderAvatar.Bottom, view);
			botBorder.setCoord(isu.new Coord(currentX+botBorder.size.x_cm, bottomY));
			placeEntity(botBorder);

			currentX += botBorder.size.x_cm;
		}
	}
	
	public void genAleatoire() {
		
		while (Timer >0 && nbEnemies < 10) {
			double al = Math.random();
			if (al>=0.75) {
				
			}else if (al>=0.5 && al < 0.75) {
				
			}else if (al >=0.25 && al <0.5) {
				
			}else {
				
			}
		}
	}
	
	public void placeEntity(Entity e) {
		e.setBounding();
		model.add(e);
		view.add(e.getAvatar());
	}
}
