package engine.moves;

import engine.Board;
import engine.Point;

public class Move {
    Point directionVector;
    int max;

    public Move(Point directionVecteur, int max) {
        this.directionVector = directionVecteur;
        this.max = max;
    }

    public boolean corresponds(Point from, Point to) {
        int deltaX = to.getCoordX() - from.getCoordX();
        int absDeltaX = Math.abs(deltaX);
        int deltaY = to.getCoordY() - from.getCoordY();
        int absDeltaY = Math.abs(deltaY);

        // TODO: doit-on refactoriser ces calculs de math un peu difficile à comprendre?

        // If deltas are equivalent, meaning they are equal or with the same multiplier
        boolean equivalentDelta = true;
        boolean underMax = true;
        if (directionVector.getCoordX() != 0) {
            equivalentDelta = absDeltaX % directionVector.getCoordX() == 0;
            // The multiplier is under the max multiplier
            underMax = (absDeltaX / directionVector.getCoordX()) <= max;
        }

        if (directionVector.getCoordY() != 0)
            equivalentDelta = equivalentDelta && absDeltaY % directionVector.getCoordY() == 0;

        if (directionVector.getCoordX() != 0 && directionVector.getCoordY() != 0)
            equivalentDelta = equivalentDelta
                    && (absDeltaX / directionVector.getCoordX() == absDeltaY / directionVector.getCoordY());

        // We are on the same sides ()
        boolean sameSide = deltaX * directionVector.getCoordX() > 0 && deltaY * directionVector.getCoordY() > 0;

        return equivalentDelta && underMax && sameSide;
    }

	//TODO
	public void applyBoardChanges(Board board) {

	}

    boolean collision(Board board, Point from, Point to) {
        return true;// TODO
    }

}
