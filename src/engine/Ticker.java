package engine;

import game.Model;
import oop.tasks.Task;
import oop.tasks.Runtime;
import oop.tasks.Runnable;

public class Ticker implements Runnable {
	private Model model;
	private Task task;
	private long time;
	private int ms;
	
	public Ticker(Model model, Task task, int ms) {
		long time = System.currentTimeMillis();
//		this.task = Runtime.newTask("Ticker");
		this.model = model;
		this.task = task;
		this.ms = ms;
		this.task.post(this, ms);
	}

	@Override
	public void run() {
		long now = System.currentTimeMillis();
		long elapsed = now - this.time;
		this.time = now;
		this.model.tick(elapsed);
		this.task.post(this, ms);
	}
}
