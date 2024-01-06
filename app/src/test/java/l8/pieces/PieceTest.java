package l8.pieces;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import l8.engine.Board;
import l8.engine.ChessGame;
import l8.engine.Point;
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
}
