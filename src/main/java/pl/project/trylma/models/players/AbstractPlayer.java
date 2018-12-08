package pl.project.trylma.models.players;

import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.board.IBoard;

public abstract class AbstractPlayer implements IPlayer {
  Owner id;
  IBoard board;
  boolean isConnected = false;

  public Owner getId() {
    return id;
  }

  private void setId(Owner id) {
    this.id = id;
  }

  AbstractPlayer(Owner id){
    board=Board.getInstance();
    setId(id);
  }

  //TODO: -return (Wywolujemy metode np. boolean board.sprawdzRuchDlaPionka(move.getFrom()))
  private boolean isMoveCorrect(Movement move) {
    return false;
  }

  @Override
  public boolean isConnected() {
    return isConnected;
  }
}
