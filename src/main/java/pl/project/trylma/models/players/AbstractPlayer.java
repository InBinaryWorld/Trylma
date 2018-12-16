package pl.project.trylma.models.players;

import pl.project.trylma.models.Owner;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.board.IBoard;

public abstract class AbstractPlayer implements IPlayer {
  Owner id;
  IBoard board;

  /**
   * @return player's id.
   */
  public Owner getId() {
    return id;
  }

  private void setId(Owner id) {
    this.id = id;
  }

  AbstractPlayer(Owner id) {
    board = Board.getInstance();
    setId(id);
  }

}
