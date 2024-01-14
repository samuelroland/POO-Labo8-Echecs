package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.King;
import l8.engine.pieces.Piece;

public class CastleMove extends Move {
    private final boolean isKingSide; // true petit roque, false grand roque
    private boolean castleIsOnRight; // le roque se fait à droite logique du roi

    public CastleMove(boolean isKingSide) {
        super(new Point(isKingSide ? 2 : -2, 0), 1);
        this.isKingSide = isKingSide;
    }

    @Override
    public void applyBoardChanges(Board board, Piece king, Point to) {

        // Déplacement tour en fonction du roque choisi
        Point rookStart = new Point(castleIsOnRight ? 7 : 0, king.getPoint().y());
        Point rookEnd = new Point(castleIsOnRight ? 5 : 3, king.getPoint().y());
        board.movePieces(rookStart, rookEnd);

        // Déplacement roi
        board.movePieces(king.getPoint(), to);

        System.out.println("CastleMove done");
    }

    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {

        // Vérification que le mouvement correspond au vecteur défini
        if (!super.corresponds(board, color, from, to))
            return false;

        // Le vecteurs étant inversés dans super.corresponds() pour les noirs, on doit
        // également inversé le concept de droite
        castleIsOnRight = color == PlayerColor.BLACK ? !isKingSide : isKingSide;

        // Vérifie si roi a déjà bougé
        var king = board.getPiece(from);
        if (king.getLastMove() != null) {
            System.out.println("CastleMove.corresponds false king already moved " + king.getLastMove());
            return false;
        }

        // Vérifie si tour a déjà bougé
        Point rookStart = new Point(castleIsOnRight ? 7 : 0, king.getPoint().y());
        Piece rook = board.getPiece(rookStart);
        if (rook == null || rook.getLastMove() != null) {
            System.out.println("CastleMove.corresponds false rook already moved or doesnt not exist");
            return false;
        }

        // Vérifie aucune pièce entre roi et tour
        // TODO: refactor with checkfreepath
        int step = castleIsOnRight ? 1 : -1;
        for (int x = king.getPoint().x() + step; x != rookStart.x(); x += step) {
            if (!board.isEmpty(new Point(x, king.getPoint().y()))) {
                System.out.println("CastleMove.corresponds false some pieces in between");
                return false;
            }
        }

        // Vérifie que le roi n'est pas déjà en échecs (le roque est interdit dans ce
        // cas même si cela lui permet de sortir de son échec)
        if (board.isKingInCheck(color)) {
            System.out.println("CastleMove.corresponds false king already in check");
            return false;
        }

        // Vérifie que la case intermédiaire n'est pas menacée
        // (en simulant un mouvement du roi sur cette case)
        // TODO: test this
        if (board.ownKingInCheckAfterMove(new Move(new Point(directionVector.x() < 0 ? -1 : 1, 0), 1), king, to)) {
            System.out.println("CastleMove.corresponds false king in check in intermediatePoint");
            return false;
        }
        // Note: pas besoin de vérifier que le roi ne sera pas en échecs sur la case
        // d'arrivée parce que c'est déjà fait par Piece.getValidMove()

        System.out.println("CastleMove corresponds true !");
        return true;
    }
}
