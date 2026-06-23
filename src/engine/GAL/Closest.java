package engine.GAL;

package engine.GAL;

import engine.Entity;

class Closest  extends GALCondition{

	int nbStep;

	// CONSTRUCTOR

	Closest(Direction dir, Category cat, int nbStep){ 
		 super(dir,cat);
		 this.nbStep=nbStep;
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
	
		boolean eval(Entity e) {

			// 1. On récupère la position actuelle du Bot
			int currentX = e.x(); 
			int currentY = e.y(); 

			// 2. On extrait les vecteurs de direction (-1, 0, ou 1)
			int dx = this.dir.getDx(); 
			int dy = this.dir.getDy(); 

			// 3. CALCUL DIRECT : On cible directement la case située à 'nbStep' pas
			int targetX = currentX + (dx * this.nbStep);
			int targetY = currentY + (dy * this.nbStep);

			// 4. Sécurité : Si la case visée est hors de la map
			if (e.model().isOutOfBounds(targetX, targetY)) {
				return false;
			}

			// 5. On regarde ce qui se trouve sur cette case précise
			Entity targetEntity = e.model().getEntityAt(targetX, targetY);

			// La condition est vraie si une entité existe à cet endroit ET qu'elle appartient à la bonne catégorie
			return targetEntity != null && targetEntity.category() == this.cat;
		}
}

