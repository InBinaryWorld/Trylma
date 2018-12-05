package pl.project.trylma.Models.board;

import pl.project.trylma.Models.Coord;

import java.util.ArrayList;
import java.util.List;


public final class Board implements IBoard {
  public static Board instance;
  private int [][] fields;

  /**
   * Metody prywatne to szczegoly implementacji tworzenia planszy,
   * nie trzeba ich analizowac.
   */
  private void drawMap() {
    setTriangleShape(12, 0, -13);
    setTriangleShape(12, 16, 13);
  }

  private void setPawnsOnBoard(final int y, final int x) {
    findAndReplace(1, 3, 0, 7, 0, 8);
    findAndReplace(1, 4, 8, 17, 0, 8);
    findAndReplace(1, 5, 17, 26, 0, 8);
    findAndReplace(1, 6, 0, 7, 8, 17);
    findAndReplace(1, 7, 8, 17, 8, 17);
    findAndReplace(1, 8, 17, 26, 8, 17);
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

  private List<Coord> getNbhd(Coord coord) {
    List<Coord> result = new ArrayList<Coord>();
    for (int i = coord.getY() - 1; i < coord.getY() + 2; i++) {
      for (int j = coord.getX() - 2; j < coord.getX() + 3; j++) {
        if (fields[i][j] != 0 && !(i == coord.getY() && j == coord.getX())) {
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
      if (this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] == 2) {
        result.add(coordsUnderTest);
        List<Coord> temp = onLine(coordsUnderTest, x, y, false);
        if (temp != null) {
          result.addAll(temp);
        }
      } else {
        return null;
      }
    } else { //szukaj zajetych
      if (this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] != 2 &&
              this.fields[coordsUnderTest.getY()][coordsUnderTest.getX()] != 0) {
        List<Coord> temp = onLine(coordsUnderTest, x, y, true);
        if (temp != null) {
          result.addAll(temp);
        }
      }
    }
    return result;
  }

  /**
   * Zwraca dostepne pola dla danego pionka.
   * Pierwsze wywolanie metody jako parametr lookFor musi miec ustawione 2.
   * Parametr jest konieczny dla rekurencyjnego rozwiazania problemu
   * @param coord   - koordy dla ktorych szukamy mozliwego ruchu
   * @param lookFor - Metoda wywolana na serwerze powinna miec ustawione "2" w tym miejscu
   * @return        - Lista mozliwych posuniec
   */
  public List<Coord> getAvailableMoves(Coord coord, int lookFor) {
    List<Coord> friends = getNbhd(coord);
    for(Coord coords : friends) {
      System.out.println(this.fields[coords.getY()][coords.getX()]);
    }
    //System.out.println("Wyswietlilem");
    List<Coord> result = new ArrayList<Coord>();
    for (Coord coords : friends) {
      if (this.fields[coords.getY()][coords.getX()] == 2) {
        System.out.println(this.fields[coords.getY()][coords.getX()]);
        result.add(coords);
      } else {
        List<Coord> temp = onLine(coords,
                coords.getX() - coord.getX(),
                coords.getY() - coord.getY(),
                true);
        if (temp != null) {
          result.addAll(temp);
        }
      }
    }
    return result;
  }

  public void createBoard() {
    final int yDim = 17;
    final int xDim = 26;
    this.fields = new int[yDim][xDim];
    this.drawMap();
    this.setPawnsOnBoard(yDim, xDim);
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
   * @param coord - dla kogo szukac wierzcholka
   * @return      - zwraca wierzcholek.
   *
   * Wywolana dla nieprawidlowego argumentu
   * zwraca obiekt NULL;
   */
  public Coord getOppositeTop(Coord coord) {
    int x = coord.getX();
    int y = coord.getY();
    int owner = fields[y][x];
    switch (owner) {
      case 3 : return new Coord(24, 12);
      case 4 : return new Coord(12, 16);
      case 5 : return new Coord(0, 12);
      case 6 : return new Coord(24, 4);
      case 7 : return new Coord(12, 0);
      case 8 : return new Coord(0, 4);
    }
    return null;
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

  public void setField(int x, int y, int var) {
    fields[y][x] = var;
  }
}