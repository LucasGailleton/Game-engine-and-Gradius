package engine.gal;

import java.util.LinkedList;

import engine.Entity;

public class Disjunction {

	// FIELDS

	LinkedList<iGALCondition> conditions;

	// CONSTRUCTOR

	Disjunction(){ 
		 this.conditions=new LinkedList<iGALCondition>();
	 }

	// BUILDER

	 void add(iGALCondition c){ 
		 this.conditions.add(c);
	 }

	// EVAL
	 boolean eval(Entity e){ 
		 for(iGALCondition cond:this.conditions) {
			 boolean b=cond.eval(e);
			 if(b) {
				 return true;
			 }
		 }
		 return false;
	 }
}
