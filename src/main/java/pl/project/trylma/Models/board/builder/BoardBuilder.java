package pl.project.trylma.models.board.builder;

public interface BoardBuilder {
  /** sets shape of game map */
  void setShape();
  /** sets pawns on board */
  void setPawns();
  Object build();
}
