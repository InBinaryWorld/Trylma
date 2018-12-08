package pl.project.trylma.models;

/**
 * Enumy reprezentuja inty, to pomaga mi odnalezc sie na mapie.
 * Stad dodatkowe metody, ktorych nie bylo. Tobie nie powinno
 * to zmieniac niczego w strukturze. Jakby co to dzwon/pisz.
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
  private Owner(int v) {
    this.value = v;
  }
  public int getValue() {
    return value;
  }
}
