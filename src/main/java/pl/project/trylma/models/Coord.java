package pl.project.trylma.models;

import java.io.Serializable;

/**
 * Representation of two integers,
 * which describes postion on map.
 */
public class Coord implements Serializable {
  static final long serialVersionUID = 8588980448693010399L;
  private int x;
  private int y;

  /**
   * .
   * @return horizontal position in array
   */
  public int getX() {
    return x;
  }

  /**
   * .
   * @return vertical position in array
   */
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
