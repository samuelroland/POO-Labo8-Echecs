package engine.pieces;

import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class Knight extends Piece {
	static final Move[] validMoves = new Move[] {
			new Move(new Point(2, 1), 1),
			new Move(new Point(2, -1), 1),
			new Move(new Point(-2, -1), 1),
			new Move(new Point(-2, 1), 1),
			new Move(new Point(1, 2), 1),
			new Move(new Point(-1, 2), 1),
			new Move(new Point(1, -2), 1),
			new Move(new Point(-1, -2), 1) };

	Knight(Board board, PlayerColor color) {
		super(board, color);
	}
}
