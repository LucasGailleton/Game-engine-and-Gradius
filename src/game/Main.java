package game;

import java.awt.Dimension;
import java.util.List;

import DebugMode.VectAvatar;
import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.Ticker;
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

public class Main implements Runnable {
	
	Canvas canvas;
	Task task;
	List<Entity> entities;
	public static final double windowScale = 4;
	public static final int headerSize = 61;
	public static final boolean debug = true;

	public static void main(String[] args) {
		Dimension d = new Dimension((int) (256*windowScale), (int) (240*windowScale)+headerSize);
		Runtime.boot(d, new Main(), debug);
	}

	@Override
	public void run() throws Exception {
		Game game = new Game((int) (31*windowScale), (int) (35*windowScale));
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
		
		Ship ship1 = new Ship();
		ship1.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale+20, 4*1.8*Game.cmPerCell*windowScale+20));
		ship1.setBounding();
		model.add(ship1);
		view.add(ship1.new ShipAvatar());
		BasicStunt ShipStunt1 = new BasicStunt(model, ship1);
		ship1.setStunt(ShipStunt1);
		ShipStunt1.walk(0);
		VectAvatar vp = new VectAvatar(ship);
		view.add(va);
		
		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 17);
			}
		}, 17);
		
		Controller ct = new Controller(ShipStunt);
		KeyListener kl = new KeyListener(ct);
		canvas.set(kl);
		
		ship1.setStunt(ShipStunt);
		ShipStunt.walk(0);		
	}

}
