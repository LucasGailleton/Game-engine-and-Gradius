package game.entities;

import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.ISU.Dimension;
import engine.shapes.Rect;
import engine.view.View;
import game.Main;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

//A voir si on fait comme ça parce que ya pas les sprites pour le haut donc faurdrait faire des rotations
// En soit on peut aussi juste faire les sprites en les retournant dans un logiciel
// Mais pourquoi pas tenter de les tourner directemement dans le code

public class Borders extends Entity {
	protected int chosenSprite;
	protected int chosenPos;
	private View view;

	public Borders(String name, int chosenSprite, int chosenPos, View view) {
		super(name);
		this.setSize(this.isu.new Dimension(8.6 * Game.cmPerCell * Main.windowScale,
				2.5 * Game.cmPerCell * Main.windowScale));
		this.chosenSprite = chosenSprite;
		this.chosenPos = chosenPos;
		this.avatar = new BorderAvatar(this);
		this.view = view;
	}

	@Override
	public void setBounding() {
		ISU.Coord center = this.center();
		ISU.Coord centerBorder;
		double factor;
		switch (chosenSprite) {
		case 0: factor = (chosenPos == BorderAvatar.Top) ? -1.4 : 1.7; break;
		case 1: factor = (chosenPos == BorderAvatar.Top) ? -1.5 : 1.8; break;
		case 2, 3: factor = (chosenPos == BorderAvatar.Top) ? -1.0 : 1.3; break;
		default: factor = (chosenPos == BorderAvatar.Top) ? -1.3 : 1.6; break;		// Case 4
		}
		centerBorder = Game.game().isu.new Coord(center.x_cm, center.y_cm + factor * Game.cmPerCell * Main.windowScale);
		this.bounding().add(new Rect(centerBorder, this.size, this.orientation()));
	}

	@Override
	public void tick(double seconds, boolean shift) {
		if (shift)
			translate(isu.new Vector((View.speed / 4.0) * Main.windowScale, 0.0));

		// On calcule la position logique du bord GAUCHE de la caméra dans le monde.
		// (J'utilise votre facteur 0.5 exactement comme dans votre missile)
		double leftScreenEdge = -(view.shiftPX * 0.5);

		// On vérifie si la bordure est passée à gauche de cette ligne
		// (On soustrait aussi la moitié de sa taille pour qu'elle disparaisse sans
		// effet de coupure "popping")
		if (this.center().x_cm <= leftScreenEdge - (this.size.x_cm / 2.0)) {
			this.die();
		}
	}

	public class BorderAvatar extends Avatar {

		public static final int spriteSizeX = 64;
		public static final int spriteSizeY = 19; // était 12
		public final static int Top = 1;
		public final static int Bottom = 0;

		public BorderAvatar(Entity e) {
			super(e);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("obstacles.png");
			this.sprites = new BufferedImage[5][2];

			// Sprite de commencement de la grotte
			this.sprites[0][Bottom] = imgSprites.getSubimage(5, 110, spriteSizeX, spriteSizeY);
			this.sprites[0][Top] = imgSprites.getSubimage(80, 46, spriteSizeX, spriteSizeY);

			// Sprite intermédiaire
			this.sprites[1][Bottom] = imgSprites.getSubimage(6, 150, spriteSizeX, spriteSizeY);
			this.sprites[1][Top] = imgSprites.getSubimage(6, 177, spriteSizeX, spriteSizeY);

			// Sprite intermédiaire avec petite montagne
			this.sprites[2][Bottom] = imgSprites.getSubimage(74, 150, spriteSizeX, spriteSizeY);
			this.sprites[2][Top] = imgSprites.getSubimage(73, 177, spriteSizeX, spriteSizeY);

			// Sprite intermédiare épais
			this.sprites[3][Bottom] = imgSprites.getSubimage(142, 150, spriteSizeX, spriteSizeY);
			this.sprites[3][Top] = imgSprites.getSubimage(142, 177, spriteSizeX, spriteSizeY);

			// Sprite intermédiaire
			this.sprites[4][Bottom] = imgSprites.getSubimage(213, 149, spriteSizeX, spriteSizeY);
			this.sprites[4][Top] = imgSprites.getSubimage(213, 177, spriteSizeX, spriteSizeY);

		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (sprites == null)
				this.getSprites(g);

			ISU.Coord center = Borders.this.center();
//            int posX = this.toPixel(center.x_cm - (Borders.this.size.x_cm / 2));
//            int posY = this.toPixel(center.y_cm - (Borders.this.size.y_cm / 2));
			int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
			int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);
			BufferedImage currentImg = sprites[chosenSprite][chosenPos];
			g.drawImage(currentImg, posX + shiftPX, posY, (int) (spriteSizeX * Main.windowScale),
					(int) (spriteSizeY * Main.windowScale));

		}

	}

}
