package engine.GAL;

import java.util.LinkedList;
import java.util.List;


import engine.Entity;
import engine.Game;
import engine.Grid.Cell;

public class Closest  extends GALCondition{

	int nbStep;

	// CONSTRUCTOR

	public Closest(Direction dir, Category cat, int nbStep,Variable v){ 
		 super(dir,cat);
		 this.nbStep=nbStep;
		 this.var=v;
	 }
	
	public Closest(Direction dir, Category cat,Variable v){ 
		 super(dir,cat);
		 this.var=v;
	 }

	// EVAL

	/**
	 * @apiNote check if the condition AtStep(...) is satisfied by the given entity
	 * @param e = the entity that does the evaluation
	 * @implNote AtStep(...) conditions are intensively used and must be efficient:
	 *           efficiency is perhaps more important than accuracy.
	 * @implNote There is plenty room for optimization here in collaboration with
	 *           the Model and the Bot.
	 */
	public boolean eval(Entity player) {
		if(this.direction!=null) {
			if(this.direction.isAbsolute()) {
				Entity min=player;
					if(this.direction==Direction.S) {
						for(Entity e:player.getModel().entities()) {
							if(catcond(e) && e.center().y_cm>player.center().y_cm) {
								if(e!=player && (min==player || e.center().distanceTo(player.center())<min.center().distanceTo(player.center()))) {
									min=e;
								}
							}
						}
					}
					else if(this.direction==Direction.N) {
						for(Entity e:player.getModel().entities()) {
							if(catcond(e) && e.center().y_cm<player.center().y_cm) {
								if(e!=player && (min==player || e.center().distanceTo(player.center())<min.center().distanceTo(player.center()))) {
									min=e;
								}
							}
						}
					}
					else if(this.direction==Direction.E) {
						for(Entity e:player.getModel().entities()) {
							if(catcond(e) && e.center().x_cm>player.center().x_cm) {
								if(e!=player && (min==player || e.center().distanceTo(player.center())<min.center().distanceTo(player.center()))) {
									min=e;
								}
							}
						}
					}
					else {
						for(Entity e:player.getModel().entities()) {
							if(catcond(e) && e.center().x_cm<player.center().x_cm) {
								if(e!=player && (min==player || e.center().distanceTo(player.center())<min.center().distanceTo(player.center()))) {
									min=e;
								}
							}
						}
					}
					if(min!=player) {
						this.var.content=min;
						return true;
					}
				}
			}
		else {
			Entity min=player;
			for(Entity e:player.getModel().entities()) {
				if(catcond(e)) {
					if(e!=player && (min==player || e.center().distanceTo(player.center())<min.center().distanceTo(player.center()))) {
						min=e;
					}
				}
			}
			if(min!=player) {
				this.var.content=min;
				return true;
			}
		}
		return false;
	}
	
	boolean catcond(Entity e) {
		if(this.category!=null) {
			return e.Category()==this.category;
		}
		return true;
	}
	
		/*public boolean eval(Entity player) {
			if(this.direction!=null) {
				if(this.direction.isAbsolute()) {
					List<Entity> l;
					if(this.direction==Direction.S) {
						l=parcourscouche(true,1,player.position().x,player.position().y,player);
					}
					else if(this.direction==Direction.N) {
						l=parcourscouche(true,-1,player.position().x,player.position().y,player);
					}
					else if(this.direction==Direction.E) {
						l=parcourscouche(false,1,player.position().y,player.position().x,player);
					}
					else {
						l=parcourscouche(false,-1,player.position().y,player.position().x,player);
					}
					if(l!=null) {
						Entity min=l.getFirst();
						for(Entity e:l) {
							if(e.center().distanceTo(player.center())<min.center().distanceTo(player.center())) {
								min=e;
							}
						}
						this.var.content=min;
						return true;
					}
				}
			}
			return false;
		}*/
		
		/*List<Entity> parcourscouche(boolean horizontal,int orientation,int milieux,int prof_min,Entity player) {
			List<Entity> l;
			boolean prof_max=true;
			int jmin=0;
			int profondeur=prof_min;
			//N ou S
			if(horizontal) {
				int max_y = Game.game().height_ncell;
	            int max_x = Game.game().width_ncell;
				while(true) {
					if(prof_max && profondeur>=0 && profondeur < max_y) {
						l=parcourscell(Game.game().grid.cellAt(milieux,profondeur),player);
						if(!l.isEmpty()) {
							return l;
						}
					}
					int j1=milieux-jmin;
					int j2=milieux+jmin;
					for(int i=profondeur-orientation;i!=prof_min;i-=orientation) {
						if (i < 0 || i >= max_y) {
	                        break;
	                    }
						//gauche
						if(j1>0) {
							j1--;
							l=parcourscell(Game.game().grid.cellAt(j1,i),player);
							if(!l.isEmpty()) {
								return l;
							}
						}
						//droite
						if(j2<max_x-1) {
							j2++;
							l=parcourscell(Game.game().grid.cellAt(j2,i),player);
							if(!l.isEmpty()) {
								return l;
							}
						}
					}
					int newprof=profondeur+orientation;
					if(newprof<max_y && newprof>=0) {
						profondeur=newprof;
					}
					else if(jmin>max_x/2) {
						return null;
					}
					else {
						prof_max=false;
						jmin++;
					}
				}
			}
			
			if(!horizontal) {
				while(true) {
					if(prof_max && profondeur!=Game.game().width_ncell) {
						l=parcourscell(Game.game().grid.cellAt(profondeur,milieux),player);
						if(!l.isEmpty()) {
							return l;
						}
					}
					int j1=milieux-jmin;
					int j2=milieux+jmin;
					for(int i=profondeur-orientation;i!=prof_min;i-=orientation) {
						//gauche
						if(j1>0) {
							j1--;
							l=parcourscell(Game.game().grid.cellAt(i,j1),player);
							if(!l.isEmpty()) {
								return l;
							}
						}
						//droite
						if(j2<Game.game().height_ncell-1) {
							j2++;
							l=parcourscell(Game.game().grid.cellAt(i,j2),player);
							if(!l.isEmpty()) {
								return l;
							}
						}
					}
					int newprof=profondeur+orientation;
					if(newprof<=Game.game().width_ncell && newprof>=0) {
						profondeur=newprof;
					}
					else if(jmin>Game.game().height_ncell/2) {
						return null;
					}
					else {
						prof_max=false;
						jmin++;
					}
				}
			}
			return null;
		}*/
		
		List<Entity> parcourscell(Cell c,Entity source){
			c.show(System.out);
			List<Entity> l=new LinkedList<Entity>();
			for(Entity e:c.entities) {
				if(e.Category()==this.category && e!=source) {
					l.add(e);
				}
			}
			return l;
		}

		@Override
		public Variable variable() {
			// TODO Auto-generated method stub
			return this.var;
		}
		
}

