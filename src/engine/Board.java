package engine;

import java.security.InvalidParameterException;

import chess.PieceType;
import chess.PlayerColor;
import engine.pieces.Piece;

public class Board {
	private Piece[][] pieces;
	private boolean blackKingInCheck = false;
	private boolean whiteKingInCheck = false;

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

	// Est-ce qu'un des 2 rois sont en échecs, sur le plateau temporaire fourni ?
	// Utile pour savoir s'il peut y sauter ou que c'est déjà le cas
	// si c'est une autre pièce qui bouge
	public void lookIfKingsInCheck(Piece[][] piecesCopy) {
		// On cherche d'abord la position des 2 rois
		// TODO: devrait-on plutot stocker les positisions des 2 rois ??
		Point blackKingPos = null, whiteKingPos = null;
		for (int i = 0; i < piecesCopy.length; i++) {
			for (int j = 0; j < piecesCopy[i].length; j++) {
				if (isEmpty(i, j))
					continue;
				Piece p = piecesCopy[i][j];
				if (p.getType().equals(PieceType.KING)) {
					if (p.getColor().equals(PlayerColor.BLACK)) {
						blackKingPos = p.getPoint();
					} else {
						whiteKingPos = p.getPoint();
					}
				}
			}
			// On a trouvé les 2 rois, on peut s'arrêter de chercher
			if (blackKingPos != null && whiteKingPos != null) {
				break;
			}
		}

		// Note: On est garanti d'avoir les positions des 2 rois, les pièces ne peuvent
		// jamais être retirées du plateau

		// On check pour toutes les pièces est-ce que d'aller sur cette position est un
		// coup valide, si oui, alors une pièce menace le roi de l'autre couleur
		for (int i = 0; i < piecesCopy.length; i++) {
			for (int j = 0; j < piecesCopy[i].length; j++) {
				if (isEmpty(i, j))
					continue;
				Piece p = getPiece(i, j);

				if (p.getColor().equals(PlayerColor.WHITE)) {
					// Est-ce que cette pièce blanche menace le roi noir ?
					if (p.isValid(blackKingPos)) {
						blackKingInCheck = true;
					}
				} else {
					// Est-ce que cette pièce noire menace le roi blanc ?
					if (p.isValid(whiteKingPos)) {
						whiteKingInCheck = true;
					}
				}
			}
		}
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
