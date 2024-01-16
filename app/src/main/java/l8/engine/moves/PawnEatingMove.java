package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;

/**
 * EnPassant class represents a en passant move.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class PawnEatingMove extends Move {
    /**
     * Constructeur de EnPassant
     * @param right true si le pion mange à droite, false si il mange à gauche
     */
    public PawnEatingMove(boolean right) {
        super(new Point(right ? 1 : -1, 1), 1);
    }

    /**
     * Determines if the move corresponds to the vector, and if the piece is an ennemy
     * @param board the board
     * @param color the color
     * @param from the point from
     * @param to the point to
     * @return
     */
    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {
        // The move must match and the destination must be an enemy
        return super.corresponds(board, color, from, to)
                && board.getPiece(from).isEnemy(to);
    }
}
