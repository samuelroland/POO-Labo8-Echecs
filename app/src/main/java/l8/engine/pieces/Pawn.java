package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.EnPassant;
import l8.engine.moves.Move;
import l8.engine.moves.PawnEatingMove;
import l8.engine.moves.TwoSquaresMove;
import l8.engine.Point;

/**
 * Pawn class
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class Pawn extends Piece {

    /**
     * Array of valid moves for the chess piece.
     * @return valid moves for Pawn
     */
    Move[] validMoves() {
        return new Move[] { new Move(new Point(0, 1), 1), new TwoSquaresMove(),
                new EnPassant(true),
                new EnPassant(false),
                new PawnEatingMove(true),
                new PawnEatingMove(false),
        };
    }

    /**
     * Constructor for Pawn
     * @param board the board
     * @param color the color
     * @param point the point
     */
    public Pawn(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.PAWN);
    }

    /**
     * Check if the destination is valid for the piece.
     * @param to the destination
     * @return true if the destination is valid
     */
    public boolean checkDestination(Point to) {
        // We want to maintain the check that the other piece is indeed an enemy
        if (!super.checkDestination(to))
            return false;

        // However, even if it's an enemy, we want to invalidate the straight move because pawns cannot capture a piece directly in front of them.
        // Note: Moves to an empty square, as well as moves to capture diagonally will not be cancelled.

        if (!board().isEmpty(to) && to.x() - point.x() == 0) {
            return false;
        }

        return true;
    }

    /**
     * Check the promotion
     * @return true if the pawn can be promoted
     */
    public boolean checkPromotion() {
        return getLine() == 7;
    }
}
