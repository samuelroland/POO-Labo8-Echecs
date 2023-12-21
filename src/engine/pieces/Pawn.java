package engine.pieces;

import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

public class Pawn extends Piece {
	static final Move[] validMoves = new Move[] { new Move(new Point(0, 1), 1) };

	Pawn(Board board, PlayerColor color) {
		super(board, color);
	}


}
