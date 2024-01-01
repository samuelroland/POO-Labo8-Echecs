package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class Queen extends Piece {
	public Queen(Board board, PlayerColor color, Point point) {
		super(board, color, point, PieceType.QUEEN);
	}

	protected Queen(Board board, PlayerColor color, Point point, PieceType type) {
		super(board, color, point, type);
	}

	static final Move[] validMoves = new Move[] {
			new Move(new Point(0, 1), 7),
			new Move(new Point(0, -1), 7),
			new Move(new Point(1, 0), 7),
			new Move(new Point(-1, 0), 7),
			new Move(new Point(1, 1), 7),
			new Move(new Point(1, -1), 7),
			new Move(new Point(-1, 1), 7),
			new Move(new Point(-1, -1), 7)
	};
}
