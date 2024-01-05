package engine.moves;

import engine.Board;
import engine.Point;
import engine.pieces.Piece;

public class Move {
    Point directionVector;
    int max;

    public Move(Point directionVecteur, int max) {
        this.directionVector = directionVecteur;
        this.max = max;
    }

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
    public boolean corresponds(PlayerColor color, Point from, Point to) {
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

    public void applyBoardChanges(Board board, Piece piece, Point to) {
        System.out.println("Applying board changes in Move");
        board.movePieces(piece.getPoint(), to);
    }

    boolean collision(Board board, Point from, Point to) {
        return true;// TODO
    }

}
