package pl.project.trylma.models.board;

import org.junit.Before;
import org.junit.Test;
import pl.project.trylma.models.Coord;
import pl.project.trylma.models.Field;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import java.util.*;

import static org.junit.Assert.*;

/** How board looks like after basic establish:
 *     0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27
 *
 * 0                                       2
 * 1                                    2     2
 * 2                                 2     2     2
 * 3                              2     2     2     2
 * 4   1     1     1     1     7     7     7     7     7     3     3     3     3
 * 5      1     1     1     7     7     7     7     7     7     3     3     3
 * 6         1     1     7     7     7     7     7     7     7     3     3
 * 7            1     7     7     7     7     7     7     7     7     3
 * 8               7     7     7     7     7     7     7     7     7
 * 9            4     7     7     7     7     7     7     7     7     6
 * 10        4     4     7     7     7     7     7     7     7     6     6
 * 11     4     4     4     7     7     7     7     7     7     6     6     6
 * 12  4     4     4     4     7     7     7     7     7     6     6     6     6
 * 13                             5     5     5     5
 * 14                                5     5     5
 * 15                                   5     5
 * 16                                      5
 */

public class BoardTest {
  Board board = Board.getInstance();

  @Before
  public void Initialize() {
    this.board.resetBoard();
  }

  @Test
  public void getAvailableMoves() {
    Coord coordUnderTest = new Coord(12, 0);
    Field fieldUnderTest = new Field(coordUnderTest, Owner.SECOND);
    List<Coord> availableMoves = board.getAvailableMoves(fieldUnderTest);
    assertEquals("getAvailableMoves\n", 0, availableMoves.size());

    coordUnderTest = new Coord(5, 5);
    fieldUnderTest = new Field(coordUnderTest, Owner.FIRST);
    availableMoves = board.getAvailableMoves(fieldUnderTest);
    assertEquals("getAvailableMoves\n", 2, availableMoves.size());

    coordUnderTest = new Coord(12, 8);
    fieldUnderTest = new Field(coordUnderTest, Owner.FIRST);
    availableMoves = board.getAvailableMoves(fieldUnderTest);
    assertEquals("getAvailableMoves\n", 6, availableMoves.size());

    coordUnderTest = new Coord(19, 11);
    fieldUnderTest = new Field(coordUnderTest, Owner.FIRST);
    availableMoves = board.getAvailableMoves(fieldUnderTest);
    assertEquals("getAvailableMoves\n", 0, availableMoves.size());
  }

  @Test
  public void getOppositeTop() {
    Coord coordUnderTest = board.getOppositeTop(Owner.FIRST);

    assertEquals("getOppositeTop\n", 24, coordUnderTest.getX());
    assertEquals("getOppositeTop\n", 12, coordUnderTest.getY());
  }

  @Test
  public void makeMove() {
    //the corectness of movement is checked in isMoveCorrect();
    Coord begCoord = new Coord(0, 4);
    Coord endCoord = new Coord(12, 8);

    Movement movement = new Movement(begCoord, endCoord, Owner.FIRST);

    board.makeMove(movement);

    assertEquals("makeMove\n", 10, board.getOwnersPawns(Owner.FIRST).size());
    assertEquals("makeMove\n", 1, board.getField(endCoord));
  }

  @Test
  public void isMovementCorrect() {
    Coord begCoord = new Coord(6, 4);
    Coord endCoord = new Coord(8, 9);

    Movement movement = new Movement(begCoord, endCoord, Owner.FIRST);
    assertFalse(board.isMovementCorrect(movement));

    endCoord = new Coord(7, 5);
    movement = new Movement(begCoord, endCoord, Owner.FIRST);
    assertTrue(board.isMovementCorrect(movement));

    //skippings pawns in a line
    board.setField(8, 4, Owner.SECOND.getValue());
    board.setField(12, 4, Owner.SECOND.getValue());

    endCoord = new Coord(10, 4);
    movement = new Movement(begCoord, endCoord, Owner.FIRST);
    assertTrue(board.isMovementCorrect(movement));

    endCoord = new Coord(14, 4);
    movement = new Movement(begCoord, endCoord, Owner.FIRST);
    assertTrue(board.isMovementCorrect(movement));
  }

  @Test
  public void setPlayers() {
    List<Owner> owners = new ArrayList<>();
    owners.add(Owner.FIFTH);
    owners.add(Owner.THIRD);
    board.setPlayers(owners);

    assertEquals("setPlayers\n", 0, board.getOwnersPawns(Owner.FIRST).size());
    assertEquals("setPlayers\n", 10, board.getOwnersPawns(Owner.THIRD).size());
  }

  @Test
  public void hasWinner() {
    List<Owner> players = new ArrayList<>();
    players.add(Owner.FIRST);
    board.setPlayers(players);
    board.setField(21, 9, Owner.FIRST.getValue());
    board.setField(20, 10, Owner.FIRST.getValue());
    board.setField(22, 10, Owner.FIRST.getValue());
    board.setField(19, 11, Owner.FIRST.getValue());
    board.setField(21, 11, Owner.FIRST.getValue());
    board.setField(23, 11, Owner.FIRST.getValue());
    board.setField(18, 12, Owner.FIRST.getValue());
    board.setField(20, 12, Owner.FIRST.getValue());
    board.setField(22, 12, Owner.FIRST.getValue());
    assertEquals("HasWinner", 0, board.hasWinner());
    board.setField(24, 12, Owner.FIRST.getValue());
    assertEquals("hasWinner", 1, board.hasWinner());
  }

  @Test
  public void getOwnersPawns() {
    assertEquals("getOwnersPawns", 10, board.getOwnersPawns(Owner.FIRST).size());
  }
}