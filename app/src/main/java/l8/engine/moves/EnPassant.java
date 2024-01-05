package engine.moves;

import chess.PieceType;
import engine.Board;
import engine.Point;
import engine.pieces.Pawn;
import engine.pieces.Piece;

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

    @Override
    public boolean corresponds(Board board, Point from, Point to) {
        Piece attacker = board.getPiece(from);
        if(attacker.getType() != PieceType.PAWN ) {
            return false;
        }

        //Comme dans classe Piece
        // Determination of direction vector
        int deltaX = to.getCoordX() - from.getCoordX();
        int deltaY = to.getCoordY() - from.getCoordY();

        int directionX = Integer.compare(deltaX, 0);
        int directionY = Integer.compare(deltaY, 0);

        Point pointRight = new Point(attacker.getPoint().getCoordX() + directionX, attacker.getPoint().getCoordY() + directionY);


        if(!board.isEmpty(pointRight) && board.getPiece(pointRight).getType() == PieceType.PAWN && attacker.isEnemy(pointRight)){
            enPassantAt.add(pointRight);
            canEatRight = true;
        }
        if(!board.isEmpty(pointLeft) && board.getPiece(pointLeft).getType() == PieceType.PAWN && attacker.isEnemy(pointLeft)){
            enPassantAt.add(pointLeft);
            canEatLeft = true;
        }
        return true;
    }

    @Override
    public void applyBoardChanges(Board board, Point to){
        board.removePiece(attacker.getPoint());
        board.putPieceAt(attacker, to);
        board.removePiece(victim.getPoint());
        victim.setPoint(null);
    }
}
