package engine.view;

import oop.graphics.Canvas;
import oop.graphics.VirtualKeyCodes;

public class KeyListener implements Canvas.KeyListener {
	Controller c;

	public KeyListener(Controller c) {
		this.c = c;
	}

	@Override
	public void pressed(Canvas canvas, int keyCode, char keyChar) {
		if (keyCode == VirtualKeyCodes.VK_LEFT)
			c.left();
		else if (keyCode == VirtualKeyCodes.VK_RIGHT)
			c.right();
		else if (keyCode == VirtualKeyCodes.VK_UP)
			c.up();
		else if (keyCode == VirtualKeyCodes.VK_DOWN)
			c.down();
		canvas.repaint();
	}

	@Override
	public void released(Canvas canvas, int keyCode, char keyChar) {}

	@Override
	public void typed(Canvas canvas, char keyChar) {}
}