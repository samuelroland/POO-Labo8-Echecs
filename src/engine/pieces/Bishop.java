package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.moves.Move;
import engine.Point;

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
