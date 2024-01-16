package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;

/**
 * TwoSquaresMove class represents a two squares move.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class TwoSquaresMove extends Move {
    /**
     * Constructeur de TwoSquaresMove
     */
    public TwoSquaresMove() {
        super(new Point(0, 2), 1);
    }

    /**
     * Determines if the move corresponds to the vector, and if this is the first move
     * @param board the board
     * @param color the color
     * @param from the point from
     * @param to the point to
     * @return
     */
    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {
        // The move must match and be the first one to be valid
        return super.corresponds(board, color, from, to)
                && board.getPiece(from).getLastMove() == null;
    }
}
