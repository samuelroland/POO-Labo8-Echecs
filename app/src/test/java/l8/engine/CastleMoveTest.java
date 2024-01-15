package l8.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import l8.engine.pieces.Piece;
import l8.engine.pieces.*;

import l8.engine.moves.*;
import l8.chess.PlayerColor;

import static org.junit.jupiter.api.Assertions.*;

public class CastleMoveTest {
    private Board board;
    private King whiteKing;
    private Rook whiteRookQueenSide;
    private Rook whiteRookKingSide;
    private King blackKing;
    private Rook blackRookQueenSide;
    private Rook blackRookKingSide;

    @BeforeEach
    public void setupBoard() {
        board = new Board(new Piece[8][8]);
        whiteKing = new King(board, PlayerColor.WHITE, new Point(4, 0));
        whiteRookKingSide = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        whiteRookQueenSide = new Rook(board, PlayerColor.WHITE, new Point(0, 0));

        blackKing = new King(board, PlayerColor.BLACK, new Point(4, 7));
        blackRookQueenSide = new Rook(board, PlayerColor.BLACK, new Point(0,7));
        blackRookKingSide = new Rook(board, PlayerColor.BLACK, new Point(7, 7));

        board.addPiece(whiteKing);
        board.addPiece(whiteRookQueenSide);
        board.addPiece(whiteRookKingSide);
        board.addPiece(blackKing);
        board.addPiece(blackRookQueenSide);
        board.addPiece(blackRookKingSide);
    }

    // Test du Petit Roque sur roi blanc
    @Test
    public void testKingsideCastle() {
        Point dest = new Point(6, 0);
        Move move = whiteKing.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, whiteKing, dest, false);
        assertEquals(dest, whiteKing.getPoint());
        assertEquals(new Point(dest.x() - 1, dest.y()), whiteRookKingSide.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), whiteKing);
        assertEquals(board.getPiece(dest.x() - 1, dest.y()), whiteRookKingSide);
    }

    // Test du Grand Roque sur roi blanc
    @Test
    public void testQueensideCastle() {
        Point dest = new Point(2, 0);
        Move move = whiteKing.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, whiteKing, dest, false);
        assertEquals(dest, whiteKing.getPoint());
        assertEquals(new Point(dest.x() + 1, dest.y()), whiteRookQueenSide.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), whiteKing);
        assertEquals(board.getPiece(dest.x() + 1, dest.y()), whiteRookQueenSide);
    }

    // Test du Petit Roque sur roi noir
    @Test
    public void testBlackKingsideCastle() {
        Point dest = new Point(6, 7);
        Move move = blackKing.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, blackKing, dest, false);
        assertEquals(dest, blackKing.getPoint());
        assertEquals(new Point(dest.x() - 1, dest.y()), blackRookKingSide.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), blackKing);
        assertEquals(board.getPiece(dest.x() - 1, dest.y()), blackRookKingSide);
    }

    // Test du Grand Roque sur roi noir
    @Test
    public void testBlackQueensideCastle() {
        Point dest = new Point(2, 7);
        Move move = blackKing.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, blackKing, dest, false);
        assertEquals(dest, blackKing.getPoint());
        assertEquals(new Point(dest.x() + 1, dest.y()), blackRookQueenSide.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), blackKing);
        assertEquals(board.getPiece(dest.x() + 1, dest.y()), blackRookQueenSide);
    }

    // Test roque avec des pions qui bloquent le chemin
    @Test
    public void testCastleBlockedPath() {
        Pawn blockingPawn = new Pawn(board, PlayerColor.WHITE, new Point(5, 0));
        board.addPiece(blockingPawn);

        Move move = whiteKing.getValidMove(new Point(6, 0));
        assertNull(move, "CastleMove not allowed");
    }

    // Test roque ne se produit pas car le roi a déjà bougé
    @Test
    public void testCastleNotAllowedIfKingHasMoved() {
        whiteKing.setLastMove(new Move(new Point(1, 1), 1)); // un mouvement random juste pour simuler qu'il a bougé

        Move move = whiteKing.checkMoves(new Point(4, 0));
        assertNull(move, "CastleMove not allowed");
    }

    // Test roque ne se produit pas car la tour a déjà bougé
    @Test
    public void testCastleNotAllowedIfRookHasMoved() {
        whiteRookKingSide.setLastMove(new Move(new Point(1, 1), 1)); // un mouvement random juste pour simuler qu'il a bougé

        Move move = whiteKing.getValidMove(new Point(6, 0));
        assertNull(move, "CastleMove not allowed");
    }

    // Test grand roque blanc ne se produit pas si on tente de bouger le roi de 3
    // cases à gauche
    @Test
    public void testCastleNotAllowedIf3Cases() {
        Move move = whiteKing.getValidMove(new Point(1, 0));
        assertNull(move, "CastleMove should not be allowed");
    }

    // Test roi en échec -> fail
    @Test
    public void testCastleInCheck() {
        Queen blackQueen = new Queen(board, PlayerColor.BLACK, new Point(4, 1));
        board.addPiece(blackQueen);

        Move move = whiteKing.getValidMove(new Point(6, 0));
        assertNull(move, "King in check should have stopped the castle move");
    }

    // Test roi en échec sur la case intermédiaire -> fail
    @Test
    public void testCastleInCheckInIntermediatePoint() {
        Pawn blackPawn = new Pawn(board, PlayerColor.BLACK, new Point(4, 1));
        board.addPiece(blackPawn);

        Move move = whiteKing.getValidMove(new Point(6, 0));
        assertNull(move, "King in check should have stopped the castle move");
    }

    // Test que le roi ne soit pas menacé sur la case d'arrivée -> fail
    @Test
    public void testCastleThroughAttackedSquare() {
        Queen blackQueen = new Queen(board, PlayerColor.BLACK, new Point(7, 1));
        board.addPiece(blackQueen);

        Move move = whiteKing.getValidMove(new Point(6, 0));
        assertNull(move);
    }
}
