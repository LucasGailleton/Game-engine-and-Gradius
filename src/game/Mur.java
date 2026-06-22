package game;

import engine.Entity;
import engine.Game;
import engine.ISU;
import game.entities.Avatar;
import oop.graphics.Graphics;

public class Mur extends Entity {

	public Mur(int x, int y) {
		super("mur");
		
		this.setSize(this.isu.new Dimension(
				(int)(150 * Game.cmPerCell * Main.windowScale),
				(int)(2 * Game.cmPerCell * Main.windowScale)
		));
		
		this.setCoord(isu.new Coord(
				x * Game.cmPerCell * Main.windowScale, 
				y * Game.cmPerCell * Main.windowScale
		));

		this.deploy();
		this.setBounding();		
		this.avatar = new MurAvatar();
	}
	
	public class MurAvatar extends Avatar {
		public MurAvatar() {
			super(Mur.this);
		}

		@Override
		public void getSprites(Graphics g) {
			// Pas de sprite pour un mur invisible
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			// On ne dessine aucune image. 
			// Mais grâce à cet avatar, View.java dessignera la hitbox rouge par-dessus !
		}
	}
}