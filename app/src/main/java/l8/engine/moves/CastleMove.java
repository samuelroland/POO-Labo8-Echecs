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
    private boolean castleIsOnRight; // le roque se fait à droite logique du roi

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

        // Déplacement tour en fonction du roque choisi
        Point rookStart = new Point(castleIsOnRight ? 7 : 0, king.getPoint().y());
        Point rookEnd = new Point(castleIsOnRight ? 5 : 3, king.getPoint().y());
        board.movePieces(rookStart, rookEnd, isBoardCopy);

        // Déplacement roi
        board.movePieces(king.getPoint(), to, isBoardCopy);

        System.out.println("CastleMove done");
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

        // Note: pas besoin de vérifier qu'il n'y a aucune pièce entre roi et tour car
        // cela est déjà fait par Piece.checkFreePath()

        // Vérifie que le roi n'est pas déjà en échecs (le roque est interdit dans ce
        // cas même si cela lui permet de sortir de son échec)
        if (board.isKingInCheck(color)) {
            System.out.println("CastleMove.corresponds false king already in check");
            return false;
        }

        // Vérifie que la case intermédiaire n'est pas menacée
        // (en simulant un mouvement du roi sur cette case)
        Point moveOneCell = new Point(castleIsOnRight ? 1 : -1, 0);
        if (board.ownKingInCheckAfterMove(new Move(moveOneCell, 1), king, from.getAdded(moveOneCell))) {
            System.out.println("CastleMove.corresponds false king in check in intermediatePoint");
            return false;
        }
        // Note: pas besoin de vérifier que le roi ne sera pas en échecs sur la case
        // d'arrivée parce que c'est déjà fait par Piece.getValidMove()

        System.out.println("CastleMove corresponds true !");
        return true;
    }
}
