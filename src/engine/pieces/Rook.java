package engine.pieces;

import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class Rook extends Piece{
    Rook(Board board, PlayerColor color) {
        super(board, color);
    }

    static final Move[] validMoves = new Move[]{
            new Move(new Point(0, 1), 7),
            new Move(new Point(0, -1), 7),
            new Move(new Point(1, 0), 7),
            new Move(new Point(-1, 0), 7)
    };
}
