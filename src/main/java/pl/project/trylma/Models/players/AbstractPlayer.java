package pl.project.trylma.Models.players;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Move;
import pl.project.trylma.Models.Owner;
import pl.project.trylma.Models.boards.Board;
import pl.project.trylma.Models.pawn.IPawn;

import java.util.List;

public abstract class AbstractPlayer implements IPlayer {
  Owner id;               //enum: numer gracza
  Board board;            //instancja aktualnej mapy;

  private void setId(Owner id) {
    this.id = id;
  }

  AbstractPlayer(Owner id){
    //TODO: -pobiera instancje mapy z Board;
    //      -ustawia this.board mapą z getInstance();
    //      -Wywoluje setId(id);
  }

  //TODO: -return (Wywolujemy metode np. boolean board.sprawdzRuchDlaPionka(move.getFrom()))
  private boolean isMoveCorrect(Move move) {
    return false;
  }
}
