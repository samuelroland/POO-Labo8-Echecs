package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

public class Bishop extends Piece {
    public Bishop(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.BISHOP);
    }

    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(1, 1), 7),
                new Move(new Point(1, -1), 7),
                new Move(new Point(-1, -1), 7),
                new Move(new Point(-1, 1), 7)
        };
    }

}
