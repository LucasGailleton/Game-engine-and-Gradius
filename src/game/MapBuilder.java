package game;

import engine.EnemyStunt;
import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Coord;
import engine.GAL.GALBot;
import engine.view.View;
import game.entities.Borders;
import game.entities.Borders.BorderAvatar;
import game.entities.Boss;
import game.entities.EnemyChicken;
import game.entities.EnemyHoming;
import game.entities.EnemyKamikaze;
import game.entities.EnemyWalking;
import game.entities.MontFendu;
import game.entities.PowerUp;
import oop.graphics.Canvas;

//classe pour construire la map, placer les obstacles et les entités
//WORK IN PROGRESS

public class MapBuilder {

	public static final double mult = Game.cmPerCell * Main.windowScale;
	public static double borderStartX = 40 * mult;
	public static final int maxBottomCell = 28;
	
	public static int phase;
	public static final int phase_init = 0;
	public static final int phase_void = 1;
	public static final int phase_cave = 2;
	public static final int phase_boss = 3;

	private Model model;
	private View view;
	private ISU isu;

	public static int nbEnemies = 0;
	private double spawnCooldown = 0.0;
	private static double spawnInterval; // Temps en secondes entre deux vagues	

	public MapBuilder(Model model, View view) {
		this.model = model;
		this.view = view;
		this.isu = Game.game().isu;
		phase = 0;
	}
	
	public void placeObjects() {

		EnemyKamikaze kz = new EnemyKamikaze("kz1");
		kz.setCoord(Game.game().isu.new Coord(20.0 * mult, 1.8 * mult + 100));
		placeEntity(kz);
		
		
	    
	}
	
	private void placeBorder() {

		double topY = 8.7;
		double bottomY = maxBottomCell * mult; // 28 cases semble être la bonne hauteur minimale
		double currentX = borderStartX;

		Borders firstTopBorder = new Borders("top_init", 0, BorderAvatar.Top, view);
		firstTopBorder.setCoord(isu.new Coord(currentX, topY));
		placeEntity(firstTopBorder);

		Borders firstBottomBorder = new Borders("bot_init", 0, BorderAvatar.Bottom, view);
		firstBottomBorder.setCoord(isu.new Coord(currentX, bottomY));
		placeEntity(firstBottomBorder);

		int spriteType;
		for (int i = 0; i < 10; i++) {
			spriteType = (int) (Math.random() * 4) + 1;

			// --- PLACEMENT EN HAUT ---
			spriteType = (int) (Math.random() * 4) + 1;
			Borders topBorder = new Borders("top_" + i, spriteType, BorderAvatar.Top, view);
			topBorder.setCoord(isu.new Coord(currentX+topBorder.size.x_cm, topY));
			placeEntity(topBorder);

			// --- PLACEMENT EN BAS ---
			spriteType = (int) (Math.random() * 4) + 1;
			Borders botBorder = new Borders("bot_" + i, spriteType, BorderAvatar.Bottom, view);
			botBorder.setCoord(isu.new Coord(currentX+botBorder.size.x_cm, bottomY));
			placeEntity(botBorder);

			currentX += botBorder.size.x_cm;
		}
	}
	
