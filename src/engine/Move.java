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

		// If deltas are equivalent, meaning they are equal or with the same multiplier
		boolean equivalentDelta = absDeltaX % directionVector.x == 0 && absDeltaY % directionVector.y == 0
				&& absDeltaX / directionVector.x == absDeltaY / directionVector.y;

		// The multiplier is under the max multiplier
		boolean underMax = (absDeltaX / directionVector.x) <= max;

		// We are on the same sides ()
		boolean sameSide = deltaX * directionVector.x > 0 && deltaY * directionVector.y > 0;

		return equivalentDelta && underMax && sameSide;
	}
}
