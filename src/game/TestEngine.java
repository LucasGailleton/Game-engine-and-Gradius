package game;

import engine.Game;
import engine.Grid;
import engine.ISU;
import engine.Physic;
import engine.Grid.Position;
import engine.shapes.Circle;
import engine.shapes.Rect;
import engine.Entity;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_METHOD)
public class TestEngine {
	
	@BeforeEach
	public void resetGameSingleton() {
		try {
			Field instance = Game.class.getDeclaredField("game");
			instance.setAccessible(true);
			instance.set(null, null);
		} catch (Exception e) {
			fail("Impossible de reset le singleton Game via Réflexion : " + e.getMessage());
		}
	}

	@Test
	public void test00() {
		System.out.println("Test00 :");
		Game game = new Game(2, 2);
		Grid grid = game.grid;
		game.show(System.out);
		assertEquals(grid.width(), game.width_ncell);
		assertEquals(grid.height(), game.height_ncell);
		System.out.println("Test00 : PASSED\n\n");
	}

	@Test
	public void test01() {
		System.out.println("Test01 :");
		Game game = new Game(5, 5);
		Entity ghost = new Entity("Blinky");
		ghost.show(System.out);
		assertEquals("Blinky", ghost.name);
		assertEquals(0, ghost.orientation());
		assertNull(ghost.position());
		assertNull(ghost.center());
		System.out.println("Test01 : PASSED\n\n");
	}

	@Test
	public void test02() {
		System.out.println("Test02 :");
		Game game = new Game(4, 4);
		Grid grid = game.grid;
		Entity pacman = new Entity("PacMan");

		Grid.Position startPos = grid.new Position(1, 1);
		pacman.setSize(grid.new Dimension(1, 1));
		pacman.setPosition(startPos.copy());
		pacman.deploy();
		grid.cellAt(startPos).add(pacman);
		
		pacman.show(System.out);
		System.out.println();
		grid.cellAt(startPos).show(System.out);
		grid.cellAt(grid.new Position(3, 2)).show(System.out);
		System.out.println();
		
		Grid.Vector v = grid.new Vector(2, 1);
		pacman.translate(v);
		System.out.println("Déplacement discret : +2 cases sur X, +1 case sur Y");
		pacman.show(System.out);
		System.out.println();
		grid.cellAt(startPos).show(System.out);
		grid.cellAt(grid.new Position(3, 2)).show(System.out);
		
		assertEquals(3, pacman.position().x);
		assertEquals(2, pacman.position().y);
		assertEquals(12.95, pacman.center().x_cm, 0.001);
		assertEquals(9.25, pacman.center().y_cm, 0.001);
		
		assertTrue(grid.cellAt(grid.new Position(3, 2)).entities.contains(pacman));
		assertFalse(grid.cellAt(startPos).entities.contains(pacman));
		System.out.println("Test02 : PASSED\n\n");
	}

	@Test
	public void test03() {
		System.out.println("Test03 :");
		Game game = new Game(4, 4);
		Grid grid = game.grid;
		Entity pacman = new Entity("PacMan");
		
		Grid.Position startPos = grid.new Position(0, 0);
		pacman.setSize(grid.new Dimension(1, 1));
		pacman.setPosition(startPos);
		pacman.deploy();
		grid.cellAt(startPos).add(pacman);
		pacman.show(System.out);
		System.out.println();
		
		System.out.println("Déplacement continu : on avance de 4.0 cm en X et 0.0 cm en Y");
		ISU.Vector v = game.isu.new Vector(4.0, 0.0);
		pacman.translate(v);
		pacman.show(System.out);
		System.out.println();
		
		assertEquals(5.85, pacman.center().x_cm, 0.001);
		assertEquals(1, pacman.position().x);
		assertEquals(0, pacman.position().y);
		assertTrue(grid.cellAt(pacman.position()).entities.contains(pacman));
		System.out.println("Test03 : PASSED\n\n");
	}

