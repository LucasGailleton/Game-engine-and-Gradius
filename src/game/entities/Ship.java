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
import engine.ISU.Dimension;
import engine.ISU.Vector;
import engine.LaserStunt;
import engine.ShipStunt;
import engine.shapes.Circle;
import engine.shapes.Rect;
import engine.view.Controller;
import engine.view.View;
import game.Main;
import game.MapBuilder;
import game.Model;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;
import oop.tasks.Task;
import oop.utils.SoundPlayer;
import oop.tasks.Runnable;
import oop.tasks.Runtime;

public class Ship extends Entity {

	public static double ShipSpeed = 20 * Main.windowScale;
	public static final int missileSpeed = 850;

	private double shootCooldown = 0.0; // Temps d'attente restant en secondes
	private final double SHOOT_DELAY = 0.15; // DELAI (on peut modifier)

	private static boolean hit = false;
	private static int hp = 3;
	private Task task;
	
	private View view;

	private SoundPlayer tir1, tir2, degat, mort;
	private int indexAudio;

	private int powerUp;
	public boolean isShooting;
	public static boolean double_tir = false;
	public final List<Slave> slaves = new ArrayList<>();

	public Ship(View view) {
		super("Ship");
		this.setSize(game.isu.new Dimension(16.0*Main.windowScale, 12.0*Main.windowScale));
		this.avatar = new ShipAvatar();
		this.task = Runtime.task();
		this.view = view;
		this.powerUp = 0;

		try {
			this.tir1 = new SoundPlayer(new File("Audio/Piou1.wav"));
			this.tir2 = new SoundPlayer(new File("Audio/Piou2.wav"));
			this.degat = new SoundPlayer(new File("Audio/Aie.wav"));
			this.mort = new SoundPlayer(new File("Audio/PlayerBoom1.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void setBounding() {
		double x_cm = this.size.x_cm;
		double y_cm = this.size.y_cm;
		
		ISU.Coord center = this.center();
		double x = center.x_cm;
		double y = center.y_cm;
		
		ISU.Dimension shapeSize;
		ISU.Coord shapeCenter;
		
		shapeSize = isu.new Dimension(x_cm/1.4, y_cm/2);
		shapeCenter = isu.new Coord(x, y);
		this.bounding().add(new Rect(shapeCenter, shapeSize, 0));
	}
	

	public void tick(double elapsed, boolean shift) {
		double seconds = elapsed /1000;
		pickUpPowerUps();
		
	    int taille_map = (int) (Game.game().width_ncell * Game.cmPerCell * Game.pixelPerCm);
	    ISU.Coord center = this.center();
	    int entityX = (int) (center.x_cm * Game.pixelPerCm);
	    int entityY = (int) (center.y_cm * Game.pixelPerCm);
	    
	    int shift1 = View.shiftPX;
	    int screenX = entityX + shift1;
	    int w = view.canvas().getWidth();

	    if (screenX < -300) {
	        shift1 += taille_map;
	    } else if (screenX > w + 300) {
	        shift1 -= taille_map;
	    }

	    int bordX = entityX + shift1;

	    // Limites bord gauche et droit
	    if (bordX < 0) {
	    	this.translate(isu.new Vector(ShipSpeed*seconds, 0.0));
	    } else if (bordX > w) {
	    	this.translate(isu.new Vector(-ShipSpeed*seconds, 0.0));
	    }
		
	    // Limites haut et bas
	    if (entityY < 0) {
	    	this.translate(isu.new Vector(0.0, ShipSpeed*seconds));
	    } else if (entityY > 210 * Main.windowScale) {
	    	this.translate(isu.new Vector(0.0, -ShipSpeed*seconds));
	    }
	    
		// Annulation du scrolling
		if (shift && !Main.GAMEOVER)
			translate(isu.new Vector((-View.speed / 2.0) * Main.windowScale, 0.0));

		if (this.shootCooldown > 0) {
			this.shootCooldown -= seconds;
		}
	}

	public boolean getHit() {
		return hit;
	}

	public static boolean isGameOver() {
		return Main.GAMEOVER;
	}

	// reset le vaisseau pour une nouvelle partie
	public static void reset() {
		hp = 3;
		hit = false;
		Main.GAMEOVER = false;
		double_tir = false;
	}

	public void gotHit() {
		if (hit || Main.GAMEOVER)
			return;
		hp--;

		if (hp <= 0) {
			// game over : explosion + le vaisseau disparait
			this.mort.play(1);
			Main.GAMEOVER = true;
			setlSpeed(0, 0);
			View.viewSpeed = 0;
			((ShipAvatar) avatar).explode();
			task.post(() -> {
				this.die();
			}, 200);
			return;
		}

		this.degat.play(1);
		hit = true;

		ShipAvatar sa = (ShipAvatar) avatar;

		if (!sa.clignote)
			sa.clignoter();

		// 1.5 secondes d'invicibilité
		task.post(() -> {
			hit = false;
		}, 1500);
	}
	
	public void upgradePowerUp() {
		if (this.powerUp <= 4)
			this.powerUp++;
		System.out.println("Augumenation de powerUp : " + this.powerUp);
		switch (this.powerUp) {
		case PowerUp.Speed: Ship.ShipSpeed *= 1.5; break;
		case PowerUp.Double: Ship.double_tir = true; break;
		case PowerUp.Laser:
			LaserStunt ls = new LaserStunt(this.model, this);
			ls.direction = this.stunt().direction;
			this.setStunt(ls); 
			break;
		case PowerUp.Slave: this.addSlave(); break;
		default: break;
		}
	}
	
	public boolean canShoot() {
		return this.shootCooldown <= 0.0;
	}

	public void resetCooldown() {
		this.shootCooldown = SHOOT_DELAY;
	}

	public void playSound() {
		if (indexAudio % 2 == 0) tir1.play(1);
		else tir2.play(1);
		indexAudio++;
	}

	public void triggerSlaves() {
		for (int i=0; i<this.slaves.size(); i++) {
			ISU.Coord oc = slaveCoord(i);
			MissilePlayer m = new MissilePlayer(view, "slave shot");
			m.setCoord(this.isu.new Coord(oc.x_cm + 20, oc.y_cm));
			m.setlSpeed(missileSpeed, 0);
			Main.mb.placeEntity(m);
		}
	}

	private void addSlave() {
		if (this.slaves.size() >= 4)
			return;
		Slave s = new Slave(this, this.slaves.size());
		this.model.getView().add(s.getAvatar());
		this.model.add(s);
		this.slaves.add(s);
	}

	// alternance au-dessus / en dessous du vaisseau
	public ISU.Coord slaveCoord(int i) {
		double spacing = Game.cmPerCell * 2.0 * Main.windowScale;
		int rang = i / 2 + 1;
		int sign = (i % 2 == 0) ? -1 : 1;
		return this.isu.new Coord(this.center().x_cm, this.center().y_cm + sign * rang * spacing);
	}

	private void pickUpPowerUps() {
		for (Entity e : this.trueCollisions) {
			if (e instanceof PowerUp) {
				upgradePowerUp();
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

		public void explode() {
			List<Slave> slaves = Ship.this.slaves; 
			if (!slaves.isEmpty()) {
				for (Slave s : slaves) {
					Main.task.post(()->{
						s.explode = true;
					}, 100);
				}
			}
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
			this.death.add(imgSprites.getSubimage(238, 29, 24, 12));
			this.death.add(imgSprites.getSubimage(267, 25, 24, 12));
			this.death.add(imgSprites.getSubimage(298, 21, 24, 12));
			this.death.add(imgSprites.getSubimage(330, 27, 24, 12));
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
			if (Main.debug)
				g.drawString(vies, 10, 120);
			else
				g.drawString(vies, 10, 23);

			if (this.exploding) {
				int i = Math.min(this.deathIndex, this.death.size() - 1);
				g.drawImage(this.death.get(i), posX-3+shiftPX, posY-3, (int) (spriteSizeX * Main.windowScale),(int) (spriteSizeY * Main.windowScale));
			} else if (this.visible) {
				g.drawImage(sprites[direction][index], posX-3+shiftPX, posY-3, (int) (spriteSizeX * Main.windowScale), (int) (spriteSizeY * Main.windowScale));
			}

			if (Main.GAMEOVER) {
				g.setColor(Colors.red);
				g.setFont(g.getFont("Monospaced", 1, 40));
				g.drawString("GAME OVER", 400, 480);
			}
		}

	}

	public View getView() {
		return this.view;
	}
}
