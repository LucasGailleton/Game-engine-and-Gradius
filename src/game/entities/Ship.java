package game.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.Grid.Position;
import engine.ISU;
import engine.ISU.Coord;
import engine.ISU.Vector;
import engine.ShipStunt;
import engine.shapes.Circle;
import engine.shapes.Rect;
import engine.view.Controller;
import engine.view.View;
import game.Main;
import game.Model;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;
import oop.tasks.Task;
import oop.utils.SoundPlayer;
import oop.tasks.Runnable;
import oop.tasks.Runtime;

public class Ship extends Entity implements Shooter {

	public static final double ShipSpeed = 5 * Main.windowScale;
	public static final int missileSpeed = 150;

	private double shootCooldown = 0.0; // Temps d'attente restant en secondes
	private final double SHOOT_DELAY = 0.15; // DELAI (on peut modifier)

	private static boolean hit = false;
	private static int hp = 3;
	private Task task;
	private static boolean gameOver = false;
	private View view;

	private SoundPlayer tir1, tir2, degat, mort;
	private int indexAudio;

	public static final int WEAPON_SIMPLE = 0;
	public static final int WEAPON_DOUBLE = 1;
	public static final int WEAPON_LASER = 2;
	private int weapon = WEAPON_SIMPLE;
	private int speedBoost = 0;
	private final List<Slave> slaves = new ArrayList<>();
	
	private Controller controller;
	
	private ShipStunt shipStunt;

	public Ship(View view) {
		super("Ship");
		this.setSize(game.isu.new Dimension(2.0 * Game.cmPerCell * Main.windowScale,
				1.0 * Game.cmPerCell * Main.windowScale));
		this.avatar = new ShipAvatar();
		this.task = Runtime.task();
		this.view = view;
		

		try {
			this.tir1 = new SoundPlayer(new File("Audio/Piou1.wav"));
			this.tir2 = new SoundPlayer(new File("Audio/Piou2.wav"));
			this.degat = new SoundPlayer(new File("Audio/Aie.wav"));
			this.mort = new SoundPlayer(new File("Audio/PlayerBoom1.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void initComponents(Model model) {
	    this.shipStunt = new ShipStunt(model, this);
	    this.controller = new Controller(this.shipStunt);
	}
	
	public ShipStunt getShipStunt() {
		return this.shipStunt;
	}

	public void tick(double elapsed, boolean shift) {
		
		double seconds = elapsed/1000;
		this.controller.recalculer();
		ramasserPowerUps ();
		double angularSpeed = aSpeed();
		if (angularSpeed != 0) {
			turn((int) (angularSpeed * seconds));

			List<Entity> collisions = this.model.getCollision(this);

			if (!collisions.isEmpty()) {
				turn((int) -(angularSpeed * seconds));
				setaSpeed(0);
				collision(collisions);
			}
		}

		// Annulation du scrolling
		if (shift)
			translate(isu.new Vector((-View.speed / 4.0) * Main.windowScale, 0.0));

		if (this.shootCooldown > 0) {
			this.shootCooldown -= seconds;
		}
	}

	@Override
	public void tryToShoot() {

		if (this.shootCooldown <= 0.0) {
			this.shoot();
			this.shootCooldown = SHOOT_DELAY;
		}
	}
	@Override
	public void shoot() {

		// Code de Dario
		Position pos = this.position();

		if (this.weapon == WEAPON_LASER) {
			LaserShot laser = new LaserShot(view, "laser");
			laser.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 50, pos.y * Game.cmPerCell));
			laser.setBounding();
			laser.setlSpeed(missileSpeed * 2, 0);
			this.model.add(laser);
			this.model.getView().add(laser.getAvatar());
		} else {
			MissilePlayer msp = new MissilePlayer(view, "missile simple");
			msp.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 20, pos.y * Game.cmPerCell));
			msp.setBounding();
			msp.setlSpeed(missileSpeed, 0);
			this.model.add(msp);
			this.model.getView().add(msp.getAvatar());

			// power-up du 2e tir a 45 deg vers le bas
			if (this.weapon == WEAPON_DOUBLE) {
				MissilePlayer msp2 = new MissilePlayer(view, "missile double");
				msp2.setCoord(Game.game().isu.new Coord(pos.x * Game.cmPerCell + 20, pos.y * Game.cmPerCell));
				msp2.setBounding();
				msp2.setlSpeed(missileSpeed, missileSpeed);
				this.model.add(msp2);
				this.model.getView().add(msp2.getAvatar());
			}
		}
		for (int i=0; i<this.slaves.size(); i++) {
			ISU.Coord oc = slaveCoord(i);
			MissilePlayer m = new MissilePlayer(view, "slave shot");
			m.setCoord(this.isu.new Coord(oc.x_cm + 20, oc.y_cm));
			m.setBounding();
			m.setlSpeed(missileSpeed, 0);
			this.model.add(m);
			this.model.getView().add(m.getAvatar());
		}
		// Fin du code de Dario

