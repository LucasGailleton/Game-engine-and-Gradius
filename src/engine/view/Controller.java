package engine.view;

import engine.BasicStunt;
import game.Avatar;
import oop.tasks.Task;
import oop.tasks.Runtime;

public class Controller {
	private BasicStunt player;
	private Task task;

	public Controller(BasicStunt player) {
		this.player = player;
		this.task = Runtime.task();
	}

	public void left() {
		if (player.forbiddenDirection != Avatar.West) {
			if (player.forbiddenDirection != -1)
				player.set(-0.3, 0);
			player.forbiddenDirection = -1;
			player.direction = Avatar.West;
			task.post(() -> {
				player.walk(180);
			}, 10);
		}
	}

	public void right() {
		if (player.forbiddenDirection != Avatar.East) {
			if (player.forbiddenDirection != -1)
				player.set(0.3, 0);
			player.forbiddenDirection = -1;
			player.direction = Avatar.East;
			task.post(() -> {
				player.walk(0);
			}, 10);
		}
	}

	public void up() {
		if (player.forbiddenDirection != Avatar.North) {
			if (player.forbiddenDirection != -1)
				player.set(0, -0.3);
			player.forbiddenDirection = -1;
			player.direction = Avatar.North;
			task.post(() -> {
				player.walk(270);
			}, 10);
		}
	}

	public void down() {
		if (player.forbiddenDirection != Avatar.South) {
			if (player.forbiddenDirection != -1)
				player.set(0, 0.3);
			player.forbiddenDirection = -1;
			player.direction = Avatar.South;
			task.post(() -> {
				player.walk(90);
			}, 10);
		}
	}
}
