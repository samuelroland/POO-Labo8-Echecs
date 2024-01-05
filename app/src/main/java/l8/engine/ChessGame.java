package l8.engine;

import l8.chess.ChessController;
import l8.chess.ChessView;
import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.pieces.*;

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

        var destination = new Point(toX, toY);
        var piece = board.getPiece(fromX, fromY);
        System.out.println(String.format("\n >>>> Mouvement de %s de (%d, %d) à (%d, %d)",
                piece.getType().name(), fromX, fromY, toX, toY));

        // Si le mouvement est valide, alors on le fait
        var validMoveFound = piece.getValidMove(destination);
        if (validMoveFound != null) {
            validMoveFound.applyBoardChanges(board, piece, destination);

            if (piece.getType().equals(PieceType.PAWN) && ((Pawn) piece).checkPromotion()) {
                promotion();
            }

            // Regénérer la vue pour qu'elles y affichent la dernière version
            // du board avec tous les derniers mouvements
            updateView();

            return true;
        }
        return false; // TODO
    }

    // TODO
    private void promotion() {

    }

    @Override
    public void newGame() {
        view.displayMessage("Start new game"); // TODO
        Piece[][] pieces = new Piece[8][8];

        board = new Board(pieces);

        // BLANC
        board.addPiece(new Rook(board, PlayerColor.WHITE, new Point(0, 0)));
        board.addPiece(new Knight(board, PlayerColor.WHITE, new Point(1, 0)));
        board.addPiece(new Bishop(board, PlayerColor.WHITE, new Point(2, 0)));
        board.addPiece(new Queen(board, PlayerColor.WHITE, new Point(3, 0)));
        board.addPiece(new King(board, PlayerColor.WHITE, new Point(4, 0)));
        board.addPiece(new Bishop(board, PlayerColor.WHITE, new Point(5, 0)));
        board.addPiece(new Knight(board, PlayerColor.WHITE, new Point(6, 0)));
        board.addPiece(new Rook(board, PlayerColor.WHITE, new Point(7, 0)));
        for (int i = 0; i < 8; i++) {
            board.addPiece(new Pawn(board, PlayerColor.WHITE, new Point(i, 1)));
        }

        // NOIR
        board.addPiece(new Rook(board, PlayerColor.BLACK, new Point(0, 7)));
        board.addPiece(new Knight(board, PlayerColor.BLACK, new Point(1, 7)));
        board.addPiece(new Bishop(board, PlayerColor.BLACK, new Point(2, 7)));
        board.addPiece(new Queen(board, PlayerColor.BLACK, new Point(3, 7)));
        board.addPiece(new King(board, PlayerColor.BLACK, new Point(4, 7)));
        board.addPiece(new Bishop(board, PlayerColor.BLACK, new Point(5, 7)));
        board.addPiece(new Knight(board, PlayerColor.BLACK, new Point(6, 7)));
        board.addPiece(new Rook(board, PlayerColor.BLACK, new Point(7, 7)));
        for (int i = 0; i < 8; i++) {
            board.addPiece(new Pawn(board, PlayerColor.BLACK, new Point(i, 6)));
        }

        updateView();
    }

    public void updateView() {
        // TODO: magic values of 8 ??
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board.isEmpty(i, j)) {
                    Piece p = board.getPiece(i, j);
                    view.putPiece(p.getType(), p.getColor(), i, j);
                } else {
                    view.removePiece(i, j);
                }
            }
        }
    }
}
