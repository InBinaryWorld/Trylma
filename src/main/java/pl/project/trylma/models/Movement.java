package pl.project.trylma.models;

public class Movement {
  private Field from;
  private Coord to;

  public Owner getOwner() {
    return from.getOwner();
  }

  public Coord getTo() {
    return to;
  }

  public Field getFrom() {
    return from;
  }

  public Movement(Coord from, Coord to, Owner owner) {
    this.from = new Field(from, owner);
    this.to = to;
  }
}
