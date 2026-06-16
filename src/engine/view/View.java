package engine.view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import DebugMode.Profiler;
import DebugMode.ProfilingManager;
import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.shapes.Rect;
import game.Main;
import game.entities.Avatar;
import oop.graphics.Canvas;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class View {
	Canvas canvas;
	List<Avatar> avatars;
	private Profiler vp, mp;
	private boolean showStats = true;
	long lastTime = System.currentTimeMillis();
	int frames = 0;
	int currentFPS = 0;
	
	public View(Canvas canvas) {
		this.mp = ProfilingManager.getProfiler("model");
		this.vp = ProfilingManager.getProfiler("view");
		this.canvas = canvas;
		this.avatars = new ArrayList<Avatar>();
	}
	
	public void add(Avatar e) {
		this.avatars.add(e);
	}
	
	public void paint(Graphics g) {
		if (ProfilingManager.PROFILING)
			vp.start();
		for (Avatar a : avatars) {
			if (Main.debug) {
	            Entity entity = a.entity();
	            g.setColor(Colors.red);
	            Rect boundingBox = entity.boundingBox();
	            ISU.Coord centre = boundingBox.center();
	            
	            double h = boundingBox.getHalfHeight();
	            double w = boundingBox.getHalfWidth();
	            
	            g.drawRect((int)(centre.x_cm-w) * Game.pixelPerCm, 
	                    (int)(centre.y_cm - h) * Game.pixelPerCm, 
	                    (int)w*2*Game.pixelPerCm, 
	                    (int)h*2*Game.pixelPerCm);
			}
            a.paint(g);
		}
		frames++;
		
		long now = System.currentTimeMillis();
	    if (now - lastTime >= 1000) {
	        currentFPS = frames;
	        frames = 0;
	        lastTime = now;
	    }
		
		if (ProfilingManager.PROFILING)
			vp.end();
		if (ProfilingManager.PROFILING && showStats) {
			g.setColor(Colors.green);
		    g.setFont(g.getFont("Monospaced", 0, 16));
		    g.drawString("FPS: " + currentFPS, 10, 20);
		    
			g.setColor(Graphics.Colors.red);
			g.drawString(vp.toString(), 90, 20);
			g.drawString(mp.toString(), 90, 40);
			}
	}	
}
