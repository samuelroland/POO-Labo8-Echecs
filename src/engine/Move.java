package engine;

public class Move {
	Point directionVector;
	int max;

	public Move(Point directionVecteur, int max) {
		this.directionVector = directionVecteur;
		this.max = max;
	}

	public boolean corresponds(Point from, Point to) {
		int deltaX = to.x - from.x;
		int absDeltaX = Math.abs(to.x - from.x);
		int deltaY = to.y - from.y;
		int absDeltaY = Math.abs(to.y - from.y);

		// TODO: doit-on refactoriser ces calculs de math un peu difficile à comprendre?

		// If deltas are equivalent, meaning they are equal or with the same multiplier
		boolean equivalentDelta = true;
		boolean underMax = true;
		if (directionVector.x != 0) {
			equivalentDelta = absDeltaX % directionVector.x == 0;
			// The multiplier is under the max multiplier
			underMax = (absDeltaX / directionVector.x) <= max;
		}

		if (directionVector.y != 0)
			equivalentDelta = equivalentDelta && absDeltaY % directionVector.y == 0;

		if (directionVector.x != 0 && directionVector.y != 0)
			equivalentDelta = equivalentDelta && (absDeltaX / directionVector.x == absDeltaY / directionVector.y);

		// We are on the same sides ()
		boolean sameSide = deltaX * directionVector.x > 0 && deltaY * directionVector.y > 0;

		return equivalentDelta && underMax && sameSide;
	}

	boolean collision(Board board, Point from, Point to) {
		return true;// TODO
	}

}
