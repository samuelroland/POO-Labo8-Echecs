package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

/**
 * Queen class
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class Queen extends Piece {
    public Queen(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.QUEEN);
    }

    /**
     * Constructor for Queen
     * @param board the board
     * @param color the color
     * @param point the point
     * @param type the type of the piece
     */
    protected Queen(Board board, PlayerColor color, Point point, PieceType type) {
        super(board, color, point, type);
    }

    /**
     * Array of valid moves for the chess piece.
     * @return valid moves for Queen
     */
    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(0, 1), 7),
                new Move(new Point(0, -1), 7),
                new Move(new Point(1, 0), 7),
                new Move(new Point(-1, 0), 7),
                new Move(new Point(1, 1), 7),
                new Move(new Point(1, -1), 7),
                new Move(new Point(-1, 1), 7),
                new Move(new Point(-1, -1), 7)
        };
    }
}
