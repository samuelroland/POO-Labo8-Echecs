package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class Rook extends Piece {
	public Rook(Board board, PlayerColor color, Point point) {
		super(board, color, point, PieceType.ROOK);
	}

	static final Move[] validMoves = new Move[] {
			new Move(new Point(0, 1), 7),
			new Move(new Point(0, -1), 7),
			new Move(new Point(1, 0), 7),
			new Move(new Point(-1, 0), 7)
	};
}
