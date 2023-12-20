package engine;

public class Board {
	Piece[][] pieces = new Piece[8][8];

	Piece getPiece(int x, int y) {
		// TODO: check boundaries
		return pieces[x][y];
	}

	boolean collision(Point from, Point to) {
		return false;
	}

	boolean isEmpty(int x, int y) {
		return getPiece(x, y) == null;
	}
}
