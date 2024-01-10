/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package l8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import l8.engine.Point;
import l8.engine.moves.Move;
import l8.chess.PlayerColor;

class MoveCase {
    String title;
    Move m;
    Point from;
    Point[] okayTos; // target points that should pass
    Point[] koTos;// target points that should fail
    PlayerColor color;

    MoveCase(String title, Move m, Point from, Point[] okayTos, Point[] koTos, PlayerColor color) {
        this.title = title;
        this.m = m;
        this.from = from;
        this.okayTos = okayTos;
        this.koTos = koTos;
        this.color = color;
    }
}

class MoveTest {
    MoveCase[] tests = new MoveCase[] {
            new MoveCase("whiteSimplePawnMove",
                    new Move(new Point(0, 1), 1),
                    new Point(1, 1),
                    new Point[] {
                            new Point(1, 2),
                    },
                    new Point[] {
                            new Point(1, 4),
                            new Point(0, 3),
                            new Point(0, 1), // en arrière
                            new Point(0, 2),
                            new Point(2, 2),
                            new Point(2, 1),
                    },
                    PlayerColor.WHITE),
            new MoveCase("blackSimplePawnMove",
                    new Move(new Point(0, 1), 1),
                    new Point(1, 6),
                    new Point[] {
                            new Point(1, 5),
                    },
                    new Point[] {
                            new Point(2, 5),
                            new Point(2, 4),
                            new Point(3, 4),
                            new Point(1, 3),
                            new Point(0, 5),
                            new Point(1, 7), // en arrière
                    },
                    PlayerColor.BLACK),

            new MoveCase("simpleForwardMoveWithMax1",
                    new Move(new Point(0, 1), 1),
                    new Point(0, 0),
                    new Point[] {
                    },
                    new Point[] {
                            new Point(0, 2),
                            new Point(0, 3),
                            new Point(0, 4),
                    },
                    PlayerColor.WHITE),

            new MoveCase("moveWithMax3",
                    new Move(new Point(2, 1), 3),
                    new Point(0, 0),
                    new Point[] {
                            new Point(2, 1),
                            new Point(4, 2),
                            new Point(6, 3), // max of 3 jumps
                    },
                    new Point[] {
                    },
                    PlayerColor.WHITE),

            new MoveCase("moveWithMax7GoingOutOfBoardDoNotFails",
                    new Move(new Point(1, 1), 7),
                    new Point(1, 0),
                    new Point[] {
                            new Point(2, 1),
                            new Point(3, 2),
                            new Point(4, 3),
                            new Point(5, 4),
                            new Point(6, 5),
                            new Point(7, 6),
                    },
                    new Point[] {
                            new Point(7, 7),
                    },
                    PlayerColor.WHITE),

            new MoveCase("Move going out of board directly",
                    new Move(new Point(1, 1), 7),
                    new Point(1, 7),
                    new Point[] {
                    },
                    new Point[] {
                            new Point(7, 7),
                    },
                    PlayerColor.WHITE),

            new MoveCase("Knight like move",
                    new Move(new Point(1, 2), 1),
                    new Point(6, 0),
                    new Point[] {
                            new Point(7, 2),
                    },
                    new Point[] {
                    },
                    PlayerColor.WHITE),
    };

    @Test
    void runCases() {
        for (MoveCase c : tests) {
            for (Point to : c.okayTos) {
                assertTrue(c.m.corresponds(null, c.color, c.from, to),
                        "Move from:" + c.from + " -> to:" + to + " should correspond to move " + c.m);
            }
            for (Point to : c.koTos) {
                assertFalse(c.m.corresponds(null, c.color, c.from, to),
                        "Move from:" + c.from + " -> to:" + to + " should NOT correspond to move " + c.m);
            }
        }
    }
}
