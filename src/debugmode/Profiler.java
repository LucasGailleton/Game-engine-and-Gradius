package debugmode;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Profiler {
	private String name; // profiler name
	private long time; // total time
	private long elapsed; // total elapsed time
	private long min, max, avg; // elapsed per period: min, max, and average
	private long period; // this profiler period
// the fields below are internal implementation details.
	private long last, start, end, nperiods, sum;

	public Profiler(String name, long period) {
		this.name = name;
		this.period = period;
		this.min = Long.MAX_VALUE;
		last = System.currentTimeMillis();
	}

	public void start() {
		if (!ProfilingManager.PROFILING)
			return;
		start = System.currentTimeMillis();
	}

	public void end() {
		if (!ProfilingManager.PROFILING)
			return;
		end = System.currentTimeMillis();
		this.sum += (end - start);
		long t = end - this.last;
		if (t >= this.period) {
			this.nperiods++;
			this.time = t;
			this.elapsed = this.sum;
			this.avg = this.elapsed / this.nperiods;
			if (this.sum < min)
				min = this.sum;
			if (this.sum > max)
				max = this.sum;
			this.sum = 0;
			this.last = end;
		}
	}

	public void log(Logger logger, Level level) {
		logger.log(level, () -> {
			return toString();
		});
	}

	public void callback(ProfilingManager.Listener l) {
		l.stats(this.name, this.min, this.avg, this.max, this.elapsed, this.time);
	}

	@Override
	public String toString() {
		float percent=0F;
		if (this.time!=0L)
			percent = (float)this.elapsed/(float)this.time;
		return String.format("%s: min=%dms avg=%dms max=%dms, elapsed=%dmspercent=%.2f",
		this.name, this.min, this.avg, this.max, this.elapsed, percent);
	}
}
