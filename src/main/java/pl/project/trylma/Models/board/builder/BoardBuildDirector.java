package pl.project.trylma.models.board.builder;

public class BoardBuildDirector {
  BoardBuilder builder;

  public BoardBuildDirector(final BoardBuilder builder) {
    this.builder = builder;
  }

  public Object construct() {
    builder.setShape();
    builder.setPawns();
    return builder.build();
  }
}
