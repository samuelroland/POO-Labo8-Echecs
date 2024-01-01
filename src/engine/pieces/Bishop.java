package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class Bishop extends Piece {
	public Bishop(Board board, PlayerColor color, Point point) {
		super(board, color, point, PieceType.BISHOP);
	}

	static final Move[] validMoves = new Move[] {
			new Move(new Point(1, 1), 7),
			new Move(new Point(1, -1), 7),
			new Move(new Point(-1, -1), 7),
			new Move(new Point(-1, 1), 7)
	};

}
