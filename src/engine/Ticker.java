package engine;

import game.Model;
import oop.tasks.Task;
import oop.tasks.Runnable;

public class Ticker implements Runnable {
	private Model model;
	private Task task;
	private long time;
	private int ms;
	private boolean stopped = false;

	public Ticker(Model model, Task task, int ms) {
		this.model = model;
		this.task = task;
		this.ms = ms;
		this.task.post(this, ms);
	}

	public void stop() {
		this.stopped = true;
	}

	@Override
	public void run() {
		if (this.stopped)
			return;
		long now = System.currentTimeMillis();
		long elapsed = now - this.time;
		this.time = now;
		this.model.tick(elapsed);
		this.task.post(this, ms);
	}
}
