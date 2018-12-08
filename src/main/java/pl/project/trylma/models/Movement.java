package pl.project.trylma.models;

/**
 * Dodalem pare metod potrzebnych w board.
 * Zmienilem nazwe z Move na Movement.
 */
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
