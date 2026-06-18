package game;

import java.awt.Dimension;
import java.util.List;

import DebugMode.VectAvatar;
import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.ISU.Coord;
import engine.Ticker;
import engine.GAL.Mode;
import engine.view.Controller;
import engine.view.KeyListener;
import engine.view.PaintListener;
import engine.view.View;
import game.entities.Background;
import game.entities.EnemyChicken;
import game.entities.EnemyHoming;
import game.entities.EnemyKamikaze;
import game.entities.EnemyWalking;
import game.entities.Ghost;
import game.entities.MontFendu;
import game.entities.Obstacle;
import game.entities.PacMan;
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
	public static final double windowScale = 2;
	public static final int headerSize = 61;
	public static final boolean debug = true;

	public static void main(String[] args) {
		Dimension d = new Dimension((int) (256*windowScale), (int) (240*windowScale)+headerSize);
		Runtime.boot(d, new Main(), debug);
	}

	@Override
	public void run() throws Exception {
		Game game = new Game((int) (150*windowScale), (int) (35*windowScale));
		this.task = Runtime.task();
		this.canvas = (Canvas) this.task.find("canvas");
		View view = new View(canvas);
		canvas.set(new PaintListener(view));
		Model model = new Model(view);
		Task tick = Runtime.newTask("tick");
		Ticker ticker = new Ticker(model, tick, 10);
		Background bg = new Background();
		view.add(bg.new BackgroundAvatar());
		

		Ship ship = new Ship();
		ship.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale, 4*1.8*Game.cmPerCell*windowScale));
		ship.setBounding();
		model.add(ship);
		view.add(ship.new ShipAvatar());
		BasicStunt ShipStunt = new BasicStunt(model, ship);
		ship.setStunt(ShipStunt);
		ShipStunt.walk(0);
		VectAvatar va = new VectAvatar(ship);
		view.add(va);
		
		/*Ship ship1 = new Ship();
		ship1.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+20, 4*1.8*Game.cmPerCell*windowScale+20));
		ship1.setBounding();
		model.add(ship1);
		view.add(ship1.new ShipAvatar());
		BasicStunt ShipStunt1 = new BasicStunt(model, ship1);
		ship1.setStunt(ShipStunt1);
		ShipStunt1.walk(0);
		VectAvatar vp = new VectAvatar(ship);
		view.add(va);*/
		
		MontFendu mf = new MontFendu();
		mf.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+60, 4*1.8*Game.cmPerCell*windowScale+60));
		mf.setBounding();
		model.add(mf);
		view.add(mf.new MontFenduAvatar());
		
		BasicStunt shipStunt = init(model,view);

		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 17);
//				view.shift();
			}
		}, 17);
		
		Controller ct = new Controller(shipStunt);
		KeyListener kl = new KeyListener(ct);
		canvas.set(kl);
		
		ship.setStunt(ShipStunt);
		ShipStunt.walk(0);		
	}
	public BasicStunt init(Model model, View view) {
			
		
		
		Ship ship = new Ship();
		
		ship.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale, 1.8*Game.cmPerCell*windowScale));
		ship.setBounding();
		view.add(ship.avatar());
		model.add(ship);
		BasicStunt shipStunt = new BasicStunt(model, ship);
		ship.setStunt(shipStunt);
		shipStunt.walk(0);
		VectAvatar va = new VectAvatar(ship);
		view.add(va);

		
		EnemyChicken chick = new EnemyChicken("chicken1");
		
		chick.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+200));
		chick.setBounding();
		view.add(chick.getAvatar());
		model.add(chick);
		BasicStunt chicStunt = new BasicStunt(model, chick);
		chick.setStunt(chicStunt);
		ship.setCoord(Game.game().isu.new Coord(15*Game.cmPerCell*windowScale, 10*Game.cmPerCell*windowScale));
		ship.setBounding();
		model.add(ship);
		view.add(ship.new ShipAvatar());
		
		BasicStunt ShipStunt = new BasicStunt(model, ship);
		ship.setStunt(ShipStunt);
		
		Ship ship2 = new Ship();
		ship2.setCoord(Game.game().isu.new Coord(38*Game.cmPerCell*windowScale, 15*Game.cmPerCell*windowScale));
		ship2.setBounding();
		model.add(ship2);
		view.add(ship2.new ShipAvatar());
		
		EnemyKamikaze kz= new EnemyKamikaze("kz1");
		
		kz.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
		kz.setBounding();
		view.add(kz.getAvatar());
		model.add(kz);
		
		EnemyHoming hm = new EnemyHoming("hm1");
		hm.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
		hm.setBounding();
		view.add(hm.getAvatar());
		model.add(hm);
		
		EnemyWalking wk = new EnemyWalking("wk1");
		wk.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
		wk.setBounding();
		view.add(wk.getAvatar());
		model.add(wk);
		
		return shipStunt;
	}
}


