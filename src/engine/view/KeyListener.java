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
		c.update(keyCode, true);
	}

	@Override
	public void released(Canvas canvas, int keyCode, char keyChar) {
		c.update(keyCode, false);
	}

	@Override
	public void typed(Canvas canvas, char keyChar) {}
}