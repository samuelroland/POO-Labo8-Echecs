package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;

/**
 * CastleMove class represents a castle move.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class CastleMove extends Move {
    private final boolean isKingSide; // true king castlemove, false queen castlemove
    private boolean castleIsOnRight; // Castling is done to the logical right of the king

    /**
     * Constructeur de CastleMove
     * @param isKingSide true si roque du roi, false si roque de la reine
     */
    public CastleMove(boolean isKingSide) {
        super(new Point(isKingSide ? 2 : -2, 0), 1);
        this.isKingSide = isKingSide;
    }

    /**
     * Applies the board changes in the board, the piece, the point to and if the board is a copy.
     * @param board the board
     * @param king the piece
     * @param to the point to
     * @param isBoardCopy if the board is a copy
     */
    @Override
    public void applyBoardChanges(Board board, Piece king, Point to, boolean isBoardCopy) {

        // Movement of the rook
        Point rookStart = new Point(castleIsOnRight ? 7 : 0, king.getPoint().y());
        Point rookEnd = new Point(castleIsOnRight ? 5 : 3, king.getPoint().y());
        board.movePieces(rookStart, rookEnd, isBoardCopy);

        // Movement of the king
        board.movePieces(king.getPoint(), to, isBoardCopy);
    }

    /**
     * Determines if the move corresponds to the vector, if the king and rook have not found, that king is not in check and not threatened on next piece
     * @param board the board
     * @param color the color
     * @param from the point from
     * @param to the point to
     * @return
     */
    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {

        // check if the move is valid
        if (!super.corresponds(board, color, from, to))
            return false;

        // Since the vectors are inverted in super.corresponds() for the black pieces, we must also invert the concept of right
        castleIsOnRight = color == PlayerColor.BLACK ? !isKingSide : isKingSide;

        // check if the king is in the right position
        var king = board.getPiece(from);
        if (king.getLastMove() != null) {
            return false;
        }

        // check if the rook is in the right position
        Point rookStart = new Point(castleIsOnRight ? 7 : 0, king.getPoint().y());
        Piece rook = board.getPiece(rookStart);
        if (rook == null || rook.getLastMove() != null) {
            return false;
        }

        // Note: no need to check if there are no pieces between the king and the rook because this is already done by Piece.checkFreePath()

        // Verify that the king is not already in check (castling is not allowed in this case even if it would allow the king to get out of check)
        if (board.isKingInCheck(color)) {
            return false;
        }

        // Verify that the intermediate square is not threatened (by simulating a move of the king to this square)
        Point moveOneCell = new Point(castleIsOnRight ? 1 : -1, 0);
        if (board.ownKingInCheckAfterMove(new Move(moveOneCell, 1), king, from.getAdded(moveOneCell))) {
            return false;
        }
        // Note: no need to check if the king will be in check on the destination square because that is already done by Piece.getValidMove()
        return true;
    }
}
