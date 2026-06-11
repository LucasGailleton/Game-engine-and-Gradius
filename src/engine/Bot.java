package engine;

import java.util.List;

public abstract class Bot {
	protected BasicStunt stunt;

	public Bot(BasicStunt stunt) {
		this.stunt = stunt;
	}

	public abstract void think();

	public abstract void done();

	public abstract void collision(Entity e);

	public abstract void collision(List<Entity> e);
}
