package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

public class Rook extends Piece {
    public Rook(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.ROOK);
    }

    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(0, 1), 7),
                new Move(new Point(0, -1), 7),
                new Move(new Point(1, 0), 7),
                new Move(new Point(-1, 0), 7)
        };
    }
}
