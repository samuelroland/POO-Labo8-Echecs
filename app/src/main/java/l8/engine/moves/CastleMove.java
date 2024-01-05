package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;

public class CastleMove extends Move {
    private boolean isKingside; // true pour petit roque, false pour grand roque

    public CastleMove(boolean isKingside) {
        super(new Point(0, 0), 0);
        this.isKingside = isKingside;
    }

    // TODO : écrire
    @Override
    public boolean corresponds(PlayerColor color, Point from, Point to) {
        return false;
    }

    public void applyBoardChanges(Board board, Piece piece, Point to) {
        System.out.println("Applying board changes in CastleMove");
        int kingRow = piece.getLine();
        int kingCol = 4;

        // TODO: refactor to variables like KingPosition and RookPosition
        if (isKingside) {
            // Petit roque
            board.movePieces(new Point(kingCol, kingRow), new Point(6, kingRow));
            board.movePieces(new Point(7, kingRow), new Point(5, kingRow));
        } else {
            // Grand roque
            board.movePieces(new Point(kingCol, kingRow), new Point(2, kingRow));
            board.movePieces(new Point(0, kingRow), new Point(3, kingRow));
        }
    }
}
