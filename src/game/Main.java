package game;

import java.awt.Dimension;
import java.util.List;

import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.Ticker;
import engine.GAL.Mode;
import engine.view.Controller;
import engine.view.KeyListener;
import engine.view.PaintListener;
import engine.view.View;
import game.entities.EnemyChicken;
import game.entities.EnemyHoming;
import game.entities.EnemyKamikaze;
import game.entities.EnemyWalking;
import game.entities.Ghost;
import game.entities.Obstacle;
import game.entities.PacMan;
import game.entities.Ship;
import oop.graphics.Canvas;
import oop.tasks.Runnable;
import oop.tasks.Runtime;
import oop.tasks.Task;


public class Main implements Runnable {
	
	Canvas canvas;
	Task task;
	List<Entity> entities;
	public static final double windowScale = 3;
	public static final int headerSize = 61;
	public static final boolean debug = true;

	public static void main(String[] args) {
		Dimension d = new Dimension((int) (512*windowScale), (int) (240*windowScale)+headerSize);
		Runtime.boot(d, new Main(), debug);
	}

	@Override
	public void run() throws Exception {
		Game game = new Game((int) (31*windowScale), (int) (35*windowScale));
		this.task = Runtime.task();
		this.canvas = (Canvas) this.task.find("canvas");
		View view = new View(canvas);
		canvas.set(new PaintListener(view));
		Model model = new Model();
		BasicStunt shipStunt = init(game,view,canvas,model);
		Task tick = Runtime.newTask("tick");
		Ticker ticker = new Ticker(model, tick, 10);
			
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
	
	public BasicStunt init(Game game, View view,Canvas canvas, Model model) {
		
		Ship ship = new Ship();
		
		ship.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale, 1.8*Game.cmPerCell*windowScale));
		ship.setBounding();
		view.add(ship.avatar());
		model.add(ship);
		
		
//		EnemyChicken chick = new EnemyChicken("chicken1");
//		
//		chick.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+200));
//		chick.setBounding();
//		view.add(chick.getAvatar());
//		model.add(chick);
//		BasicStunt chicStunt = new BasicStunt(model, chick);
//		chick.setStunt(chicStunt);
		
//		EnemyKamikaze kz= new EnemyKamikaze("kz1");
//		
//		kz.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
//		kz.setBounding();
//		view.add(kz.getAvatar());
//		model.add(kz);
		
//		EnemyHoming hm = new EnemyHoming("hm1");
//		hm.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
//		hm.setBounding();
//		view.add(hm.getAvatar());
//		model.add(hm);
		
//		EnemyWalking wk = new EnemyWalking("wk1");
//		wk.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+100, 1.8*Game.cmPerCell*windowScale+100));
//		wk.setBounding();
//		view.add(wk.getAvatar());
//		model.add(wk);
		
		
		BasicStunt shipStunt = new BasicStunt(model, ship);
		ship.setStunt(shipStunt);
		shipStunt.walk(0);
		return shipStunt;
	}

}
