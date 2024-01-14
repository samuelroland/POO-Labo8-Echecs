package l8.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.ChessGame;
import l8.engine.Point;
import l8.engine.moves.Move;
import l8.engine.pieces.Pawn;
import l8.engine.pieces.Piece;

public class PieceTest {
    // Tests of checkFreePath
    Board board;

    PieceTest() {
        board = new Board(new Piece[8][8]);
        ChessGame.setupDefaultBoard(board);
    }

    @Test
    void checkFreePathWorks() {
        // Pawns have a free path in front and in diagonal
        assertTrue(board.getPiece(0, 1).checkFreePath(new Point(0, 2)));
        assertTrue(board.getPiece(0, 1).checkFreePath(new Point(0, 3)));
        assertTrue(board.getPiece(0, 1).checkFreePath(new Point(0, 6)));
        assertTrue(board.getPiece(0, 1).checkFreePath(new Point(5, 6)));

        //
        assertTrue(board.getPiece(0, 1).checkFreePath(new Point(5, 6)));
    }

    @Test
    void knightCanFlyOver() {
        assertTrue(board.getPiece(1, 0).checkFreePath(new Point(2, 2)));
        assertTrue(board.getPiece(1, 0).checkFreePath(new Point(0, 2)));
    }

    @Test
    void finalPositionIsSetInsidePieceAfterMove() {
        Point base = new Point(3, 1);
        Point dest = new Point(3, 3);
        Pawn pawn = new Pawn(board, PlayerColor.WHITE, base);
        board.addPiece(pawn);
        assertEquals(base, pawn.getPoint());

        Move m = pawn.getValidMove(dest);
        assertNotNull(m);
        m.applyBoardChanges(board, pawn, dest);
        assertEquals(dest, pawn.getPoint());
        assertEquals(pawn, board.getPiece(dest));
    }

    @Test
    void testSomeBasicMoves() {
        // White left bottom
        assertNotNull(board.getPiece(1, 0).getValidMove(new Point(0, 2)));
        assertNotNull(board.getPiece(1, 0).getValidMove(new Point(2, 2)));

        // Bishop left bottom
        board.removePiece(1, 1);
        assertNotNull(board.getPiece(2, 0).getValidMove(new Point(1, 1)));
        assertNotNull(board.getPiece(2, 0).getValidMove(new Point(0, 2)));

        // Rook left bottom
        board.removePiece(0, 1);
        assertNotNull(board.getPiece(0, 0).getValidMove(new Point(0, 6)));
        assertNotNull(board.getPiece(0, 0).getValidMove(new Point(0, 4)));

        // 3rd Pawn bottom left
        assertNotNull(board.getPiece(2, 1).getValidMove(new Point(2, 2)));
        assertNotNull(board.getPiece(2, 1).getValidMove(new Point(2, 3)));
    }
}
