package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;

public class PawnEatingMove extends Move {
    public PawnEatingMove(boolean right) {
        super(new Point(right ? 1 : -1, 1), 1);
    }

    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {
        // Le mouvement doit correspondre et la destination doit être un ennemi
        return super.corresponds(board, color, from, to)
                && board.getPiece(from).isEnemy(to);
    }
}
