package pl.project.trylma.models;

import java.io.Serializable;

public class Movement implements Serializable {
  static final long serialVersionUID = 1588980448693010399L;
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
