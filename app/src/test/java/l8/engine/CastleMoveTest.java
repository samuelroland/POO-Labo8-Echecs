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

    @BeforeEach
    public void setupBoard() {
        board = new Board(new Piece[8][8]);
        whiteKing = new King(board, PlayerColor.WHITE, new Point(4, 0));
        whiteRookKingSide = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        whiteRookQueenSide = new Rook(board, PlayerColor.WHITE, new Point(0, 0));
        blackKing = new King(board, PlayerColor.BLACK, new Point(4, 7));
        board.addPiece(whiteKing);
        board.addPiece(whiteRookQueenSide);
        board.addPiece(whiteRookKingSide);
        board.addPiece(blackKing);
    }

    // Test du Petit Roque sur roi blanc
    @Test
    public void testKingsideCastle() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        board.addPiece(king);
        board.addPiece(rook);
        Point dest = new Point(6, 0);
        Move move = king.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, king, dest);
        assertEquals(dest, king.getPoint());
        assertEquals(new Point(dest.x() - 1, dest.y()), rook.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), king);
        assertEquals(board.getPiece(dest.x() - 1, dest.y()), rook);
    }

    // Test du Grand Roque sur roi blanc
    @Test
    public void testQueensideCastle() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(0, 0));
        board.addPiece(king);
        board.addPiece(rook);
        Point dest = new Point(2, 0);
        Move move = king.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, king, dest);
        assertEquals(dest, king.getPoint());
        assertEquals(new Point(dest.x() + 1, dest.y()), rook.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), king);
        assertEquals(board.getPiece(dest.x() + 1, dest.y()), rook);
    }

    // Test du Petit Roque sur roi noir
    @Test
    public void testBlackKingsideCastle() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.BLACK, new Point(4, 7));
        Rook rook = new Rook(board, PlayerColor.BLACK, new Point(7, 7));
        board.addPiece(king);
        board.addPiece(rook);
        Point dest = new Point(6, 7);
        Move move = king.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, king, dest);
        assertEquals(dest, king.getPoint());
        assertEquals(new Point(dest.x() - 1, dest.y()), rook.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), king);
        assertEquals(board.getPiece(dest.x() - 1, dest.y()), rook);
    }

    // Test du Grand Roque sur roi noir
    @Test
    public void testBlackQueensideCastle() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.BLACK, new Point(4, 7));
        Rook rook = new Rook(board, PlayerColor.BLACK, new Point(0, 7));
        board.addPiece(king);
        board.addPiece(rook);
        Point dest = new Point(2, 7);
        Move move = king.getValidMove(dest);
        assertInstanceOf(CastleMove.class, move);

        // La tour et le roi bougent correctement
        move.applyBoardChanges(board, king, dest);
        assertEquals(dest, king.getPoint());
        assertEquals(new Point(dest.x() + 1, dest.y()), rook.getPoint());
        // et sont bougés sur le plateau
        assertEquals(board.getPiece(dest), king);
        assertEquals(board.getPiece(dest.x() + 1, dest.y()), rook);
    }

    // Test roque avec des pions qui bloquent le chemin
    @Test
    public void testCastleBlockedPath() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Pawn blockingPawn = new Pawn(board, PlayerColor.WHITE, new Point(5, 0));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(blockingPawn);

        Move move = king.getValidMove(new Point(4, 0));
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
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        board.addPiece(king);
        board.addPiece(rook);

        // TODO: replace with applyboardchanges
        rook.setLastMove(new Move(new Point(1, 1), 1)); // un mouvement random juste pour simuler qu'il a bougé

        Move move = king.getValidMove(new Point(6, 0));
        assertNull(move, "CastleMove shuold not be allowed");
    }

    // Test grand roque blanc ne se produit pas si on tente de bouger le roi de 3
    // cases à gauche
    @Test
    public void testCastleNotAllowedIf3Cases() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Rook rook2 = new Rook(board, PlayerColor.WHITE, new Point(0, 0));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(rook2);

        Move move = king.getValidMove(new Point(1, 0));
        assertNull(move, "CastleMove should not be allowed");
    }

    // Test roi en échec -> fail
    @Test
    public void testCastleInCheck() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Queen enemyQueen = new Queen(board, PlayerColor.BLACK, new Point(4, 1));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(enemyQueen);

        Move move = king.getValidMove(new Point(6, 0));
        assertNull(move, "King in check should have stopped the castle move");
    }

    // Test roi en échec sur la case intermédiaire -> fail
    @Test
    public void testCastleInCheckInIntermediatePoint() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Pawn enemyPawn = new Pawn(board, PlayerColor.BLACK, new Point(4, 1));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(enemyPawn);

        Move move = king.getValidMove(new Point(6, 0));
        assertNull(move, "King in check should have stopped the castle move");
    }

    // Test que le roi ne soit pas menacé sur la case d'arrivée -> fail
    @Test
    public void testCastleThroughAttackedSquare() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Queen enemyQueen = new Queen(board, PlayerColor.BLACK, new Point(7, 1));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(enemyQueen);

        Move move = king.getValidMove(new Point(6, 0));
        assertNull(move);
    }
}
