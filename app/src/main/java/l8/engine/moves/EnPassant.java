
package l8.engine.moves;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;

public class EnPassant extends Move {

    public EnPassant(boolean right) {
        super(new Point(right ? 1 : -1, 1), 1);
    }

    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {
        if (!super.corresponds(board, color, from, to))
            return false;

        Piece p = board.getPiece(from);
        Point victimPoint = getVictimPosition(from, to);

        if (!board.isEmpty(victimPoint)
                && board.getPiece(victimPoint).getType() == PieceType.PAWN
                && p.isEnemy(victimPoint)) {
            System.out.println("Checkmoves in Pawn en passant move");
            return true;
        }

        return false;
    }

    // Récuper la position de la victime, sur la même colonne que la destination
    // de l'attaquant et la même ligne que sa position de départ
    // -> on trouve la position en dessous à droite ou en dessous à gauche,
    // de la destination
    private Point getVictimPosition(Point from, Point to) {
        return new Point(to.x(), from.y());
    }

    @Override
    public void applyBoardChanges(Board board, Piece p, Point to) {
        // Attacker movement
        board.movePieces(p.getPoint(), to);

        // Victim's death
        board.removePiece(getVictimPosition(p.getPoint(), to));
    }
}
