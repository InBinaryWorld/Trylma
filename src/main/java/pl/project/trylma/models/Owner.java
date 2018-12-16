package pl.project.trylma.models;

/**
 * Enum for easy recognizing players on board.
 * NONE value is associated to free coords.
 */
public enum Owner {
  NONE(7),
  FIRST(1),
  SECOND(2),
  THIRD(3),
  FOURTH(4),
  FIFTH(5),
  SIXTH(6);

  private final int value;
  Owner(int v) {
    this.value = v;
  }

  /**
   * @return
   * 1  - Owner.FIRST,
   * 2  - Owner.SECOND,
   * 3  - Owner.THIRD,
   * 4  - Owner.FOURTH,
   * 5  - Owner.FIFTH,
   * 6  - Owner.SIXTH,
   * 7  - Owner.NONE
   */
  public int getValue() {
    return value;
  }
}
