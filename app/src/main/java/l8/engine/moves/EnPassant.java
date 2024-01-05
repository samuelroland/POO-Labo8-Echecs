
package l8.engine.moves;

import l8.chess.PieceType;
import l8.engine.Board;
import l8.engine.Point;
import l8.engine.pieces.Pawn;
import l8.engine.pieces.Piece;

import java.util.ArrayList;

public class EnPassant extends Move{
    Pawn attacker;
    Pawn victim;
    ArrayList<Point> enPassantAt = new ArrayList<>();
    boolean canEatLeft = false; //Necessaire?
    boolean canEatRight = false; //Necessaire?

    public EnPassant(Point directionVecteur, int max) {
        super(directionVecteur, max);
    }


    public boolean corresponds(Board board, Point from, Point to) {
        Piece attacker = board.getPiece(from);

        //Comme dans classe Piece
        // Determination of direction vector
        int deltaX = to.x() - from.x();
        int deltaY = to.y() - from.y();

        int directionX = Integer.compare(deltaX, 0);
        int directionY = Integer.compare(deltaY, 0);

        Point pointRight = new Point(attacker.getPoint().x() + directionX, attacker.getPoint().y() + directionY);


        /*if(!board.isEmpty(pointRight) && board.getPiece(pointRight).getType() == PieceType.PAWN && attacker.isEnemy(pointRight)){
            enPassantAt.add(pointRight);
            canEatRight = true;
        }
        if(!board.isEmpty(pointLeft) && board.getPiece(pointLeft).getType() == PieceType.PAWN && attacker.isEnemy(pointLeft)){
            enPassantAt.add(pointLeft);
            canEatLeft = true;
        }*/
        return true;
    }

    @Override
    public void applyBoardChanges(Board board, Piece p, Point to){
        board.removePiece(attacker.getPoint());
        board.putPieceAt(attacker, to);
        board.removePiece(victim.getPoint());
        victim.setPoint(null);
    }
}

