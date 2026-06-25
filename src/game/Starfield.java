package game;

import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class Starfield {

	private final int W = (int) (256 * Main.windowScale);
	private final int H = (int) (240 * Main.windowScale) + Main.headerSize;

	private final int nb = 80;
	private final double[] x = new double[nb];
	private final double[] y = new double[nb];
	private final double[] vitesse = new double[nb];

	public Starfield() {
		for (int i = 0; i < nb; i++) {
			x[i] = Math.random() * W;
			placer(i);
		}
	}

	private void placer(int i) {
		y[i] = Math.random() * H;
		vitesse[i] = 1 + Math.random() * 4;
	}

	public void paint(Graphics g) {
		g.setColor(Colors.white);
		for (int i = 0; i < nb; i++) {
			x[i] -= vitesse[i];
			if (x[i] < 0) {
				x[i] = W;
				placer(i);
			}
			int taille = vitesse[i] > 3 ? 2 : 1;
			g.fillRect((int) x[i], (int) y[i], taille, taille);
		}
	}
}
