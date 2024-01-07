package l8.engine.moves;

import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Piece;

public class CastleMove extends Move {
    private final boolean isKingSide; // true petit roque, false grand roque

    public CastleMove(boolean isKingSide) {
        super(new Point(isKingSide ? 2 : -2, 0), 1);
        this.isKingSide = isKingSide;
    }

    @Override
    public void applyBoardChanges(Board board, Piece king, Point to) {
        if (!canCastle(board, king)) {
            return;
        }

        // Déplacement roi
        board.removePiece(king.getPoint());
        board.putPieceAt(king, to);

        // Déplacement tour en fonction du roque choisi
        Point rookStart = new Point(isKingSide ? 7 : 0, king.getPoint().y());
        Point rookEnd = new Point(isKingSide ? 5 : 3, king.getPoint().y());
        Piece rook = board.getPiece(rookStart);
        board.removePiece(rookStart);
        board.putPieceAt(rook, rookEnd);
        System.out.println("CastleMove done");
    }

    public boolean canCastle(Board board, Piece king) {
        // Vérifie si roi a déjà bougé
        if (king.hasMoved()) {
            return false;
        }

        // Vérifie si tour a déjà bougé
        Point rookStart = new Point(isKingSide ? 7 : 0, king.getPoint().y());
        Piece rook = board.getPiece(rookStart);
        if (rook == null || rook.hasMoved()) {
            return false;
        }

        // Vérifie aucune pièce entre roi et tour
        int step = isKingSide ? 1 : -1;
        for (int x = king.getPoint().x() + step; x != rookStart.x(); x += step) {
            if (!board.isEmpty(new Point(x, king.getPoint().y()))) {
                return false;
            }
        }

        // Vérifie roi pas en échec
        return !board.kingIsInCheck(king.getColor());
    }
}
