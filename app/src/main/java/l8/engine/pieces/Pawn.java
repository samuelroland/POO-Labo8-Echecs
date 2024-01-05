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
    };

    // Les mouvements particuliers pour manger en diagonale devant
    private static Move eatingDiagRight = new Move(new Point(1, 1), 1);
    private static Move eatingDiagLeft = new Move(new Point(-1, 1), 1);
    private static EnPassant enPassantRight = new EnPassant(new Point(1, 1), 1);
    private static EnPassant enPassantLeft = new EnPassant(new Point(-1, 1), 1);

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
        // TODO check is enemy dans move?
        // Permet à un pion de manger une pièce de l'autre couleur en diagonale
        if (eatingDiagRight.corresponds(color, point, to)&& isEnemy(to)) {
        System.out.println("Checkmoves in Pawn true with diag eating");
            return eatingDiagRight;
        }
        if (eatingDiagLeft.corresponds(color, point, to) && isEnemy(to)) {
        System.out.println("Checkmoves in Pawn true with diag eating");

            return eatingDiagLeft;
        }

        // Permet à un pion d'effectuer le mouvement en passant
        if(enPassantRight.corresponds(color, point, to)){
            System.out.println("Checkmoves in Pawn true with en passant right");
            return enPassantRight;
        }
        if (enPassantLeft.corresponds(color, point, to)){

        }

        System.out.println("Checkmoves in Pawn false");
        return null;
    }

    boolean checkDestination(Point to) {
        //On veut garder le check que l'autre pièce est bien un ennemi
        if (!super.checkDestination(to)) return false;

        // Par contre, même si c'est un ennemi on veut invalider le mouvement tout droit
        // car les pions ne peuvent pas manger une pièce en face
        // Note: Les mouvements sur une case vide, ainsi que les mouvements pour manger en diagonales
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
