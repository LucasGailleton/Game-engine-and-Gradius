package engine.view;

import java.util.ArrayList;
import java.util.List;

import debugmode.Profiler;
import debugmode.ProfilingManager;
import debugmode.VectAvatar;
import engine.Entity;
import engine.Game;
import engine.ISU;

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
	public static int shiftPX;
	
	public static final int viewSpeedChg = 3;
	public static final int speed = -1;
	public static int viewSpeed = (int)(speed*Main.windowScale);
	
	private VectAvatar va;
	
	public View(Canvas canvas) {
		this.mp = ProfilingManager.getProfiler("model");
		this.vp = ProfilingManager.getProfiler("view");
		this.canvas = canvas;
		this.avatars = new ArrayList<Avatar>();
		this.showStats = true;
		
		this.shiftChg = 0;
		shiftPX = 0;
	}
	
	public void add(Avatar a) {
		this.avatars.add(a);
		if(a instanceof VectAvatar) {
			this.va = (VectAvatar) a;
		}
	}
	
	public VectAvatar getVA() {
		return this.va;
	}
	
	public void remove(Avatar a) {
		this.avatars.remove(a);
	}

	public int shiftPX() {
		return shiftPX;
	}
	
	public boolean shift() {
		if (viewSpeed == 0)
			return false;
		if (this.shiftChg == viewSpeedChg) {
			shiftPX += viewSpeed;
			int taille_map = (int) (Game.game().width_ncell * Game.cmPerCell * Game.pixelPerCm);
			
			if (shiftPX <= -taille_map) {
				shiftPX += taille_map;
			}
			this.shiftChg = 0;
			return true;
		}
		this.shiftChg += 1;
		return false;
	}
	
	public Canvas canvas() {
		return this.canvas;
	}
	
	public void paint(Graphics g) {
		
		Model model = null;
		if (ProfilingManager.PROFILING)
			vp.start();
		
		int taille_map = (int) (Game.game().width_ncell * Game.cmPerCell * Game.pixelPerCm);
		int canvasWidth = canvas.getWidth();
		
		for (Avatar a : avatars) {
            a.paint(g, shiftPX);
            Entity entity = a.entity();
            
            int shift = shiftPX;
            
            if (!(entity instanceof Background)) {
				int entityX = (int)(entity.center().x_cm * Game.pixelPerCm);
				int screenX = entityX + shiftPX;
				
				if (screenX < -300) {
					shift += taille_map;
				}
				else if (screenX > canvasWidth + 300) {
					shift -= taille_map;
				}
			}
            a.paint(g, shift);
            
			if (Main.debug) {
	            model = entity.getModel();
	            if (!(entity instanceof Background)) {
		            g.setColor(Colors.red);
		            ISU.Coord centre = entity.center();
		            
		            double w = entity.size.x_cm/2.0;
		            double h = entity.size.y_cm/2.0;
		            
			        g.drawOval((int)(centre.x_cm*Game.pixelPerCm)-2+shift, (int)(centre.y_cm*Game.pixelPerCm)-2, 4, 4);
			        g.drawRect(((int)(centre.x_cm-w)*Game.pixelPerCm)+shift, (int)(centre.y_cm-h)*Game.pixelPerCm, (int)w*2*Game.pixelPerCm, (int)h*2*Game.pixelPerCm);
			        
			        entity.bounding().paint(g, shift);
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
		if (Main.debug && showStats) {
			g.setColor(Colors.green);
		    g.setFont(g.getFont("Monospaced", 0, 16));
		    g.drawString("FPS: " + currentFPS, 10, 20);
		    
		    g.drawString("E: " + model.entities().size(), 10, 40);
		    
			g.setColor(Graphics.Colors.red);
			g.drawString(vp.toString(), 90, 20);
			g.drawString(mp.toString(), 90, 40);
		}
	}	
}
