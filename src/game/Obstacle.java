package game;

import engine.Entity;
import engine.Game;
import engine.shapes.Rect;
import game.PacMan.PacManAvatar;
import oop.graphics.Graphics;

public class Obstacle extends Entity {

	private ObstacleAvatar self;

	// CONSTRUCTOR
	public Obstacle(int x_ncell, int y_ncell) {
		super("Obstacle");
		this.setSize(this.isu.new Dimension(Game.cmPerCell, Game.cmPerCell));
		this.setPosition(this.grid.new Position(x_ncell, y_ncell));
		this.setlSpeed(0.0, 0.0);
		this.setBounding();
		this.occupy(this.position());
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Rect(this.center(), this.size, this.orientation()));
	}
	
	public class ObstacleAvatar extends Avatar {

		public ObstacleAvatar() {
			super(Obstacle.this);
			Obstacle.this.self = this;
		}

		@Override
		public void getSprites(Graphics g) {}

		@Override
		public void paint(Graphics g) {}
		
	}

}
