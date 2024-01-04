package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.moves.Move;
import engine.Point;

import java.util.ArrayList;
import java.util.List;

abstract public class Piece {
    PieceType type; // okay to be defined by subclasses only ?
    Point point;
    PlayerColor color;
    Board board;

    public Piece(Board board, PlayerColor color, Point point, PieceType type) {
        this.board = board;
        this.color = color;
        this.point = point;
        this.type = type;
    }

    public Move getValidMove(Point to) {
        boolean result = false;
        Move foundMove = checkMoves(to);
        // Check si le move fait partie des moves basiques de la pièce
        // Vérifie s'il y a une pièce sur le chemin, si oui alors on est
        // bloqué et ne
        // peut pas avancer.
        if (foundMove != null && checkFreePath(to)) {
            // Si occupé, il faut check si c'est un ennemi, dans ce cas on peut bouger.
            // Sinon, on ne peut pas bouger
            // Vérifie si la destination est occupé ou pas
            // Si la pièce à la destination est un ennemi, alors on la mange.
            if (checkDestination(to)) {
                result = true;
            }
            // TODO: check le roi pas en échecs

            // Check si après le mouvement, notre roi est en échecs (celui de la pièce qui bouge)
            var boardCopy = board.clone();
            boardCopy.movePieces(depart, destination);
            boardCopy.lookIfKingsInCheck();
            if (boardCopy.kingIsInCheck(piece.getColor()))
                return false;
        }
        System.out.println("isValid " + result);
        return foundMove;
    }

    abstract Move[] validMoves();

    Move checkMoves(Point to) {
        for (var move : validMoves()) {
            if (move.corresponds(point, to)) {
                System.out.println("checkMoves true");
                return move;
            }
        }
        System.out.println("checkMoves false");
        return null;
    }

    // différent pion s'il y a personne devant il avance sinon sur la diagonale avec
    // pion inverse
    boolean checkDestination(Point to) {

        // TODO Si Roi, vérifier si en échec ici, si c'est le cas retourner false.

        // Si la case est occupée et que c'est une pièce de même couleur, on ne peut pas
        // bouger.
        if (!board.isEmpty(to) && !isEnemy(to)) {
            System.out.println("checkDestination false");
            return false;
        }

        // TODO Pion: effectuer la promotion ici?
        System.out.println("checkDestination true");

        return true;
    }

    // différent cavalier parce qu'il peut sauter par dessus
    boolean checkFreePath(Point to) {
        // Pour chaque case entre Point Piece.point et Point to, checker si la case est
        // vide.
        // Si les cases sont vides, la pièce peut se déplacer donc retourner true.
        // Sinon, la pièce ne peut pas se déplacer donc retourner false.
        // Cas spécial pour le cavalier: si les cases ne sont pas vides, il peut quand
        // même se déplacer, donc retourner true.

        // TODO mettre cette méthode dans la classe Move?
        // Génération d'une liste contenant les points intermédiaires entre from et to
        // qu'on veut checker.

        // 1. Détermination de la direction du déplacement (pour savoir comment
        // incrémenter les points dans le vecteur)
        // Détermination du vecteur de déplacement
        int deltaX = to.getCoordX() - point.getCoordX();
        int deltaY = to.getCoordY() - point.getCoordY();

        int directionX = Integer.compare(deltaX, 0);
        // équivaut: int directionX = deltaX == 0 ? 0 : deltaX > 0 ? 1 : -1;
        int directionY = Integer.compare(deltaY, 0);

        // 2. Liste des points
        List<Point> intermediatePoints = new ArrayList<>();

        for (int i = 1; i < Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
            int intermediateX = point.getCoordX() + i * directionX;
            int intermediateY = point.getCoordY() + i * directionY;
            intermediatePoints.add(new Point(intermediateX, intermediateY));
        }

        for (Point intermediatePoint : intermediatePoints) {
            if (!board.isEmpty(intermediatePoint)) {
                System.out.println("checkFreePath false");
                return false;
            }
        }

        System.out.println("checkFreePath true");
        return true;
    }

    private boolean isEnemy(Point to) {
        return board.getPiece(to).getColor() != this.color;
    }

    public int getLine() {
        return color == PlayerColor.WHITE ? point.getCoordY() : 8 - point.getCoordY();
    }

    public int getColumn() {
        return color == PlayerColor.WHITE ? point.getCoordX() : 8 - point.getCoordX();
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public Point getPoint() {
        return point;
    }

    // Méthode qui vérfie s'il y a eu un premier mouvement
    private boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    // Modifie bool s'il y a eu un premier mouvement
    // TODO: a supprimer ?
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
