package l8.engine;

import org.junit.jupiter.api.Test;


import l8.engine.pieces.Piece;
import l8.engine.pieces.*;

import l8.engine.moves.*;
import l8.chess.PlayerColor;

import static org.junit.jupiter.api.Assertions.*;


public class CastleMoveTest {

    // Test du Petit Roque
    @Test
    public void testKingsideCastle() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        board.addPiece(king);
        board.addPiece(rook);

        assertInstanceOf(CastleMove.class, king.checkMoves(new Point(6, 0)));
    }

    //Test du Grand Roque
    @Test
    public void testQueensideCastle() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(0, 0));
        board.addPiece(king);
        board.addPiece(rook);

        assertInstanceOf(CastleMove.class, king.checkMoves(new Point(2, 0)));
    }

    //Test roque avec des pions qui bloquent le chemin -> fail
    @Test
    public void testCastleBlockedPath() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Pawn blockingPawn = new Pawn(board, PlayerColor.WHITE, new Point(5, 0));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(blockingPawn);

        assertNull(king.checkMoves(new Point(6, 0)));
    }

    //Test roque ne se produit pas car le roi a déjà bougé
    @Test
    public void testCastleNotAllowedIfKingHasMoved() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        board.addPiece(king);
        board.addPiece(rook);

        king.setHasMoved(true);

        Move move = king.checkMoves(new Point(5, 0));
        assertNull(move, "CastleMove not allowed");
    }

    //Test roque ne se produit pas car la tour a déjà bougé -> fail
    @Test
    public void testCastleNotAllowedIfRookHasMoved() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        board.addPiece(king);
        board.addPiece(rook);

        rook.setHasMoved(true);

        Move move = king.checkMoves(new Point(5, 0));
        assertNull(move, "CastleMove not allowed");
    }

    //Test roi en échec -> fail
    @Test
    public void testCastleWhileInCheck() {
        Board board = new Board(new Piece[8][8]);
        King king = new King(board, PlayerColor.WHITE, new Point(4, 0));
        Rook rook = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
        Queen enemyQueen = new Queen(board, PlayerColor.BLACK, new Point(4, 1));
        board.addPiece(king);
        board.addPiece(rook);
        board.addPiece(enemyQueen);

        // Le roi est en échec
        assertNull(king.checkMoves(new Point(6, 0)), "Roi en echec");
    }
}
