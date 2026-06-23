
package engine.GAL;


import java.util.LinkedList;

import engine.Entity;


class Conjunction  implements iGALCondition{


	// FIELDS

	LinkedList<iGALCondition> conditions;

	// CONSTRUCTOR

	 Conjunction(){ 
		 this.conditions=new LinkedList<iGALCondition>();
	 }

	// BUILDER

	 void add(iGALCondition c){ 
		 this.conditions.add(c);
	 }

	// EVAL
	 public boolean eval(Entity e){ 
		 for(iGALCondition cond:this.conditions) {
			 boolean b=cond.eval(e);
			 if(!b) {
				 return b;
			 }
		 }
		 return true;
	 }

}
