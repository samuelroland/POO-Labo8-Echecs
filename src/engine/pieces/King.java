package engine.pieces;

import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class King extends Queen{
    King(Board board, PlayerColor color) {
        super(board, color);
    }

    static final Move[] validMoves = new Move[]{
            new Move(new Point(0, 1), 1),
            new Move(new Point(0, -1), 1),
            new Move(new Point(1, 0), 1),
            new Move(new Point(-1, 0), 1),
            new Move(new Point(1, 1), 1),
            new Move(new Point(1, -1), 1),
            new Move(new Point(-1, 1), 1),
            new Move(new Point(-1, -1), 1)
    };
}