	public void genAleatoire(double seconds) {
		if (Main.GAMEOVER)
			return;
		if (Main.Timer <= 0 && phase < phase_boss) {
			Main.Timer = Main.TimerOrig;
			phase++;
			System.out.println("Changement de phase : " + phase);
			if (phase == phase_cave) {
				borderStartX = (int) (Main.windowWidth*Game.pixelPerCm)-(View.shiftPX*0.5) + 100;
				placeBorder();
				placeMontFendu(borderStartX);
			}
			return;
	    }
		
		if (spawnCooldown == 0.0) {
	        double spawnX = (Main.windowWidth*Game.pixelPerCm)-(View.shiftPX*0.5) + 50;
		    
	        int choixMonstre = (int) (Math.random() * 10);
	        
	        switch (phase) {
	        case phase_init: return;
	        case phase_void:
			    switch (choixMonstre) {
			    case 0, 1, 2, 3, 4: spawnKamikaze(spawnX); break;
			    case 5, 6, 7: spawnWalking(spawnX); break;
			    default : spawnHoming(spawnX); break; 
			    }
			    spawnInterval = 0.5 + Math.random();	// Intervalle de spawn entre 0.5 et 1.5 secondes pour la première partie
			    break;
	        case phase_cave:
			    switch (choixMonstre) {
			    case 0, 1, 2, 3: spawnKamikaze(spawnX); break;
			    case 4, 5, 6: spawnWalking(spawnX); break;
			    case 7, 8: spawnHoming(spawnX); break;
			    default : spawnChicken(spawnX); break;
			    }
			    spawnInterval = 0.2 + (Math.random() * 0.8);	// Intervalle de spawn entre 0.2 et 1.0 secondes pour la seconde partie
			    break;
	        case phase_boss:
	        	spawnBoss(spawnX);
	        	spawnInterval = -10.0;	// Désactivation du cooldown et donc du spawn des entités enemies
	        	Main.task.post(()->{
	        		View.viewSpeed = 0;	// Arret du scrolling pour le boss
	        	}, 5000);
	        	break;
	        }
		    
		    // On comptabilise le monstre et on enclenche le cooldown
		    nbEnemies++;
		    spawnCooldown = spawnInterval;
	    } else if (!(spawnCooldown == -10.0)) { 
	        spawnCooldown -= seconds;
	        if (spawnCooldown < 0)
	        	spawnCooldown = 0;
	        
	    }
	    
	}
	
	private void placeMontFendu(double x) {
		MontFendu mf = new MontFendu();
		mf.setCoord(isu.new Coord(x + 25 * mult, 25 * mult));
		placeEntity(mf);
	}
	
	private void spawnWalking(double x) {
	    EnemyWalking wlk ;
	    boolean hautOuBas = Math.random()<0.5;
	    double randomY = 0;
	    if (hautOuBas) {
	    	wlk = new EnemyWalking(view,"walking",2);
	    	randomY = 102*Main.windowScale;
	    }else {
	    	randomY = 5*Main.windowScale;
	    	wlk = new EnemyWalking(view,"walking",0);
	    }
	    
	    wlk.setCoord(isu.new Coord(x, randomY));
	    placeEntity(wlk);
	  
	    // Partie Bot
		EnemyStunt bs =new EnemyStunt(model,wlk);
		wlk.setStunt(bs);
		GALBot gb = new GALBot(wlk);
		gb.set(Main.auts.getFirst());
		gb.setthinkspeed(50);
	}

	private void spawnHoming(double x) {
	    EnemyHoming hom = new EnemyHoming(view,"homing");
	    double randomY = 80 + (Math.random() * 250);
	    hom.setCoord(isu.new Coord(x, randomY));
	    placeEntity(hom);
	}

	private void spawnChicken(double x) {
		EnemyChicken chk;
		boolean hautOuBas = Math.random()<0.5;
	    double randomY = 0;
	    if (hautOuBas) {
	    	chk = new EnemyChicken(view,"chicken",1);
	    	randomY=102*Main.windowScale;
	    }else {
	    	chk = new EnemyChicken(view,"chicken",0);
	    	randomY=5*Main.windowScale;
	    }  
	    chk.setCoord(isu.new Coord(x, randomY));
	    placeEntity(chk);
	}

	private void spawnKamikaze(double x) {
	    EnemyKamikaze kmkz = new EnemyKamikaze("kamikaze");
	    double randomY = 50 + (Math.random() * 300);
	    kmkz.setCoord(isu.new Coord(x, randomY));
	    placeEntity(kmkz);
	}
	
	private void spawnBoss(double x) {
	    Boss boss= new Boss(view);
	    boss.setCoord(isu.new Coord(x,15* mult));
	    placeEntity(boss);
	    boss.createGuns(model, view);
	}

	public void placeEntity(Entity e) {
		e.setBounding();
		model.add(e);
		view.add(e.getAvatar());
	}
}
