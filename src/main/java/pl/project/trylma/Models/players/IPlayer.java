package pl.project.trylma.Models.players;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Owner;

import java.util.List;

public interface IPlayer {
  void setId(Owner id);

  //TODO;
  Move makeMove(List<Coord> availableMoves);

  //TODO: czy ruch jest na liście;
  Boolean isMoveCorrect(Move move);

  //TODO;
  void endTurn();

  //TODO;
  void sendMessage();

}
