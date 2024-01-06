
package l8.engine.moves;

import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Pawn;
import l8.engine.pieces.Piece;

public class EnPassant extends Move{
    Pawn attacker;
    Pawn victim;

    public EnPassant(Point directionVecteur, int max, Pawn attacker, Pawn victim) {
        super(directionVecteur, max);
        this.attacker = attacker;
        this.victim = victim;
    }

    @Override
    public void applyBoardChanges(Board board, Piece p, Point to){
        // Attacker movement
        board.removePiece(attacker.getPoint());
        board.putPieceAt(attacker, to);

        // Victim's death
        board.removePiece(victim.getPoint());
        victim.setPoint(null);
    }
}

