package game;

import java.awt.Dimension;
import java.util.List;

import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.Ticker;
import engine.view.Controller;
import engine.view.KeyListener;
import engine.view.PaintListener;
import engine.view.View;
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
	

	public static void main(String[] args) {
		Dimension d = new Dimension((int) (224*windowScale), (int) (248*windowScale)+headerSize);
		Runtime.boot(d, new Main(), true);
	}

	@Override
	public void run() throws Exception {
		Game game = new Game((int) (31*windowScale), (int) (35*windowScale));
		this.task = Runtime.task();
		this.canvas = (Canvas) this.task.find("canvas");
		View view = new View(canvas);
		canvas.set(new PaintListener(view));
		Model model = new Model();
		Task tick = Runtime.newTask("tick");
		Ticker ticker = new Ticker(model, tick, 10);
		
		PacMan pacman = new PacMan();
//		pacman.setPosition(model.grid().new Position((int) (1*windowScale), (int) (1*windowScale)));
		pacman.setCoord(Game.game().isu.new Coord(1.8*Game.cmPerCell*windowScale, 1.8*Game.cmPerCell*windowScale));
		pacman.setBounding();
		model.add(pacman);
		view.add(pacman.new PacManAvatar());
		BasicStunt PacManStunt = new BasicStunt(model, pacman);
		pacman.setStunt(PacManStunt);
//		PacManStunt.walk(0);
		
		Obstacle wall_top = new Obstacle(0, 0);
		wall_top.setCoord(Game.game().isu.new Coord(15*Game.cmPerCell*windowScale, 2.3));
		wall_top.setSize(Game.game().isu.new Dimension(30*Game.cmPerCell*windowScale, 0.8*Game.cmPerCell*windowScale));
		wall_top.setBounding();
		model.add(wall_top);
		view.add(wall_top.new ObstacleAvatar());
		
		Obstacle wall_top_left = new Obstacle(0, 0);
		wall_top_left.setCoord(Game.game().isu.new Coord(3.7, 5.5*Game.cmPerCell*windowScale));
		wall_top_left.setSize(Game.game().isu.new Dimension(0.6*Game.cmPerCell*windowScale, 19*Game.cmPerCell*windowScale));
		wall_top_left.setBounding();
		model.add(wall_top_left);
		view.add(wall_top_left.new ObstacleAvatar());
		
		Obstacle wall_top_right = new Obstacle(0, 0);
		wall_top_right.setCoord(Game.game().isu.new Coord(30.15*Game.cmPerCell*windowScale, 5.5*Game.cmPerCell*windowScale));
		wall_top_right.setSize(Game.game().isu.new Dimension(0.6*Game.cmPerCell*windowScale, 19*Game.cmPerCell*windowScale));
		wall_top_right.setBounding();
		model.add(wall_top_right);
		view.add(wall_top_right.new ObstacleAvatar());
		
		Obstacle wall_top2 = new Obstacle(0, 0);
		wall_top2.setCoord(Game.game().isu.new Coord(15.2*Game.cmPerCell*windowScale, 2.8*Game.cmPerCell*windowScale));
		wall_top2.setSize(Game.game().isu.new Dimension(0.9*Game.cmPerCell*windowScale, 4.5*Game.cmPerCell*windowScale));
		wall_top2.setBounding();
		model.add(wall_top2);
		view.add(wall_top2.new ObstacleAvatar());
		
		Obstacle block_top_leftleft = new Obstacle(0, 0);
		block_top_leftleft.setCoord(Game.game().isu.new Coord(4.4*Game.cmPerCell*windowScale, 3.92*Game.cmPerCell*windowScale));
		block_top_leftleft.setSize(Game.game().isu.new Dimension(3.2*Game.cmPerCell*windowScale, 2.2*Game.cmPerCell*windowScale));
		block_top_leftleft.setBounding();
		model.add(block_top_leftleft);
		view.add(block_top_leftleft.new ObstacleAvatar());
		
		Obstacle block_top_leftright = new Obstacle(0, 0);
		block_top_leftright.setCoord(Game.game().isu.new Coord(10.35*Game.cmPerCell*windowScale, 3.92*Game.cmPerCell*windowScale));
		block_top_leftright.setSize(Game.game().isu.new Dimension(4.4*Game.cmPerCell*windowScale, 2.2*Game.cmPerCell*windowScale));
		block_top_leftright.setBounding();
		model.add(block_top_leftright);
		view.add(block_top_leftright.new ObstacleAvatar());
		
		Ghost blinky = new Ghost();
//		blinky.setPosition(model.grid().new Position((int) (0*windowScale), (int) (0*windowScale)));
		blinky.setCoord(Game.game().isu.new Coord(30*Game.cmPerCell*windowScale*0.5, 35*Game.cmPerCell*windowScale*0.5));
		blinky.setBounding();
		model.add(blinky);
		view.add(blinky.new GhostAvatar(0));
		BasicStunt BlinkyStunt = new BasicStunt(model, blinky);
		blinky.setStunt(BlinkyStunt);
//		BlinkyStunt.walk(0);

		Controller ct = new Controller(PacManStunt);
		KeyListener kl = new KeyListener(ct);
		canvas.set(kl);
		
		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 17);
			}
		}, 17);
			
	}

}
