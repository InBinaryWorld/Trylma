package pl.project.trylma.models;

public class Field extends Coord {
  private Owner owner;

  public Owner getOwner() {
    return owner;
  }

  public Field(Coord coord, Owner owner) {
    super(coord);
    this.owner = owner;
  }
}
