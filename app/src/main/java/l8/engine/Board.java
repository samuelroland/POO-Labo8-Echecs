package l8.engine;

import java.security.InvalidParameterException;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.moves.Move;
import l8.engine.pieces.*;

public class Board {
    private Piece[][] pieces;
    private boolean blackKingInCheck = false;
    private boolean whiteKingInCheck = false;

    private Point whiteKingPosition;
    private Point blackKingPosition;

    void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    void setLastMovedPiece(Piece lastMovedPiece) {
        this.lastMovedPiece = lastMovedPiece;
    }

    Move lastMove;

    /* Reference to last moved piece */
    Piece lastMovedPiece;

    public Board(Piece[][] pieces) {
        if (pieces.length != 8)
            throw new InvalidParameterException();
        for (int i = 0; i < 8; i++) {
            if (pieces[i].length != 8)
                throw new InvalidParameterException();
        }

        this.pieces = pieces;
        this.lastMove = null;
        this.lastMovedPiece = null;
    }

    public void movePieces(Point from, Point to) {
        movePieces(from, to, false);
    }

    public void movePieces(Point from, Point to, boolean isBoardCopy) {
        if (isEmpty(from) || from.equals(to))
            return;

        Piece p = getPiece(from);

        pieces[to.x()][to.y()] = p;
        pieces[from.x()][from.y()] = null;

        // Ne pas modifier le point si ce n'est qu'une copie du board
        // la position interne n'est pas utilisée et la pièce n'est pas dupliquée durant
        // le clone du board, c'est donc la pièce réel
        if (!isBoardCopy) {
            p.setPoint(to);
        }
    }

    public void removePiece(int x, int y) {
        pieces[x][y] = null;
    }

    public void removePiece(Point p) {
        removePiece(p.x(), p.y());
    }

    // Check si après le mouvement donné, le roi sera en échecs
    // (celui de la pièce qui bouge)
    public boolean ownKingInCheckAfterMove(Move move, Piece piece, Point to) {
        var boardCopy = clone();
        move.applyBoardChanges(boardCopy, piece, to, true); // apply changes but do not change pieces as the boardCopy
                                                            // is a shallow copy
        if (boardCopy.isKingInCheck(piece.getColor())) {
            System.out.println("le roi " + piece.getColor() + " sera en échec avec ce coup");
            return true;
        }
        System.out.println("le roi " + piece.getColor() + " ne sera PAS en échec avec ce coup");
        return false;

    }

    public boolean isKingInCheck(PlayerColor kingColor) {
        Point kingPosition = kingPosition(kingColor);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = getPiece(x, y);
                if (piece != null && piece.getColor() != kingColor) {
                    System.out.println(">> Looking if " + piece.getType() + " on " + piece.getPoint()
                            + " is threatening the " + kingColor + " king");
                    if (piece.getValidMove(kingPosition, true) != null) {
                        System.out.println("La pièce est menacante !!");
                        return true; // Le roi est en échec
                    }
                }
            }
        }
        System.out.println("Aucune pièce menacante...");
        return false; // Le roi n'est pas en échec
    }

    Point kingPosition(PlayerColor kingColor) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = getPiece(x, y);
                if (piece instanceof King && piece.getColor() == kingColor) {
                    return new Point(x, y); // Position du roi trouvé
                }
            }
        }
        return null; // Le roi n'a pas été trouvé (ne devrait pas arriver)
    }

    // Est-ce que le roi de la couleur donnée est en échecs ?
    // lookIfKingsInCheck() doit être appelé d'abord, les valeurs retournées
    // viennent de ce dernier calcul
    // public boolean kingIsInCheck(PlayerColor kingColor) {
    // if (blackKingInCheck || whiteKingInCheck)
    // System.out.println(">> A king is in check !!");
    // return (kingColor.equals(PlayerColor.BLACK)) ? blackKingInCheck :
    // whiteKingInCheck;
    // }

    public void addPiece(Piece piece) {
        if (piece == null)
            return;
        pieces[piece.getPoint().x()][piece.getPoint().y()] = piece;
    }

    public Piece getPiece(int x, int y) {
        if (x >= 8 || y >= 8)
            return null;
        return pieces[x][y];
    }

    public Piece getPiece(Point p) {
        return getPiece(p.x(), p.y());
    }

    public boolean isEmpty(int x, int y) {
        return getPiece(x, y) == null;
    }

    public boolean isEmpty(Point p) {
        return isEmpty(p.x(), p.y());
    }

    public Board clone() {
        Piece[][] pieces = new Piece[8][8];
        Board clonedBoard = new Board(pieces);
        for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[x].length; y++) {
                clonedBoard.pieces[x][y] = this.pieces[x][y];
            }
        }
        return clonedBoard;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }
}
