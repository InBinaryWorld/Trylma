package pl.project.trylma.models.board;

import pl.project.trylma.models.Coord;
import pl.project.trylma.models.Field;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.board.builder.BaseBoardBuilder;
import pl.project.trylma.models.board.builder.BoardBuildDirector;
import pl.project.trylma.models.board.builder.BoardBuilder;

import java.util.ArrayList;
import java.util.List;

public final class Board implements IBoard {
  public static Board instance;
  /**Board representation as two dim int map.*/
  private int [][] fields;
  /**List of players in current game.*/
  private List<Owner> list;
  /**set of coords every player want get.*/
  private ArrayList<ArrayList<Coord>> finalCoords;


  private Board() {
    createBoard();
  }


  private void createBoard() {
    final BoardBuilder builder = new BaseBoardBuilder();
    final BoardBuildDirector director = new BoardBuildDirector(builder);
    this.fields = ((int [][]) director.construct());
    setFinalCoords();
  }

  /**sets values for finalCoord attribute.*/
  private void setFinalCoords() {
    finalCoords = new ArrayList<ArrayList<Coord>>();
    for (int i = 0; i < 7; i++) {
      finalCoords.add(new ArrayList<Coord>());
    }
    for (int i = 0; i < 17; i++) {
      for (int j = 0; j < 26; j++) {
        switch (fields[i][j]) {
          case 1  : finalCoords.get(6).add(new Coord(j, i));
            break;
          case 2  : finalCoords.get(5).add(new Coord(j, i));
            break;
          case 3  : finalCoords.get(4).add(new Coord(j, i));
            break;
          case 4  : finalCoords.get(3).add(new Coord(j, i));
            break;
          case 5  : finalCoords.get(2).add(new Coord(j, i));
            break;
          case 6  : finalCoords.get(1).add(new Coord(j, i));
            break;
        }
      }
    }
  }

