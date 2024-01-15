
package l8.engine.moves;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;

/**
 * EnPassant class represents a en passant move.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class EnPassant extends Move {
    /**
     * EnPassant constructor
     * @param right
     */
    public EnPassant(boolean right) {
        super(new Point(right ? 1 : -1, 1), 1);
    }

    /**
     * Determines if the move corresponds to the board, the color, the point from and the point to.
     * @param board the board
     * @param color the color
     * @param from the point from
     * @param to the point to
     * @return
     */
    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {
        if (!super.corresponds(board, color, from, to))
            return false;

        Piece attacker = board.getPiece(from);
        Point victimPoint = getVictimPosition(from, to);
        Piece victim = board.getPiece(victimPoint);

        if (!board.isEmpty(victimPoint) &&
                attacker.isEnemy(victim) &&
                victim.getType() == PieceType.PAWN &&
                victim == board.getLastMovedPiece() &&
                board.getLastMove().moveEquals(new TwoSquaresMove()) &&
                victim == board.getLastMovedPiece()) {
            System.out.println("Checkmoves in Pawn en passant move");
            return true;
        }
        return false;
    }

    /**
     * Retrieve the position of the victim, on the same column as the destination of the attacker and the same row as its starting position -> we find the position below to the right or below to the left, of the destination
     * @param from
     * @param to
     * @return
     */
    private Point getVictimPosition(Point from, Point to) {
        return new Point(to.x(), from.y());
    }

    /**
     * Applies the board changes in the board, the piece, the point to and if the board is a copy.
     * @param board the board
     * @param p the piece
     * @param to the point to
     * @param isBoardCopy if the board is a copy
     */
    @Override
    public void applyBoardChanges(Board board, Piece p, Point to, boolean isBoardCopy) {
        // Attacker movement
        Point start = p.getPoint();
        board.movePieces(p.getPoint(), to, isBoardCopy);

        // Victim's death
        board.removePiece(getVictimPosition(start, to));
    }
}
