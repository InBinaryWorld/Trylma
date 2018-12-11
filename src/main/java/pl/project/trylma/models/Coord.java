package pl.project.trylma.models;

import java.io.Serializable;

public class Coord implements Serializable {
  static final long serialVersionUID = 8588980448693010399L;
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
