package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class Pawn extends Piece {
	static final Move[] validMoves = new Move[] { new Move(new Point(0, 1), 1) };

	public Pawn(Board board, PlayerColor color, Point point) {
		super(board, color, point, PieceType.PAWN);
	}

}
