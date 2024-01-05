package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

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
