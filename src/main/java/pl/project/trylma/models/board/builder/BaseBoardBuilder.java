package pl.project.trylma.models.board.builder;

import pl.project.trylma.models.Owner;

public class BaseBoardBuilder implements BoardBuilder {
  private final int XSIZE = 27;
  private final int YSIZE = 18;

  int [][] fields;

  public BaseBoardBuilder() {
    fields = new int[YSIZE][XSIZE];
  }

  @Override
  public void setShape() {
    setTriangleShape(12, 0, -13);
    setTriangleShape(12, 16, 13);
  }

  @Override
  public void setPawns() {
    findAndReplace(2, Owner.NONE.getValue(), 0, 26, 0, 17);
    findAndReplace(1, Owner.SECOND.getValue(), 8, 17, 0, 8);
    findAndReplace(1, Owner.THIRD.getValue(), 17, 26, 0, 8);
    findAndReplace(1, Owner.FOURTH.getValue(), 0, 7, 8, 17);
    findAndReplace(1, Owner.FIFTH.getValue(), 8, 17, 8, 17);
    findAndReplace(1, Owner.SIXTH.getValue(), 17, 26, 8, 17);
  }

  @Override
  public Object build() {
    return this.fields;
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
}
