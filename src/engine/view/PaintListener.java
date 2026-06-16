package engine.view;

import java.util.List;

import engine.Game;
import oop.graphics.BufferedImage;
import oop.graphics.Canvas;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class PaintListener implements Canvas.PaintListener {
	private View view;

	public PaintListener(View view) {
		this.view = view;
	}

	@Override
	public void visible(Canvas canvas) {}

	@Override
	public void paint(Canvas canvas, Graphics g) {
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		g.setColor(Colors.black);
		g.fillRect(0, 0, w, h);
		
		if (view != null)
			view.paint(g);
	}

	@Override
	public void revoked(Canvas canvas) {
		System.exit(0);
	}
}