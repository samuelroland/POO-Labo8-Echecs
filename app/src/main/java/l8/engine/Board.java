package l8.engine;

import java.security.InvalidParameterException;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.moves.Move;
import l8.engine.pieces.Piece;

public class Board {
    private Piece[][] pieces;
    private boolean blackKingInCheck = false;
    private boolean whiteKingInCheck = false;

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
        if (isEmpty(from) || from.equals(to))
            return;

        Piece p = getPiece(from);

        pieces[to.x()][to.y()] = p;
        pieces[from.x()][from.y()] = null;

        lastMovedPiece = p;
        lastMove = new Move(new Point(from.x() - to.x(), from.y() - to.y()), 1);

        // how can we do multiple pieces moves ?
    }

    public void removePiece(int x, int y) {
        pieces[x][y] = null;
    }

    public void removePiece(Point p) {
        removePiece(p.x(), p.y());
    }

    public void putPieceAt(Piece piece, Point p) {
        removePiece(piece.getPoint());
        pieces[p.x()][p.y()] = piece;
        piece.setPoint(p);
    }

    // Est-ce qu'un des 2 rois sont en échecs ?
    // Est normalé appelé sur une copie temporaire du board
    // Utile pour si une pièce mettrait son roi en échecs ou que
    // l'autre roi de couleur B sera en échecs
    // après le déplacement d'une pièce de la couleur A
    public void lookIfKingsInCheck() {
        // On cherche d'abord la position des 2 rois
        // TODO: devrait-on plutot stocker les positisions des 2 rois ??
        Point blackKingPos = null, whiteKingPos = null;
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (isEmpty(i, j))
                    continue;
                Piece p = getPiece(i, j);
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
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (isEmpty(i, j))
                    continue;
                Piece p = getPiece(i, j);

                if (p.getColor().equals(PlayerColor.WHITE)) {
                    // Est-ce que cette pièce blanche menace le roi noir ?
                    System.out.println(
                            ">> Looking if " + p.getType() + " on " + p.getPoint() + " is threatening the black king");
                    if (p.getValidMove(blackKingPos) != null) {
                        blackKingInCheck = true;
                    }
                } else {
                    // Est-ce que cette pièce noire menace le roi blanc ?
                    System.out.println(
                            ">> Looking if " + p.getType() + " on " + p.getPoint() + " is threatening the white king");
                    if (p.getValidMove(whiteKingPos) != null) {
                        whiteKingInCheck = true;
                    }
                }
            }
        }

        if (!whiteKingInCheck && !blackKingInCheck) {
            System.out.println("Aucun roi en échecs !");
        }
    }

    // Est-ce que le roi de la couleur donnée est en échecs ?
    // lookIfKingsInCheck() doit être appelé d'abord, les valeurs retournées
    // viennent de ce dernier calcul
    public boolean kingIsInCheck(PlayerColor kingColor) {
        if (blackKingInCheck || whiteKingInCheck)
            System.out.println(">> A king is in check !!");
        return (kingColor.equals(PlayerColor.BLACK)) ? blackKingInCheck : whiteKingInCheck;
    }

    void addPiece(Piece piece) {
        if (piece == null)
            return;
        pieces[piece.getPoint().x()][piece.getPoint().y()] = piece;
    }

    public Piece getPiece(int x, int y) {
        // TODO: check boundaries
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
}
