package l8.engine;

import l8.chess.ChessController;
import l8.chess.ChessView;
import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.chess.PromotionChoice;
import l8.engine.pieces.*;

/**
 * ChessGame class represents the main control structure for a chess game.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */

public class ChessGame implements ChessController {
    private ChessView view;
    private Board board;
    private PlayerColor currentPlayerColor;

    /**
     * Constructor of ChessGame
     * @param view the view
     */
    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    /**
     * Determines if the move corresponds to the board, the color, the point from and the point to.
     * @param fromX the x coordinate of the point from
     * @param fromY the y coordinate of the point from
     * @param toX the x coordinate of the point to
     * @param toY the y coordinate of the point to
     * @return true if the move corresponds, false otherwise
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        // No piece to move
        if (board.isEmpty(fromX, fromY))
            return false;

        var destination = new Point(toX, toY);
        var piece = board.getPiece(fromX, fromY);

        if (piece.getColor() != currentPlayerColor) {
            System.out.println("C'est au tour de l'autre joueur...");
            return false;
        }

        // If the move is valid, then execute it
        var validMoveFound = piece.getValidMove(destination);
        if (validMoveFound != null) {
            validMoveFound.applyBoardChanges(board, piece, destination, false);
            validMoveFound.postBoardChangeActions(piece, false);

            // The move has been made, perhaps it's a pawn on the last row Pawn promotion needs to be handled.
            if (piece.getType().equals(PieceType.PAWN) && ((Pawn) piece).checkPromotion()) {
                promotion(destination, piece.getColor());
            }

            // Display a message in case of failure
            if (board.isKingInCheck(PlayerColor.WHITE) || board.isKingInCheck(PlayerColor.BLACK)) {
                view.displayMessage("Check !");
            }

            // Regenerate the view to display the latest version of the board with all the recent moves
            updateView();
            nextPlayer();

            board.setLastMovedPiece(piece);
            board.setLastMove(validMoveFound);

            return true;
        }

        return false;
    }

    /**
     * Passes the turn to the next player.
     */
    private void nextPlayer() {
        currentPlayerColor = (currentPlayerColor == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    /**
     * Promotes a pawn to a piece.
     * @param to the point to
     * @param color the color
     */
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

    /**
     * Sets up the default board.
     * @param board the board
     */
    static public void setupDefaultBoard(Board board) {
        // white
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

        // black
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

    /**
     * Starts a new game.
     */
    @Override
    public void newGame() {
        view.displayMessage("Start new game");
        Piece[][] pieces = new Piece[8][8];

        board = new Board(pieces);

        // white starts
        this.currentPlayerColor = PlayerColor.WHITE;

        setupDefaultBoard(board);

        updateView();
    }

    /**
     * Loads a game.
     */
    public void updateView() {
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
