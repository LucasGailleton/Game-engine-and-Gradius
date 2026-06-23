package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.Main;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class LaserShot extends MissilePlayer {

	public LaserShot(String name) {
		super(name);
		this.setSize(this.isu.new Dimension(Game.cmPerCell*3*Main.windowScale, (Game.cmPerCell/3.0)*Main.windowScale));
		this.avatar = new LaserAvatar(this);
	}

	public class LaserAvatar extends Avatar {

		public LaserAvatar(Entity e) {
			super(e);
		}

		@Override
		public void getSprites(Graphics g) {
			// j'ai pas encore le sprite du laser pr le moment
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			ISU.Coord c = LaserShot.this.center();
			int w = (int) (40 * Main.windowScale);
			int h = (int) (3 * Main.windowScale);
			int posX = this.toPixel(c.x_cm) - w / 2 + shiftPX;
			int posY = this.toPixel(c.y_cm) - h / 2;
			g.setColor(Colors.cyan);
			g.fillRect(posX, posY, w, h);
		}
	}
}
