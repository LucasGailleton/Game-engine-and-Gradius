package game;

import engine.Entity;
import engine.ISU;
import game.entities.EnemyShot;

public class Tir {
	
	public static void viser(Entity projectile, ISU.Coord cible, double vitesse) {
		ISU.Coord from = projectile.center();
		double dx = cible.x_cm - from.x_cm;
		double dy = cible.y_cm - from.y_cm;
		double dist = from.distanceTo(cible);
		if (dist == 0)
			dist = 1;
		projectile.setlSpeed(vitesse * dx / dist, vitesse * dy / dist);
	}

	public static EnemyShot tirerVers(Model model, Entity tireur, Entity cible, double vitesse) {
		EnemyShot tir = new EnemyShot("tir");
		tir.setCoord(tireur.center());
		model.add(tir);
		model.getView().add(tir.getAvatar());
		viser(tir, cible.center(), vitesse);
		return tir;
	}
}
