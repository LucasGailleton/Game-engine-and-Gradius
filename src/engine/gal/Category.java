package engine.gal;

import java.util.HashMap;
import java.util.Map;

public class Category {

	// CONSTANTS

	static Category Adversary, Obstacle, Team, Void;
	static Map<String, Category> categories;
	static boolean interaction[][];
	static int indMax;

	// STATIC INITIALIZATION

	static {
		categories = new HashMap<String, Category>();
		Adversary = new Category("Adversary", 0);
		Obstacle = new Category("Obstacle", 1);
		Team = new Category("Team", 2);
		Void = new Category("Void", 3);
		indMax = 4;
		int size = 20;
		interaction = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				interaction[i][j] = false;
		}
	}

	// FACTORY

	Category canonical(String name) {
		if (this.categories.containsKey(name))
			return this.categories.get(name);
		Category cat = new Category(name, indMax);
		indMax++;
		this.categories.put(name, cat);
		return cat;
	}

	// CONSTRUCTOR
	int index;

	Category(String name, int index) {
		this.index = index;
		this.categories.put(name, this);
	}

	// SETTER

	/**
	 * @apiNote updates the interaction table such that <i>c1 interactsWith c2
	 *          &equiv; bool</i>
	 * @param c1
	 * @param c2
	 * @param bool
	 */
	void setInteraction(Category c1, Category c2, boolean bool) {
		interaction[c1.index][c2.index] = bool;
	}

	// PREDICATE

	/**
	 * @apiNote Check the table to see if there is an interaction between
	 *          {@code this} and the {@code c} category.
	 * @param c
	 */
	boolean interactsWith(Category c) {
		return interaction[this.index][c.index];
	}

}
