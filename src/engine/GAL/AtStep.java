package engine.GAL;

import java.util.LinkedList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.Grid.Cell;

class AtStep  extends GALCondition{

	int nbStep;

	// CONSTRUCTOR

	 AtStep(Direction dir, Category cat, int nbStep,Variable v){ 
		 super(dir,cat);
		 this.nbStep=nbStep;
	 }
	 
	 AtStep(Direction dir, Category cat,Variable v){ 
		 super(dir,cat);
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

		public boolean eval(Entity e) {
			if(this.category==null || this.direction==null) {
				return false;
			}
			if(this.direction.isRelative()) {
				if(this.direction==Direction.H) {
					for(Entity h_ent:e.grid.cellAt(e.position()).entities){
						if(catcond(h_ent)) {
							if(h_ent!=e) {
								this.var.content=h_ent;
								return true;
							}
						}
					}
				}
				if(this.direction==Direction.F) {
					this.direction=Direction.toDir(e.orientation());
				}
				if(this.direction==Direction.B) {
					this.direction=Direction.toDir(e.orientation()+180);
				}
				if(this.direction==Direction.R) {
					this.direction=Direction.toDir(e.orientation()+270);
				}
				if(this.direction==Direction.L) {
					this.direction=Direction.toDir(e.orientation()+90);
				}
			}
			int currentX=e.position().x;
			int currentY=e.position().y;
			if (this.nbStep <= 0) {
				if(this.direction==Direction.N) {
					for(int i=currentY-1;i>=0;i--) {
						Cell c=e.grid.cellAt(currentX,i);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				}
				if(this.direction==Direction.S) {
					for(int i=currentY+1;i<Game.game().height_ncell;i++) {
						Cell c=e.grid.cellAt(currentX,i);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				}
				if(this.direction==Direction.E) {
					for(int i=currentX+1;i<Game.game().width_ncell;i++) {
						Cell c=e.grid.cellAt(i,currentY);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				}
				if(this.direction==Direction.W) {
					for(int i=currentX-1;i>=0;i--) {
						Cell c=e.grid.cellAt(i,currentY);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				}
				
			}
			else {
				if(this.direction==Direction.N) {
					int new_y=currentY-this.nbStep;
					if(new_y<0) {
						return false;
					}
						Cell c=e.grid.cellAt(currentX,new_y);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				if(this.direction==Direction.S) {
					int new_y=currentY+this.nbStep;
					if(new_y>=Game.game().height_ncell) {
						return false;
					}
						Cell c=e.grid.cellAt(currentX,new_y);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				}
				if(this.direction==Direction.E) {
					int new_x=currentX+this.nbStep;
					if(new_x>=Game.game().width_ncell) {
						return false;
					}
						Cell c=e.grid.cellAt(new_x,currentY);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				if(this.direction==Direction.W) {
					int new_x=currentX-this.nbStep;
					if(new_x<0) {
						return false;
					}
						Cell c=e.grid.cellAt(new_x,currentY);
						List<Entity> l=parcourscell(c,e);
						if(!l.isEmpty()) {
							Entity min=l.getFirst();
							double dist=min.center().distanceTo(e.center());
							for(Entity ent:l) {
								double ndist=ent.center().distanceTo(e.center());
								if(ndist<dist) {
									min=ent;
									dist=ndist;
								}
							}
							this.var.content=min;
							return true;
						}
					}
				return false;
				
		}
	 
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
		boolean catcond(Entity e) {
			if(this.category!=null) {
				return e.Category()==this.category;
			}
			return true;
		}
	 @Override
	 public Variable variable() {
		// TODO Auto-generated method stub
		return this.var;
	 }
}
