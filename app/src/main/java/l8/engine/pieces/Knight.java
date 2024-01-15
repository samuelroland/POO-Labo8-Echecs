package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

/**
 * Knight class
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class Knight extends Piece {
    /**
     * Array of valid moves for the chess piece.
     * @return valid moves for Knight
     */
    Move[] validMoves() {
        return new Move[] {
                new Move(new Point(2, 1), 1),
                new Move(new Point(2, -1), 1),
                new Move(new Point(-2, -1), 1),
                new Move(new Point(-2, 1), 1),
                new Move(new Point(1, 2), 1),
                new Move(new Point(-1, 2), 1),
                new Move(new Point(1, -2), 1),
                new Move(new Point(-1, -2), 1) };
    };

    /**
     * Constructor for Knight
     * @param board the board
     * @param color the color
     * @param point the point
     */
    public Knight(Board board, PlayerColor color, Point point) {
        super(board, color, point, PieceType.KNIGHT);
    }

    /**
     * Check if the path is free
     * @param to the point
     * @return true if the path is free
     */
    public boolean checkFreePath(Point to) {
        // Le cavalier peut passer par dessus n'importe quelle pièce
        return true;
    }

}
