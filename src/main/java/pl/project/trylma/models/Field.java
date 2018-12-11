package pl.project.trylma.models;

import java.io.Serializable;

public class Field extends Coord{
  private Owner owner;

  public Owner getOwner() {
    return owner;
  }

  public Field(Coord coord, Owner owner) {
    super(coord);
    this.owner = owner;
  }
}
