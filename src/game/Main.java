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
	Task task;
	List<Entity> entities;
	List<Automaton> auts;
	public static MapBuilder mb;
	public static final double windowScale = 4;
	public static final int windowWidth = 256;
	public static final int windowHeight = 240;
	public static final int headerSize = 61;
	public static final boolean debug = true;
	public static boolean debugView = false;
	private Ticker ticker;
	private Model model;
	private View view;
	private StartScreen menu;

	public static void main(String[] args) {

		if (debug) {
			long pid = ProcessHandle.current().pid();
			System.out.println("PID: " + pid);
			System.out.println("Hit enter to start the execution");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		Dimension d = new Dimension((int) (windowWidth * windowScale), (int) (windowHeight * windowScale) + headerSize);
		Runtime.boot(d, new Main(), debug);
	}
	
	

	@Override
	public void run() throws Exception {
		this.task = Runtime.task();
		this.canvas = (Canvas) this.task.find("canvas");

		this.task.post(new Runnable() {
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
		Ship.reset();
		if (this.ticker != null)
			this.ticker.stop();
		if (this.model != null)
			for (Entity e : new java.util.ArrayList<Entity>(this.model.entities()))
				e.die();
		if (Game.game() == null)
			new Game((int) (50 * windowScale), (int) (35 * windowScale));

		this.view = new View(canvas);
		this.model = new Model(this.view);
		Task tick = Runtime.newTask("tick");
		this.ticker = new Ticker(this.model, tick, 10);

		Background bg = new Background();
		this.view.add(bg.new BackgroundAvatar());

		Main.mb = new MapBuilder(this.model, this.view);
		mb.placeObjects();

		Ship ship = new Ship(this.view);
		ship.setCoord(Game.game().isu.new Coord(15 * Game.cmPerCell * windowScale, 10 * Game.cmPerCell * windowScale));
		mb.placeEntity(ship);

		ShipStunt shipStunt = new ShipStunt(this.model, ship);
		ship.setStunt(shipStunt);
		if (debugView) {
			VectAvatar va = new VectAvatar(ship);
			this.view.add(va);
		}

		Controller ct = new Controller(shipStunt);
		this.menu.playGame(this.view, ct);

		surveillerGameOver();
	}

	private void surveillerGameOver() {
		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				if (Ship.isGameOver()) {
					// explosion du vaisseau puis menu game over
					task.post(new Runnable() {
						@Override
						public void run() throws Exception {
							ticker.stop();
							menu.showGameOver();
						}
					}, 1200);
					return;
				}
				task.post(this, 200);
			}
		}, 200);
	}
	
	public ShipStunt init(Model model, View view) {
		return null;
//		MapBuilder.placeObstacle(model,view, canvas);
//		
//		Ship ship = new Ship(view);
//		ship.setCoord(Game.game().isu.new Coord(15*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
//		ship.setBounding();
//		model.add(ship);
//		view.add(ship.new ShipAvatar());
//		
//		ShipStunt shipStunt = new ShipStunt(model, ship);
//		ship.setStunt(shipStunt);
//		VectAvatar va = new VectAvatar(ship);
//		view.add(va);
//		
//		ship.setStunt(shipStunt);
//			
//		MontFendu mf = new MontFendu();
//		mf.setCoord(Game.game().isu.new Coord(18*Game.cmPerCell*windowScale, 25*Game.cmPerCell*windowScale));
//		mf.setBounding();
//		model.add(mf);
//		view.add(mf.getAvatar());
		
		/*EnemyChicken chick=new EnemyChicken(view, "testchick");
		chick.setCoord(Game.game().isu.new Coord(5*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
		chick.setBounding();
		chick.setStunt(new BasicStunt(chick.getModel(),chick));
		model.add(chick);
		view.add(chick.getAvatar());
		GALBot gb=new GALBot(chick); 
		gb.set(auts.get(0));*/
//
//		Mur plafond = new Mur(75, 0);
//		model.add(plafond);
//		view.add(plafond.getAvatar());
//		
//		Mur sol = new Mur(75, 30);
//		model.add(sol);
//		view.add(sol.getAvatar());
//		
//		Ship ship = new Ship();
//		
//		ship.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale, 1.8*Game.cmPerCell*windowScale));
//		ship.setBounding();
//		view.add(ship.avatar());
//		model.add(ship);
//		BasicStunt shipStunt = new BasicStunt(model, ship);
//		ship.setStunt(shipStunt);
//		shipStunt.walk(0);
//		VectAvatar va = new VectAvatar(ship);
//		view.add(va);
//
//		
//		EnemyChicken chick = new EnemyChicken("chicken1");
//		
//		chick.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+200));
//		chick.setBounding();
//		view.add(chick.getAvatar());
//		model.add(chick);
//		BasicStunt chicStunt = new BasicStunt(model, chick);
//		chick.setStunt(chicStunt);
//		ship.setCoord(Game.game().isu.new Coord(15*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
//		ship.setBounding();
//		model.add(ship);
//		view.add(ship.new ShipAvatar());
//		
//		BasicStunt ShipStunt = new BasicStunt(model, ship);
//		ship.setStunt(ShipStunt);
//		
//		Ship ship2 = new Ship();
//		ship2.setCoord(Game.game().isu.new Coord(38*Game.cmPerCell*windowScale, 15*Game.cmPerCell*windowScale));
//		ship2.setBounding();
//		model.add(ship2);
//		view.add(ship2.new ShipAvatar());
//		
		
//		
//		EnemyHoming hm = new EnemyHoming("hm1");
//		hm.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
//		hm.setBounding();
//		view.add(hm.getAvatar());
//		model.add(hm);
//		
//		EnemyWalking wk = new EnemyWalking("wk1");
//		wk.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
//		wk.setBounding();
//		view.add(wk.getAvatar());
//		model.add(wk);
		
//		PowerUp pu = new PowerUp("pu1");
//		pu.setCoord(Game.game().isu.new Coord(18*Game.cmPerCell*windowScale, 25*Game.cmPerCell*windowScale));
//		pu.setBounding();
//		model.add(pu);
//		view.add(pu.getAvatar());
//		
		// power-ups à ramasser
//		PowerUp puDouble = new PowerUp("double", PowerUp.DOUBLE);
//		puDouble.setCoord(Game.game().isu.new Coord(18*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
//		puDouble.setBounding();
//		model.add(puDouble);
//		view.add(puDouble.getAvatar());
//
//		PowerUp puSpeed = new PowerUp("speed", PowerUp.SPEED);
//		puSpeed.setCoord(Game.game().isu.new Coord(22*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
//		puSpeed.setBounding();
//		model.add(puSpeed);
//		view.add(puSpeed.getAvatar());
//
//		PowerUp puSlave = new PowerUp("slave", PowerUp.SLAVE);
//		puSlave.setCoord(Game.game().isu.new Coord(26*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
//		puSlave.setBounding();
//		model.add(puSlave);
//		view.add(puSlave.getAvatar());
//
//		PowerUp puLaser = new PowerUp("laser", PowerUp.LASER);
//		puLaser.setCoord(Game.game().isu.new Coord(30*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
//		puLaser.setBounding();
//		model.add(puLaser);
//		view.add(puLaser.getAvatar());
//
//		return shipStunt;
	}
}


