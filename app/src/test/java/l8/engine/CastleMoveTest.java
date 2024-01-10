package l8.engine;

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

    CastleMoveTest() {
        board = new Board(new Piece[8][8]);
        whiteKing = new King(board, PlayerColor.WHITE, new Point(4, 0));
        whiteRookQueenSide = new Rook(board, PlayerColor.WHITE, new Point(0, 0));
        whiteRookKingSide = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        blackKing = new King(board, PlayerColor.BLACK, new Point(4, 7));
        board.addPiece(whiteKing);
        board.addPiece(whiteRookQueenSide);
        board.addPiece(whiteRookKingSide);
        board.addPiece(blackKing);
    }

    // Test du Petit Roque
    @Test
    public void testKingsideCastle() {
        assertInstanceOf(CastleMove.class, whiteKing.checkMoves(new Point(6, 0)));
    }

    //Test du Grand Roque
    @Test
    public void testQueensideCastle() {
        assertInstanceOf(CastleMove.class, whiteKing.checkMoves(new Point(2, 0)));
    }

    //Test roque avec des pions qui bloquent le chemin
    @Test
    public void testCastleBlockedPath() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Pawn blockingPawn = new Pawn(board, PlayerColor.WHITE, new Point(5, 0));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(blockingPawn);

        assertNull(king.checkMoves(new Point(4, 0)));
    }

    //Test roque ne se produit pas car le roi a déjà bougé
    @Test
    public void testCastleNotAllowedIfKingHasMoved() {
        whiteKing.setLastMove(new Move(new Point(1,1),1));   //un mouvement random juste pour simuler qu'il a bougé

        Move move = whiteKing.checkMoves(new Point(4, 0));
        assertNull(move, "CastleMove not allowed");
    }

    //Test roque ne se produit pas car la tour a déjà bougé
    @Test
    public void testCastleNotAllowedIfRookHasMoved() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        board.addPiece(king);
        board.addPiece(rook);

        rook.setLastMove(new Move(new Point(1,1),1));   //un mouvement random juste pour simuler qu'il a bougé

        Move move = king.checkMoves(new Point(7, 0));
        assertNull(move, "CastleMove not allowed");
    }

    //Test roi en échec -> fail
    @Test
    public void testCastleInCheck() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Queen enemyQueen = new Queen(board, PlayerColor.BLACK, new Point(4, 1));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(enemyQueen);

        assertNull(king.checkMoves(new Point(6, 0)), "King in check");
    }

    //Test que le roi ne soit pas menacé -> fail
    @Test
    public void testCastleThroughAttackedSquare() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Queen enemyQueen = new Queen(board, PlayerColor.BLACK, new Point(5, 1));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(enemyQueen);

        // Une case que le roi traverse est attaquée
        assertNull(king.checkMoves(new Point(6, 0)));
    }
}
