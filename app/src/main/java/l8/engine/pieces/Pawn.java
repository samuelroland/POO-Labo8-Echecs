package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.EnPassant;
import l8.engine.moves.Move;
import l8.engine.Point;

public class Pawn extends Piece {

    Move[] validMoves() {
        return new Move[] { new Move(new Point(0, 1), 1) };
    }

    // Les mouvements particuliers pour manger en diagonale devant
    private static final Move[] frontDiagMoves = new Move[] {
            new Move(new Point(1, 1), 1), new Move(new Point(-1, 1), 1),
    };

    public Pawn(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.PAWN);
    }

    Move checkMoves(Point to) {
        var basicMove = super.checkMoves(to);
        if (basicMove != null) {
            System.out.println("Checkmoves in Pawn basic move");
            return basicMove;
        }

        // Permet à un pion de faire un saut de 2 au tout début
        var newPawnFirstMove = new Move(new Point(0, 2), 1);
        if (getLine() == 1 && newPawnFirstMove.corresponds(color, point, to)) {
            System.out.println("Checkmoves in Pawn first move");
            return newPawnFirstMove;
        }

        // Permet à un pion de manger une pièce de l'autre couleur en diagonale
        for (Move diagMove : frontDiagMoves) {
            if (diagMove.corresponds(color, point, to)) {

                // Peut le manger
                if (isEnemy(to)) {
                    System.out.println("Checkmoves in Pawn diag move");
                    return diagMove;
                }

                Point victimPoint = new Point(to.x(), point.y());
                System.out.println("victimPoint " + victimPoint);

                Piece potentialVictim = board.getPiece(victimPoint);
                if (!board.isEmpty(victimPoint) && potentialVictim.getType() == PieceType.PAWN
                        && this.isEnemy(victimPoint)) {
                    Pawn victim = (Pawn) potentialVictim;
                    System.out.println("Checkmoves in Pawn en passant move");
                    return new EnPassant(diagMove.getDirectionVector(), 1, this, victim);
                }
            }
        }

        System.out.println("Checkmoves in Pawn false");
        return null;
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
