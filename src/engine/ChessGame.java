package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.pieces.*;

public class ChessGame implements ChessController {

	private ChessView view;
	private Board board;

	@Override
	public void start(ChessView view) {
		this.view = view;
		view.startView();
	}

	@Override
	public boolean move(int fromX, int fromY, int toX, int toY) {

		// Aucune pièce à déplacer
		if (board.isEmpty(fromX, fromY))
			return false;

		var depart = new Point(fromX, fromY);
		var destination = new Point(toX, toY);
		var piece = board.getPiece(fromX, fromY);
		System.out.println(String.format("Mouvement de %s de (%d, %d) à (%d, %d)",
				piece.getType().name(), fromX, fromY, toX, toY));

		// Si le mouvement est valide, alors on le fait
		if (piece.isValid(destination)) {
			// TODO: better move implementation for special moves and eating
			board.movePieces(depart, destination);
			view.removePiece(fromX, fromY);
			view.putPiece(piece.getType(), piece.getColor(), toX, toY);
			return true;
		}
		return false; // TODO
	}

	@Override
	public void newGame() {
		view.displayMessage("Start new game"); // TODO
		Piece[][] pieces = new Piece[8][8];

		board = new Board(pieces);

		// BLANC
		// TODO: board.addPiece(new Rook(board, PlayerColor.WHITE, new Point(0, 0)));
		pieces[1][0] = new Knight(board, PlayerColor.WHITE, new Point(1, 0));
		pieces[2][0] = new Bishop(board, PlayerColor.WHITE, new Point(2, 0));
		pieces[3][0] = new Queen(board, PlayerColor.WHITE, new Point(3, 0));
		pieces[4][0] = new King(board, PlayerColor.WHITE, new Point(4, 0));
		pieces[5][0] = new Bishop(board, PlayerColor.WHITE, new Point(5, 0));
		pieces[6][0] = new Knight(board, PlayerColor.WHITE, new Point(6, 0));
		pieces[7][0] = new Rook(board, PlayerColor.WHITE, new Point(7, 0));
		for (int i = 0; i < 8; i++) {
			pieces[i][1] = new Pawn(board, PlayerColor.WHITE, new Point(i, 1));
		}

		// NOIR
		pieces[0][7] = new Rook(board, PlayerColor.BLACK, new Point(0, 7));
		pieces[1][7] = new Knight(board, PlayerColor.BLACK, new Point(1, 7));
		pieces[2][7] = new Bishop(board, PlayerColor.BLACK, new Point(2, 7));
		pieces[3][7] = new Queen(board, PlayerColor.BLACK, new Point(3, 7));
		pieces[4][7] = new King(board, PlayerColor.BLACK, new Point(4, 7));
		pieces[5][7] = new Bishop(board, PlayerColor.BLACK, new Point(5, 7));
		pieces[6][7] = new Knight(board, PlayerColor.BLACK, new Point(6, 7));
		pieces[7][7] = new Rook(board, PlayerColor.BLACK, new Point(7, 7));
		for (int i = 0; i < 8; i++) {
			pieces[i][6] = new Pawn(board, PlayerColor.BLACK, new Point(i, 6));
		}

		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces.length; j++) {
				if (!board.isEmpty(i, j)) {
					view.putPiece(pieces[i][j].getType(), pieces[i][j].getColor(), i, j);
				}
			}
		}

	}
}
