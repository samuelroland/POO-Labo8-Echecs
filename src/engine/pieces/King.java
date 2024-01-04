package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class King extends Queen {
    public King(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.KING);
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

    boolean checkMoves(Point to) {
        if (super.checkMoves(to))
            return true;

        // Check petit roque
        //Vérifie que le roi n'a pas bougé
        if (!this.hasMoved() && getLine() == 0) {
            // Vérifier la tour
            Piece rook = board.getPiece(7, 0);
            if (rook != null && rook.getType() == PieceType.ROOK && !rook.hasMoved()) {
                // Vérifier cases entre le roi et la tour sont libres
                return board.isEmpty(5, 0) && board.isEmpty(6, 0);
            }
        }

        /*if (getLine() == 0 && !board.isEmpty(7, 0) && board.getPiece(7, 0).getType().equals(PieceType.ROOK)) {
            return true;
        }*/

        // Check grand roque
        if (!this.hasMoved() && getLine() == 0) {
            Piece rook = board.getPiece(0, 0);
            if (rook != null && rook.getType() == PieceType.ROOK && !rook.hasMoved()) {
                return board.isEmpty(1, 0) && board.isEmpty(2, 0) && board.isEmpty(3, 0);
            }
        }

        return false;
    }

}
