package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.CastleMove;
import l8.engine.moves.Move;
import l8.engine.Point;

/**
 * King class
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class King extends Piece {
    /**
     * Constructor for King
     * @param board the board
     * @param color the color
     * @param point the point
     */
    public King(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.KING);
    }

    /**
     * Array of valid moves for the chess piece.
     * @return valid moves for King
     */
    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(0, 1), 1),
                new Move(new Point(0, -1), 1),
                new Move(new Point(1, 0), 1),
                new Move(new Point(-1, 0), 1),
                new Move(new Point(1, 1), 1),
                new Move(new Point(1, -1), 1),
                new Move(new Point(-1, 1), 1),
                new Move(new Point(-1, -1), 1),
                new CastleMove(true), // king side castle
                new CastleMove(false) // queen side castle
        };
    }
}