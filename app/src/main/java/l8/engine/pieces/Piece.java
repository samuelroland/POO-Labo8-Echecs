package l8.engine.pieces;

import l8.chess.PieceType;
import l8.chess.PlayerColor;
import l8.engine.Board;
import l8.engine.moves.Move;
import l8.engine.Point;

import java.util.ArrayList;
import java.util.List;

abstract public class Piece {
    PieceType type; // okay to be defined by subclasses only ?
    Point point;
    PlayerColor color;
    Board board;
    private Point futurePosition;

    // Dernier mouvement, permet de savoir si on a bougé
    // et check avance de 2 pour En passant.
    Move lastMove = null;

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
            if (!checkDestination(to)) {
                System.out.println("checkDestination fails..");
                return null;
            }
            // TODO: check le roi pas en échecs

            // Check si cela met notre roi est en échecs
            // (cela inclus de le fait de sortir de l'échec,
            // après le mouvement il ne doit plus être en échec)
            // le mouvement n'est pas valide
            if (ourKingIsInCheckAfterPieceMove(to)) {
                System.out.println("Mouvement invalide car le roi tjrs en échecs");
                return null;
            }

            // Le mouvement est valide, il sera effectué donc on sauve sa future position
            futurePosition = to;
            System.out.println("getValidMove false");
            return foundMove;
        }
        System.out.println("getValidMove false");
        return null;
    }

    abstract Move[] validMoves();

    public void commitNewPosition() {
        point = futurePosition;
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

    // différent pion s'il y a personne devant il avance sinon sur la diagonale avec
    // pion inverse
    public boolean checkDestination(Point to) {
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
    public boolean checkFreePath(Point to) {
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
        int deltaX = to.x() - point.x();
        int deltaY = to.y() - point.y();

        int directionX = Integer.compare(deltaX, 0);
        // équivaut: int directionX = deltaX == 0 ? 0 : deltaX > 0 ? 1 : -1;
        int directionY = Integer.compare(deltaY, 0);

        // 2. Liste des points
        List<Point> intermediatePoints = new ArrayList<>();

        for (int i = 1; i < Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
            int intermediateX = point.x() + i * directionX;
            int intermediateY = point.y() + i * directionY;
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

    public boolean isEnemy(Point to) {
        var enemy = board.getPiece(to);
        if (enemy == null) {
            return false;
        }
        System.out.println("isEnemy " + (board.getPiece(to).getColor() != this.color));
        return enemy.getColor() != this.color;
    }

    public boolean isEnemy(Piece piece){
        if (piece == null) {
            return false;
        }
        System.out.println("isEnemy " + (piece.getColor() != this.color));
        return piece.getColor() != this.color;
    }

    // Check si après le mouvement donné, notre roi sera en échecs
    // (celui de la pièce qui bouge)
    public boolean ourKingIsInCheckAfterPieceMove(Point to) {
        var boardCopy = board.clone();
        boardCopy.movePieces(point, to);
        boardCopy.lookIfKingInCheck();
        if (boardCopy.kingIsInCheck(getColor())) {
            System.out.println("le roi sera en échecs avec ce coup");
            return true;
        }
        // Mettre à jour le status de l'autre roi dans le board réel
        // TODO: ici si c'est vrm le bon endroit...

        return false;
    }

    // Recevoir le numéro de ligne relatif à la couleur (de 0 à 7
    // le 0 étant en la première ligne en bas pour les blancs)
    // en considérant que les blancs sont toujours en bas
    public int getLine() {
        return color == PlayerColor.WHITE ? point.y() : 7 - point.y();
    }

    // Recevoir le numéro de colonne relatif à la couleur (de 0 à 7,
    // le 0 étant la colonne tout à gauche pour les blancs)
    // en considérant que les blancs sont toujours en bas
    public int getColumn() {
        return color == PlayerColor.WHITE ? point.x() : 7 - point.x();
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setPoint(Point p) {
        point = p;
    }

    public Point getPoint() {
        return point;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public boolean canMoveTo(Point to) {

        if (!checkFreePath(to) || !checkDestination(to)) {
            return false;
        }
        for (Move move : validMoves()) {
            if (move.corresponds(board, color, point, to)) {
                return true;
            }
        }
        return false;
    }
}
