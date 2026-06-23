package engine.GAL;

import java.util.List;

import oop.tasks.Runnable;
import oop.tasks.Task;

public class Ticker implements Runnable{
	int ms; //ms between each tick
	Task t;
	List<Bot> bots;
	private long t_instant;
	
	public Ticker(List<Bot> bots,Task t,int ms){
		this.ms=ms;
		this.t=t;
		this.bots=bots;
		this.t_instant= System.currentTimeMillis();
		t.post(this,ms);
	}

	@Override
	public void run() throws Exception {
		long now=System.currentTimeMillis();
		long delay=now-t_instant;
		this.t_instant=now;
		for(Bot b:this.bots) {
			b.tick(delay);
		}
		t.post(this,ms);
	}
}
