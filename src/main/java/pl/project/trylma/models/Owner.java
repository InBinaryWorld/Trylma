package pl.project.trylma.models;

import java.io.Serializable;

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
  public int getValue() {
    return value;
  }
}
