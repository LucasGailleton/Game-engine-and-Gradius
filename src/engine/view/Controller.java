package engine.view;


import engine.Entity;
import engine.ShipStunt;
import engine.Stunt;
import game.entities.Avatar;
import oop.tasks.Task;
import oop.graphics.VirtualKeyCodes;
import oop.tasks.Runtime;

public class Controller {

	private Entity player;
	private Task task;
	private boolean stopped = true;

	private int dir = -1;
	private boolean left, right, up, down, space;

	public Controller(Entity player) {
		this.player = player;
		this.task = Runtime.task();
	}

	public void update(int keyCode, boolean isDown) {
		if (keyCode == VirtualKeyCodes.VK_LEFT) left = isDown;
        if (keyCode == VirtualKeyCodes.VK_RIGHT) right = isDown;
        if (keyCode == VirtualKeyCodes.VK_UP) up = isDown;
        if (keyCode == VirtualKeyCodes.VK_DOWN) down = isDown;
        if (keyCode == VirtualKeyCodes.VK_SPACE) space = isDown;

		recalculer();
	}

	public void recalculer() {
		
		if(space) {		
			shoot();
		}
		int dx = 0;
		int dy = 0;
		

		if (left) dx -= 1;
        if (right) dx += 1;
        if (up) dy -= 1;
        if (down) dy += 1;

		if (dx == 0 && dy == 0) {
			if (!stopped) {
				Stunt playerStunt = player.stunt();
				if (playerStunt != null)
					playerStunt.stop();
				stopped = true;
				dir = -1;
			}
			return;
		}

		int angle = 0;

		if (dx == 1 && dy == 0) angle = 0;
        else if (dx == 1 && dy == 1) angle = 45;
        else if (dx == 0 && dy == 1) angle = 90;
        else if (dx == -1 && dy == 1) angle = 135;
        else if (dx == -1 && dy == 0) angle = 180;
        else if (dx == -1 && dy == -1) angle = 225;
        else if (dx == 0 && dy == -1) angle = 270;
        else if (dx == 1 && dy == -1) angle = 315;

		if (angle == dir && !stopped) {
			return;
		}

		dir = angle;
		stopped = false;
		player.stunt().forbiddenDirection = -1;
		
		double speed = 0;
		
		if(dx != 0 && dy != 0) {
			speed = 0.212;
		}
		else {
			speed = 0.3;
		}

		if (dx == -1)
			player.stunt().direction = Avatar.West;
		else if (dx == 1)
			player.stunt().direction = Avatar.East;
		else if (dy == -1)
			player.stunt().direction = Avatar.North;
		else if (dy == 1)
			player.stunt().direction = Avatar.South;
		
		player.stunt().set(dx * speed, dy * speed);
		player.stunt().walk(angle);
		
	}

	

	public void shoot() {
		Stunt playerStunt = player.stunt();
		if (playerStunt != null)
			playerStunt.shootOnce();
	}

	public void stop() {
		if (!stopped) {
			player.stunt().stop();
			stopped = true;
		}
	}
}
	
	
//	public void right() {
//	if (player.forbiddenDirection != Avatar.East) {
//		if (player.forbiddenDirection != -1)
//			player.set(0.3, 0);
//		player.forbiddenDirection = -1;
//		player.direction = Avatar.East;
//		player.walk(0);
//		stopped = false;
//	}
//}
//
//public void up() {
//	if (player.forbiddenDirection != Avatar.North) {
//		if (player.forbiddenDirection != -1)
//			player.set(0, -0.3);
//		player.forbiddenDirection = -1;
//		player.direction = Avatar.North;
//		player.walk(270);
//		stopped = false;
//	}
//}
//
//public void down() {
//	if (player.forbiddenDirection != Avatar.South) {
//		if (player.forbiddenDirection != -1)
//			player.set(0, 0.3);
//		player.forbiddenDirection = -1;
//		player.direction = Avatar.South;
//		player.walk(90);
//		stopped = false;
//	}
//}
	
//	public void left() {
//		if (player.forbiddenDirection != Avatar.West) {
//			if (player.forbiddenDirection != -1)
//				player.set(-0.3, 0);
//			player.forbiddenDirection = -1;
//			player.direction = Avatar.West;
//			player.walk(180);
//			stopped = false;
//		}
//	}
//}
