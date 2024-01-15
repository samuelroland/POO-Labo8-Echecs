package l8.engine;

import java.security.InvalidParameterException;

import l8.chess.PlayerColor;
import l8.engine.moves.Move;
import l8.engine.pieces.*;

/**
 * Board class, contains the chessboard and the pieces.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class Board {
    private Piece[][] pieces;
    Move lastMove;
    Piece lastMovedPiece; // Reference to last moved piece

    /**
     * Constructor of Board
     * @param pieces the pieces of the board
     */
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

    /**
     * Moves a piece from a point to another
     * @param from the point from where the piece is moved
     * @param to the point where the piece is moved
     */
    public void movePieces(Point from, Point to) {
        movePieces(from, to, false);
    }

    /**
     * Moves a piece from a point to another
     * @param from the point from where the piece is moved
     * @param to the point where the piece is moved
     * @param isBoardCopy if the board is a copy
     */
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

    /**
     * Removes a piece from the board
     * @param x the x coordinate of the piece
     * @param y the y coordinate of the piece
     */
    public void removePiece(int x, int y) {
        pieces[x][y] = null;
    }

    /**
     * Removes a piece from the board
     * @param p the point of the piece
     */
    public void removePiece(Point p) {
        removePiece(p.x(), p.y());
    }

    /**
     *Checks if moving a given piece to a specified point would put the player's own king in check.
     * @param move the move to check
     * @param piece the piece to move
     * @param to the point to move the piece to
     * @return true if the move would put the player's own king in check, false otherwise
     */
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

    /**
     * Checks if the king of the given color is in check
     * @param kingColor the color of the king to check
     * @return true if the king is in check, false otherwise
     */
    public boolean isKingInCheck(PlayerColor kingColor) {
        Point kingPosition = kingPosition(kingColor);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = getPiece(x, y);
                if (piece != null && piece.getColor() != kingColor) {
                    System.out.println(">> Looking if " + piece.getType() + " on " + piece.getPoint()
                            + " is threatening the " + kingColor + " king");

                    // Regarder si la pièce peut faire ce mouvement mais ne doit pas checker
                    // si elle met son camp en échecs car cela reste une pièce menaçante
                    // On passe en copie de plateau soit même (cela pourrait être une copie ou non)
                    if (piece.getValidMove(kingPosition, this, true) != null) {
                        System.out.println("La pièce est menacante !!");
                        return true; // Le roi est en échec
                    }
                }
            }
        }
        System.out.println("Aucune pièce menacante...");
        return false; // Le roi n'est pas en échec
    }

    /**
     * Finds the position of the king of a given color on the board.
     * @param kingColor the color of the king to find
     * @return the position of the king
     */
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

    /**
     * Adds a piece to the board at its current point.
     * @param piece the piece to add
     */
    public void addPiece(Piece piece) {
        if (piece == null)
            return;
        pieces[piece.getPoint().x()][piece.getPoint().y()] = piece;
    }

    /**
     * Retrieves the piece located at the specified coordinates.
     * @param x the x coordinate of the piece
     * @param y the y coordinate of the piece
     * @return the piece at the specified coordinates, or null if there is no piece
     */
    public Piece getPiece(int x, int y) {
        if (x >= 8 || y >= 8)
            return null;
        return pieces[x][y];
    }

    /**
     * Retrieves the piece located at the specified point.
     * @param p the point of the piece
     * @return the piece at the specified point
     */
    public Piece getPiece(Point p) {
        return getPiece(p.x(), p.y());
    }

    /**
     * Checks if the specified coordinates is empty.
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return true if the specified coordinates is empty, false otherwise
     */
    public boolean isEmpty(int x, int y) {
        return getPiece(x, y) == null;
    }

    /**
     * Checks if the specified point is empty.
     * @param p the point to check
     * @return true if the specified point is empty, false otherwise
     */
    public boolean isEmpty(Point p) {
        return isEmpty(p.x(), p.y());
    }

    /**
     * Creates a clone of the board and its pieces.
     * @return the cloned board
     */
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

    /**
     * Retrieves the last move made on the board.
     * @return the last move made on the board
     */
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Retrieves the last moved piece.
     * @return the last moved piece
     */
    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }

    /**
     * Sets the last move made on the board.
     * @param lastMove the last move made on the board
     */
    void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * Sets the last moved piece.
     * @param lastMovedPiece
     */
    void setLastMovedPiece(Piece lastMovedPiece) {
        this.lastMovedPiece = lastMovedPiece;
    }
}
