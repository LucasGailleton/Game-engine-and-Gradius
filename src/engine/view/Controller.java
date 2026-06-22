package engine.view;


import engine.BasicStunt;
import engine.ShipStunt;
import game.entities.Avatar;
import oop.tasks.Task;
import oop.graphics.VirtualKeyCodes;
import oop.tasks.Runtime;

public class Controller {

	private ShipStunt player;
	private Task task;
	private boolean stopped = true;

	private boolean leftDown = false;
	private boolean rightDown = false;
	private boolean upDown = false;
	private boolean downDown = false;
	private boolean space =false;

	private int dir = -1;

	public Controller(ShipStunt player) {

		this.player = player;
		this.task = Runtime.task();
	}

	public void update(int keyCode, boolean isDown) {
		if (keyCode == VirtualKeyCodes.VK_LEFT)
			leftDown = isDown;
		else if (keyCode == VirtualKeyCodes.VK_RIGHT)
			rightDown = isDown;
		else if (keyCode == VirtualKeyCodes.VK_UP)
			upDown = isDown;
		else if (keyCode == VirtualKeyCodes.VK_DOWN)
			downDown = isDown;
		else if (keyCode == VirtualKeyCodes.VK_SPACE) {
			space = isDown;
		}

		recalculer();
	}

	public void recalculer() {
		int dx = 0;
		int dy = 0;
		
		if(space) {
			shoot();
//			task.post(()->{
//				shoot();
//			},100);
		}

		if (leftDown)
			dx -= 1;
		if (rightDown)
			dx += 1;
		if (upDown)
			dy -= 1;
		if (downDown)
			dy += 1;

		if (dx == 0 && dy == 0) {
			if (!stopped) {
				player.stop();
				stopped = true;
				dir = -1;
			}
			return;
		}

		int angle = 0;

		if (dx == 1 && dy == 0)
			angle = 0;
		else if (dx == 1 && dy == 1)
			angle = 45;
		else if (dx == 0 && dy == 1)
			angle = 90;
		else if (dx == -1 && dy == 1)
			angle = 135;
		else if (dx == -1 && dy == 0)
			angle = 180;
		else if (dx == -1 && dy == -1)
			angle = 225;
		else if (dx == 0 && dy == -1)
			angle = 270;
		else if (dx == 1 && dy == -1)
			angle = 315;

		if (angle == dir && !stopped) {
			return;
		}

		dir = angle;
		stopped = false;
		player.forbiddenDirection = -1;
		
		double speed = 0;
		
		if(dx != 0 && dy != 0) {
			speed = 0.212;
		}
		else {
			speed = 0.3;
		}

		if (dx == -1)
			player.direction = Avatar.West;
		else if (dx == 1)
			player.direction = Avatar.East;
		else if (dy == -1)
			player.direction = Avatar.North;
		else if (dy == 1)
			player.direction = Avatar.South;
		
		player.set(dx * speed, dy * speed);
		player.walk(angle);
	}

	public void left() {
		if (player.forbiddenDirection != Avatar.West) {
			if (player.forbiddenDirection != -1)
				player.set(-0.3, 0);
			player.forbiddenDirection = -1;
			player.direction = Avatar.West;
			player.walk(180);
			stopped = false;
		}
	}

	public void right() {
		if (player.forbiddenDirection != Avatar.East) {
			if (player.forbiddenDirection != -1)
				player.set(0.3, 0);
			player.forbiddenDirection = -1;
			player.direction = Avatar.East;
			player.walk(0);
			stopped = false;
		}
	}

	public void up() {
		if (player.forbiddenDirection != Avatar.North) {
			if (player.forbiddenDirection != -1)
				player.set(0, -0.3);
			player.forbiddenDirection = -1;
			player.direction = Avatar.North;
			player.walk(270);
			stopped = false;
		}
	}

	public void down() {
		if (player.forbiddenDirection != Avatar.South) {
			if (player.forbiddenDirection != -1)
				player.set(0, 0.3);
			player.forbiddenDirection = -1;
			player.direction = Avatar.South;
			player.walk(90);
			stopped = false;
		}
	}
	
	public void shoot() {
		player.shootOnce();
	}

	public void stop() {
		if (!stopped) {
			player.stop();
			stopped = true;
		}
	}
}
