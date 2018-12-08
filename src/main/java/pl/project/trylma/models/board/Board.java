package pl.project.trylma.models.board;

import pl.project.trylma.models.Coord;
import pl.project.trylma.models.Field;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;

import java.util.ArrayList;
import java.util.List;

/**
 * Plansza powinna dobrze sprawdzac mozliwe ruchy,
 * rowniez te, ktore wymagaja przeskoczenia.
 * Jezeli dany pionek znajduje sie w swoim docelowym
 * ramieniu gwiazdy to nie powinien z niego juz wychodzic,
 * co jest zaimplementowane w funkcji getAvailable.
 *
 * Moze sie wyjebac cos, jak bedziesz sprawdzal mozliwe ruchy
 * dla pionkow, ktore sa na granicy tablicy (wierzcholki ramion)
 * Nie zaimplementowalem sprawdzania, czy nie wychodzi poza tablice. JESZCZE!
 *
 * Powinienem dodac jeszcze metode hasWinner(), wydaje mi sie ze to powinno
 * byc zaimplementowane w tej klasie.
 */
public final class Board implements IBoard {
  public static Board instance;
  private int [][] fields;

  private ArrayList<ArrayList<Coord>> finalCoordsFor;

  /**
   * Metody prywatne to szczegoly implementacji tworzenia planszy,
   * nie trzeba ich analizowac.
   */
  private void drawMap() {
    setTriangleShape(12, 0, -13);
    setTriangleShape(12, 16, 13);
  }

  private void setPawnsOnBoard(final int y, final int x) {
    findAndReplace(2, Owner.NONE.getValue(), 0, 26, 0, 17);
    findAndReplace(1, Owner.SECOND.getValue(), 8, 17, 0, 8);
    findAndReplace(1, Owner.THIRD.getValue(), 17, 26, 0, 8);
    findAndReplace(1, Owner.FOURTH.getValue(), 0, 7, 8, 17);
    findAndReplace(1, Owner.FIFTH.getValue(), 8, 17, 8, 17);
    findAndReplace(1, Owner.SIXTH.getValue(), 17, 26, 8, 17);
  }

  private void findAndReplace(int valToReplace, int replacement,
                              int xBeg, int xEnd, int yBeg, int yEnd) {
    for (int i = yBeg; i < yEnd; i++) {
      for (int j = xBeg; j < xEnd; j++) {
        if (this.fields[i][j] == valToReplace) {
          this.fields[i][j] = replacement;
        }
      }
    }
  }

  private Board() {
    createBoard();
  }

  public List<Coord> getFinalCoordsFor(Owner owner) {
    return finalCoordsFor.get(owner.getValue());
  }

  private void setTriangleShape(int x, int y, int levels) {
    if (levels < 0) {
      levels = Math.abs(levels);
      int leftBound = x;
      int rightBound = x;
      int arrWidth = this.fields[0].length;
      for (int i = y; i < levels; i++) {
        for (int j = 0; j < arrWidth; j++) {
          if (j == leftBound) {
            int k = j;
            for (k = j; k <= rightBound; k = k + 2) {
              this.fields[i][k] = this.fields[i][k] + 1;
            }
            j = k;
          }
        }
        leftBound = leftBound - 1;
        rightBound = rightBound + 1;
      }
    } else {
      int leftBound = x;
      int rightBound = x;
      int arrWidth = this.fields[0].length;
      for (int i = levels; i > 0; i--) {
        for (int j = 0; j < arrWidth; j++) {
          if (j == leftBound) {
            int k = j;
            for (k = j; k <= rightBound; k = k + 2) {
              this.fields[y][k] = this.fields[y][k] + 1;
            }
            j = k;
          }
        }
        leftBound = leftBound - 1;
        rightBound = rightBound + 1;
        y--;
      }
    }
  }

  private List<Coord> getNbhd(Field field) {
    List<Coord> result = new ArrayList<Coord>();
    for (int i = field.getY() - 1; i < field.getY() + 2; i++) {
      for (int j = field.getX() - 2; j < field.getX() + 3; j++) {
        if (fields[i][j] != 0 && !(i == field.getY() && j == field.getX())) {
          result.add(new Coord(j, i));
        }
      }
    }
    return result;
  }

