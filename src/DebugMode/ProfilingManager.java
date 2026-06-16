package DebugMode;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfilingManager {
	public static final boolean PROFILING = true;
	public static ProfilingManager self;

	public static Profiler getProfiler(String name) {
		if (self == null) // default is a period of 1s
			new ProfilingManager(1000);
		return self.get(name);
	}

	private long period;
	private Map<String, Profiler> profilers;

	public ProfilingManager(int period) {
		self = this;
		this.period = period;
		this.profilers = new Hashtable();
	}

	public Profiler get(String name) {
		Profiler s = profilers.get(name);
		if (s == null) {
			s = new Profiler(name, period);
			profilers.put(name, s);
		}
		return s;
	}

	public void log(Logger logger, Level level) {
		if (PROFILING)
			for (Profiler s : profilers.values()) {
				s.log(logger, level);
			}
		else
			logger.log(level, "Profiling is turned off.");
	}

	public interface Listener {
		void stats(String name, long min, long avg, long max, long elapsed, long time);
	}

	public void stats(Listener l) {
		if (PROFILING)
			for (Profiler s : profilers.values()) {
				s.callback(l);
			}
	}
}
