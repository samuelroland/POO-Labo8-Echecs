
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

    // faire setHasMovedTwo
    // au lieu de passer vecteur, passer droie gauche dans enPassant

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

    @Test
    void testEnPassantRightWhiteAttacker() {
        setAttacker(PlayerColor.WHITE, new Point(3, 4));
        setVictim(PlayerColor.BLACK, new Point(4, 6));

        Move victimMove2Forward = victim.getValidMove(new Point(4, 4));
        assertNotNull(victimMove2Forward);
        victimMove2Forward.applyBoardChanges(board, victim, new Point(4, 4));
        board.movePieces(victim.getPoint(), new Point(4, 4));
        victim.setPoint(new Point(4, 4));

        Move attackingMove = attacker.getValidMove(new Point(4, 5));
        assertNotNull(attackingMove);
        attackingMove.applyBoardChanges(board, attacker, new Point(4, 5));
        board.movePieces(attacker.getPoint(), new Point(4, 5));
        attacker.setPoint(new Point(4, 5));

        // Verifies victim was killed
        assertNull(board.getPiece(new Point(4, 4)));
    }

    @Test
    void testEnPassantLeftWhiteAttacker() {

        setAttacker(PlayerColor.WHITE, new Point(3, 4));
        setVictim(PlayerColor.BLACK, new Point(2, 6));

        assertNotNull(victim.getValidMove(new Point(2, 4)));
        board.movePieces(victim.getPoint(), new Point(2, 4));
        assertNotNull(attacker.getValidMove(new Point(2, 5)));
        board.movePieces(attacker.getPoint(), new Point(2, 5));

        assertNull(board.getPiece(new Point(2, 4)));
    }

    @Test
    void testEnPassantRightBlackAttacker() {
        setAttacker(PlayerColor.BLACK, new Point(4, 3));
        setVictim(PlayerColor.WHITE, new Point(5, 1));

        assertNotNull(victim.getValidMove(new Point(5, 3)));
        board.movePieces(victim.getPoint(), new Point(5, 3));
        assertNotNull(attacker.getValidMove(new Point(5, 2)));
        board.movePieces(attacker.getPoint(), new Point(5, 2));

        assertNull(board.getPiece(new Point(5, 3)));
    }

    @Test
    void testEnPassantLeftBlackAttacker() {
        setAttacker(PlayerColor.BLACK, new Point(4, 3));
        setVictim(PlayerColor.WHITE, new Point(3, 1));

        assertNotNull(victim.getValidMove(new Point(3, 3)));
        board.movePieces(victim.getPoint(), new Point(3, 3));
        assertNotNull(attacker.getValidMove(new Point(3, 2)));
        board.movePieces(attacker.getPoint(), new Point(3, 2));

        assertNull(board.getPiece(new Point(3, 3)));
    }

    @Test
    void testEnPassantVictimsLastMoveIsOneCase() {
        setAttacker(PlayerColor.WHITE, new Point(3, 4));
        setVictim(PlayerColor.BLACK, new Point(4, 5));

        assertNotNull(victim.getValidMove(new Point(4, 4)));
        board.movePieces(victim.getPoint(), new Point(4, 4));
        assertNotNull(attacker.getValidMove(new Point(4, 5)));
        board.movePieces(attacker.getPoint(), new Point(4, 5));

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
