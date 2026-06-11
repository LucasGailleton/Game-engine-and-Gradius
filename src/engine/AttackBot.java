package engine;

import java.util.List;

public class AttackBot extends Bot{

	public AttackBot(BasicStunt stunt) {
		super(stunt);
	}

	@Override
	public void think() { 
		Entity pacman = null;
		
		int i=0;
		int max = this.stunt.model.entities().size();
		while (pacman == null && i<max) {
			Entity e = this.stunt.model.entities().get(i);
			if (e.name.equals("PacMan")) {
				pacman = e;
			}
			i++;
		}
		if (pacman == null)
			return;
		
		ISU.Vector direction = this.stunt.entity.center().mkVectorToward(pacman.center());
		int angle = (int) Math.toDegrees(Math.atan2(-direction.targetY_cm, direction.targetX_cm));
		angle = (angle%360 + 360) % 360;
		this.stunt.walk(angle);
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collision(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collision(List<Entity> e) {
		// TODO Auto-generated method stub
		
	}

}
