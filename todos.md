Notes random:
- check du tour
- check des mouvements
- implémenter une interface ChessController (click new game: reset game + init board, move: pos start and end (check si piece existe), start)
- si possible éviter dutiliser piecetype dans la logique. (sauf si on est amené à faire un instanceof).
- aucune pièce est stockée dans l'interface
- mouv: remove pièce départ, put pièce arrivée
- se fait par groupe de 3

Conseils
- Bien organiser le travail
- arriver à factoriser les comportements communs entre pièce
- check des collisions (sauf pour cavalier) commune à toutes les pièces. et check de la trajectoire.

Règles:

```mermaid
classDiagram
direction LR
class Piece {
	PieceType type
	Position position
	PlayerColor color

	Piece(Board board, PlayerColor color)
	boolean isValid(Position to)
	boolean checkDestination(Position to) check destination is ennemy in case the destination is not empty
	boolean checkMoves(Position to)
	boolean checkFreePath(Position to)	//check du chemin intermédiaire sans la destination
	const Move[] possibleMoves
	
	-boolean checkIsEnemy(Position to) check destination is ennemy in case the destination is not empty

	getLine(): int
	getColumn(): int
	getColor(): PlayerColor
}

class Pawn extends Piece {
	const Position[] relativeValidMoves: [(0,1)]
	boolean checkMoves(Position to)//check en plus: move de 2 si ligne == 2, move diag si peu manger.

}

class Knight extends Piece {
	boolean checkCollision(Position to) //support fly over mode + 
}

class Position {
	int x
	int y
}

class Board {
	Piece[8][8] pieces
	getPiece(int x, int y): Piece
	collision(Point from, Point to)
	isEmpty(int x, int y): boolean
}

class Move {
	Position directionVecteur
	int max

	corresponds(Point from, Point to)
}

class ChessController {
	move(Position from, Position to)
}

```

Choix d'architectures:
1. Une pièce n'a pas de contexte
1. Une pièce peut déterminer les positions d'arrivée valides
   1. Chaque sous-classe de Pièce a une liste de mouvements relatifs checkés par isValid() dans la super classe.
   1. La rédéfinition de isValid dans chaque sous classe	

Liste des validations du ChessController:
1. Demandes à la pièce si le mouvement est valide
1. Si le mouvement est valide, il faut checker si c'est un cas particulier
   1. Si pas de cas particulier alors le 

TODO: continue la réflexion


idées:

canFlyOver() return true slt si cheval. permet de factoriser dans le controlleur.

cangohorizontal(), cangovertical() utilisé et redéfinit dans les sous classes de Piece. permet à Piece.isValid() de tester ça.

global moves: enum MoveDirection[] = {HORIZ, VERT, DIAG}
precisesMove: {FORWARD}


idée de Camille: faire que le roi hérite de la reine, tout est pareil sauf le max a 1 et la gestion du roque.