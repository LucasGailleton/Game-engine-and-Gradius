package game;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import debugmode.VectAvatar;
import engine.ShipStunt;
import engine.Entity;
import engine.Game;
import engine.ISU.Coord;
import engine.ShipStunt;
import engine.Ticker;
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
import game.entities.Ghost;
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
	public static final double windowScale = 4;
	public static final int windowWidth = 256;
	public static final int windowHeight = 240;
	public static final int headerSize = 61;
	public static final boolean debug = true;
	
	public static MapBuilder mb;

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
		Game game = new Game((int) (150 * windowScale), (int) (35 * windowScale));
		this.task = Runtime.task();
		this.canvas = (Canvas) this.task.find("canvas");
		View view = new View(canvas);
		canvas.set(new PaintListener(view));
		Model model = new Model(view);
		Task tick = Runtime.newTask("tick");
		Ticker ticker = new Ticker(model, tick, 10);
		Background bg = new Background();
		view.add(bg.new BackgroundAvatar());

		this.mb = new MapBuilder(model, view);
		mb.placeObjects();

		Ship ship = new Ship(view);
		ship.setCoord(Game.game().isu.new Coord(15 * Game.cmPerCell * windowScale, 10 * Game.cmPerCell * windowScale));
		mb.placeEntity(ship);

		ShipStunt shipStunt = new ShipStunt(model, ship);
		ship.setStunt(shipStunt);
		VectAvatar va = new VectAvatar(ship);
		view.add(va);

		

		
		// On va s'en occuper après ce ces deux conneries là
		Mur plafond = new Mur();
		plafond.setCoord(Game.game().isu.new Coord(150 * Game.cmPerCell * Main.windowScale, 2 * Game.cmPerCell * Main.windowScale));
		model.add(plafond);
		view.add(plafond.getAvatar());

		Mur sol = new Mur();
		sol.setCoord(Game.game().isu.new Coord(150 * Game.cmPerCell * Main.windowScale, 2 * Game.cmPerCell * Main.windowScale));
		model.add(sol);
		view.add(sol.getAvatar());
		
		
		


		
		
		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 17);
			}
		}, 17);

		Controller ct = new Controller(shipStunt);
		KeyListener kl = new KeyListener(ct);
		canvas.set(kl);
	}

	public ShipStunt init(Model model, View view) {
		return null;
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
//		EnemyKamikaze kz= new EnemyKamikaze("kz1");
//		
//		kz.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
//		kz.setBounding();
//		view.add(kz.getAvatar());
//		model.add(kz);
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
		//
//		return shipStunt;
	}
}
