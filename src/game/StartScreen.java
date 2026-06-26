package game;

import debugmode.ProfilingManager;
import engine.view.Controller;
import engine.view.View;
import oop.graphics.Canvas;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;
import oop.graphics.VirtualKeyCodes;

public class StartScreen implements Canvas.PaintListener, Canvas.KeyListener {

	private static final int MENU = 0;
	private static final int CREDITS = 1;
	private static final int GAMEOVER = 2;

	private final Main main;
	private final Starfield etoiles = new Starfield();
	private BufferedImage ship;
	private int frame = 0;
	private int page = MENU;
	private boolean lance = false;

	private View gameView;
	private Controller controller;
	private boolean playing = false;

	private final String[] equipe = {
		"Lucas Gailleton",
		"Amaury de Valicourt",
		"Clément Alexandre",
		"Erwan Fontaine",
		"Dario Nonis"
	};

	public StartScreen(Main main) {
		this.main = main;
	}

	public void playGame(View view, Controller controller) {
		this.gameView = view;
		this.controller = controller;
		this.playing = true;
	}

	public void showGameOver() {
		this.playing = false;
		this.page = GAMEOVER;
		this.lance = false;     // pour pouvoir rejouer
	}

	private void texteCentre(Graphics g, Canvas canvas, String texte, int taille, int style, int y) {
		g.setFont(g.getFont("Monospaced", style, taille));
		int largeur = (int) (texte.length() * taille * 0.6);
		g.drawString(texte, (canvas.getWidth() - largeur) / 2, y);
	}

	@Override
	public void paint(Canvas canvas, Graphics g) {
		g.setColor(Colors.black);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		if (this.playing) {
			if (this.gameView != null)
				this.gameView.paint(g);
			return;
		}

		this.frame++;
		this.etoiles.paint(g);

		if (this.page == CREDITS)
			paintCredits(canvas, g);
		else
			paintMenu(canvas, g);
	}

	private void paintMenu(Canvas canvas, Graphics g) {
		int cy = canvas.getHeight() / 2;

		if (this.page == GAMEOVER) {
			g.setColor(Colors.red);
			texteCentre(g, canvas, "GAME OVER", 80, 1, cy - 150);
		} else {
			g.setColor(Colors.cyan);
			texteCentre(g, canvas, "GRADIUS", 90, 1, cy - 150);
		}

		if (this.ship == null)
			this.ship = g.load("all_Sprites.png").getSubimage(262, 4, 32, 16);
		g.drawImage(this.ship, (canvas.getWidth() - 160) / 2, cy - 100, 160, 80);

		if ((this.frame / 30) % 2 == 0) {
			g.setColor(Colors.white);
			String txt = (this.page == GAMEOVER) ? "Appuyez sur ESPACE pour rejouer" : "Appuyez sur ESPACE pour jouer";
			texteCentre(g, canvas, txt, 28, 0, cy + 50);
		}

		g.setColor(Colors.lightGray);
		texteCentre(g, canvas, "D : Mode debug", 22, 0, cy + 130);
		texteCentre(g, canvas, "C : Crédits", 22, 0, cy + 170);
	}

	private void paintCredits(Canvas canvas, Graphics g) {
		int cy = canvas.getHeight() / 2;

		g.setColor(Colors.yellow);
		texteCentre(g, canvas, "CRÉDITS", 70, 1, cy - 230);

		g.setColor(Colors.white);
		texteCentre(g, canvas, "Équipe 10 - PLE 2026", 26, 1, cy - 110);

		g.setColor(Colors.lightGray);
		for (int i = 0; i < this.equipe.length; i++)
			texteCentre(g, canvas, this.equipe[i], 22, 0, cy - 40 + i * 45);

		g.setColor(Colors.gray);
		texteCentre(g, canvas, "ECHAP : retour", 20, 0, cy + 220);
	}

	@Override
	public void pressed(Canvas canvas, int keyCode, char keyChar) {
		if (this.playing) {
			if (this.controller != null)
				this.controller.update(keyCode, true);
			return;
		}

		if (this.page == CREDITS) {
			if (keyCode == VirtualKeyCodes.VK_ESCAPE)
				this.page = MENU;
			return;
		}

		// pages menu et game over
		if (keyCode == VirtualKeyCodes.VK_C) {
			this.page = CREDITS;
		} else if (!this.lance && (keyCode == VirtualKeyCodes.VK_SPACE || keyCode == VirtualKeyCodes.VK_ENTER)) {
			this.lance = true;
			Main.debug = false; 
			this.main.startGame();
		} else if (!this.lance && keyCode == VirtualKeyCodes.VK_D) {
			this.lance = true;
			Main.debug = true; 
			ProfilingManager.PROFILING = true;
			this.main.startGame();
		}
	}

	@Override
	public void visible(Canvas canvas) {}

	@Override
	public void revoked(Canvas canvas) {
		System.exit(0);
	}

	@Override
	public void released(Canvas canvas, int keyCode, char keyChar) {
		if (this.playing && this.controller != null)
			this.controller.update(keyCode, false);
	}

	@Override
	public void typed(Canvas canvas, char keyChar) {}
}
