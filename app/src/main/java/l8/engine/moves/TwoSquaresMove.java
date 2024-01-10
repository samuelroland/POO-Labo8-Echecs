package l8.engine.moves;

import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.Point;

public class TwoSquaresMove extends Move {
    public TwoSquaresMove() {
        super(new Point(0, 2), 1);
    }

    public boolean corresponds(Board board, PlayerColor color, Point from, Point to) {
        // Le mouvement doit correspondre et être le premier pour être valide
        return super.corresponds(board, color, from, to)
                && board.getPiece(from).getLastMove() == null;
    }
}
