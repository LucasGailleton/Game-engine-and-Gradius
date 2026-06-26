package engine.GAL;

import java.util.HashMap;
import java.util.Map;

public class Category {

	// CONSTANTS

	public static Category Adversary, Obstacle, Team, Void,Joueur,Boss,Missile;
	// ...

	// STATIC
	public	static Map<String, Category> categories = new HashMap<>();
	//Object categories; // <-- FIXME

	public static boolean interaction[][];
	public static int ind;
	// STATIC INITIALIZATION

	static {
		Category.interaction=new boolean[20][20];
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				Category.interaction[i][j]=false;
			}
		}
		Adversary = new Category("A",0);
		Obstacle = new Category("Obstacle",1);
		Team = new Category("Team",2);
		Void = new Category("Void",3);
		Boss = new Category("#",4);
		Missile = new Category("Missile",5);
		Category.ind=6;
	}

	// FACTORY

	 public static Category canonical(String name){ 
			if(categories.containsKey(name)) {
				return categories.get(name);
			}
			Category newCat = new Category(name,Category.ind);
			Category.ind++;
			return newCat;
	 }

	// CONSTRUCTOR

	 private int index;
	 private String name;
	 public Category(String name, int index){ 
		 this.index=index;
		 categories.put(name, this);
		 this.name=name;
	 }

	// SETTER

	/**
	 * @apiNote updates the interaction table such that <i>c1 interactsWith c2
	 *          &equiv; bool</i>
	 * @param c1
	 * @param c2
	 * @param bool
	 */
	 public void setInteraction(Category c1, Category c2, boolean bool){ 
		Category.interaction[c1.index][c2.index]=bool; 
	 }

	// PREDICATE

	/**
	 * @apiNote Check the table to see if there is an interaction between
	 *          {@code this} and the {@code c} category.
	 * @param c
	 */
	 public boolean interactsWith(Category c){ 
		 return Category.interaction[this.index][c.index];
	 }

}
