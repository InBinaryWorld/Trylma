package pl.project.trylma.models.players;

import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;

public interface IPlayer {

  Movement makeMove();

  Owner getId();

  void sendMessage(String command);

  void sendMove(Movement movement);

  void endGame(Owner winner);
}
