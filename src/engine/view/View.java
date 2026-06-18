package engine.view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import debugmode.Profiler;
import debugmode.ProfilingManager;
import engine.Entity;
import engine.Game;
import engine.ISU;
import engine.shapes.Circle;
import engine.shapes.Rect;
import game.Main;
import game.Model;
import game.entities.Avatar;
import game.entities.Background;
import oop.graphics.Canvas;
import oop.graphics.Graphics;
import oop.graphics.Graphics.Colors;

public class View {
	Canvas canvas;
	List<Avatar> avatars;
	private Profiler vp, mp;
	private boolean showStats;
	long lastTime = System.currentTimeMillis();
	int frames = 0;
	int currentFPS = 0;
	
	private int shiftChg;
	private int shiftPX;
	
	public static final int viewSpeedChg = (int)((Main.windowScale));
	public static final int speed = -1;
	public static final int viewSpeed = (int)(speed*Main.windowScale);
	
	public View(Canvas canvas) {
		this.mp = ProfilingManager.getProfiler("model");
		this.vp = ProfilingManager.getProfiler("view");
		this.canvas = canvas;
		this.avatars = new ArrayList<Avatar>();
		this.showStats = true;
		
		this.shiftChg = 0;
		this.shiftPX = 0;
	}
	
	public void add(Avatar e) {
		this.avatars.add(e);
	}
	
	public boolean shift() {
		if (this.shiftChg == viewSpeedChg) {
			this.shiftPX += viewSpeed;
			this.shiftChg = 0;
			return true;
		}
		this.shiftChg += 1;
		return false;
	}
	
	public void paint(Graphics g) {
//		Model model = null;
//		if (ProfilingManager.PROFILING)
//			vp.start();
//		for (Avatar a : avatars) {
//            a.paint(g, shiftPX);
//			if (Main.debug) {
//	            Entity entity = a.entity();
//	            
//	            model = entity.getModel();
//	            if (!(entity instanceof Background)) {
//		            g.setColor(Colors.red);
//		            ISU.Coord centre = entity.center();
//		            
//		            int w = (int)(centre.x_cm * Game.pixelPerCm);
//		            int h = (int)(centre.y_cm * Game.pixelPerCm);
//		            
//		            g.drawOval(w-3, h-3, 3, 3);
//		            
//		            if(ProfilingManager.PROFILING) {
//			            for (engine.shapes.iShape shape : entity.bounding().boundings()) {
//			            	
//			            	if (shape instanceof Rect) {
//			            		Rect r = (Rect) shape;
//			            		int rW = (int) (r.getHalfWidth() * 2 * Game.pixelPerCm);
//			            		int rH = (int) (r.getHalfHeight() * 2 * Game.pixelPerCm);
//			            		int rX = (int) ((r.center().x_cm - r.getHalfWidth()) * Game.pixelPerCm);
//			            		int rY = (int) ((r.center().y_cm - r.getHalfHeight()) * Game.pixelPerCm);
//			            		g.drawRect(rX, rY, rW, rH);
//			            	}
//			            	else if (shape instanceof Circle) {
//			            		Circle c = (Circle) shape;
//			            		int radius = (int) (c.getRadius() * Game.pixelPerCm);
//			            		int cX = (int) (c.center().x_cm * Game.pixelPerCm) - radius;
//			            		int cY = (int) (c.center().y_cm * Game.pixelPerCm) - radius;
//			            		g.drawOval(cX, cY, radius * 2, radius * 2);
//			            	}
//			            }
//		            }
//		            g.drawOval((int)(centre.x_cm*Game.pixelPerCm)-3+shiftPX, (int)(centre.y_cm*Game.pixelPerCm)-3, 3, 3);
//		            g.drawRect(((int)(centre.x_cm-w)*Game.pixelPerCm)+shiftPX, (int)(centre.y_cm-h)*Game.pixelPerCm, (int)w*2*Game.pixelPerCm, (int)h*2*Game.pixelPerCm);
//	            }
//			}
//		}
//		frames++;
		
		
		Model model = null;
		if (ProfilingManager.PROFILING)
			vp.start();
		for (Avatar a : avatars) {
            a.paint(g, shiftPX);
			if (Main.debug) {
	            Entity entity = a.entity();
	            model = entity.getModel();
	            if (!(entity instanceof Background)) {
		            g.setColor(Colors.red);
		            ISU.Coord centre = entity.center();
		            
		            double w = entity.size.x_cm/2.0;
		            double h = entity.size.y_cm/2.0;
		            
		            g.drawOval((int)(centre.x_cm*Game.pixelPerCm)-2+shiftPX, (int)(centre.y_cm*Game.pixelPerCm)-2, 4, 4);
		            g.drawRect(((int)(centre.x_cm-w)*Game.pixelPerCm)+shiftPX, (int)(centre.y_cm-h)*Game.pixelPerCm, (int)w*2*Game.pixelPerCm, (int)h*2*Game.pixelPerCm);
	            }
			}
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
		    
		    if(model != null)
		    	g.drawString("E: " + model.entities().size(), 10, 40);
		    
			g.setColor(Graphics.Colors.red);
			g.drawString(vp.toString(), 90, 20);
			g.drawString(mp.toString(), 90, 40);
		}
	}	
}
