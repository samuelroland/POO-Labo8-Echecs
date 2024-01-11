package l8.engine.moves;

import l8.chess.PlayerColor;
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

    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {
        var king = board.getPiece(from);
        // Vérifie si roi a déjà bougé
        if (king.getLastMove() != null) {
            return false;
        }

        Board clonedBoard = board.clone();
        //Vérifie roi pas en échec
        if (clonedBoard.kingIsInCheck(color)) {
            return false;
        }

        // Vérifie si tour a déjà bougé
        Point rookStart = new Point(isKingSide ? 7 : 0, king.getPoint().y());
        Piece rook = board.getPiece(rookStart);
        if (rook == null || rook.getLastMove() != null) {
            return false;
        }

        // Vérifie aucune pièce entre roi et tour
        int step = isKingSide ? 1 : -1;
        for (int x = king.getPoint().x() + step; x != rookStart.x(); x += step) {
            if (!board.isEmpty(new Point(x, king.getPoint().y()))) {
                return false;
            }
        }

        // Vérifie cases où le roi passent ne sont pas attaquées
        /*for (int x = king.getPoint().x(); x != to.x(); x += step) {
            clonedBoard.movePieces(king.getPoint(), new Point(x, king.getPoint().y()));
            clonedBoard.lookIfKingsInCheck();
            if (clonedBoard.kingIsInCheck(color)) {
                return false;
            }
        }*/

        return true;
    }
}
