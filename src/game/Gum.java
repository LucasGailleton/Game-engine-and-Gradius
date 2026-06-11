// == GUM ==
package game;

import engine.Entity;
import engine.Game;
import engine.Grid.Position;
import engine.ISU.Dimension;
import engine.shapes.Circle;
import oop.graphics.BufferedImage;
import oop.graphics.Canvas;
import oop.graphics.Graphics;

public class Gum extends Entity {

	// CONSTRUCTOR
	public Gum() {
		super("Gum");
		this.setSize(this.isu.new Dimension(Game.cmPerCell/3, Game.cmPerCell/3));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Circle(this.center(), 0.1));
	}
	
	public class GumAvatar extends Avatar {

		public GumAvatar() {
			super(Gum.this);
		}

		@Override
		public void getSprites(Graphics g) {
			this.sprites = new BufferedImage[1][1];
		}

		@Override
		public void paint(Graphics g) {
			getSprites(g);
			g.drawImage(sprites[0][0], Gum.this.position().x, Gum.this.position().y);
		}
		
	}

}
