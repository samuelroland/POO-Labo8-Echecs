package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

abstract public class Piece {
	PieceType type;
	Point point;
	PlayerColor color;
	Board board;
	static final Move[] validMoves = new Move[] {};

	Piece(Board board, PlayerColor color) {
		this.board = board;
		this.color = color;
	}

	boolean isValid(Point to) {
		checkMoves(to);

		return false;
	}

	boolean checkMoves(Point to) {
		for (var move : validMoves) {
			if (move.corresponds(point, to))
				return true;
		}

		return false;
	}

	boolean checkDestination(Point to) {
		return false;
	}

	boolean checkFreePath(Point to) {
		return false;
	}

	private boolean checkIsEnemy(Point to) {
		return false;
	}

	int getLine() {
		return color == PlayerColor.WHITE ? point.getCoordY() : 8 - point.getCoordY();
	}

	int getColumn() {
		return color == PlayerColor.WHITE ? point.getCoordX() : 8 - point.getCoordX();
	}

	PlayerColor getColor() {
		return color;
	}
}
