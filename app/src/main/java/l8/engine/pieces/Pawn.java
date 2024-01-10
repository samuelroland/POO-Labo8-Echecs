package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.EnPassant;
import l8.engine.moves.Move;
import l8.engine.moves.PawnEatingMove;
import l8.engine.moves.TwoSquaresMove;
import l8.engine.Point;

public class Pawn extends Piece {

    Move[] validMoves() {
        return new Move[] { new Move(new Point(0, 1), 1), new TwoSquaresMove(),
                new EnPassant(true),
                new EnPassant(false),
                new PawnEatingMove(true),
                new PawnEatingMove(false),
        };
    }

    public Pawn(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.PAWN);
    }

    boolean checkDestination(Point to) {
        // On veut garder le check que l'autre pièce est bien un ennemi
        if (!super.checkDestination(to))
            return false;

        // Par contre, même si c'est un ennemi on veut invalider le mouvement tout droit
        // car les pions ne peuvent pas manger une pièce en face
        // Note: Les mouvements sur une case vide, ainsi que les mouvements pour manger
        // en diagonales
        // ne seront ainsi pas annulés
        if (!board.isEmpty(to) && to.x() - point.x() == 0) {
            return false;
        }

        return true;
    }

    // Verification de la promotion
    public boolean checkPromotion() {
        return getLine() == 7;
    }
}
