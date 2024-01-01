package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Point;

abstract public class Piece {
	PieceType type; // okay to be defined by subclasses only ?
	Point point;
	PlayerColor color;
	Board board;
	static final Move[] validMoves = new Move[] {};

	public Piece(Board board, PlayerColor color, Point point, PieceType type) {
		this.board = board;
		this.color = color;
		this.point = point;
		this.type = type;
	}

	public boolean isValid(Point to) {
		checkMoves(to); // Check si le move fait partie des moves basiques de la pièce
		checkFreePath(to); // Vérifie s'il y a une pièce sur le chemin
							// Si pièce sur le chemin, on est bloqué et ne peut pas avancer.
		checkDestination(to); // Vérifie si la destination est occupé ou pas
								// Si occupé, il faut check si c'est un ennemi, dans ce cas on peut bouger.
								// Sinon, on ne peut pas bouger
		checkIsEnemy(to); // Si la pièce à la destination est un ennemi, alors on la mange.

		return true;
	}

	boolean checkMoves(Point to) {
		for (var move : validMoves) {
			if (move.corresponds(point, to))
				return true;
		}

		return false;
	}

	// différent pion s'il y a personne devant il avance sinon sur la diagonale avec
	// pion inverse
	boolean checkDestination(Point to) {
		return false;
	}

	// différent cavalier parce qu'il peut sauter par dessus
	boolean checkFreePath(Point to) {
		return false;
	}

	private boolean checkIsEnemy(Point to) {
		return false;
	}

	public int getLine() {
		return color == PlayerColor.WHITE ? point.getCoordY() : 8 - point.getCoordY();
	}

	public int getColumn() {
		return color == PlayerColor.WHITE ? point.getCoordX() : 8 - point.getCoordX();
	}

	public PieceType getType() {
		return type;
	}

	public PlayerColor getColor() {
		return color;
	}
}
