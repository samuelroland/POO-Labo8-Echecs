package l8.engine;

import l8.chess.ChessController;
import l8.chess.ChessView;
import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.chess.PromotionChoice;
import l8.engine.pieces.*;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;

    private PlayerColor currentPlayer;

    public ChessGame() {
        Piece[][] pieces = new Piece[8][8];
        this.board = new Board(pieces);
        // Les blancs commencent
        this.currentPlayer = PlayerColor.WHITE;
    }

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

        if (piece.getColor() != currentPlayer) {
            System.out.println("Autre joueur");
            return false;
        }

        System.out.println(String.format("\n >>>> Mouvement de %s de (%d, %d) à (%d, %d)",
                piece.getType().name(), fromX, fromY, toX, toY));

        // Si le mouvement est valide, alors on le fait
        var validMoveFound = piece.getValidMove(destination);
        if (validMoveFound != null) {
            validMoveFound.applyBoardChanges(board, piece, destination);

            // Le mouvement a été fait, peut-être que c'est un pion sur la dernière ligne
            // il faut gérer la promotion de pion.
            if (piece.getType().equals(PieceType.PAWN) && ((Pawn) piece).checkPromotion()) {
                promotion(destination, piece.getColor());
            }

            // Afficher un message en cas d'échec
            // TODO: not working because 2 boolean are not replicated in the real board
            if (board.kingIsInCheck(PlayerColor.WHITE) || board.kingIsInCheck(PlayerColor.BLACK)) {
                view.displayMessage("Check !");
            }

            // Regénérer la vue pour qu'elles y affichent la dernière version
            // du board avec tous les derniers mouvements
            updateView();
            nextPlayer();

            board.setLastMovedPiece(piece);
            board.setLastMove(validMoveFound);

            return true;
        }

        return false; // TODO
    }

    private void nextPlayer() {
        currentPlayer = (currentPlayer == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    private void promotion(Point to, PlayerColor color) {
        PromotionChoice answer = view.askUser("Promotion de pions",
                "Par quelle pièce souhaitez-vous promouvoir ce pion ?",
                new PromotionChoice[] {
                        new PromotionChoice(PieceType.ROOK),
                        new PromotionChoice(PieceType.KNIGHT),
                        new PromotionChoice(PieceType.BISHOP),
                        new PromotionChoice(PieceType.QUEEN)
                });

        Piece newPiece = switch (answer.type) {
            case PieceType.ROOK -> new Rook(board, color, to);
            case PieceType.KNIGHT -> new Knight(board, color, to);
            case PieceType.BISHOP -> new Bishop(board, color, to);
            case PieceType.QUEEN -> new Queen(board, color, to);
            default -> null;
        };

        board.addPiece(newPiece);
    }

    static public void setupDefaultBoard(Board board) {
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
    }

    @Override
    public void newGame() {
        view.displayMessage("Start new game"); // TODO
        Piece[][] pieces = new Piece[8][8];

        board = new Board(pieces);

        setupDefaultBoard(board);

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
