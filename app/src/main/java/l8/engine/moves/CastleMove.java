package l8.engine.moves;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;

public class CastleMove extends Move {
    private boolean isKingside; // true pour petit roque, false pour grand roque

    public CastleMove(boolean isKingside) {
        super(new Point(isKingside ? 2 : -2, 0),1);
        this.isKingside = isKingside;
    }

    // TODO : écrire
    @Override
    public boolean corresponds(PlayerColor color, Point from, Point to) {
        Point expectedKingDestination = isKingside ? new Point(6, from.y()) : new Point(2, from.y());
        return to.equals(expectedKingDestination);
    }

    public void applyBoardChanges(Board board, Piece piece, Point to) {
        System.out.println("Applying board changes in CastleMove");
        int kingRow = piece.getLine();
        int kingCol = 4;

        Point kingStartPos = new Point(kingCol, kingRow);
        Point kingEndPos;
        Point rookStartPos;
        Point rookEndPos;
        if (!piece.hasMoved()) {
            if (isKingside) {
                // Petit roque
                kingEndPos = new Point(6, kingRow);
                rookStartPos = new Point(7, kingRow);
                rookEndPos = new Point(5, kingRow);
                System.out.println("Applying board changes in PETIT ROQUE " + kingRow);
            } else {
                // Grand roque
                kingEndPos = new Point(2, kingRow);
                rookStartPos = new Point(0, kingRow);
                rookEndPos = new Point(3, kingRow);
                System.out.println("Applying board changes in GRAND ROQUE " + kingRow);
            }

            board.movePieces(kingStartPos, kingEndPos);
            board.movePieces(rookStartPos, rookEndPos);

            Piece king = board.getPiece(kingEndPos.x(), kingEndPos.y());
            Piece rook = board.getPiece(rookEndPos.x(), rookEndPos.y());
            if (king != null) king.setHasMoved(true);
            if (rook != null) rook.setHasMoved(true);
        }
    }
}
