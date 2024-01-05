package l8;

import org.junit.jupiter.api.Test;


import l8.engine.Board;
import l8.engine.pieces.Piece;
import l8.engine.pieces.*;

import l8.engine.Point;
import l8.engine.moves.*;
import l8.chess.PlayerColor;

import static org.junit.jupiter.api.Assertions.*;


public class KingTest {

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

}
