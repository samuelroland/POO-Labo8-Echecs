package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Piece class serves as an abstract base for different types of chess pieces.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
abstract public class Piece {
    PieceType type;
    Point point;
    PlayerColor color;
    private Board board;
    private Board alternativeBoard = null;
    Move lastMove = null; // Last move, used to determine if a move has been made and checks for a two-square advance for En passant.

    abstract Move[] validMoves();

    /**
     * Constructor of Piece
     *
     * @param board the board
     * @param color the color of the piece
     * @param point the point of the piece
     * @param type  the type of the piece
     */
    public Piece(Board board, PlayerColor color, Point point, PieceType type) {
        this.board = board;
        this.color = color;
        this.point = point;
        this.type = type;
    }

    /**
     * Determines the board to use for validating movements. Can use an alternative board if needed for temporary or hypothetical move validations.
     *
     * @return the board
     */
    protected Board board() {
        return alternativeBoard == null ? board : alternativeBoard;
    }

    /**
     * Gets a valid move for the piece to a specified position.
     *
     * @param to the position
     * @return the valid move, or null
     */
    public Move getValidMove(Point to) {
        return getValidMove(to, null);
    }

    /**
     * Gets a valid move for the piece to a specified position.
     *
     * @param to             the target position
     * @param boardCopyToUse the board to use for validating the move
     * @return the valid move, or null
     */
    public Move getValidMove(Point to, Board boardCopyToUse) {
        return getValidMove(to, boardCopyToUse, false); // do not skip king in check verif
    }

    /**
     * Gets a valid move for the piece to a specified position.
     *
     * @param to                          the target position
     * @param alternativeBoard            the board to use for validating the move
     * @param skipKingInCheckVerification if true, skips the king in check verification
     * @return
     */
    public Move getValidMove(Point to, Board alternativeBoard, boolean skipKingInCheckVerification) {
        this.alternativeBoard = alternativeBoard;

        Move foundMove = checkMoves(to);
        // Check if the move is part of the piece's basic moves
        // Verify if there is a piece on the path, if yes then we are blocked and cannot proceed.
        if (foundMove != null && checkFreePath(to)) {
            // If occupied, check if it's an enemy piece, in which case movement is possible.
            // Otherwise, movement is not allowed.
            // Verify if the destination is occupied or not.
            // If the piece at the destination is an enemy, then capture it.
            if (!checkDestination(to)) {
                System.out.println("checkDestination fails..");
                return null;
            }

            // Check if this puts our king in check (this includes getting out of check, after the move, the king must not be in check) the move is not valid
            System.out.println("Check finale de mise en échecs " + !skipKingInCheckVerification);
            if (!skipKingInCheckVerification) {
                if (board().ownKingInCheckAfterMove(foundMove, this, to)) {
                    System.out.println("Mouvement invalide car le roi tjrs en échecs");
                    return null;
                }
            } else {
                System.out.println("skipKingInCheckVerification for Piece.getValidMove " + this);
            }

            System.out.println("getValidMove true");
            return foundMove;
        }
        System.out.println("getValidMove false");
        return null;
    }

    public Move checkMoves(Point to) {
        for (var move : validMoves()) {
            if (move.corresponds(board, color, point, to)) {
                System.out.println("checkMoves true");
                return move;
            }
        }
        System.out.println("checkMoves false");
        return null;
    }

    /**
     * Checks if the destination is valid.
     * @param to the point to check
     * @return true if the destination is valid, false otherwise
     */
    public boolean checkDestination(Point to) {
        // Si la case est occupée et que c'est une pièce de même couleur, on ne peut pas
        // bouger.
        if (!board().isEmpty(to) && !isEnemy(to)) {
            System.out.println("checkDestination false");
            return false;
        }
        System.out.println("checkDestination true");

        return true;
    }

    /**
     * Checks if the path is free.
     * @param to the point to check
     * @return true if the path is free, false otherwise
     */
    public boolean checkFreePath(Point to) {
        // For each square between Point Piece.point and Point to, check if the square is empty.
        // If the squares are empty, the piece can move, so return true.
        // Otherwise, the piece cannot move, so return false.
        // Special case for the knight: if the squares are not empty, it can still move, so return true.

        // Generating a list containing the intermediate points between from and to that we want to check.

        // 1. Determining the direction of the movement (to know how to increment the points in the vector)
        // Determining the movement vector
        int deltaX = to.x() - point.x();
        int deltaY = to.y() - point.y();

        int directionX = Integer.compare(deltaX, 0);
        // Equivalent to: int directionX = deltaX == 0 ? 0 : deltaX > 0 ? 1 : -1;
        int directionY = Integer.compare(deltaY, 0);

        // 2. List of intermediate points
        List<Point> intermediatePoints = new ArrayList<>();

        for (int i = 1; i < Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
            int intermediateX = point.x() + i * directionX;
            int intermediateY = point.y() + i * directionY;
            intermediatePoints.add(new Point(intermediateX, intermediateY));
        }

        for (Point intermediatePoint : intermediatePoints) {
            if (!board().isEmpty(intermediatePoint)) {
                System.out.println("checkFreePath false on " + intermediatePoint);
                System.out.println("actual piece is " + board().getPiece(intermediatePoint));
                return false;
            }
        }

        System.out.println("checkFreePath true");
        return true;
    }

    /**
     * Checks if the piece is an enemy
     *
     * @param to the point to check
     * @return true if the piece is an enemy, false otherwise
     */
    public boolean isEnemy(Point to) {
        var enemy = board().getPiece(to);
        if (enemy == null) {
            return false;
        }
        System.out.println("isEnemy " + (board().getPiece(to).getColor() != this.color));
        return enemy.getColor() != this.color;
    }

    /**
     * Checks if the piece is an enemy
     *
     * @param piece the piece to check
     * @return true if the piece is an enemy, false otherwise
     */
    public boolean isEnemy(Piece piece) {
        if (piece == null) {
            return false;
        }
        System.out.println("isEnemy " + (piece.getColor() != this.color));
        return piece.getColor() != this.color;
    }

    /**
     * Gets the relative line number of the piece based on its color. For white pieces, line numbers start from 0 at the bottom. For black pieces, line numbers are inverted with 0 at the top.
     *
     * @return int The line number (0-7) relative to the piece's color.
     */
    public int getLine() {
        return color == PlayerColor.WHITE ? point.y() : 7 - point.y();
    }

    /**
     * Gets the relative column number of the piece based on its color. For white pieces, column numbers start from 0 on the left. For black pieces, column numbers are inverted with 0 on the right.
     *
     * @return int The column number (0-7) relative to the piece's color.
     */
    public int getColumn() {
        return color == PlayerColor.WHITE ? point.x() : 7 - point.x();
    }

    /**
     * Retrieves the type of the piece.
     *
     * @return the type of the piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Retrieves the color of the piece.
     *
     * @return the color of the piece
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Sets the point (position) of the piece on the board.
     *
     * @param p the point
     */
    public void setPoint(Point p) {
        point = p;
    }

    /**
     * Retrieves the point of the piece on the board.
     *
     * @return the point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Retrieves the last move of the piece.
     *
     * @return the last move
     */
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Sets the last move of the piece.
     *
     * @param lastMove the last move
     */
    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * Returns a string representation of the piece, including its color, type, and current position.
     *
     * @return String A string representing the piece's attributes.
     */
    public String toString() {
        return super.toString() + ": " + color + " " + type + " on " + point;
    }
}
