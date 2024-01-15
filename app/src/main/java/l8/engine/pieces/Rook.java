package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

/**
 * Rook class
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */

public class Rook extends Piece {
    /**
     * Constructor for Rook
     * @param board the board
     * @param color the color
     * @param point the point
     */
    public Rook(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.ROOK);
    }

    /**
     * Array of valid moves for the chess piece.
     * @return valid moves for Rook
     */
    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(0, 1), 7),
                new Move(new Point(0, -1), 7),
                new Move(new Point(1, 0), 7),
                new Move(new Point(-1, 0), 7)
        };
    }
}
