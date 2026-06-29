package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;
import oop.tasks.Runnable;

// projectile qui part ligne droite et tu peux l'orienter vers une cible avec Tir.viser(...)
public class EnemyShot extends Entity {

	private int age = 0;

	public EnemyShot(String name) {
		super(name);
		this.setSize(this.isu.new Dimension(Game.cmPerCell/Main.windowScale, Game.cmPerCell/Main.windowScale));
		this.avatar = new EnemyShotAvatar(this);
		planifierNettoyage();
	}

	private void planifierNettoyage() {
		Main.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				if (getModel() == null) {		   // pas encore ajoute au modele
					Main.task.post(this, 16);
					return;
				}
				age++;
				
				// on a touché qqch (lSpeed remis a 0) donc on le supprime
				if (lSpeed().targetX_cm == 0 && lSpeed().targetY_cm == 0) {
					die();return;
				}
				
				// durée de vie écoulée, ou sorti en haut/bas (sinon ça crash) donc on le supprime
				int y = position().y;
				if (age > 150 || y < 10 || y > grid.height() - 10) {
					die();
					return;
				}
				Main.task.post(this, 16);
			}
		}, 16);
	}
	
	@Override
	public void tick(double elapsed, boolean shift) {}

	private class EnemyShotAvatar extends Avatar {

		public EnemyShotAvatar(Entity e) {
			super(e);
		}

		@Override
		public void getSprites(Graphics g) {}

		@Override
		public void paint(Graphics g, int shiftPX) {
			ISU.Coord c = EnemyShot.this.center();
			int s = (int) (4 * Main.windowScale);
			int posX = this.toPixel(c.x_cm) - s / 2 + shiftPX;
			int posY = this.toPixel(c.y_cm) - s / 2;
			g.setColor(Colors.red);
			g.fillOval(posX, posY, s, s);
		}
	}
}
