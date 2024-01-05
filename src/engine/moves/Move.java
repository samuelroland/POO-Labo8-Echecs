package engine.moves;

import chess.PlayerColor;
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

    public boolean corresponds(PlayerColor color, Point from, Point to) {

        Point vector = color == PlayerColor.WHITE
                ? directionVector
                : directionVector.getMultiplied(-1);

        for (int i = 1; i <= max; i++) {
            if (from.getAdded(vector.getMultiplied(i)).equals(to)) {
                System.out.println("Move.corresponds true");
                return true;
            }
        }
        System.out.println("Move.corresponds false");
        return false;
    }

    public String toString() {
        return "vect" + directionVector + " * " + max;
    }

    public void applyBoardChanges(Board board, Piece piece, Point to) {
        System.out.println("Applying board changes in Move");
        board.movePieces(piece.getPoint(), to);
    }

    boolean collision(Board board, Point from, Point to) {
        return true;// TODO
    }

}
