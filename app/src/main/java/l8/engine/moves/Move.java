package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;
/**
 * Move class, contains the move.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class Move {
    Point directionVector;
    int max;

    /**
     * Constructor of Move
     * @param directionVecteur the direction vector
     * @param max the max
     */
    public Move(Point directionVecteur, int max) {
        this.directionVector = directionVecteur;
        this.max = max;
    }

    /**
     * Determines if the move corresponds to the board, the color, the point from and the point to.
     * @param board the board
     * @param color the color
     * @param from the point from
     * @param to the point to
     * @return true if the move corresponds, false otherwise
     */
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

    /**
     * Returns the string of the move.
     * @return the string of the move
     */
    public String toString() {
        return "vect" + directionVector + " * " + max;
    }

    /**
     * Applies the board changes in the board, the piece, the point to and if the board is a copy.
     * @param board the board
     * @param piece the piece
     * @param to the point to
     * @param isBoardCopy if the board is a copy
     */
    public void applyBoardChanges(Board board, Piece piece, Point to, boolean isBoardCopy) {
        System.out.println("Applying board changes in Move : " + this);
        board.movePieces(piece.getPoint(), to, isBoardCopy);
    }

    /**
     * Post board change actions in the piece and if the board is a copy.
     * @param piece the piece
     * @param isBoardCopy if the board is a copy
     */
    public void postBoardChangeActions(Piece piece, boolean isBoardCopy) {
        if (!isBoardCopy) {
            piece.setLastMove(this);
        }
    }

    /**
     * Returns the direction vector.
     * @return the direction vector
     */
    public Point getDirectionVector() {
        return directionVector;
    }

    /**
     * Determines if the move equals to the move m.
     * @param m the move
     * @return true if the move equals, false otherwise
     */
    public boolean moveEquals(Move m) {
        return this.directionVector.equals(m.getDirectionVector());
    }

}
