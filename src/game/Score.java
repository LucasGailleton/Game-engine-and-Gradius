package game;

import oop.graphics.Graphics;

public class Score {
	
	private int value;

    // Barème des points
    private static final int ENEMY_KILL = 100;
    private static final int POWERUP = 500;
    private static final int HIT_PENALTY = 200;
    private static final int SUB_BOSS_KILL = 5000;
    private static final int BOSS_KILL = 2000;
	
	public Score() {
		this.value = 0;
	}
	
	public void addEnemyKill() {
        this.value += ENEMY_KILL;
    }

    public void addPowerUp() {
        this.value += POWERUP;
    }
    
    public void addSubBossKill() {
        this.value += SUB_BOSS_KILL;
    }
    
    public void addBossKill() {
        this.value += BOSS_KILL;
    }

    public void subtractHit() {
        this.value -= HIT_PENALTY;
    }

    public int getScore() {
        return this.value;
    }

    public void reset() {
        this.value = 0;
    }
    
    
    public void paint(Graphics g) {
    	
    	g.drawString("Score : "+ this.value, (int) (3*Main.windowScale), (int) (12*Main.windowScale));
    	g.setFont(g.getFont("Monospaced", 1, 40));
    	g.drawString("SCORE : " + this.value, (int)(Main.windowScale*30),(int)(Main.windowScale * 225));
    }
}
