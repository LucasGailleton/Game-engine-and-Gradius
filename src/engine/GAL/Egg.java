package engine.GAL;

import engine.Entity;

public class Egg extends GALAction{
	Entity child;
	
	public Egg() {
		
	}
	public void set(Entity e) {
		this.child=e;
	}
	@Override
	public boolean exec(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
}
