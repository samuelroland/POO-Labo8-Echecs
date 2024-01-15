package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;

public class Move {
    Point directionVector;
    int max;

    public Move(Point directionVecteur, int max) {
        this.directionVector = directionVecteur;
        this.max = max;
    }

    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {

        Point vector = color == PlayerColor.WHITE
                ? directionVector
                : directionVector.getMultiplied(-1);

        for (int i = 1; i <= max; i++) {
            if (from.getAdded(vector.getMultiplied(i)).equals(to)) {
                // System.out.println("Move.corresponds true " + this);
                return true;
            }
        }
        // System.out.println("Move.corresponds false " + this + color + from + to);
        return false;
    }

    public String toString() {
        return "vect" + directionVector + " * " + max;
    }

    public void applyBoardChanges(Board board, Piece piece, Point to, boolean isBoardCopy) {
        System.out.println("Applying board changes in Move : " + this);
        board.movePieces(piece.getPoint(), to, isBoardCopy);
    }

    public void postBoardChangeActions(Piece piece, boolean isBoardCopy) {
        if (!isBoardCopy) {
            piece.setLastMove(this);
        }
    }

    boolean collision(Board board, Point from, Point to) {
        return true;// TODO
    }

    public Point getDirectionVector() {
        return directionVector;
    }

    public boolean moveEquals(Move m) {
        return this.directionVector.equals(m.getDirectionVector());
    }

}
