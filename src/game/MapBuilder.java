package game;

import game.entities.Borders;
import game.entities.MontFendu;

//classe pour construire la map, placer les obstacles et les entités
//WORK IN PROGRESS
public class MapBuilder {
	
	private MontFendu mfd;
	private Borders border;

	public MapBuilder() {
		this.mfd = new MontFendu();
		this.border = new Borders("1");
		
		placeObstacle();
	}
	
	public void placeObstacle() {
		
	}
}
