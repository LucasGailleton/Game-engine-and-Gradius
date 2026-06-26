package game;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import debugmode.ProfilingManager;
import debugmode.VectAvatar;
import engine.ShipStunt;
import engine.BasicStunt;
import engine.EnemyStunt;
import engine.Entity;
import engine.Game;
import engine.ISU.Coord;
import engine.ShipStunt;
import engine.Ticker;
import engine.GAL.Automaton;
import engine.GAL.AutomatonLoader;
import engine.GAL.GALBot;
import engine.GAL.GALStunt;
import engine.GAL.Mode;

import engine.view.Controller;
import engine.view.KeyListener;
import engine.view.PaintListener;
import engine.view.View;
import game.entities.Background;

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
	public static List<Automaton> auts;
	public static MapBuilder mb;
	public static final double windowScale = 4;
	public static final int windowWidth = 256;
	public static final int windowHeight = 240;
	public static final int headerSize = 61;
	public static boolean debug = true;
	public static boolean GAMEOVER = false;
	private Ticker ticker;
	private Model model;
	private View view;
	private StartScreen menu;
	
	public static int Timer = 300;			// Phase initiale sans monstres = 3 secondes
	public static int TimerOrig = 3000;		// Temps d'une phase = 30 secondes

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

		task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 17);
			}
		}, 17);

		this.menu = new StartScreen(this);
		canvas.set((Canvas.PaintListener) this.menu);
		canvas.set((Canvas.KeyListener) this.menu);
	}

	public void startGame() {
		View.shiftPX = 0;
		View.viewSpeed = (int)(View.speed*Main.windowScale);
		Ship.reset();
		if (this.ticker != null)
			this.ticker.stop();
		if (this.model != null)
			for (Entity e : new ArrayList<Entity>(this.model.entities()))
				e.die();
		if (Game.game() == null)
			new Game((int) (500 * windowScale), (int) (35 * windowScale));

		this.view = new View(canvas);
		this.model = new Model(this.view);
		view.setScore(model.score);
		Task tick = Runtime.newTask("tick");
		this.ticker = new Ticker(this.model, tick, 10);

		AutomatonLoader al=new AutomatonLoader(3);
		try {
//		    //auts=al.loadAutomata("PARSER/JavaProject/src/gal/demo/test/a2026.gal");
			//auts = al.loadAutomata("PARSER/JavaProject/src/gal/gradius/chickentest");
			auts = al.loadAutomata("PARSER/JavaProject/src/gal/gradius/gradius_auts");
		} catch (Exception e) {
		    e.printStackTrace(); // Regardez votre console !
		}
		
		Background bg = new Background();
		view.add(bg.new BackgroundAvatar());
		
		mb = new MapBuilder(model, view);
		mb.placeObjects();		

		Ship ship = new Ship(view);
		model.setPlayer(ship);
		ship.setCoord(Game.game().isu.new Coord(15 * Game.cmPerCell * windowScale, 10 * Game.cmPerCell * windowScale));
		ship.setView(view);
		mb.placeEntity(ship);
		
		

		ShipStunt shipStunt = new ShipStunt(this.model, ship);
		ship.setStunt(shipStunt);
		if (debug) {
			VectAvatar va = new VectAvatar(ship);
			this.view.add(va);
		}

		Controller ct = new Controller(ship);
		this.menu.playGame(this.view, ct);

		surveillerGameOver();
	}

	private void surveillerGameOver() {
		task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				if (Ship.isGameOver()) {
					task.post(()->{
						ticker.stop();
						menu.showGameOver();
					}, 1200);
					return;				// Pas de repost car on ne joue plus
				} else if (Ship.win) {
					task.post(()->{
						ticker.stop();
						menu.showTitleScreen();
					}, 1200);
					return;
				}
				task.post(this, 200);
			}
		}, 17);
		
	}
}