  /**
   * Returns list of six coords, which are
   * closest neighbourhood for specified field.
   * @param field - pawn for searching neighbourhood
   * @return - list of coords
   */
  private List<Coord> getNbhd(Field field) {
    List<Coord> result = new ArrayList<Coord>();
    for (int i = field.getY() - 1; i < field.getY() + 2; i++) {
      for (int j = field.getX() - 2; j < field.getX() + 3; j++) {
        try {
          if (fields[i][j] != 0 && !(i == field.getY() && j == field.getX())) {
            result.add(new Coord(j, i));
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          continue;
        }
      }
    }
    return result;
  }

  /**
   * Finds all available fields to go to
   * skipping occupied fields.
   * The direction is designated by vector.
   * @param coords - entry point of finding available fields
   * @param x - vertical of vector
   * @param y - horizontal of vector
   * @param lookFor - used for specify which field (occupied/free)
   *                now it should look for (reccursion).
   * @return - list of available coords to jump to
   */
  private List<Coord> onLine(Coord coords, int x, int y, boolean lookFor) {
    Coord coordsUnderTest = new Coord(coords.getX() + x, coords.getY() + y);
    List<Coord> result = new ArrayList<Coord>();
    if (lookFor == true) { //szukaj wolnych
      try {
        if (this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] == 7) {
          result.add(coordsUnderTest);
          List<Coord> temp = onLine(coordsUnderTest, x, y, false);
          if (temp != null) {
            result.addAll(temp);
          }
        } else {
          return null;
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        return null;
      }
    } else { //szukaj zajetych
      try {
        if (this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] != 7
                && this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] != 0) {
          List<Coord> temp = onLine(coordsUnderTest, x, y, true);
          if (temp != null) {
            result.addAll(temp);
          }
        }
      } catch (ArrayIndexOutOfBoundsException e) { ; }
    }
    return result;
  }

  /**
   * sets specified coord as free.
   * @param coord - coord to release
   */
  private void releaseField(Coord coord) {
    setField(coord.getX(), coord.getY(), Owner.NONE.getValue());
  }

  /**
   * Returns available coords for pawn specified by field
   * to go to.
   * @param field - pawn
   * @return - available moves
   */
  @Override
  public List<Coord> getAvailableMoves(Field field) {
    List<Coord> friends = getNbhd(field);
    ArrayList<Coord> result = new ArrayList<Coord>();
    for (Coord coords : friends) {
      if (this.fields[coords.getY()][coords.getX()] == 7) {
        result.add(coords);
      } else {
        List<Coord> temp = onLine(coords,
                coords.getX() - field.getX(),
                coords.getY() - field.getY(),
                true);
        if (temp != null) {
          result.addAll(temp);
        }
      }
    }
    for (int i = 1; i < 7; i++) {
      if (field.getOwner().getValue() == i && contain(finalCoords.get(i), field)) {
        result = commonPart(finalCoords.get(i), result);
      }
    }
    return result;
  }


  /**Makes part of job in searching availables moves.*/
  private ArrayList<Coord> commonPart(ArrayList<Coord> b, ArrayList<Coord> a) {
    ArrayList<Coord> common = new ArrayList<Coord>();
    for (Coord c1 : b) {
      for (Coord c2 : a) {
        if (c1.getX() == c2.getX()
                && c1.getY() == c2.getY()) {
          common.add(c1);
        }
      }
    }
    return common;
  }

  /**Makes part of job in searching available moves.*/
  private boolean contain(final ArrayList<Coord> ar, final Coord coord) {
    for (Coord crd : ar) {
      if (crd.getX() == coord.getX()
              && crd.getY() == coord.getY()) {
        return true;
      }
    }
    return false;
  }


  public static Board getInstance() {
    if (instance == null) {
      instance = new Board();
    }
    return instance;
  }


  /**
   * Returns opposite top of star-shape mape.
   * @param owner - for which player return the top
   * @return      - returns top coords. If bad arg returns null
   */
  public Coord getOppositeTop(Owner owner) {
    switch (owner.getValue()) {
      case 1 : return new Coord(24, 12);
      case 2 : return new Coord(12, 16);
      case 3 : return new Coord(0, 12);
      case 4 : return new Coord(24, 4);
      case 5 : return new Coord(12, 0);
      case 6 : return new Coord(0, 4);
    }
    return null;
  }

  /**
   * Makes move on map by releasing field and occuping other.
   * @param movement - information about move
   */
  public void makeMove(Movement movement) {
    releaseField(movement.getFrom());
    setField(movement.getTo().getX(), movement.getTo().getY(), movement.getOwner().getValue());
  }


  public static void resetBoard() {
    instance = new Board();
  }

  /**
   * Checks whether the movement follows the game rules.
   * @param movement - information about move
   * @return - boolean value (true if correct)
   */
  @Override
  public boolean isMovementCorrect(Movement movement) {
    Field testedField = movement.getFrom();
    Coord to = movement.getTo();
    List<Coord> list = getAvailableMoves(testedField);
    for (Coord coord : list) {
      if (coord.getX() == to.getX() && coord.getY() == to.getY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets players on board following game rules.
   * @param list - list of player
   */
  @Override
  public void setPlayers(List<Owner> list) {
    this.list = list;
    List<Owner> ownerList = new ArrayList<>();
    ownerList.add(Owner.FIRST);
    ownerList.add(Owner.SECOND);
    ownerList.add(Owner.THIRD);
    ownerList.add(Owner.FOURTH);
    ownerList.add(Owner.FIFTH);
    ownerList.add(Owner.SIXTH);
    for (int i = 0; i < 6; i++) {
      if (!list.contains(ownerList.get(i))) {
        for (int j = 0; j < 17; j++) {
          for (int k = 0; k < 26; k++) {
            if (fields[j][k] == ownerList.get(i).getValue()) {
              fields[j][k] = Owner.NONE.getValue();
            }
          }
        }
      }
    }
  }

  /**
   * Check if game has Winner.
   * @return value assignated for player
   *         or 0 if there is no winner
   */
  @Override
  public int hasWinner() {
    final int numOfPawns = 10;
    int checker = 0;
    for (Owner owner : list) {
      List<Coord> pawns = getOwnersPawns(owner);
      for (Coord finalCoord : finalCoords.get(owner.getValue())) {
        for (Coord actualCoord : pawns) {
          if (actualCoord.getX() == finalCoord.getX()
                  && actualCoord.getY() == finalCoord.getY()) {
            checker++;
          }
        }
      }
      if (checker == numOfPawns) {
        return owner.getValue();
      }
      checker = 0;
    }
    return 0;
  }

  /**.
   * @param owner - player
   * @return - list of coords occupied by owner.
   */
  @Override
  public List<Coord> getOwnersPawns(Owner owner) {
    List<Coord> result = new ArrayList<>();
    for (int i = 0; i < 17; i++) {
      for (int j = 0; j < 26; j++) {
        if (fields[i][j] == owner.getValue()) {
          result.add(new Coord(j, i));
        }
      }
    }
    return result;
  }

  /**
   * gives two dimensional, integer, board representaton.
   * of current game
   * @return two dimensional array associated with game
   */
  @Override
  public int[][] getFields() {
    return fields;
  }

  /**
   * puts value on specified position on board,
   * mainly for debugging.
   */
  void setField(int x, int y, int var) {
    fields[y][x] = var;
  }

  /**
   * returns value of specified field by coord.
   */
  int getField(Coord coord) {
    return fields[coord.getY()][coord.getX()];
  }

  /**
   * debugging purposes.
   */
  public void printAr() {
    System.out.println("    0  1  2  3  4  5  6  7  8  9  10 11 " +
            "12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27\n");
    for (int i = 0; i < 17; i++) {
      if (i < 10) {
        System.out.print(i + "   ");
      } else {
        System.out.print(i + "  ");
      }
      for (int j = 0; j < 26; j++) {
        if (this.fields[i][j] == 0) {
          System.out.print("   ");
        }
        else {
          System.out.print(this.fields[i][j] + "  ");
        }
      }
      System.out.print("\n");
    }
  }
}