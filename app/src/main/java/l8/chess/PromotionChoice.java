package l8.chess;

import l8.chess.ChessView.UserChoice;

// Une option au choix de la pièce à utiliser 
// pour promouvoir une nouvelle pièce
public class PromotionChoice implements UserChoice {
    public final PieceType type;

    public PromotionChoice(PieceType type) {
        this.type = type;
    }

    public String textValue() {
        return type.name();
    }
}