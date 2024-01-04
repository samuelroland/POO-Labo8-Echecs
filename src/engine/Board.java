package engine;

import java.security.InvalidParameterException;

import engine.pieces.Piece;

public class Board {
	private Piece[][] pieces;

	Board(Piece[][] pieces) {
		if (pieces.length != 8)
			throw new InvalidParameterException();
		for (int i = 0; i < 8; i++) {
			if (pieces[i].length != 8)
				throw new InvalidParameterException();
		}

		this.pieces = pieces;
	}

	// TODO
	void movePieces(Point from, Point to) {
		// params okay ??
		// how can we do multiple pieces moves ?
	}

	void addPiece(Piece piece) {
		if (piece == null)
			return;
		pieces[piece.getPoint().getCoordX()][piece.getPoint().getCoordY()] = piece;
	}

	public Piece getPiece(int x, int y) {
		// TODO: check boundaries
		return pieces[x][y];
	}

	public Piece getPiece(Point p) {
		return getPiece(p.getCoordX(), p.getCoordY());
	}

	public boolean isEmpty(int x, int y) {
		return getPiece(x, y) == null;
	}

	public boolean isEmpty(Point p) {
		return isEmpty(p.getCoordX(), p.getCoordY());
	}
}
