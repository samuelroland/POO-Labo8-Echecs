package engine;

import chess.PieceType;

abstract public class Piece {
	PieceType type;
	Position position;
	int max;
	static final Move[] relativeValidMoves = new Move[] {};

	boolean isValid(Position end) {
		Move triedMove = new Move(end.x - position.x, end.y + position.y, 1);
		for (var move : relativeValidMoves) {
			if (move.equals(triedMove))
				return true;
		}

		return false;
	}
}
