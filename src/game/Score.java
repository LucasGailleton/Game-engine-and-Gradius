package game;

import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class Score {
	
	private int value;

    // Barème des points
    private static final int ENEMY_KILL = 100;
    private static final int POWERUP = 500;
    private static final int HIT_PENALTY = -200;
    private static final int SUB_BOSS_KILL = 2000;
    private static final int BOSS_KILL = 5000;
	
	public Score() {
		this.value = 0;
	}
	
	public void addEnemyKill() {
        this.value += ENEMY_KILL;
        this.checkValue();
    }

    private void checkValue() {
		if (value < 0)
			value = 0;
	}

	public void addPowerUp() {
        this.value += POWERUP;
        this.checkValue();
    }
    
    public void addSubBossKill() {
        this.value += SUB_BOSS_KILL;
        this.checkValue();
    }
    
    public void addBossKill() {
        this.value += BOSS_KILL;
        this.checkValue();
    }

    public void subtractHit() {
        this.value += HIT_PENALTY;
        this.checkValue();
    }

    public int getScore() {
        return this.value;
    }

    public void reset() {
        this.value = 0;
    }
    
    
    public void paint(Graphics g) {
    	g.setColor(Colors.green);
    	g.setFont(g.getFont("Monospaced", 1, 40));
    	g.drawString("SCORE : " + this.value, (int)(Main.windowScale*30),(int)(Main.windowScale * 225));
    }
}
