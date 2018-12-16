package pl.project.trylma.models;

import java.io.Serializable;

/**
 * Extending coord by adding owner property,
 * which ascribes to each field owner or set
 * field as free
 */
public class Field extends Coord implements Serializable{
  static final long serialVersionUID = 5588980448693010399L;
  private Owner owner;

  /**
   * .
   * @return owner of pawn as enum.
   */
  public Owner getOwner() {
    return owner;
  }

  /**
   * ascribe coord to owner.
   * @param coord - coordinates
   * @param owner - enum owner
   */
  public Field(Coord coord, Owner owner) {
    super(coord);
    this.owner = owner;
  }
}
