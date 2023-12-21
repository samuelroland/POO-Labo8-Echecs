package engine.pieces;

import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;


public class Bishop extends Piece{
    Bishop(Board board, PlayerColor color) {
        super(board, color);
    }

    static final Move[] validMoves = new Move[] {
            new Move(new Point(1, 1), 7),
            new Move(new Point(1, -1), 7),
            new Move(new Point(-1, -1), 7),
            new Move(new Point(-1, 1), 7)
    };

}
