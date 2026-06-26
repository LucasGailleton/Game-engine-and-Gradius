package engine.GAL;

import engine.Entity;
import game.entities.EnemyChicken;

public class Egg extends GALAction{
	
	public Egg() {
		
	}

	@Override
	public boolean exec(Entity e) {
		// TODO Auto-generated method stub
		if(e instanceof EnemyChicken chick) {
			chick.egg();
			return true;
		}
		return false;
	}
}