		if (indexAudio % 2 == 0)
			tir1.play(1);
		else
			tir2.play(1);
		indexAudio++;
	}

	public boolean getHit() {
		return hit;
	}

	public void gotHit() {

		if (hit || gameOver)
			return;

		hp--;

		if (hp <= 0) {
			// game over : explosion + le vaisseau disparait
			this.mort.play(1);
			gameOver = true;
			setlSpeed(0, 0);
			((ShipAvatar) avatar).exploser();
			return;
		}

		this.degat.play(1);
		hit = true;

		ShipAvatar sa = (ShipAvatar) avatar;

		if (!sa.clignote)
			sa.clignoter();

		// 3 secondes d'invicibilité
		task.post(() -> {
			hit = false;
		}, 3000);
	}

	public void applyPowerUp(int type) {
		switch (type) {
		case PowerUp.SPEED:
			this.speedBoost++;
			break;
		case PowerUp.DOUBLE:
			this.weapon = WEAPON_DOUBLE;
			break;
		case PowerUp.LASER:
			this.weapon = WEAPON_LASER;
			break;
		case PowerUp.SLAVE:
			addSlave();
			break;
		default:
			break;
		}
		System.out.println("[Ship] Power-up ramasse ! type = " + type);
	}

	public double walkSpeed() {
		return 15.0 + this.speedBoost * 5.0;
	}

	private void addSlave() {
		if (this.slaves.size() >= 4)
			return;
		Slave s = new Slave(this, this.slaves.size());
		this.model.getView().add(s.getAvatar());
		this.slaves.add(s);
	}

	// alternance au-dessus / en dessous du vaisseau
	public ISU.Coord slaveCoord(int i) {
		double spacing = Game.cmPerCell * 2.0 * Main.windowScale;
		int rang = i / 2 + 1;
		int sign = (i % 2 == 0) ? -1 : 1;
		return this.isu.new Coord(this.center().x_cm, this.center().y_cm + sign * rang * spacing);
	}

	private void ramasserPowerUps() {
		for (Entity e : new ArrayList<Entity>(this.model.entities())) {
			if (e instanceof PowerUp && this.center().distanceTo(e.center()) < (this.size.x_cm + e.size.x_cm) / 2.0) {
				applyPowerUp(((PowerUp) e).type());
				e.die();
			}
		}
	}

	public class ShipAvatar extends Avatar {

		private List<BufferedImage> death;

		public static final int spriteSizeX = 32;
		public static final int spriteSizeY = 16;
		public static final int spriteNeutral = 0;
		public static final int spriteUp = 1;
		public static final int spriteDown = 2;

		private boolean visible = true;
		private boolean clignote = false;
		private boolean exploding = false;
		private int deathIndex = 0;

		public ShipAvatar() {
			super(Ship.this);
			Ship.this.avatar = this;
			this.direction = East;
			this.death = new ArrayList<>();
		}

		public void clignoter() {
			if (!Ship.this.getHit()) {
				this.visible = true;
				this.clignote = false;
				return;
			}

			this.visible = !this.visible;
			this.clignote = true;

			task.post(new Runnable() {
				@Override
				public void run() throws Exception {
					clignoter();
				}
			}, 50);

		}

		public void exploser() {
			this.exploding = true;
			this.visible = true;
			this.deathIndex = 0;
			avancerExplosion();
		}

		private void avancerExplosion() {
			if (this.deathIndex >= this.death.size()) {
				this.exploding = false;
				this.visible = false;
				return;
			}
			task.post(new Runnable() {
				@Override
				public void run() throws Exception {
					deathIndex++;
					avancerExplosion();
				}
			}, 120);
		}

		@Override
		public void getSprites(Graphics g) {
			BufferedImage imgSprites = g.load("all_Sprites.png");
			this.sprites = new BufferedImage[4][3];

			sprites[East][spriteNeutral] = imgSprites.getSubimage(262, 4, spriteSizeX, spriteSizeY);
			sprites[East][spriteUp] = imgSprites.getSubimage(295, 4, spriteSizeX, spriteSizeY);
			sprites[East][spriteDown] = imgSprites.getSubimage(327, 4, spriteSizeX, spriteSizeY);

			this.death.clear();
			this.death.add(imgSprites.getSubimage(238, 29, 24, 24));
			this.death.add(imgSprites.getSubimage(267, 25, 24, 24));
			this.death.add(imgSprites.getSubimage(298, 21, 24, 24));
			this.death.add(imgSprites.getSubimage(330, 27, 24, 24));
		}

		@Override
		public void paint(Graphics g, int shiftPX) {
			if (this.sprites == null)
				this.getSprites(g);

			ISU.Coord center = Ship.this.center();
			int posX = this.toPixel(center.x_cm) - ((int) (spriteSizeX * Main.windowScale) / 2);
			int posY = this.toPixel(center.y_cm) - ((int) (spriteSizeY * Main.windowScale) / 2);

			int index;
			ISU.Vector lSpeed = Ship.this.lSpeed();
			if (lSpeed.targetY_cm == 0)
				index = spriteNeutral;
			else if (lSpeed.targetY_cm > 0)
				index = spriteDown;
			else
				index = spriteUp;

			String vies = "Player HP: " + Ship.hp;
			g.setColor(Colors.green);
			g.setFont(g.getFont("Monospaced", 0, 16));
			g.drawString(vies, 10, 120);

			if (this.exploding) {
				int i = Math.min(this.deathIndex, this.death.size() - 1);
				g.drawImage(this.death.get(i), posX - 3 + shiftPX, posY - 3, (int) (spriteSizeX * Main.windowScale),
						(int) (spriteSizeY * Main.windowScale));
			} else if (this.visible) {
				g.drawImage(sprites[direction][index], posX - 3 + shiftPX, posY - 3,
						(int) (spriteSizeX * Main.windowScale), (int) (spriteSizeY * Main.windowScale));
			}

			if (gameOver) {
				g.setColor(Colors.red);
				g.setFont(g.getFont("Monospaced", 1, 40));
				g.drawString("GAME OVER", 400, 480);
			}
		}

	}
}
