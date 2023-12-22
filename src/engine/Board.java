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

	public Piece getPiece(int x, int y) {
		// TODO: check boundaries
		return pieces[x][y];
	}

	public boolean isEmpty(int x, int y) {
		return getPiece(x, y) == null;
	}
}
