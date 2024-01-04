package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.moves.Move;
import engine.Point;

public class Pawn extends Piece {
    Move[] validMoves() {
        return new Move[] { new Move(new Point(0, 1), 1) };
    };

    public Pawn(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.PAWN);
    }

    Move checkMoves(Point to) {
        if (super.checkMoves(to) != null)
            return true;

        // Permet à un pion de faire un saut de 2 au tout début
        var newPawnFirstMove = new Move(new Point(0, 2), 1);
        if (getLine() == 1 && newPawnFirstMove.corresponds(point, to)) {
            return true;
        }
        return false;
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
}
