
package l8.engine;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.moves.EnPassant;
import l8.engine.moves.Move;
import l8.engine.pieces.King;
import l8.engine.pieces.Pawn;
import l8.engine.pieces.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class EnPassantTest {
    // CheckMoves de Pawn
    Board board;
    Pawn attacker;
    Pawn victim;

    EnPassantTest() {
        board = new Board(new Piece[8][8]);

        board.addPiece(new King(board, PlayerColor.WHITE, new Point(7, 0)));
        board.addPiece(new King(board, PlayerColor.BLACK, new Point(0, 7)));
    }

    private void setAttacker(PlayerColor color, Point to) {
        attacker = new Pawn(board, color, to);
        board.addPiece(attacker);
    }

    private void setVictim(PlayerColor color, Point to) {
        victim = new Pawn(board, color, to);
        board.addPiece(victim);
    }

    private void moveIfValid(Pawn pawn, Point to) {
        Move move = pawn.getValidMove(to);
        assertNotNull(move);

        // If valid, let apply it
        move.applyBoardChanges(board, pawn, to);
        board.movePieces(pawn.getPoint(), to);
        pawn.setPoint(to);
    }

    @Test
    void testEnPassantRightWhiteAttacker() {
        setAttacker(PlayerColor.WHITE, new Point(3, 4));
        setVictim(PlayerColor.BLACK, new Point(4, 6));

        // Move 2 cases forward
        moveIfValid(victim, new Point(4, 4));

        // Attacker going behind victim
        moveIfValid(attacker, new Point(4, 5));

        // Verifies victim was killed
        assertNull(board.getPiece(new Point(4, 4)));
    }

    @Test
    void testEnPassantLeftWhiteAttacker() {

        setAttacker(PlayerColor.WHITE, new Point(3, 4));
        setVictim(PlayerColor.BLACK, new Point(2, 6));

        // Move 2 cases forward
        moveIfValid(victim, new Point(2, 4));

        // Attacker going behind victim
        moveIfValid(attacker, new Point(2, 5));

        assertNull(board.getPiece(new Point(2, 4)));
    }

    @Test
    void testEnPassantRightBlackAttacker() {
        setAttacker(PlayerColor.BLACK, new Point(4, 3));
        setVictim(PlayerColor.WHITE, new Point(5, 1));

        // Move 2 cases forward
        moveIfValid(victim, new Point(5, 3));

        // Attacker going behind victim
        moveIfValid(attacker, new Point(5, 2));

        assertNull(board.getPiece(new Point(5, 3)));
    }

    @Test
    void testEnPassantLeftBlackAttacker() {
        setAttacker(PlayerColor.BLACK, new Point(4, 3));
        setVictim(PlayerColor.WHITE, new Point(3, 1));

        // Move 2 cases forward
        moveIfValid(victim, new Point(3, 3));

        // Attacker going behind victim
        moveIfValid(attacker, new Point(3, 2));

        assertNull(board.getPiece(new Point(3, 3)));
    }

    @Test
    void testEnPassantVictimsLastMoveIsOneCase() {
        setAttacker(PlayerColor.WHITE, new Point(3, 4));
        setVictim(PlayerColor.BLACK, new Point(4, 5));

        // Move 2 cases forward
        moveIfValid(victim, new Point(4, 4));

        // Attacker going behind victim
        assertNull(attacker.getValidMove(new Point(4, 5)));

        // Verifies "victim" is present
        assertNotNull(board.getPiece(new Point(4, 4)));
    }

    // Cas où victime a bougé de deux avant le dernier tour -> fail
    @Test
    void testEnPassantVictimMovedAfterTwoCaseMove() {
        setAttacker(PlayerColor.WHITE, new Point(3, 3));
        setVictim(PlayerColor.BLACK, new Point(4, 5));
        Pawn otherPawn = new Pawn(board, PlayerColor.BLACK, new Point(0, 6));

        assertNotNull(victim.getValidMove(new Point(4, 4)));
        board.movePieces(victim.getPoint(), new Point(4, 4));
        assertNotNull(attacker.getValidMove(new Point(3, 4)));
        board.movePieces(attacker.getPoint(), new Point(3, 4));
        otherPawn.getValidMove(new Point(0, 4));
        board.movePieces(otherPawn.getPoint(), new Point(0, 4));
        assertNotNull(attacker.getValidMove(new Point(4, 5)));
        board.movePieces(attacker.getPoint(), new Point(4, 5));

        assertNotNull(board.getPiece(new Point(4, 4)));
    }

    // Cas où le pion a déjà bougé -> fail
}
