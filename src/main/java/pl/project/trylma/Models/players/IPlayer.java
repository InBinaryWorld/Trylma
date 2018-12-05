package pl.project.trylma.Models.players;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Move;
import pl.project.trylma.Models.Owner;

import java.util.List;

public interface IPlayer {

  Move makeMove();

  Owner getId();

  //TODO: wysyla komunikat o akcji ktora ma nastapic;
  void sendMessage(String command);
}
