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
        if (basicMove != null){
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

                // Fait de la prise en passant
                // Direction vector determination
                int deltaX = to.x() - point.x();
                int deltaY = to.y() - point.y();

                int directionX = Integer.compare(deltaX, 0);
                int directionY = Integer.compare(deltaY, 0);

                // Victim's supposed location
                Point victimsPoint = new Point(point.x() + directionX, point.y() + directionY);

                if(!board.isEmpty(victimsPoint) && board.getPiece(victimsPoint).getType() == PieceType.PAWN && this.isEnemy(victimsPoint)){
                    Pawn victim = (Pawn) board.getPiece(victimsPoint);
                    System.out.println("Checkmoves in Pawn en passant move");
                    return new EnPassant(diagMove.getDirectionVector(), 1, this, (Pawn) board.getPiece(victimsPoint));
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
