package engine;

public class Move {
	Point directionVecteur;
	int max;

	public Move(Point directionVecteur, int max) {
		this.directionVecteur = directionVecteur;
		this.max = max;
	}

	public boolean corresponds(Point from, Point to) {
		return false;
	}
}
