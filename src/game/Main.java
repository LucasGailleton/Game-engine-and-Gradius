package game;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import debugmode.VectAvatar;
import engine.ShipStunt;
import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.ISU.Coord;
import engine.ShipStunt;
import engine.Ticker;
import engine.GAL.Automaton;
import engine.GAL.AutomatonLoader;
import engine.GAL.GALBot;
import engine.GAL.Mode;

import engine.view.Controller;
import engine.view.KeyListener;
import engine.view.PaintListener;
import engine.view.View;
import game.entities.Background;
import game.entities.Boss;
import game.entities.BossGunBottom;
import game.entities.BossGunTop;
import game.entities.EnemyChicken;
import game.entities.EnemyHoming;
import game.entities.EnemyKamikaze;
import game.entities.EnemyWalking;
import game.entities.MontFendu;
import game.entities.PowerUp;
import game.entities.Ship;
import oop.graphics.Canvas;
import oop.tasks.Runnable;
import oop.tasks.Runtime;
import oop.tasks.Task;
import engine.ISU.*;


public class Main implements Runnable {
	
	Canvas canvas;
	public static Task task;
	List<Entity> entities;
	List<Automaton> auts;
	public static MapBuilder mb;
	public static final double windowScale = 4;
	public static final int windowWidth = 256;
	public static final int windowHeight = 240;
	public static final int headerSize = 61;
	public static final boolean debug = true;
	public static boolean GAMEOVER = false;
	
	public static int Timer = 1000;		// 1 phase = 10 secondes
	public static int TimerOrig = Timer;

	public static void main(String[] args) {

//		if (debug) {
//			long pid = ProcessHandle.current().pid();
//			System.out.println("PID: " + pid);
//			System.out.println("Hit enter to start the execution");
//			try {
//				System.in.read();
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.exit(1);
//			}
//		}

		Dimension d = new Dimension((int) (windowWidth * windowScale), (int) (windowHeight * windowScale) + headerSize);
		Runtime.boot(d, new Main(), debug);
	}
	
	

	@Override
	public void run() throws Exception {
		Game game = new Game((int) (500 * windowScale), (int) (35 * windowScale));
		task = Runtime.task();
		this.canvas = (Canvas) task.find("canvas");
		View view = new View(canvas);
		canvas.set(new PaintListener(view));
		Model model = new Model(view);
		Task tick = Runtime.newTask("tick");
		Ticker ticker = new Ticker(model, tick, 10);
		
//		AutomatonLoader al=new AutomatonLoader(3);
//		try {
//		    //auts=al.loadAutomata("PARSER/JavaProject/src/gal/demo/test/a2026.gal");
//			auts = al.loadAutomata("PARSER/JavaProject/src/gal/gradius/chickentest");
//		    System.out.println("Automate chargé avec succès !"); // Point d'arrêt ici
//		} catch (Exception e) {
//		    e.printStackTrace(); // Regardez votre console !
//		}
			
		Background bg = new Background();
		view.add(bg.new BackgroundAvatar());
		
		Ship ship = new Ship(view);
		model.setPlayer(ship);
		ship.setCoord(Game.game().isu.new Coord(15 * Game.cmPerCell * windowScale, 10 * Game.cmPerCell * windowScale));
		

		Main.mb = new MapBuilder(model, view);
		mb.placeObjects();
		mb.placeEntity(ship);

		ShipStunt shipStunt = new ShipStunt(model, ship);
		ship.setStunt(shipStunt);
		VectAvatar va = new VectAvatar(ship);
		view.add(va);

		task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 17);
			}
		}, 17);
		
		Controller ct = new Controller(ship);
		KeyListener kl = new KeyListener(ct);
		canvas.set(kl);
	}
}


