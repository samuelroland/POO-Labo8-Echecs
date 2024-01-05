package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

public class Pawn extends Piece {
    Move[] validMoves() {
        return new Move[] { new Move(new Point(0, 1), 1) };
    };

    public Pawn(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.PAWN);
    }

    Move checkMoves(Point to) {
        var basicMove = super.checkMoves(to);
        if (basicMove != null)
            return basicMove;
        // Permet à un pion de faire un saut de 2 au tout début
        var newPawnFirstMove = new Move(new Point(0, 2), 1);
        if (getLine() == 1 && newPawnFirstMove.corresponds(color, point, to)) {
            System.out.println("Checkmoves in Pawn true");
            return newPawnFirstMove;
        }
        System.out.println("Checkmoves in Pawn false");
        return null;
    }

    boolean checkDestination(Point to) {
        // Si la case est occupée (peu importe la couleur)
        if (!board.isEmpty(to)) {
            return false;
        }

        // TODO: make sure we managed to fix the case with eating in diagonal (not empty
        // but valid)
        return true;
    }

    // Verification de la promotion
    public boolean checkPromotion() {
        return getLine() == 7;
    }
}
