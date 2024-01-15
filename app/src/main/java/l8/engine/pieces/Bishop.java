package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

public class Bishop extends Piece {
    /**
     * Constructor for Bishop
     * @param board the board
     * @param color the color
     * @param point the point
     */
    public Bishop(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.BISHOP);
    }

    /**
     * Array of valid moves for the chess piece.
     * @return valid moves for Bishop
     */
    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(1, 1), 7),
                new Move(new Point(1, -1), 7),
                new Move(new Point(-1, -1), 7),
                new Move(new Point(-1, 1), 7)
        };
    }

}
