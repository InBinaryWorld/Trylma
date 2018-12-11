package pl.project.trylma.models;

public class Coord {
  private int x;
  private int y;

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coord(Coord coord) {
    this.x = coord.getX();
    this.y = coord.getY();
  }
}