	@Test
	public void test04() {
		System.out.println("Test04 : Test des limites du monde sur l'axe X");
		Game game = new Game(3, 3);
		Grid grid = game.grid;
		Entity pacman = new Entity("TorusRunnerX");
		
		Grid.Position rightEdge = grid.new Position(2, 1); // Case tout à droite
		pacman.setSize(grid.new Dimension(1, 1));
		pacman.setPosition(rightEdge);
		pacman.setCoord(rightEdge.toISUCoordCentered());
		grid.cellAt(rightEdge).add(pacman);
		
		pacman.show(System.out);
		System.out.println();
		grid.cellAt(rightEdge).show(System.out);
		
		System.out.println("Fait un pas vers l'Est sur la grille (sort à droite)");
		pacman.moveEast(1);
		pacman.show(System.out);
		
		assertEquals(0, pacman.position().x);
		assertEquals(1, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(0, 1)).entities.contains(pacman));
		System.out.println("Test04 : PASSED\n\n");
	}

	@Test
	public void test05() {
		System.out.println("Test05 : Test des limites du monde sur l'axe Y");
		Game game = new Game(3, 3);
		Grid grid = game.grid;
		Entity pacman = new Entity("TorusRunnerY");
		pacman.show(System.out);
		System.out.println();
		
		Grid.Position topEdge = grid.new Position(1, 0);
		pacman.setSize(grid.new Dimension(1, 1));
		pacman.setPosition(topEdge);
		grid.cellAt(topEdge).add(pacman);
		
		System.out.println("Bouge de 1 case vers le Nord (sort par le haut)");
		pacman.moveNorth(1);
		pacman.show(System.out);
		
		assertEquals(1, pacman.position().x);
		assertEquals(2, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(1, 2)).entities.contains(pacman));
		System.out.println("Test05 : PASSED\n\n");
	}

	@Test
	public void test06() {
		System.out.println("Test06 : Test des rotations");
		Game game = new Game(5, 5);
		Entity pacman = new Entity("Spinner");
		pacman.show(System.out);
		System.out.println();
		
		assertEquals(0, pacman.orientation());
		
		System.out.println("Tourne de 90 degrés à droite");
		pacman.turn(90);
		pacman.show(System.out);
		System.out.println();
		assertEquals(90, pacman.orientation());
	
		System.out.println("Dépassement des 360 degrés");
		pacman.turn(300);
		pacman.show(System.out);
		System.out.println();
		assertEquals(30, pacman.orientation());
		
		System.out.println("Gestion des angles négatifs (tourner à gauche)");
		pacman.turn(-40);
		pacman.show(System.out);
		assertEquals(350, pacman.orientation()); // -10 + 360 = 350
		System.out.println("Test06 : PASSED\n\n");
	}

	@Test
	public void test07() {
		System.out.println("Test07 : Test du move en cm");
		Game game = new Game(5, 5);
		Grid grid = game.grid;
		Entity pacman = new Entity("ContinuousMover");
		
		Grid.Position centerPos = grid.new Position(2, 2);
		pacman.setSize(grid.new Dimension(1, 1));
		pacman.setPosition(centerPos.copy());
		grid.cellAt(centerPos).add(pacman);
		pacman.show(System.out);
		System.out.println();
		
		pacman.moveEast(3.7);
		pacman.show(System.out);
		System.out.println();
		assertEquals(12.95, pacman.center().x_cm, 0.001);
		assertEquals(3, pacman.position().x);
		assertTrue(grid.cellAt(grid.new Position(3, 2)).entities.contains(pacman));
		// x_cm = 12.95, y_cm = 9,25
		
		pacman.moveNorth(7.2);
		pacman.show(System.out);
		System.out.println();
		assertEquals(2.05, pacman.center().y_cm, 0.001);
		assertEquals(0, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(3, 0)).entities.contains(pacman));
		// x_cm = 12.95, y_cm = 2,05
		
		pacman.moveWest(5.4);
		pacman.show(System.out);
		System.out.println();
		assertEquals(7.55, pacman.center().x_cm, 0.001);
		assertEquals(2, pacman.position().x);
		assertTrue(grid.cellAt(grid.new Position(2, 0)).entities.contains(pacman));
		// x_cm = 7,55, y_cm = 2,05
		
		pacman.moveSouth(3.9);
		pacman.show(System.out);
		System.out.println();
		assertEquals(5.95, pacman.center().y_cm, 0.001);
		assertEquals(1, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(2, 1)).entities.contains(pacman));
		// x_cm = 7,55, y_cm = 5,95		
		
		System.out.println("Test07 : PASSED\n\n");
	}
	
	@Test
	public void test08() {
		System.out.println("Test08 : Test du move en ncell (cases)");
		Game game = new Game(5, 5);
		Grid grid = game.grid;
		Entity pacman = new Entity("DiscreteMover");
		
		Grid.Position centerPos = grid.new Position(2, 2);
		pacman.setSize(grid.new Dimension(1, 1));
		pacman.setPosition(centerPos.copy());
		pacman.deploy();
		grid.cellAt(centerPos).add(pacman);
		pacman.show(System.out);
		System.out.println();
		
		pacman.moveEast(1);
		System.out.println("moveEast(1)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(3, pacman.position().x);
		assertEquals(2, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(3, 2)).entities.contains(pacman));
		
		pacman.moveNorth(2);
		System.out.println("moveNorth(2)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(3, pacman.position().x);
		assertEquals(0, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(3, 0)).entities.contains(pacman));
		
		pacman.moveEast(2);
		System.out.println("moveEast(2)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(0, pacman.position().x);
		assertEquals(0, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(0, 0)).entities.contains(pacman));

		pacman.moveNorth(1);
		System.out.println("moveNorth(1)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(0, pacman.position().x);
		assertEquals(4, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(0, 4)).entities.contains(pacman));

		pacman.moveWest(1);
		System.out.println("moveWest(1)");
		pacman.show(System.out);
		System.out.println();
		assertEquals(4, pacman.position().x);
		assertEquals(4, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(4, 4)).entities.contains(pacman));
		
		pacman.moveSouth(2);
		System.out.println("moveSouth(2)");
		pacman.show(System.out);
		assertEquals(4, pacman.position().x);
		assertEquals(1, pacman.position().y);
		assertTrue(grid.cellAt(grid.new Position(4, 1)).entities.contains(pacman));
		
		System.out.println("Test08 : PASSED\n\n");
	}
	
	@Test
	public void test09() {
		System.out.println("Test09 : Test des opérations sur Grid.Vector");
		Game game = new Game(5, 5);
		Grid grid = game.grid;
		
		Grid.Vector v1 = grid.new Vector(2, 3);
		assertEquals(2, v1.dx);
		assertEquals(3, v1.dy);
		
		System.out.println("Addition de deux vecteurs discrets");
		Grid.Vector v2 = grid.new Vector(1, -1);
		v1.add(v2); // (2+1, 3-1) -> (3, 2)
		
		assertEquals(3, v1.dx);
		assertEquals(2, v1.dy);
		v1.show(System.out);
		System.out.println("Test09 : PASSED\n\n");
	}

	@Test
	public void test10() {
		System.out.println("Test10 : Test complet de Grid.Dimension et équivalences");
		Game game = new Game(4, 4);
		Grid grid = game.grid;
		
		Grid.Dimension d1 = grid.new Dimension(2, 2);
		Grid.Dimension d2 = grid.new Dimension(2, 2);
		Grid.Dimension d3 = grid.new Dimension(6, 6);
		
		assertEquals(2, d1.x());
		assertEquals(2, d1.y());
		d1.show(System.out);
		
		assertTrue(d1.equals(d2));
		assertFalse(d1.equals(d3));
		assertFalse(d1.equals(null));
		assertFalse(d1.equals("Pas une dimension"));
		
		System.out.println("Vérification de l'équivalence avec le Tore");
		assertTrue(d1.equiv(d3));
		assertFalse(d1.equiv(null));
		
		Grid.Dimension dHorsBornes = grid.new Dimension(5, -1);
		dHorsBornes.normalize();
		assertEquals(1, dHorsBornes.x());
		assertEquals(3, dHorsBornes.y());
		
		ISU.Dimension isuDim = d1.toISUDimension();
		assertEquals(2 * Game.cmPerCell, isuDim.x(), 0.001);
		assertEquals(2 * Game.cmPerCell, isuDim.y(), 0.001);
		
		System.out.println("Test10 : PASSED\n\n");
	}

	@Test
	public void test11() {
		System.out.println("Test11 : Test des calculs géométriques de la Grille (Distance, Rotation, Cellules)");
		Game game = new Game(5, 5);
		Grid grid = game.grid;
		
		assertEquals(5, grid.width());
		assertEquals(5, grid.height());
		grid.show(System.out);
		
		Grid.Position p1 = grid.new Position(1, 1);
		Grid.Position p2 = grid.new Position(4, 1);
		
		double dist = p1.distanceTo(p2);
		System.out.println("Distance calculée sur le Tore : " + dist);
		assertTrue(dist > 0);
		
		Grid.Position pivot = grid.new Position(2, 2);
		Grid.Position point = grid.new Position(3, 2);
		
		System.out.println("Rotation de 90 degrés autour du pivot (2,2)");
		point.rotateAround(pivot, 90);
		assertNotNull(point);
		
		assertTrue(p1.equals(p1.copy()));
		assertFalse(p1.equals(p2));
		assertFalse(p1.equals("coucou"));
		
		System.out.println("Test11 : PASSED\n\n");
	}

	@Test
	public void test12() {
		System.out.println("Test12 : Gestion avancée des entités multiples dans les Cellules");
		Game game = new Game(5, 5);
		Grid grid = game.grid;
		
		Grid.Position p = grid.new Position(0, 0);
		Grid.Cell cell = grid.cellAt(p);
		cell.show(System.out);
		
		Entity e1 = new Entity("Ghost1");
		Entity e2 = new Entity("Ghost2");

		cell.add(e1);
		cell.add(e2);
		cell.show(System.out);
		assertTrue(cell.contains(e1));
		assertTrue(cell.contains(e2));
		assertEquals(2, cell.entities.size());
		
		cell.add(e1);
		assertEquals(2, cell.entities.size());
		
		cell.remove(e1);
		cell.show(System.out);
		assertFalse(cell.contains(e1));
		assertTrue(cell.contains(e2));
		assertEquals(1, cell.entities.size());
		
		cell.entities = null;
		assertFalse(cell.contains(e2));
		cell.show(System.out);
		
		System.out.println("Test12 : PASSED\n\n");
	}
	
	@Test
	public void test13() {
		System.out.println("Test13 : Test ultra complet de ISU.Vector et ses rotations");
		Game game = new Game(5, 5);
		ISU isu = game.isu;
		
		ISU.Vector v1 = isu.new Vector(3.0, 4.0);
		assertEquals(3.0, v1.targetX_cm, 0.001);
		assertEquals(4.0, v1.targetY_cm, 0.001);
		
		System.out.println("Calcul de la norme : sqrt(3^2 + 4^2) = 5");
		assertEquals(5.0, v1.norm(), 0.001);
		
		System.out.println("Scale uniforme de x2");
		v1.scale(2.0); // (6.0, 8.0)
		assertEquals(6.0, v1.targetX_cm, 0.001);
		assertEquals(8.0, v1.targetY_cm, 0.001);
		
		System.out.println("Scale asymétrique (x0.5, y2.0)");
		v1.scale(0.5, 2.0); // (3.0, 16.0)
		assertEquals(3.0, v1.targetX_cm, 0.001);
		assertEquals(16.0, v1.targetY_cm, 0.001);
		
		ISU.Vector v2 = isu.new Vector(2.0, -4.0);
		v1.add(v2); // (5.0, 12.0)
		assertEquals(5.0, v1.targetX_cm, 0.001);
		assertEquals(12.0, v1.targetY_cm, 0.001);
		
		System.out.println("Produit scalaire (dot product)");
		double dotProduct = v1.dot(v2); // 5*2 + 12*(-4) = 10 - 48 = -38
		assertEquals(-38.0, dotProduct, 0.001);
		
		System.out.println("Transformation en vecteur unitaire");
		ISU.Vector v3 = isu.new Vector(0.0, 5.0);
		v3.unity(); // (0.0, 1.0)
		assertEquals(0.0, v3.targetX_cm, 0.001);
		assertEquals(1.0, v3.targetY_cm, 0.001);
		
		ISU.Vector vZero = isu.new Vector(0.0, 0.0);
		vZero.unity();
		assertEquals(0.0, vZero.targetX_cm, 0.001);
		
		System.out.println("Rotation du vecteur lui-même de 90 degrés");
		ISU.Vector vRot = isu.new Vector(1.0, 0.0);
		vRot.turn(90);
		// cos(90)=0, sin(90)=1 -> x = 1*0 - 0*1 = 0, y = 1*1 + 0*0 = 1
		assertEquals(0.0, vRot.targetX_cm, 0.001);
		assertEquals(1.0, vRot.targetY_cm, 0.001);
		
		System.out.println("Test13 : PASSED\n\n");
	}

	@Test
	public void test14() {
		System.out.println("Test14 : Test complet de ISU.Coord, ISU.Dimension et Factories");
		Game game = new Game(5, 5);
		ISU isu = game.isu;
		Grid grid = game.grid;
		isu.set(grid);
		
		ISU.Dimension dim = isu.new Dimension(10.0, 20.0);
		assertEquals(10.0, dim.x(), 0.001);
		assertEquals(20.0, dim.y(), 0.001);
		assertSame(isu, dim.isu());
		
		ISU.Dimension dimSame = isu.new Dimension(10.0, 20.0);
		assertTrue(dim.equals(dimSame));
		assertTrue(dim.equals(dim));
		assertFalse(dim.equals(null));
		assertFalse(dim.equals("chaine"));
		
		assertTrue(dim.equiv(dimSame));
		assertFalse(dim.equiv(null));
		dim.setxy(5.0, 5.0);
		dim.normalize();
		dim.show(System.out);
		System.out.println();
		
		ISU.Vector vFromDim1 = dim.mkVector();
		assertEquals(5.0, vFromDim1.targetX_cm, 0.001);
		
		ISU.Vector vFromDim2 = dim.mkScaledVector(2.0);
		assertEquals(10.0, vFromDim2.targetX_cm, 0.001);
		
		ISU.Vector vFromDim3 = dim.mkScaledVector(2.0, 3.0);
		assertEquals(15.0, vFromDim3.targetY_cm, 0.001);

		System.out.println("Création et test des Coordonnées (cm)");
		ISU.Coord c1 = isu.new Coord(3.7, 7.4);
		c1.show(System.out);
		
		assertTrue(c1.equals(c1));
		assertTrue(c1.equals(isu.new Coord(3.7, 7.4)));
		assertFalse(c1.equals(null));
		assertFalse(c1.equals(dim));
		
		ISU.Vector vTrans = isu.new Vector(1.0, 1.0);
		c1.translate(vTrans);
		
		ISU.Coord cCopy = c1.mkCopy();
		assertEquals(c1.x_cm, cCopy.x_cm, 0.001);
		
		ISU.Coord cTranslated = c1.mkTranslated(vTrans);
		assertNotNull(cTranslated);
		
		ISU.Coord cTarget = isu.new Coord(10.0, 10.0);
		double dist = c1.distanceTo(cTarget);
		assertTrue(dist >= 0);
		
		ISU.Vector vToward = c1.mkVectorToward(cTarget);
		assertNotNull(vToward);
		
		System.out.println("Rotation de Coord autour de l'origine (0,0)");
		ISU.Coord cRot = isu.new Coord(1.0, 0.0);
		cRot.rotation(90);
		
		System.out.println("Rotation de Coord autour d'un pivot spécifique");
		ISU.Coord cPivot = isu.new Coord(2.0, 2.0);
		cRot.rotateAround(cPivot, 90);
		
		Grid.Position pFromCoord = c1.toGridPosition();
		assertNotNull(pFromCoord);
		
		System.out.println("Test14 : PASSED\n\n");
	}
	
	@Test
	public void test15() {
		System.out.println("Test15 : Test des collisions Cercle/Rectangle");
		Game game = new Game(4, 4);
		ISU isu = game.isu;
		
		Rect rect1 = new Rect(isu.new Coord(5.0, 5.0), isu.new Dimension(4.0, 2.0), 0);
		Circle circle1 = new Circle(isu.new Coord(7.2, 5.0), 1.5);
		
		System.out.println("Vérification collision classique (attendré : TRUE)");
		assertTrue(rect1.intersects(circle1), "Le cercle devrait intersecter le rectangle !");
		
		double maxW = game.grid.width() * Game.cmPerCell;
		
		ISU.Coord rectCenterTorus = isu.new Coord(maxW - 1.0, 5.0);
		Rect rectTorus = new Rect(rectCenterTorus, isu.new Dimension(4.0, 2.0), 0);
		
		ISU.Coord circleCenterTorus = isu.new Coord(1.0, 5.0);
		Circle circleTorus = new Circle(circleCenterTorus, 1.5);
		
		System.out.println("Vérification collision à travers les bords du Tore (attendré : TRUE)");
		assertTrue(rectTorus.intersects(circleTorus), "La collision devrait passer à travers la couture du Tore !");
		
		System.out.println("Test15 : PASSED\n\n");
	}
	
	@Test
	public void test15_Thibaut() {
		System.out.println("Test15_Thibaut : Test collisions");
		Game game = new Game(6, 6);
		ISU isu = game.isu;
		Rect carre = new Rect(isu.new Coord(2.0, 2.0), isu.new Dimension(4.0, 4.0), 0);
		Circle circle1 = new Circle(isu.new Coord(5.0, 5.0), 2);
        assertTrue(carre.intersects(circle1));
        Circle circle2 = new Circle(isu.new Coord(2.0 ,2.0),1.5);
        assertTrue(carre.intersects(circle2));
        Rect rect = new Rect(isu.new Coord(5.0, 5.0),isu.new Dimension(4.0, 4.0), 45);
        assertTrue(rect.intersects(carre));
        rect.translate(isu.new Vector(1.0, 1.0));
        assertFalse(rect.intersects(carre));
        System.out.println("Test15_Thibaut : PASSED\n\n");
	}
	
	@Test
	public void test16() {
		System.out.println("Test16 : Test des collisions Rectangle/Rectangle");
		Game game = new Game(4, 4);
		ISU isu = game.isu;

		Rect r1 = new Rect(isu.new Coord(5.0, 5.0), isu.new Dimension(4.0, 2.0), 0);
		Rect r2 = new Rect(isu.new Coord(6.5, 5.5), isu.new Dimension(3.0, 1.5), 45);
		System.out.println("Vérification Rect/Rect avec collision");
		assertTrue(r1.intersects(r2), "Les rectangles devraient s'intersecter");
		assertTrue(r2.intersects(r1), "La collision doit être symétrique");

		Rect r3 = new Rect(isu.new Coord(10.0, 5.0), isu.new Dimension(3.0, 1.5), 45);
		System.out.println("Vérification Rect/Rect SANS collision");
		assertFalse(r1.intersects(r3), "Les rectangles ne se touchent pas du tout !");
		assertFalse(r3.intersects(r1), "La non-collision doit être symétrique aussi !");

		double maxW = game.grid.width() * Game.cmPerCell;
		Rect rTorus1 = new Rect(isu.new Coord(maxW - 1.0, 4.0), isu.new Dimension(3.0, 2.0), 0);
		Rect rTorus2 = new Rect(isu.new Coord(1.0, 4.0), isu.new Dimension(3.0, 2.0), 0);
		System.out.println("Vérification Rect/Rect à travers le Tore (attendu : TRUE)");
		assertTrue(rTorus1.intersects(rTorus2), "Le Tore doit faire boucler les rectangles, ils se touchent !");
		assertTrue(rTorus2.intersects(rTorus1), "Symétrie torique obligatoire, putain !");
		
		System.out.println("Test16 : PASSED\n\n");
	}
	
	@Test
	public void test17() {
		System.out.println("Test17 : Cas de non-collision stricts (Rect/Rect et Rect/Circle)");
		Game game = new Game(10, 10);
		ISU isu = game.isu;

		Rect r1 = new Rect(isu.new Coord(5.0, 5.0), isu.new Dimension(4.0, 2.0), 0);
		Rect r2 = new Rect(isu.new Coord(9.05, 5.0), isu.new Dimension(4.0, 2.0), 0);
		assertFalse(r1.intersects(r2), "Erreur Rect/Rect : Les rectangles ne se touchent pas (écart de 0.05cm).");
		assertFalse(r2.intersects(r1), "Erreur Symétrie Rect/Rect");
		
		Rect rRot1 = new Rect(isu.new Coord(5.0, 5.0), isu.new Dimension(4.0, 2.0), 0);
		Rect rRot2 = new Rect(isu.new Coord(8.0, 5.0), isu.new Dimension(3.0, 1.0), 90);
		System.out.println("Rect / Rect (Esquive grâce à une rotation de 90°)");
		assertFalse(rRot1.intersects(rRot2), "Erreur SAT : La rotation à 90° aurait dû éviter la collision.");
		assertFalse(rRot2.intersects(rRot1), "Erreur Symétrie Rect/Rect avec rotation");
		
		Rect rect = new Rect(isu.new Coord(5.0, 5.0), isu.new Dimension(4.0, 2.0), 0);
		Circle circle = new Circle(isu.new Coord(8.6, 5.0), 1.5);
		System.out.println("Rect / Circle (Alignés horizontalement mais distants)");
		assertFalse(rect.intersects(circle), "Erreur Rect/Circle : Le cercle est trop loin sur la droite.");
		assertFalse(circle.intersects(rect), "Erreur Symétrie Rect/Circle");

		Rect rectCorner = new Rect(isu.new Coord(5.0, 5.0), isu.new Dimension(2.0, 2.0), 0);
		Circle circleCorner = new Circle(isu.new Coord(6.8, 6.8), 1.0);
		System.out.println("Rect / Circle (Le cercle frôle un coin en diagonale)");
		assertFalse(rectCorner.intersects(circleCorner), "Erreur Rect/Circle : Le cercle frôle le coin extérieur sans entrer.");
		assertFalse(circleCorner.intersects(rectCorner), "Erreur Symétrie Rect/Circle au coin");
		
		System.out.println("Test17 : PASSED\n\n");
	}
	
	@Test
	public void test18() {
		System.out.println("Test18 : Vérification du déploiement multi-cases d'une entité (deploy, retract, occupy)");
		Game game = new Game(8, 8);
		Grid grid = game.grid;

		Entity grosMonstre = new Entity("GrosMonstre");
		grosMonstre.setSize(game.isu.new Dimension(3*Game.cmPerCell, 2*Game.cmPerCell));
		grosMonstre.setCoord(grid.new Position(2, 2).toISUCoordCentered());
		
		System.out.println("Déploiement du monstre 3x2 centré en (2,2)...");
		grosMonstre.deploy();
		
		for (int y = 0; y <= 4; y++) {
			for (int x = 0; x <= 4; x++) {
				assertTrue(grid.cellAt(grid.new Position(x, y)).entities.contains(grosMonstre), "Erreur Deploy : La case (" + x + "," + y + ") devrait être occupée !");
			}
		}
		assertFalse(grid.cellAt(grid.new Position(5, 2)).entities.contains(grosMonstre), "Erreur Deploy : La case (3,1) ne devrait pas être occupée.");

		System.out.println("Retrait du monstre...");
		grosMonstre.retract();
		
		for (int y = 0; y <= 2; y++) {
			for (int x = 0; x <= 2; x++) {
				assertFalse(grid.cellAt(grid.new Position(x, y)).entities.contains(grosMonstre), "Erreur Retract : La case (" + x + "," + y + ") n'a pas été libérée !");
			}
		}

		System.out.println("Téléportation du monstre via occupy() vers la case (4, 4)...");
		Grid.Position nouvellePos = grid.new Position(6, 4);
		grosMonstre.occupy(nouvellePos);

		assertEquals(6, grosMonstre.position().x);
		assertEquals(4, grosMonstre.position().y);

		int[] colonnesAttendues = {4, 5, 6, 7, 0};
		int[] lignesAttendues = {2, 3, 4, 5, 6};

		for (int x : colonnesAttendues) {
			for (int y : lignesAttendues) {
				assertTrue(grid.cellAt(grid.new Position(x, y)).entities.contains(grosMonstre), "Erreur Occupy/Tore : La case périodique (" + x + "," + y + ") devrait être occupée !");
			}
		}

		System.out.println("Test18 : PASSED\n\n");
	}
	
	@Test
	public void test19() {
		System.out.println("Test19 : Vérification conjointe d'une entité 1x1 et d'une entité 3x3");
		Game game = new Game(6, 6);
		Grid grid = game.grid;

		Entity petite = new Entity("Petite1x1");
		petite.setSize(game.isu.new Dimension(1.0*Game.cmPerCell, 1.0*Game.cmPerCell));
		petite.setPosition(grid.new Position(1, 1));

		Entity grosse = new Entity("Grosse3x3");
		grosse.setSize(game.isu.new Dimension(3.0*Game.cmPerCell, 3.0*Game.cmPerCell));
		grosse.setPosition(grid.new Position(4, 4));

		System.out.println("Déploiement des deux entités...");
		petite.deploy();
		grosse.deploy();

		assertTrue(grid.cellAt(grid.new Position(1, 1)).entities.contains(petite), "Erreur 1x1 : La case (1,1) devrait contenir la petite entité.");

		System.out.println("Vérification de la zone de l'entité 3x3...");
		for (int y = 3; y <= 5; y++) {
			for (int x = 3; x <= 5; x++) {
				assertTrue(grid.cellAt(grid.new Position(x, y)).entities.contains(grosse), "Erreur 3x3 : La case (" + x + "," + y + ") devrait être occupée par la grosse entité !");
			}
		}

		assertFalse(grid.cellAt(grid.new Position(1, 1)).entities.contains(grosse), "Erreur : La grosse entité a envahi la case de la petite !");
		assertFalse(grid.cellAt(grid.new Position(4, 4)).entities.contains(petite), "Erreur : La petite entité a envahi la case centrale de la grosse !");

		System.out.println("Retrait de la petite entité uniquement...");
		petite.retract();
		assertFalse(grid.cellAt(grid.new Position(1, 1)).entities.contains(petite), "Erreur Retract : La petite entité est toujours en (1,1).");

		for (int y = 3; y <= 5; y++) {
			for (int x = 3; x <= 5; x++) {
				assertTrue(grid.cellAt(grid.new Position(x, y)).entities.contains(grosse), "Erreur Retract Croisé : Le retract de la petite a altéré la grosse en (" + x + "," + y + ") !");
			}
		}

		System.out.println("Test19 : PASSED\n\n");
	}
	
	@Test
	public void test20() {
		System.out.println("Test20 : Vérification du Model et des Bounding Shapes des Entités");
		Game game = new Game(15, 15);
		Grid grid = game.grid;
		Model model = new Model();
	
		model.add(new Obstacle(1, 1));
		
		PacMan pacman = new PacMan();
		model.add(pacman);
		pacman.occupy(model.grid().new Position(5, 5));
		pacman.setBounding();
		
		Gum gum = new Gum();
		model.add(gum);
		gum.occupy(model.grid().new Position(5, 6));
		gum.setBounding();

		Ghost ghost = new Ghost();
		model.add(ghost);
		ghost.occupy(model.grid().new Position(2, 2));
		ghost.setBounding();

		Boss boss = new Boss();
		model.add(boss);
		boss.occupy(model.grid().new Position(8, 4));
		boss.setBounding();
		
		pacman = null;
		boss = null;
		Obstacle obstacle = null;
		gum = null;
		ghost = null;
		
		for (Entity e : model.entities()) {
			if (e instanceof PacMan)
				pacman = (PacMan) e;
			if (e instanceof Boss)
				boss = (Boss) e;
			if (e instanceof Obstacle)
				obstacle = (Obstacle) e;
			if (e instanceof Gum)
				gum = (Gum) e;
			if (e instanceof Ghost)
				ghost = (Ghost) e;
		}
		
		assertNotNull(pacman, "PacMan n'a pas été ajouté au Model !");
		assertNotNull(boss, "Le Boss n'a pas été ajouté au Model !");
		assertNotNull(obstacle, "L'Obstacle n'a pas été ajouté au Model !");
		assertNotNull(gum, "La Gum n'a pas été ajoutée au Model !");
		assertNotNull(ghost, "Le Ghost n'a pas été ajouté au Model !");
		
		Grid.Position posObstacle = model.grid().new Position(0, 0);
		assertTrue(grid.cellAt(posObstacle).entities.stream().anyMatch(e -> e instanceof Obstacle), "L'obstacle n'est pas enregistré dans la case (0,0) de la grille !");
		
		assertNotNull(gum.position(), "La Gum doit avoir une position après occupy() !");
		assertTrue(grid.cellAt(gum.position()).entities.contains(gum), "La grille ne sait pas que la Gum est sur sa case !");
		
		assertNotNull(pacman.position(), "PacMan doit avoir une position !");
		
		int[][] expectedCells = {
			{8, 4},
			{8, 3},
			{8, 5}, {8, 6},
			{9, 4}, {10, 4}
		};
		
		for (int[] cell : expectedCells) {
			Grid.Position p = model.grid().new Position(cell[0], cell[1]);
			assertTrue(grid.cellAt(p).entities.contains(boss), "Le Boss en T devrait occuper la case (" + cell[0] + "," + cell[1] + ") !");
		}
		
		System.out.println("Test20 : PASSED\n\n");
	}
	
	@Test
	public void test21() {
		System.out.println("Test de la Physique : SuperBoundingBox & Swept AABB avec PacMan et un Obstacle (avec et sans collision) :");
		Game game = new Game(10, 10);
		Model model = new Model();
		Physic physic = new Physic(model);

		PacMan pacman = new PacMan();
		pacman.setPosition(model.grid().new Position(1, 1));
		pacman.setlSpeed(11.1, 3.7);
		pacman.setBounding();
		model.add(pacman);

//		System.out.println("Test de la super bounding box sur 1 seconde");
		Rect s_bb = physic.superBoundingBox(pacman, 1.0);
		assertNotNull(s_bb, "La Super Bounding Box ne doit pas être nulle !");
		assertEquals(2.775, s_bb.getHalfHeight(), 0.001);
		assertEquals(6.475, s_bb.getHalfWidth(), 0.001);
		
		Obstacle wall = new Obstacle(3, 2);
		model.add(wall);

		List<Entity> collisions = physic.getCollisionsForMovement(pacman, 1.0);
		assertFalse(collisions.isEmpty(), "La SuperBox devrait détecter le mur sur la trajectoire !");

		double entryTime = physic.getCollisionEntryTime(pacman, wall, 2.0);
		assertTrue(entryTime > 0.0 && entryTime < 1.0, "L'impact doit avoir lieu pendant le tick (entre 0 et 1) !");
		
		Ghost ghost = new Ghost();
		ghost.setPosition(model.grid().new Position(6, 7));
		model.add(ghost);
		
		double clearTime = physic.getCollisionEntryTime(pacman, ghost, 2.0);
		assertEquals(1.0, clearTime, 1e-9, "Pas de collision, entryTime doit renvoyer 1.0 !");
		System.out.println("Test Physic : PASSED\n\n");
	}
	
	@Test
	public void test22() {
		System.out.println("Test de non collision entre deux PacMan dont les trajectoires se croisent.");
		Game game = new Game(10, 10);
		Model model = new Model();
		Physic physic = new Physic(model);

		PacMan pacman1 = new PacMan();
		pacman1.setPosition(model.grid().new Position(3, 5));
		pacman1.setlSpeed(14.8, 7.4);
		pacman1.setBounding();
		model.add(pacman1);
		Rect pacman1_sbb = physic.superBoundingBox(pacman1, 1.0);
		assertNotNull(pacman1_sbb, "La Super Bounding Box de pacman1 ne doit pas être nulle !");
		assertEquals(4.625, pacman1_sbb.getHalfHeight(), 0.001);
		assertEquals(8.325, pacman1_sbb.getHalfWidth(), 0.001);
		
		PacMan pacman2 = new PacMan();
		pacman2.setPosition(model.grid().new Position(6, 8));
		pacman2.setlSpeed(3.7, -22.2);
		pacman2.setBounding();
		model.add(pacman2);
		Rect pacman2_sbb = physic.superBoundingBox(pacman2, 1.0);
		assertNotNull(pacman2_sbb, "La Super Bounding Box de pacman2 ne doit pas être nulle !");
		assertEquals(12.025, pacman2_sbb.getHalfHeight(), 0.001);
		assertEquals(2.775, pacman2_sbb.getHalfWidth(), 0.001);

		List<Entity> collisions = physic.getCollisionsForMovement(pacman1, 1.0);
		assertTrue(collisions.isEmpty(), "La SuperBoundingBox de pacman1 ne doit rencontrer aucune collision !");
	}
	
}