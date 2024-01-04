package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.moves.CastleMove;
import engine.moves.Move;
import engine.Point;

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
                new Move(new Point(-1, -1), 1)
        };
    }

    Move checkMoves(Point to) {
        Move foundMove = super.checkMoves(to);
        if (foundMove != null) {
            return foundMove;
        }

        // Check petit roque
        if (!this.hasMoved() && getLine() == 0) {
            Piece rook = board.getPiece(7, 0);
            if (rook != null && rook.getType() == PieceType.ROOK && !rook.hasMoved()) {
                if (board.isEmpty(5, 0) && board.isEmpty(6, 0)) {
                    return new CastleMove(true);
                }
            }
        }

        // Check grand roque
        if (!this.hasMoved() && getLine() == 0) {
            Piece rook = board.getPiece(0, 0);
            if (rook != null && rook.getType() == PieceType.ROOK && !rook.hasMoved()) {
                if (board.isEmpty(1, 0) && board.isEmpty(2, 0) && board.isEmpty(3, 0)) {
                    return new CastleMove(false);
                }
            }
        }
        return null;
    }

}
