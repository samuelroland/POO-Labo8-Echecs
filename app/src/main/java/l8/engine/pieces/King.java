package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.CastleMove;
import l8.engine.moves.Move;
import l8.engine.Point;

public class King extends Queen {
    public King(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.KING);
    }

    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(0, 1), 1),
                new Move(new Point(0, -1), 1),
                new Move(new Point(1, 0), 1),
                new Move(new Point(-1, 0), 1),
                new Move(new Point(1, 1), 1),
                new Move(new Point(1, -1), 1),
                new Move(new Point(-1, 1), 1),
                new Move(new Point(-1, -1), 1),
                new CastleMove(true),
                new CastleMove(false)
        };
    }

    public Move checkMoves(Point to) {
        Move basicMove = super.checkMoves(to);
        if (basicMove != null) {
            setHasMoved(true);
            System.out.println("BasicMove");
            return basicMove;
        }

        return null;
    }

}
