
package engine.GAL;

import engine.Entity;

class True  implements iGALCondition{

	// CONSTRUCTOR

	 True(){
	 }

	// EVAL
	 public boolean eval(Entity __){
		 return true;
	 }

	@Override
	public Variable variable() {
		// TODO Auto-generated method stub
		return null;
	}

}