  private List<Coord> onLine(Coord coords, int x, int y, boolean lookFor) {
    Coord coordsUnderTest = new Coord(coords.getX() + x, coords.getY() + y);
    List<Coord> result = new ArrayList<Coord>();
    if (lookFor == true) {//szukaj wolnych
      if (this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] == 7) {
        result.add(coordsUnderTest);
        List<Coord> temp = onLine(coordsUnderTest, x, y, false);
        if (temp != null) {
          result.addAll(temp);
        }
      } else {
        return null;
      }
    } else { //szukaj zajetych
      if (this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] != 7 &&
              this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] != 0) {
        List<Coord> temp = onLine(coordsUnderTest, x, y, true);
        if (temp != null) {
          result.addAll(temp);
        }
      }
    }
    return result;
  }

  private void setFinalCoords() {
    finalCoordsFor = new ArrayList<ArrayList<Coord>>();
    for (int i = 0; i < 7; i++) {
      finalCoordsFor.add(new ArrayList<Coord>());
    }
    for (int i = 0; i < 17; i++) {
      for (int j = 0; j < 26; j++) {
        switch (fields[i][j]) {
          case 1  : finalCoordsFor.get(6).add(new Coord(j, i));
                    break;
          case 2  : finalCoordsFor.get(5).add(new Coord(j, i));
                    break;
          case 3  : finalCoordsFor.get(4).add(new Coord(j, i));
                    break;
          case 4  : finalCoordsFor.get(3).add(new Coord(j, i));
                    break;
          case 5  : finalCoordsFor.get(2).add(new Coord(j, i));
                    break;
          case 6  : finalCoordsFor.get(1).add(new Coord(j, i));
                    break;
        }
      }
    }
  }

  private void releaseField(Coord coord) {
    setField(coord.getX(), coord.getY(), Owner.NONE.getValue());
  }

  /**
   * Zwraca dostepne pola dla danego pionka.
   * @param field   - koordy dla ktorych szukamy mozliwego ruchu
   * @return        - Lista mozliwych posuniec
   */
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
      if (field.getOwner().getValue() == i && contain(finalCoordsFor.get(i), field)) {
        result = commonPart(finalCoordsFor.get(i), result);
      }
    }
    return result;
  }

  private ArrayList<Coord> commonPart(ArrayList<Coord> b, ArrayList<Coord> a) {
    ArrayList<Coord> common = new ArrayList<Coord>();
    for (Coord c1 : b) {
      for (Coord c2 : a) {
        if (c1.getX() == c2.getX() &&
                c1.getY() == c2.getY()) {
          common.add(c1);
        }
      }
    }
    return common;
  }

  public void createBoard() {
    final int yDim = 17;
    final int xDim = 26;
    this.fields = new int[yDim][xDim];
    this.drawMap();
    this.setPawnsOnBoard(yDim, xDim);
    setFinalCoords();
  }

  private boolean contain(final ArrayList<Coord> ar, final Coord coord) {
    for (Coord crd : ar) {
      if (crd.getX() == coord.getX() &&
              crd.getY() == coord.getY()) {
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
   * Zwraca przeciwlegly wierzcholek
   * na podstawie przyjetych koordynat
   * @param owner - dla kogo szukac wierzcholka
   * @return      - zwraca wierzcholek.
   *
   * Wywolana dla nieprawidlowego argumentu
   * zwraca obiekt NULL;
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
   *
   * @param movement - obiekt typu Movement.
   * Zmienilem nazwe klasy na rzeczownik od slowa Move xD,
   * wszystko jest tak jak ustalilismy.
   */
  public void makeMove(Movement movement) {
    releaseField(movement.getFrom());
    setField(movement.getTo().getX(), movement.getTo().getY(), movement.getOwner().getValue());
  }

  @Override
  public void resetBoard() {

  }

  @Override
  public boolean isMovementCorrect(Movement movement) {
    return false;
  }

  public void setField(int x, int y, int var) {
    fields[y][x] = var;
  }

  /**
   * DEBUGGING
   */
  public void printAr() {
    System.out.println("    0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27\n");
    for (int i = 0; i < 17; i++) {
      if (i < 10) {
        System.out.print(i + "   ");
      } else {
        System.out.print(i + "  ");
      }
      for (int j = 0; j < 26; j++) {
        if (this.fields[i][j] == 0)
          System.out.print("   ");
        else
          System.out.print(this.fields[i][j]+"  ");
      }
      System.out.print("\n");
    }
  }
}