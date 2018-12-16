package pl.project.trylma.models;

import java.io.Serializable;

/**
 * Gives easy to use interface for sending
 * moves to server and back
 */
public class Movement implements Serializable {
  static final long serialVersionUID = 1588980448693010399L;
  private Field from;
  private Coord to;

  /**
   * .
   * @return enum owner associated to player,
   *         which made move
   */
  public Owner getOwner() {
    return from.getOwner();
  }

  /**
   * .
   * @return coordinates which player move to
   */
  public Coord getTo() {
    return to;
  }

  /**
   * .
   * @return coordinates which player move from
   */
  public Field getFrom() {
    return from;
  }

  /**
   * sets information about movement.
   * @param from - starting movement position
   * @param to   - ending movement position
   * @param owner- who made move
   */
  public Movement(Coord from, Coord to, Owner owner) {
    this.from = new Field(from, owner);
    this.to = to;
  }
}
