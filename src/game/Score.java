package game;

public class Score {
	
	private int value;

    // Barème des points
    private static final int ENEMY_KILL = 100;
    private static final int POWERUP = 500;
    private static final int HIT_PENALTY = 200;
	
	public Score() {
		this.value = 0;
	}
	
	public void addEnemyKill() {
        this.value += ENEMY_KILL;
    }

    public void addPowerUp() {
        this.value += POWERUP;
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
}
