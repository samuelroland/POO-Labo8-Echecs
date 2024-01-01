package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class King extends Queen {
	public King(Board board, PlayerColor color, Point point) {
		super(board, color, point, PieceType.KING);
	}

	static final Move[] validMoves = new Move[] {
			new Move(new Point(0, 1), 1),
			new Move(new Point(0, -1), 1),
			new Move(new Point(1, 0), 1),
			new Move(new Point(-1, 0), 1),
			new Move(new Point(1, 1), 1),
			new Move(new Point(1, -1), 1),
			new Move(new Point(-1, 1), 1),
			new Move(new Point(-1, -1), 1)
	};

	boolean checkMoves(Point to) {
		if (super.checkMoves(to))
			return true;

		// Check petit roque
		// TODO: check supposition choix tour
		if (getLine() == 0 && !board.isEmpty(7, 0) && board.getPiece(7, 0).getType().equals(PieceType.ROOK)) {
			return true;
		}

		// Check grand roque

		return false;
	}

}
