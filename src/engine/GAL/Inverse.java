package engine.GAL;

import engine.Entity;

public class Inverse implements iGALCondition{
	iGALCondition cond;
	
	public Inverse(iGALCondition c){
		this.cond=c;
	}
	@Override
	public boolean eval(Entity e) {
		// TODO Auto-generated method stub
		return !cond.eval(e);
	}

	@Override
	public Variable variable() {
		// TODO Auto-generated method stub
		return null;
	}

}
