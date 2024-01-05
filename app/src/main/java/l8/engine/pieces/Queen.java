package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

public class Queen extends Piece {
    public Queen(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.QUEEN);
    }

    protected Queen(Board board, PlayerColor color, Point point, PieceType type) {
        super(board, color, point, type);
    }

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
