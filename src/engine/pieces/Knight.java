package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.moves.Move;
import engine.Point;

public class Knight extends Piece {
    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(2, 1), 1),
                new Move(new Point(2, -1), 1),
                new Move(new Point(-2, -1), 1),
                new Move(new Point(-2, 1), 1),
                new Move(new Point(1, 2), 1),
                new Move(new Point(-1, 2), 1),
                new Move(new Point(1, -2), 1),
                new Move(new Point(-1, -2), 1) };
    };

    public Knight(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.KNIGHT);
    }

    boolean checkFreePath(Point to) {
        // Le cavalier peut passer par dessus n'importe quelle pièce
        return true;
    }

}
