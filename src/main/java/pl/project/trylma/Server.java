package pl.project.trylma;


/*
 *
 *     Client -> Server           Server ->Client
 *  ---------------------      ---------------------
 *  1.MOVE move                1.STRING 'YOUR TURN'
 *                             1.STRING 'INVALID_MOVE'
 *
 *                             1.STRING 'VALID_MOVE'
 *                             1.STRING 'COMMIT_MOVE' 2.MOVE move
 *                             1.STRING 'END_GAME' 2.ENUM result
 *                             1.STRING 'MESSAGE' 2.STRING message
 *  1.STRING 'QUIT'
 *
 */

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.board.Board;
import pl.project.trylma.Models.board.IBoard;

public class Server {
  public static void main(String[] args) {
    IBoard board = Board.getInstance();
    ((Board) board).printAr();
    Coord coord = ((Board) board).getOppositeTop(new Coord(12, 0));
    ((Board) board).setField(coord.getX(), coord.getY(), 1);
    ((Board) board).printAr();
  }
}
